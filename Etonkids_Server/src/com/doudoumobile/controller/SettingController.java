package com.doudoumobile.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.ServiceLocator;

public class SettingController extends MultiActionController{
	EtonService etonService;

	private SettingController() {
		etonService = (EtonService)ServiceLocator.getService("etonService");
	}
	
	public void addCurriculum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String curriculumName = ServletRequestUtils.getStringParameter(request,"curriculumName","");
		long parentCid =  ServletRequestUtils.getLongParameter(request, "parentCurriculumId", 0);
		Curriculum curriculum = new Curriculum();
		
		curriculum.setCreatedTime(new Date());
		curriculum.setParentCurriculumId(parentCid);
		curriculum.setCurriculumName(curriculumName);
		curriculum.setImgPath("");
		etonService.addCurri(curriculum);
		
		System.out.println(curriculum.getId());
	}
	
}
