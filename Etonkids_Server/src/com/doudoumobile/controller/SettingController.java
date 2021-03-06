package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.CurriculumToUser;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.Lesson;
import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;
import com.doudoumobile.model.SessionData;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.LessonService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.util.JsonHelper;
import com.doudoumobile.util.MD5;

public class SettingController extends MultiActionController{
	EtonService etonService;
	LessonService lessonService;
	
	private SettingController() {
		etonService = (EtonService)ServiceLocator.getService("etonService");
		lessonService = (LessonService)ServiceLocator.getService("lessonService");
	}
	
	public void addCurriculum(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String curriculumName = ServletRequestUtils.getStringParameter(request,"curriculumName","");
		long parentCid =  ServletRequestUtils.getLongParameter(request, "parentCurriculumId", 0);
		String parentCurriName = ServletRequestUtils.getStringParameter(request,"parentCurriName","");
		Curriculum curriculum = new Curriculum();
		
		curriculum.setCreatedTime(new Date());
		curriculum.setParentCurriculumId(parentCid);
		curriculum.setCurriculumName(curriculumName);
		curriculum.setImgPath("");
		curriculum.setParentCurriName(parentCurriName);
		SessionData sd = (SessionData)request.getSession().getAttribute("sessionData");
		curriculum.setCreatedBy(sd.getEtonUser().getRealName());
		etonService.addCurri(curriculum);
		
		System.out.println(curriculum.getId());
		response.getWriter().print("{success:true}");
	}
	
	public void getCurriculumList(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
    	Integer start = ServletRequestUtils.getIntParameter(request, "start", 0);
		Integer limit = ServletRequestUtils.getIntParameter(request, "limit", 0);
		
		List<Curriculum> pageCurriculumList;
		
		List<Curriculum> cList = etonService.getAllCurriculumList();

		// 分页,该页size未超过剩余size
		if (cList.size() > start + limit)
			pageCurriculumList = cList.subList(start, limit + start);
		else
			pageCurriculumList = cList.subList(start, cList.size());
		
		
		
		Map<String, Object> myCurriculumMap = new HashMap<String, Object>();
		myCurriculumMap.put("totalProperty", cList.size());
		myCurriculumMap.put("curriculum", pageCurriculumList);
		
		 
		    
		JSONObject myCurriculumJSON = JsonHelper.getInstance().getJson(myCurriculumMap);
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        //JSONArray object = JsonHelper.getInstance().getJsonArray(pageLessonList);
        //System.out.println(object.toString());
    	writer.print(myCurriculumJSON);
    	
	}
	
	public void getCurriculumListForCascading(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
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
		long id = ServletRequestUtils.getLongParameter(request, "id", 0);
		String parentCurriName = ServletRequestUtils.getStringParameter(request,"parentCurriName","");
		
		Curriculum curriculum = new Curriculum();
		curriculum.setId(id);
		curriculum.setCreatedTime(new Date());
		curriculum.setParentCurriculumId(parentCid);
		curriculum.setCurriculumName(curriculumName);
		curriculum.setImgPath("");
		curriculum.setParentCurriName(parentCurriName);
		etonService.updateCurriculum(curriculum);
		
		System.out.println(curriculum.getId());
		response.getWriter().print("{success:true}");

	}
	
	public void addEtonUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String email = ServletRequestUtils.getStringParameter(request,"email","");
		String notes = ServletRequestUtils.getStringParameter(request,"notes","");
		String realName = ServletRequestUtils.getStringParameter(request,"realName","");
		int role = ServletRequestUtils.getIntParameter(request,"role",0);
		String userName = email;
		String curriIdList = ServletRequestUtils.getStringParameter(request,"curriculumsId","");
		long schoolId = ServletRequestUtils.getLongParameter(request, "schoolId", 0);
		
		EtonUser etonUser = new EtonUser();
		SessionData sd = (SessionData)request.getSession().getAttribute("sessionData");
		etonUser.setAvailable(true);
		etonUser.setCreatedBy(sd.getEtonUser().getRealName());
		etonUser.setEmail(email);
		etonUser.setNotes(notes);
		etonUser.setPassWd(MD5.encode("000000"));
		etonUser.setRealName(realName);
		etonUser.setRole(role);
		etonUser.setUserName(userName);
		etonUser.setSchoolId(schoolId);
		
		etonService.addEtonUser(etonUser);
		
		if(!curriIdList.isEmpty()) {
			String[] ids = curriIdList.split(",");
			for (String cId : ids) {
				CurriculumToUser ctu = new CurriculumToUser();
				ctu.setCurriculumId(Long.parseLong(cId));
				ctu.setAvailable(true);
				ctu.setCreatedTime(new Date());
				ctu.setUserId(etonUser.getId());
				etonService.addCurriToEtonUser(ctu);
			}
		}
		System.out.println(etonUser.getId());
		response.getWriter().print("{success:true}");

	}
	
	public void getEtonUserList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Integer start = ServletRequestUtils.getIntParameter(request, "start", 0);
		Integer limit = ServletRequestUtils.getIntParameter(request, "limit", 0);
		
		List<EtonUser> pageUserList;
		
		List<EtonUser> cList = etonService.getAllEtonUserList();

		// 分页,该页size未超过剩余size
		if (cList.size() > start + limit)
			pageUserList = cList.subList(start, limit + start);
		else
			pageUserList = cList.subList(start, cList.size());
		
		for (EtonUser etonUser : pageUserList) {
			
			if (etonUser.getRole() == EtonUser.Teacher) {
				// Curri info
				StringBuffer curriculumString = new StringBuffer();
				List<Curriculum> currList = lessonService.getRelatedCurriculums(etonUser.getId());
				if(null != currList && currList.size() > 0) {
					for (Curriculum curriculum : currList) {
						if (null != curriculum) {
							curriculumString.append(curriculum.getCurriculumName() + ",");
						}
					}	
					if (curriculumString.length() > 0) {
						curriculumString.deleteCharAt(curriculumString.length()-1);
					}
				}
				etonUser.setCurriList(curriculumString.toString());
				
				// school info
				etonUser.setSchoolInfo(etonService.getSchoolById(etonUser.getSchoolId()));
			}
		}
		
		
		Map<String, Object> myUserMap = new HashMap<String, Object>();
		myUserMap.put("totalProperty", cList.size());
		myUserMap.put("users", pageUserList);
	
		JSONObject myUserJSON = JsonHelper.getInstance().getJson(myUserMap);
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
    	writer.print(myUserJSON);
    	
	}
	
	public void updateEtonUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		long id = ServletRequestUtils.getLongParameter(request, "id", 0);
		String email = ServletRequestUtils.getStringParameter(request,"email","");
		String notes = ServletRequestUtils.getStringParameter(request,"notes","");
		String realName = ServletRequestUtils.getStringParameter(request,"realName","");
		int role = ServletRequestUtils.getIntParameter(request,"role",0);
		String userName = email;
		String curriIdList = ServletRequestUtils.getStringParameter(request,"curriculumsId","");
		long schoolId = ServletRequestUtils.getLongParameter(request, "schoolId", 0);
		
		EtonUser etonUser = new EtonUser();
		etonUser.setId(id);
		SessionData sd = (SessionData)request.getSession().getAttribute("sessionData");
		etonUser.setAvailable(true);
		etonUser.setCreatedBy(sd.getEtonUser().getRealName());
		etonUser.setEmail(email);
		etonUser.setNotes(notes);
		etonUser.setPassWd(MD5.encode("000000"));
		etonUser.setRealName(realName);
		etonUser.setRole(role);
		etonUser.setUserName(userName);
		etonUser.setSchoolId(schoolId);
		
		etonService.updateEtonUser(etonUser);

		etonService.deleteCurriculumToUserByUserId(id);
		if(!curriIdList.isEmpty()) {
			String[] ids = curriIdList.split(",");
			for (String cId : ids) {
				CurriculumToUser ctu = new CurriculumToUser();
				ctu.setCurriculumId(Long.parseLong(cId));
				ctu.setAvailable(true);
				ctu.setCreatedTime(new Date());
				ctu.setUserId(etonUser.getId());
				etonService.addCurriToEtonUser(ctu);
			}
		}
		System.out.println(etonUser.getId());
		response.getWriter().print("{success:true}");

	}
	
	public void addSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String address = ServletRequestUtils.getStringParameter(request,"address","");
		String schoolType =  ServletRequestUtils.getStringParameter(request, "schoolType", "");
		long typeId = ServletRequestUtils.getLongParameter(request, "typeId", 0);
		School school = new School();
		school.setTypeId(typeId);
		school.setAddress(address);
		school.setSchoolType(schoolType);
		SessionData sd = (SessionData)request.getSession().getAttribute("sessionData");
		school.setCreatedBy(sd.getEtonUser().getRealName());
		etonService.addSchool(school);
		
		System.out.println(school.getId());
		response.getWriter().print("{success:true}");

	}
	
	public void getSchoolList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Integer start = ServletRequestUtils.getIntParameter(request, "start", 0);
		Integer limit = ServletRequestUtils.getIntParameter(request, "limit", 0);
		
		List<School> pageSchoolList;
		
		List<School> sList = etonService.getAllSchool();

		// 分页,该页size未超过剩余size
		if (sList.size() > start + limit)
			pageSchoolList = sList.subList(start, limit + start);
		else
			pageSchoolList = sList.subList(start, sList.size());
		
		Map<String, Object> mySchoolMap = new HashMap<String, Object>();
		mySchoolMap.put("totalProperty", sList.size());
		mySchoolMap.put("campus", pageSchoolList);
		    
		JSONObject mySchoolJSON = JsonHelper.getInstance().getJson(mySchoolMap);
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        
    	writer.print(mySchoolJSON);
	}
	
	public void updateSchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String address = ServletRequestUtils.getStringParameter(request,"address","");
		String schoolType =  ServletRequestUtils.getStringParameter(request, "schoolType", "");
		long typeId = ServletRequestUtils.getLongParameter(request, "typeId", 0);
		long id = ServletRequestUtils.getLongParameter(request, "id", 0);
		School school = new School();
		school.setId(id);
		school.setTypeId(typeId);
		school.setAddress(address);
		school.setSchoolType(schoolType);
		etonService.updateSchool(school);
		
		System.out.println(school.getId());
		response.getWriter().print("{success:true}");

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
	
	public void getSchoolById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"schoolId",0);
		
		School c = etonService.getSchoolById(id);
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONObject object = JsonHelper.getInstance().getJson(c);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void getEtonUserById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"userId",0);
		EtonUser etonUser = etonService.getUser(id);
		
		if (etonUser.getRole() == EtonUser.Teacher) {
			// Curri info
			StringBuffer curriculumString = new StringBuffer();
			StringBuffer curriIdString = new StringBuffer();
			List<Curriculum> currList = lessonService.getRelatedCurriculums(etonUser.getId());
			if(null != currList && currList.size() > 0) {
				for (Curriculum curriculum : currList) {
					if (null != curriculum) {
						curriculumString.append(curriculum.getCurriculumName() + ",");
						curriIdString.append(curriculum.getId()+",");
					}
				}	
				if (curriculumString.length() > 0) {
					curriculumString.deleteCharAt(curriculumString.length()-1);
					curriIdString.deleteCharAt(curriIdString.length()-1);
				}
			}
			etonUser.setCurriList(curriculumString.toString());
			etonUser.setCurriIdList(curriIdString.toString());
			// school info
			etonUser.setSchoolInfo(etonService.getSchoolById(etonUser.getSchoolId()));
		}
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONObject object = JsonHelper.getInstance().getJson(etonUser);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void deleteEtonUserById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"userId",0);
		
		etonService.deleteUser(id);
		response.getWriter().print("{success:true}");

	}
	
	public void deleteCurriculumById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"curriId",0);
		
		etonService.deleteCurriculum(id);
		response.getWriter().print("{success:true}");

	}
	
	public void deleteSchoolById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"schoolId",0);
		
		etonService.deleteSchool(id);
		response.getWriter().print("{success:true}");

	}
	
	public void deleteSchoolTypeById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"schoolTypeId",0);
		
		etonService.deleteSchoolType(id);
		response.getWriter().print("{success:true}");

	}
	
	public void addSchoolType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String typeName =  ServletRequestUtils.getStringParameter(request, "typeName", "");
		SchoolType schoolType = new SchoolType();
		
		SessionData sd = (SessionData)request.getSession().getAttribute("sessionData");
		schoolType.setCreatedBy(sd.getEtonUser().getRealName());
		schoolType.setTypeName(typeName);
		etonService.addSchoolType(schoolType);
		
		System.out.println(schoolType.getId());
		response.getWriter().print("{success:true}");

	}
	
	public void updateSchoolType(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String typeName =  ServletRequestUtils.getStringParameter(request, "typeName", "");
		long id = ServletRequestUtils.getLongParameter(request, "id", 0);
		SchoolType schoolType = new SchoolType();
		
		schoolType.setTypeName(typeName);
		schoolType.setId(id);
		etonService.updateSchoolType(schoolType);
		
		System.out.println(schoolType.getId());
		response.getWriter().print("{success:true}");

	}
	
	public void getSchoolTypeById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long id = ServletRequestUtils.getLongParameter(request,"id",0);
		
		SchoolType c = etonService.getSchoolTypeById(id);
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONObject object = JsonHelper.getInstance().getJson(c);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
	public void getSchoolByTypeId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long typeId = ServletRequestUtils.getLongParameter(request,"typeId",0);
		
		List<School> c = etonService.getSchoolByTypeId(typeId);
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(c);
        System.out.println(object.toString());
    	writer.print(object);
	}
	
}
