package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.SessionData;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.LessonService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.util.JsonHelper;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class EtonUserController extends MultiActionController {
	
	EtonService etonService;
	LessonService lessonService;
	
	public EtonUserController() {
		etonService = (EtonService)ServiceLocator.getService("etonService");
		lessonService = (LessonService)ServiceLocator.getService("lessonService");
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userName = ServletRequestUtils.getStringParameter(request, "userName", "");
    	String passWd = ServletRequestUtils.getStringParameter(request, "passWd", "");
    	EtonUser user = etonService.verifyEtonUser(userName, passWd);
    	response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        if (user == null) {
        	writer.print("null");
        	return;
        }
        
    	
    	if (user.getRole() == EtonUser.Teacher) {
    		// Curri info
    		StringBuffer curriculumString = new StringBuffer();
    		List<Curriculum> currList = lessonService.getRelatedCurriculums(user.getId());
    		if(null != currList && currList.size() > 0) {
    			for (Curriculum curriculum : currList) {
    				curriculumString.append(curriculum.getCurriculumName() + ",");
    			}	
    			curriculumString.deleteCharAt(curriculumString.length()-1);
    		}
    		user.setCurriList(curriculumString.toString());
    		
    		// school info
    		user.setSchoolInfo(etonService.getSchoolById(user.getSchoolId()));
		}
    	JSONObject object = JsonHelper.getInstance().getJson(user);
        String doudouTicket = user.getId() + "/" + user.getUserName() + "/" + "doudouTicket";
        String encodedS = Base64.encode(doudouTicket.getBytes());
        System.out.println("encoded : " + encodedS);
        object.put("ticket", encodedS);
    	
    	writer.print(object);

    }
    
    public void modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	long userId = (Long)request.getAttribute("userId");
    	String oldPwd = ServletRequestUtils.getStringParameter(request, "oldPwd", "");
    	String newPwd = ServletRequestUtils.getStringParameter(request, "newPwd", "");
    	
    	int result = etonService.modifyPwd(userId, oldPwd, newPwd);
    	response.setContentType("text/x-json;charset=UTF-8"); 
    	response.getWriter().print(result);
    }
    
    public void loginForWeb(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userName = ServletRequestUtils.getStringParameter(request, "userName", "");
    	String passWd = ServletRequestUtils.getStringParameter(request, "passWd", "");
    	EtonUser user = etonService.verifyEtonUser(userName, passWd);
    	response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        if (user == null) {
        	writer.print("null");
        	return;
        }
        
    	if (null != user) {
    		if (user.getRole() == EtonUser.Admin) {
    			String doudouTicket = user.getId() + "/" + user.getUserName() + "/" + "doudouTicket";
    			String encodedS = Base64.encode(doudouTicket.getBytes());
    			Cookie cookie = new Cookie("EtonKids_ITeach_Ticket",encodedS);
    			cookie.setMaxAge(3600);
    			cookie.setPath("/");
    			response.addCookie(cookie);
    			
    			SessionData sessionData = new SessionData();
    			sessionData.setEtonUser(user);
    			
    			request.getSession().setAttribute("SessionData", sessionData);		
    			writer.print("LoginSuccess");
			} else {
				writer.print("NotOpenYetForOtherRole");
				return;
			}
		} 
    }
}
