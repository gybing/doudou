package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.model.SessionData;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.util.JsonHelper;
import com.doudoumobile.util.MD5;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class SCSCCUserController extends MultiActionController {
	
	EtonService etonService;
	
	public void setEtonService(EtonService etonService) {
		this.etonService = etonService;
	}
	
	public SCSCCUserController() {
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userName = ServletRequestUtils.getStringParameter(request, "userName", "");
    	String passWd = ServletRequestUtils.getStringParameter(request, "passWd", "");
    	String deviceToken = ServletRequestUtils.getStringParameter(request, "deviceToken", "");
    	
    	SCSCCUser user = etonService.verifyEtonUser(userName, passWd);
    	response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        if (user == null) {
        	JSONObject object = JsonHelper.getInstance().getJson(null);
        	System.out.println(object);
        	writer.print(object);
        	return;
        }
        
    	
    	if (user.getRole() == SCSCCUser.Teacher) {
    		// Curri info
    		StringBuffer curriculumString = new StringBuffer();
    		
    		// school info
    		//user.setSchoolInfo(etonService.getSchoolById(user.getSchoolId()));
		}
    	JSONObject object = JsonHelper.getInstance().getJson(user);
        String doudouTicket = user.getId() + "/" + user.getUserName() + "/" + "doudouTicket" + "/" + deviceToken;
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
    
    public void modifyPwdForWeb(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	SessionData sd = (SessionData)request.getSession().getAttribute("sessionData");
    	long userId = (Long)sd.getEtonUser().getId();
    	String oldPwd = ServletRequestUtils.getStringParameter(request, "oldPassword", "");
    	String newPwd = ServletRequestUtils.getStringParameter(request, "newPassword", "");
    	
    	int result = etonService.modifyPwd(userId, oldPwd, newPwd);
    	response.setContentType("text/x-json;charset=UTF-8"); 
    	if(result == 1){

    	}
    	response.getWriter().print("{success:true, msg:'"+result+"'}");

    }
    
    public void resetPwd(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//admin only
    	long id = ServletRequestUtils.getLongParameter(request, "userId", 0);
    	String resetPwd = MD5.encode("000000");
    	etonService.resetPwd(id, resetPwd);
    	
    	response.getWriter().write("Success");
    }
    
    public void loginForWeb(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userName = ServletRequestUtils.getStringParameter(request, "userName", "");
    	String passWd = ServletRequestUtils.getStringParameter(request, "passWd", "");
    	SCSCCUser user = etonService.verifyEtonUser(userName, passWd);
    	response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        if (user == null) {
        	writer.print("null");
        	return;
        }
        
    	if (null != user) {
    		if (user.getRole() != SCSCCUser.Teacher) {
    			String doudouTicket = user.getId() + "/" + user.getUserName() + "/" + "doudouTicket";
    			String encodedS = Base64.encode(doudouTicket.getBytes());
    			Cookie cookie = new Cookie("EtonKids_ITeach_Ticket",encodedS);
    			cookie.setMaxAge(3600);
    			cookie.setPath("/");
    			response.addCookie(cookie);
    			
    			SessionData sessionData = new SessionData();
    			sessionData.setEtonUser(user);
    			
    			request.getSession().setAttribute("SessionData", sessionData);		
    			writer.print(1);
			}
    		else {
				writer.print(0);
			}
		} 
    }
    
}
