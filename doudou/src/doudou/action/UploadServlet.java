package doudou.action;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import doudou.system.DoudouBackend;
import doudou.util.BaseServlet;
import doudou.util.DoudouConfig;
import doudou.util.tool.DateUtil;
import doudou.vo.Event;
import doudou.vo.Message;
import doudou.vo.Picture;
import doudou.vo.User;
import doudou.vo.model.EvtPublishTask;
import doudou.vo.model.MessagePubTask;
import doudou.vo.model.PicPublishTask;
import doudou.vo.model.SessionData;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/Upload")
public class UploadServlet extends BaseServlet{
	
	private Logger logger = Logger.getLogger(getClass());
	private String savePath = "upload";
//	@Autowired
//	private ChildService childService;
//	@Autowired
//	private CommentService commentService;
//	@Autowired
//	private PictureService picService;
	
	@RequestMapping("/Photo")
	public void uploadPhoto(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, ServletException, IOException {
		Object[] whoToPush = null;
		String photoCaption = "";
		User user = getUser(request);
		int userId = user.getId();
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
						String dirPath = DoudouConfig.getConfig().getUploadImgPath() + DateUtil.getInstance().toYearMonthString(now);
						File dir = new File(dirPath);
						if (!dir.isDirectory()) {
							dir.mkdir();
						}
						
						logger.info(String.format("文件名称：%s, 文件大小：%d, 文件类别：%s",item.getName(),item.getSize(),item.getContentType()));
						
						int pointIndex = item.getName().lastIndexOf(".");
						String poster = item.getName().substring(pointIndex, item.getName().length());
						
						// 用于获取file中的文件名（不包含路径）
						String picName = "Pic_" + userId + "_" + DateUtil.getInstance().getDetailStringFromDate(now) + poster;
						File tempFile = new File(picName);
						// 建立文件内容
						File file = new File(DoudouConfig.getConfig().getUploadImgPath() ,
								tempFile.getName());
						logger.debug(String.format("文件地址：%s", file.toString()));
						// 将文件上传至服务器
						item.write(file);
						
						DoudouBackend.getInstance().addImageToQueue(file.toString());
						
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
						//task.setChildrenList(childIdList);
						DoudouBackend.getInstance().publishTask(task);
					} else {
						logger.info("没有选择文件！");
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e);
			logger.error("上传文件失败！");
			response.getOutputStream().print(" 上传文件失败！");
		}
	}
	
	@RequestMapping("/Message")
	public void uploadNotification(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = getUser(request);
    	
    	int userId = user.getId();
		Object[] whoToPush = null;
		
		String jsonString = request.getParameter("jsonString");
		
		JSONObject object = JSONObject.fromObject(jsonString);
		whoToPush = object.getJSONArray("whoToPush").toArray();
		System.out.println(whoToPush);
		String content = object.getString("content");
		System.out.println("content = " + content);
		
		Message noti = new Message();
		noti.setContent(content);
		noti.setUserId(userId);
		//TODO
		//noti.setTitle(title)
		
		MessagePubTask task = new MessagePubTask();
		
		task.setMessage(noti);
		
		List<Integer> childIdList = new ArrayList<Integer>();
		for (Object o : whoToPush) {
			childIdList.add(Integer.parseInt((String)o));
		}
		//task.setChildrenList(childIdList);
		DoudouBackend.getInstance().publishTask(task);
    }
	
//	@RequestMapping("/HeadPic")
//	public void uploadHeadPic(HttpServletRequest request, HttpServletResponse response)
//			throws UnsupportedEncodingException, ServletException, IOException {
//		int childId = 0;
//		request.setCharacterEncoding("GBK");
//		// 使用FileItemFactory创建新的文件项目
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		// FileUpload用来解析request文件上传请求
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		try {
//
//			// 获取请求的信息存入列表list中
//			List tempList = upload.parseRequest(request);
//
//			Iterator it = tempList.iterator();
//			while (it.hasNext()) {
//				FileItem item = (FileItem) it.next();
//				// 判断items中的文本信息
//				if (item.isFormField()) {
//					String jsonString = item.getString("GBK");
//					JSONObject object = JSONObject.fromObject(jsonString);
//					// TODO
//					childId = object.getInt("childId");
//					System.out.println("表单参数的名称" + item.getFieldName()
//							+ "表单的参数值" + item.getString("GBK"));
//
//				} else {
//					if (item.getName() != null && !item.getName().equals("")) {
//						Date now = new Date();
//						logger.debug(String.format("文件名称：%s, 文件大小：%d, 文件类别：%s",
//								item.getName(), item.getSize(),
//								item.getContentType()));
//
//						int pointIndex = item.getName().lastIndexOf(".");
//						String poster = item.getName().substring(pointIndex,
//								item.getName().length());
//						System.out.println("poster = " + poster);
//						System.out.println("所上传的文件名称：" + item.getName());
//						System.out.println("所上传的文件大小：" + item.getSize());
//						System.out.println("所上传的文件类别：" + item.getContentType());
//
//						// 用于获取file中的文件名（不包含路径）
//						String picName = "Head_"
//								+ DateUtil.getInstance()
//										.getDetailStringFromDate(now) + poster;
//						File tempFile = new File(picName);
//						// 建立文件内容
//						File file = new File(MayayaConfig.getConfig()
//								.getUploadImgPath() + savePath,
//								tempFile.getName());
//						System.out.println(file.toString());
//						logger.debug(String.format("文件地址：%s", file.toString()));
//						// 将文件上传至服务器
//						item.write(file);
//						request.setAttribute("upload.message",
//								"上传文件成功！" + item.getName() + item.getSize()
//										+ item.getContentType());
//
//						MayayaBackend.getInstance().addHeadPicToQueue(
//								file.toString());
//						childService.updateHeadPic(childId, savePath + "/"
//								+ tempFile.getName());
//					} else {
//						request.setAttribute("upload.message", "没有选择文件！");
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("上传文件失败！");
//			request.setAttribute("upload.message", " 上传文件失败！");
//		}
//
//	}
//
	@RequestMapping("/Event")
	public void uploadEvent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = getUser(request);
    	
    	int userId = user.getId();
		Object[] whoToPush = null;
		
		String jsonString = request.getParameter("jsonString");
		
		JSONObject object = JSONObject.fromObject(jsonString);
		whoToPush = object.getJSONArray("whoToPush").toArray();
		
		String content = object.getString("content");
		System.out.println("content = " + content);
		String beginTimeString = object.getString("beginTimeString");
		System.out.println("beginTimeString = " + beginTimeString);
		String title = object.getString("title");
		System.out.println("title = " + title);
		String endTimeString = object.getString("endTimeString");
		System.out.println("endTimeString = " + endTimeString);
		String location  = object.getString("location");
		System.out.println("location = " + location);
		
		Event event = new Event();
		event.setContent(content);
		event.setBeginTime(DateUtil.getInstance().fromString(beginTimeString));
		event.setEndTime(DateUtil.getInstance().fromString(endTimeString));
		event.setTitle(title);
		event.setLocation(location);
		event.setUserId(userId);
		
		EvtPublishTask task = new EvtPublishTask();
		
		task.setEvent(event);
		
		List<Integer> childIdList = new ArrayList<Integer>();
		for (Object o : whoToPush) {
			childIdList.add((Integer)o);
		}
		//task.setChildIdList(childIdList);
		DoudouBackend.getInstance().publishTask(task);
		//CacheManager.getInstance().putEvt(userId);
	}
//
//	@RequestMapping("/Cover")
//	public void uploadCover(HttpServletRequest request,	HttpServletResponse response) throws UnsupportedEncodingException,
//			ServletException, IOException {
//		int childId = 0;
//		request.setCharacterEncoding("GBK");
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		try {
//
//			List tempList = upload.parseRequest(request);
//
//			Iterator it = tempList.iterator();
//			while (it.hasNext()) {
//				FileItem item = (FileItem) it.next();
//				// 判断items中的文本信息
//				if (item.isFormField()) {
//					String jsonString = item.getString("GBK");
//					JSONObject object = JSONObject.fromObject(jsonString);
//					// TODO
//					childId = object.getInt("childId");
//					System.out.println("表单参数的名称" + item.getFieldName()
//							+ "表单的参数值" + item.getString("GBK"));
//
//				} else {
//					if (item.getName() != null && !item.getName().equals("")) {
//						Date now = new Date();
//						logger.debug(String.format("文件名称：%s, 文件大小：%d, 文件类别：%s",
//								item.getName(), item.getSize(),
//								item.getContentType()));
//
//						int pointIndex = item.getName().lastIndexOf(".");
//						String poster = item.getName().substring(pointIndex,
//								item.getName().length());
//						System.out.println("poster = " + poster);
//						System.out.println("所上传的文件名称：" + item.getName());
//						System.out.println("所上传的文件大小：" + item.getSize());
//						System.out.println("所上传的文件类别：" + item.getContentType());
//
//						// 用于获取file中的文件名（不包含路径）
//						String picName = "Cover_"
//								+ DateUtil.getInstance()
//										.getDetailStringFromDate(now) + poster;
//						File tempFile = new File(picName);
//						// 建立文件内容
//						File file = new File(MayayaConfig.getConfig()
//								.getUploadImgPath() + savePath,
//								tempFile.getName());
//						System.out.println(file.toString());
//						logger.debug(String.format("文件地址：%s", file.toString()));
//						// 将文件上传至服务器
//						item.write(file);
//						request.setAttribute("upload.message",
//								"上传文件成功！" + item.getName() + item.getSize()
//										+ item.getContentType());
//						childService.updateCover(childId, savePath + "/"
//								+ tempFile.getName());
//
//					} else {
//						request.setAttribute("upload.message", "没有选择文件！");
//					}
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("上传文件失败！");
//			request.setAttribute("upload.message", " 上传文件失败！");
//		}
//
//	}
//
//	@RequestMapping("/Comment")
//	public void uploadComment(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
//		User user = getUser(request);
//    	int userId = user.getId();
//    	
//    	String content = request.getParameter("content");
//    	System.out.println("content = " + content);
//    	System.out.println("userID = " + userId);
//    	String pictureID = request.getParameter("pictureID");
//    	System.out.println("pictureID = " + pictureID);
//    	
//    	int picId = Integer.parseInt(pictureID);
//    	Comment c = new Comment();
//    	c.setContent(content);
//    	c.setUserID(userId);
//    	c.setPictureID(picId);
//    	c.setPublishTime(new Date());
//    	
//    	Picture p = picService.getPictureById(picId);
//    	
//    	commentService.addComment(c,p.getUserId(),picId);
//    	//CacheManager.getInstance().putComment(userId);
//    }
	
}