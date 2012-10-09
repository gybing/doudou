package com.doudoumobile.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class UploadController extends MultiActionController {
	
	public ModelAndView uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// FileUpload用来解析request文件上传请求
		ServletFileUpload upload = new ServletFileUpload(factory);
		
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
//					String jsonString = item.getString("UTF-8");
//					JSONObject object = JSONObject.fromObject(jsonString);
//					whoToPush = object.getJSONArray("whoToPush").toArray();
//					photoCaption = object.getString("photoCaption");
//					logger.info(String.format("whotoPush : %s, Caption : %s",whoToPush,photoCaption));
//					
//				} else {
//					if (item.getName() != null && !item.getName().equals("")) {
//						Date now = new Date();
//						logger.debug(String.format("文件名称：%s, 文件大小：%d, 文件类别：%s",item.getName(),item.getSize(),item.getContentType()));
//						
//						int pointIndex = item.getName().lastIndexOf(".");
//						String poster = item.getName().substring(pointIndex, item.getName().length());
//						
//						// 用于获取file中的文件名（不包含路径）
//						String picName = "Pic_" + DateUtil.getInstance().getDetailStringFromDate(now) + poster;
//						File tempFile = new File(picName);
//						// 建立文件内容
//						File file = new File(MayayaConfig.getConfig().getUploadImgPath() + savePath,
//								tempFile.getName());
//						logger.debug(String.format("文件地址：%s", file.toString()));
//						// 将文件上传至服务器
//						item.write(file);
//						
//						MayayaBackend.getInstance().addImageToQueue(file.toString());
//						
//						PicPublishTask task = new PicPublishTask();
//						Picture pic = new Picture();
//						pic.setUserId(userId);
//						pic.setDescription(photoCaption);
//						pic.setPictureURL(savePath + "/" + tempFile.getName());
//						pic.setPublishTime(now);
//						task.setPicture(pic);
//						
//						List<Integer> childIdList = new ArrayList<Integer>();
//						for (Object o : whoToPush) {
//							childIdList.add((Integer)o);
//						}
//						task.setChildrenList(childIdList);
//						MayayaBackend.getInstance().publishTask(task);
//						//CacheManager.getInstance().putPic(userId);
//					} else {
//						logger.info("没有选择文件！");
//					}
//				}
//			}
//			
//		} catch (Exception e) {
//			logger.error(e);
//			logger.error("上传文件失败！");
//			response.getOutputStream().print(" 上传文件失败！");
//		}
		
		
		
		System.out.println("here goes");
		ModelAndView mav = new ModelAndView();
		
		return mav;
	}
	
}
