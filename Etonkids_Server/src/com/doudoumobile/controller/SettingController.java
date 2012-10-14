package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;
import com.doudoumobile.model.SessionData;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.util.JsonHelper;
import com.doudoumobile.util.MD5;

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
	
	public void getCurriculumList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Curriculum> cList = etonService.getAllCurriculumList();
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(cList);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void getFirstClassCurriList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<Curriculum> cList = etonService.getFirstClassCurriculumList();
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(cList);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void updateCurriculum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String curriculumName = ServletRequestUtils.getStringParameter(request,"curriculumName","");
		long parentCid =  ServletRequestUtils.getLongParameter(request, "parentCurriculumId", 0);
		
		Curriculum curriculum = new Curriculum();
		
		curriculum.setCreatedTime(new Date());
		curriculum.setParentCurriculumId(parentCid);
		curriculum.setCurriculumName(curriculumName);
		curriculum.setImgPath("");
		etonService.updateCurriculum(curriculum);
		
		System.out.println(curriculum.getId());
	}
	
	public void addEtonUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String email = ServletRequestUtils.getStringParameter(request,"email","");
		String notes = ServletRequestUtils.getStringParameter(request,"notes","");
		String realName = ServletRequestUtils.getStringParameter(request,"realName","");
		int role = ServletRequestUtils.getIntParameter(request,"role",0);
		String userName = ServletRequestUtils.getStringParameter(request,"userName","");
		
		EtonUser etonUser = new EtonUser();
		SessionData sd = (SessionData)request.getAttribute("sessionData");
		etonUser.setAvailable(true);
		etonUser.setCreatedBy(sd.getEtonUser().getRealName());
		etonUser.setEmail(email);
		etonUser.setNotes(notes);
		etonUser.setPassWd(MD5.encode("000000"));
		etonUser.setRealName(realName);
		etonUser.setRole(role);
		etonUser.setUserName(userName);
		//
		etonUser.setSchoolId(1);
		
		etonService.addEtonUser(etonUser);
		
		System.out.println(etonUser.getId());
	}
	
	public void getEtonUserList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<EtonUser> cList = etonService.getAllEtonUserList();
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(cList);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void updateEtonUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String email = ServletRequestUtils.getStringParameter(request,"email","");
		String notes = ServletRequestUtils.getStringParameter(request,"notes","");
		String realName = ServletRequestUtils.getStringParameter(request,"realName","");
		int role = ServletRequestUtils.getIntParameter(request,"role",0);
		String userName = ServletRequestUtils.getStringParameter(request,"userName","");
		
		EtonUser etonUser = new EtonUser();
		SessionData sd = (SessionData)request.getAttribute("sessionData");
		etonUser.setAvailable(true);
		etonUser.setCreatedBy(sd.getEtonUser().getRealName());
		etonUser.setEmail(email);
		etonUser.setNotes(notes);
		etonUser.setPassWd(MD5.encode("000000"));
		etonUser.setRealName(realName);
		etonUser.setRole(role);
		etonUser.setUserName(userName);
		//
		etonUser.setSchoolId(1);
		
		etonService.updateEtonUser(etonUser);
		
		System.out.println(etonUser.getId());
	}
	
	public void addSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String address = ServletRequestUtils.getStringParameter(request,"address","");
		String schoolType =  ServletRequestUtils.getStringParameter(request, "schoolType", "");
		School school = new School();
		
		school.setAddress(address);
		school.setSchoolType(schoolType);
		etonService.addSchool(school);
		
		System.out.println(school.getId());
	}
	
	public void getSchoolList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<School> cList = etonService.getAllSchool();
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(cList);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void updateSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String address = ServletRequestUtils.getStringParameter(request,"address","");
		String schoolType =  ServletRequestUtils.getStringParameter(request, "schoolType", "");
		School school = new School();
		
		school.setAddress(address);
		school.setSchoolType(schoolType);
		etonService.updateSchool(school);
		
		System.out.println(school.getId());
	}
	
	public void getSchoolTypes(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<SchoolType> cList = etonService.getSchoolTypeList();
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(cList);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void getCurriculumById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"curriId",0);
		
		Curriculum c = etonService.getCurriculumById(id);
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONObject object = JsonHelper.getInstance().getJson(c);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
}
