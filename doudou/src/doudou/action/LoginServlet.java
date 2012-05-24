package doudou.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import doudou.service.*;
import doudou.util.BaseServlet;
import doudou.util.Constants;
import doudou.util.tool.Base64;
import doudou.vo.User;
import doudou.vo.model.SessionData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/WebLogin")
public class LoginServlet extends BaseServlet{

	@Autowired
	UserService userService;
	@Autowired
	DoudouService doudouService;
	
	/** 设定被随机选取的字符,用于生成验证码 */
	private static final String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	/** 验证码保存在session中的名称 */
	private static final String CODE_SESSION_NAME = "identifyCode";
	
	@RequestMapping("/getIdentifyCode")
	public void getIdentifyCode(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		// 在此处 设置JSP页面无缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		// 设置图片的长宽
		int width = 130;
		int height = 30;
		// 设置 备选随机字符的个数
		int length = base.length();
		// 创建缓存图像
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// 获取图像
		Graphics g = image.getGraphics();
		// 创建随机函数的实例
		Random random = new Random();
		// 此处 设定图像背景色
		g.setColor(getRandColor(random, 188, 235));
		g.fillRect(0, 0, width, height);
		// 设置随机 备选的字体类型
		String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53",
				"\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
		int fontTypesLength = fontTypes.length;
		// 在图片背景上增加噪点，增加图片分析难度
		g.setColor(getRandColor(random, 180, 199));
		g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		for (int i = 0; i < 4; i++) {
			g.drawString("@*@*@*@*@*@*@*", 0, 5 * (i + 2));
		}
		// 取随机产生的验证码 (4 个汉字 )
		// 保存生成的字符串
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			int start = random.nextInt(length);
			String rand = base.substring(start, start + 1);
			sRand += rand.toLowerCase();
			// 设置图片上字体的颜色
			g.setColor(getRandColor(random, 10, 150));
			// 设置字体格式
			g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],
					Font.BOLD, 18 + random.nextInt(6)));
			// 将此字符画到验证图片上面
			g.drawString(rand, 24 * i + 10 + random.nextInt(8), 24);
		}
		// 将验证码存入S ession中
		session.setAttribute(CODE_SESSION_NAME, sRand);
		g.dispose();
		// 将 图象输出到JSP页面中
		ImageIO.write(image, "JPEG", response.getOutputStream());
	}

	private Color getRandColor(Random random, int ff, int cc) {
		if (ff > 255)
			ff = 255;
		if (cc > 255)
			cc = 255;
		int r = ff + random.nextInt(cc - ff);
		int g = ff + random.nextInt(cc - ff);
		int b = ff + random.nextInt(cc - ff);
		return new Color(r, g, b);
	}
	
	/**
	 * 检验验证码填写是否正确，并冲session中移除验证码
	 */
	public static boolean validateIdentifyCode(HttpSession session, String code) {

		System.out.println("code in session:"+session.getAttribute(CODE_SESSION_NAME)+" input code:"+code);
		if (session.getAttribute(CODE_SESSION_NAME) == null)
			return false;
		String identifyCode = session.getAttribute(CODE_SESSION_NAME)
				.toString();
		if (identifyCode.equals(""))
			return false;
		if (!identifyCode.equals(code.toLowerCase()))
			return false;
		session.removeAttribute(CODE_SESSION_NAME);
		return true;
	}
	
	@RequestMapping("login")
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	String userName = getStringParameter(request, "username");
    	String passWd = getStringParameter(request, "password");
    	String code = getStringParameter(request, "code");
    	int veriResult = 0;
    	HttpSession session = request.getSession();
		if (!validateIdentifyCode(session, code)) {
			veriResult = -2;
		}
		else{
			User user = userService.verifyUserNamePwd(userName, passWd);
	    	if (null != user) {
	    		String token = doudouService.getToken(user);
				Cookie cookie = new Cookie(Constants.DOUDOU_TICKET,token);
				cookie.setMaxAge(3600);
				cookie.setPath("/");
				response.addCookie(cookie);
				
				veriResult = 1;
				
				SessionData sessionData = doudouService.getSessionData(user);
	    		
	    		request.getSession(true).setAttribute("sessionData", sessionData);
			} else {
				veriResult = -1;
			}
		}
    	
    	response.getOutputStream().print(veriResult);
    }
	
}
