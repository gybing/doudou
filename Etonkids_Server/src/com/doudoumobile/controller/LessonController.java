package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.Lesson;
import com.doudoumobile.model.Material;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.LessonService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.util.JsonHelper;

public class LessonController extends MultiActionController{
	LessonService lessonService;
	EtonService etonService;
	
	private LessonController() {
		lessonService = (LessonService)ServiceLocator.getService("lessonService");
		etonService = (EtonService)ServiceLocator.getService("etonService");
	}
	
	public void addLesson(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Lesson l = new Lesson();
		
		l.setTitle("Title1");
		l.setCreatedTime(new Date());
		l.setCurriculumId((long)1);
		l.setBeginDate(new java.sql.Date(System.currentTimeMillis()));
		l.setEndDate(new java.sql.Date(System.currentTimeMillis()));
		lessonService.addLesson(l);
		
		System.out.println(l.getId());
	}
	
	public void getLessonsToDownload(HttpServletRequest request, HttpServletResponse response) throws Exception{
		long userId = (Long)request.getAttribute("userId");
		List<Curriculum> relatedCurriculums = lessonService.getRelatedCurriculums(userId);
		for (Curriculum curriculum : relatedCurriculums) {
			List<Lesson> lessonList = lessonService.getRelatedLessonByCurrId(curriculum.getId());
			for (Lesson lesson : lessonList) {
				List<Material> materialList = etonService.getMaterialListByLessonId(lesson.getId());
				lesson.setMaterialList(materialList);
			}
			curriculum.setLessonList(lessonList);
		}
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(relatedCurriculums);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void getNowTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Date now = new Date();
		response.getWriter().print(getDateString(now));
	}
	
	private String getDateString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = "";
		result = sdf.format(date);
		return result;
	}
}
