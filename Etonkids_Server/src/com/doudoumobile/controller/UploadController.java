package com.doudoumobile.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.Lesson;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.LessonService;
import com.doudoumobile.service.ServiceLocator;

public class UploadController extends MultiActionController {
	
	LessonService lessonService;
	
	private UploadController() {
		lessonService = (LessonService)ServiceLocator.getService("lessonService");
	}

	
	public ModelAndView uploadFile(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
		
		String title = "";
		java.sql.Date beginDate = null;
		java.sql.Date endDate = null;
		long curriculumId = 0;
		boolean available = true;
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//logger.info(title);
        request.setCharacterEncoding("GBK");
		
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
					String itemName = item.getFieldName();
					String itemValue = item.getString("UTF-8");
					//JSONObject object = JSONObject.fromObject(jsonString);
					//whoToPush = object.getJSONArray("whoToPush").toArray();
					//photoCaption = object.getString("photoCaption");
					logger.info(String.format("%s : %s", itemName, itemValue));
					
					if(itemName.equals("title")){
						title = itemValue;
					}
					else if(itemName.equals("beginDate")){
						beginDate = java.sql.Date.valueOf(itemValue);
					}
					else if(itemName.equals("endDate")){
						endDate = java.sql.Date.valueOf(itemValue);
					}
					else if(itemName.equals("curriculumId")){
						curriculumId = Long.parseLong(itemValue);
					}
					else if(itemName.equals("available")){
						if(itemValue.equals("0"))
							available = false;
						else if(itemValue.equals("1"))
							available = true;
						
					}
					
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
						//String picName = "Pic_" + DateUtil.getInstance().getDetailStringFromDate(now) + poster;
						File tempFile = new File(item.getName());
						String savePath = request.getSession().getServletContext().getRealPath("/upload");;
						// 建立文件内容
						File file = new File(savePath , tempFile.getName());
						logger.debug(String.format("文件地址：%s", file.toString()));
						// 将文件上传至服务器
						item.write(file);
						
						Lesson newLesson = new Lesson();
						newLesson.setTitle(title);
						newLesson.setBeginDate(beginDate);
						newLesson.setEndDate(endDate);
						newLesson.setCurriculumId(curriculumId);
						newLesson.setPdfPath("/"+tempFile.getName());
						newLesson.setCreatedTime(new Date());
						//newLesson.setMaterialList(null);
						newLesson.setAvailable(available);
						
						lessonService.addLesson(newLesson);
						
						System.out.println(newLesson.getId());
						response.getWriter().print("{success:true}");
//						request.setAttribute("upload.message",
//                                "上传文件成功！" + item.getName() + item.getSize()
//                                                + item.getContentType());

					} else {
						logger.info("没有选择文件！");
                        //request.setAttribute("upload.message", "没有选择文件！");
					}
				}
			}
			
		} catch (Exception e) {
			logger.error(e);
			logger.error("上传文件失败！");
			//response.getOutputStream().print(" 上传文件失败！");
		}
		
		
		
		System.out.println("here goes");
		ModelAndView mav = new ModelAndView();
		
		return mav;
	}
	
}
