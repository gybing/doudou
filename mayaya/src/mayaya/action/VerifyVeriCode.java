package mayaya.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import mayaya.service.UserService;
import mayaya.service.impl.UserServiceImpl;
import mayaya.system.MayayaBackend;
import mayaya.util.BaseServlet;
import mayaya.util.tool.Base64;
import mayaya.vo.Child;
import mayaya.vo.User;
import mayaya.vo.model.VeriCodeInfo;
import mayaya.vo.model.VeriCodeVO;

/**
 * Servlet implementation class VerifyVeriCode
 */
public class VerifyVeriCode extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService;   
	private Logger logger = Logger.getLogger(getClass());
	
    /**
     * @see BaseServlet#BaseServlet()
     */
    public VerifyVeriCode() {
        super();
        userService = UserServiceImpl.getInstance();
    }
    
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    	String veriCode = request.getParameter("VeriCode");
    	String telephone = getStringParameter(request, "telephone","15201167380");
    	response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        VeriCodeVO result = new VeriCodeVO();

    	boolean isContained = MayayaBackend.getInstance().isContainedVeriCode(veriCode);
    	logger.warn(String.format("Somebody verify the code : %s and the result is %s", veriCode, isContained));
    	if (isContained) {
    		VeriCodeInfo veriCodeInfo = MayayaBackend.getInstance().getUserByVeriCode(veriCode);
    		if (!telephone.equals(veriCodeInfo.getTelephone())) {
    			result.setVerifyResult("notMatched");
    			logger.info(String.format("VeriCode: %s and the telephone: %s not matched! the correct Tele:%s",veriCode,telephone,veriCodeInfo.getTelephone()));
			} else {
	    		User user = userService.getUserById(veriCodeInfo.getUserId());
	    		logger.info(user);
	    		String tokenString = user.getUserId() + user.getLogin();
				String token = Base64.encode(tokenString.getBytes());
				List<Child> childList = userService.getChildListByUserId(user.getUserId());
				result.setVerifyResult("SUCCESS");
	    		result.setToken(token);
	    		result.setUser(user);
	    		result.setChildList(childList);
			}
		} else {
			result.setVerifyResult("notContained");
		}
    	JSONObject object = JSONObject.fromObject(result);
    	writer.print(object);
    }

}
