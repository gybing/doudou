package mayaya.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mayaya.system.CacheManager;
import mayaya.system.MayayaBackend;
import mayaya.util.MayayaConfig;
import mayaya.util.tool.DateUtil;
import mayaya.util.tool.ImageUtil;
import mayaya.vo.Picture;
import mayaya.vo.User;
import mayaya.vo.model.PicPublishTask;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

public class UploadServlet extends HttpServlet {
	// default maximum allowable file size is 1000k
	static final int MAX_SIZE = 1024000;
	
	private Logger logger = Logger.getLogger(getClass());
	private static final long serialVersionUID = 1068590804829697704L;
	private String savePath;// 保存的路径

	public void init(ServletConfig config) {
		savePath = config.getInitParameter("file-upload");
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
		Object[] whoToPush = null;
		String photoCaption = "";
		int userId = (Integer)request.getAttribute("userId");
		logger.info("UploadServlet ---> userId = " + userId);
		
		// 设置编码格式
		request.setCharacterEncoding("UTF-8");
		// 使用FileItemFactory创建新的文件项目
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// FileUpload用来解析request文件上传请求
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			
			// 获取请求的信息存入列表list中
			List tempList = upload.parseRequest(request);

			Iterator it = tempList.iterator();
			while (it.hasNext()) {
				FileItem item = (FileItem) it.next();
				// 判断items中的文本信息
				if (item.isFormField()) {
					String jsonString = item.getString("UTF-8");
					JSONObject object = JSONObject.fromObject(jsonString);
					whoToPush = object.getJSONArray("whoToPush").toArray();
					photoCaption = object.getString("photoCaption");
					logger.info(String.format("whotoPush : %s, Caption : %s",whoToPush,photoCaption));
					
				} else {
					if (item.getName() != null && !item.getName().equals("")) {
						Date now = new Date();
						logger.debug(String.format("文件名称：%s, 文件大小：%d, 文件类别：%s",item.getName(),item.getSize(),item.getContentType()));
						
						int pointIndex = item.getName().lastIndexOf(".");
						String poster = item.getName().substring(pointIndex, item.getName().length());
						
						// 用于获取file中的文件名（不包含路径）
						String picName = "Pic_" + DateUtil.getInstance().getDetailStringFromDate(now) + poster;
						File tempFile = new File(picName);
						// 建立文件内容
						File file = new File(MayayaConfig.getConfig().getUploadImgPath() + savePath,
								tempFile.getName());
						logger.debug(String.format("文件地址：%s", file.toString()));
						// 将文件上传至服务器
						item.write(file);
						
						MayayaBackend.getInstance().addImageToQueue(file.toString());
						
						PicPublishTask task = new PicPublishTask();
						Picture pic = new Picture();
						pic.setUserId(userId);
						pic.setDescription(photoCaption);
						pic.setPictureURL(savePath + "/" + tempFile.getName());
						pic.setPublishTime(now);
						task.setPicture(pic);
						
						List<Integer> childIdList = new ArrayList<Integer>();
						for (Object o : whoToPush) {
							childIdList.add((Integer)o);
						}
						task.setChildrenList(childIdList);
						MayayaBackend.getInstance().publishTask(task);
						CacheManager.getInstance().putPic(userId);
					} else {
						logger.info("没有选择文件！");
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e,e);
			logger.error("上传文件失败！");
			response.getOutputStream().print(" 上传文件失败！");
		}
	}
	
	

}