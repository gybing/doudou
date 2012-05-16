package mayaya.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.service.ChildService;
import mayaya.service.impl.ChildServiceImpl;
import mayaya.util.MayayaConfig;
import mayaya.util.tool.DateUtil;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class UploadCoverServlet extends HttpServlet {
	// default maximum allowable file size is 1000k
	static final int MAX_SIZE = 1024000;
	
	private Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1068590804829697704L;
	private ServletContext sc;// 获取设备上下文对象
	private String savePath;// 保存的路径
	private ChildService childService;
	
	public void init(ServletConfig config) {
		savePath = config.getInitParameter("file-upload");
		sc = config.getServletContext();
		childService = ChildServiceImpl.getInstance();
	}
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, ServletException, IOException {
		int childId = 0;
		request.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			
			List tempList = upload.parseRequest(request);

			Iterator it = tempList.iterator();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				// 判断items中的文本信息
				if (item.isFormField()) {
					String jsonString = item.getString("GBK");
					JSONObject object = JSONObject.fromObject(jsonString);
					// TODO
					childId = object.getInt("childId");
					System.out.println("表单参数的名称" + item.getFieldName()
							+ "表单的参数值" + item.getString("GBK"));
					
				} else {
					if (item.getName() != null && !item.getName().equals("")) {
						Date now = new Date();
						logger.debug(String.format("文件名称：%s, 文件大小：%d, 文件类别：%s",item.getName(),item.getSize(),item.getContentType()));
						
						int pointIndex = item.getName().lastIndexOf(".");
						String poster = item.getName().substring(pointIndex, item.getName().length());
						System.out.println("poster = " + poster);
						System.out.println("所上传的文件名称：" + item.getName());
						System.out.println("所上传的文件大小：" + item.getSize());
						System.out.println("所上传的文件类别：" + item.getContentType());
						
						// 用于获取file中的文件名（不包含路径）
						String picName = "Cover_" + DateUtil.getInstance().getDetailStringFromDate(now) + poster;
						File tempFile = new File(picName);
						// 建立文件内容
						File file = new File(MayayaConfig.getConfig().getUploadImgPath() + savePath,
								tempFile.getName());
						System.out.println(file.toString());
						logger.debug(String.format("文件地址：%s", file.toString()));
						// 将文件上传至服务器
						item.write(file);
						request.setAttribute("upload.message",
								"上传文件成功！" + item.getName() + item.getSize()
										+ item.getContentType());
						childService.updateCover(childId,savePath+"/"+tempFile.getName());
						
					} else {
						request.setAttribute("upload.message", "没有选择文件！");
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传文件失败！");
			request.setAttribute("upload.message", " 上传文件失败！");
		}
		
	}


}