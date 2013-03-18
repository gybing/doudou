package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.model.SessionData;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.SCSCCService;
import com.doudoumobile.util.Cn2Char;
import com.doudoumobile.util.JsonHelper;
import com.doudoumobile.util.MD5;
import com.doudoumobile.util.MapKeySorter;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class SCSCCUserController extends MultiActionController {
	
	SCSCCService scsccService;
	
	public void setEtonService(EtonService etonService) {
		this.scsccService = scsccService;
	}
	
	public SCSCCUserController() {
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String userName = ServletRequestUtils.getStringParameter(request, "userName", "");
    	String passWd = ServletRequestUtils.getStringParameter(request, "passWd", "");
    	String deviceToken = ServletRequestUtils.getStringParameter(request, "deviceToken", "");
    	
    	SCSCCUser user = scsccService.verifySCSCCUser(userName, passWd);
    	response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        if (user == null) {
        	
        	JSONObject object = JsonHelper.getInstance().getJson(null);
        	System.out.println(object);
        	writer.print(object);
        	return;
        	
        }
        
		List<SCSCCUser> contactList = scsccService.getContactList(userName);


		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("user", user);
		responseMap.put("contact", contactList);

		request.getSession().setAttribute("currentUser", user);		

    	JSONObject object = JsonHelper.getInstance().getJson(responseMap);
        //String doudouTicket = user.getId() + "/" + user.getUserName() + "/" + "doudouTicket" + "/" + deviceToken;
        //String encodedS = Base64.encode(doudouTicket.getBytes());
        //System.out.println("encoded : " + encodedS);
        //object.put("ticket", encodedS);
    	
    	writer.print(object);

    }
    
	
	public void contactList(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
    	String userId = ServletRequestUtils.getStringParameter(request, "userId", "");
	
		List<SCSCCUser> contactList = scsccService.getContactList(userId);
		
		HashMap<String, List> contactMap = new HashMap<String, List>();
		
		for(SCSCCUser user: contactList){
			
			String realname = user.getRealName();
			
			String firstAlpha = Cn2Char.getFirstCharacter(realname);
			
			if (!contactMap.containsKey(firstAlpha)){
				
				List<SCSCCUser> newList = new ArrayList<SCSCCUser>();
				newList.add(user);
				contactMap.put(firstAlpha, newList);
				
			}
			else {
				@SuppressWarnings("unchecked")
				ArrayList<SCSCCUser> theList = (ArrayList<SCSCCUser>) contactMap.get(firstAlpha);
				theList.add(user);
			}
			
		}
        Map<String, List> sortedContactMap = MapKeySorter.sort(contactMap);

		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        
        JSONArray array = JsonHelper.getInstance().getJsonArray(contactList);
    	JSONObject object = JsonHelper.getInstance().getJson(sortedContactMap);

        
    	writer.print(object);
		
	}
    /**
    public void modifyPwd(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	long userId = (Long)request.getAttribute("userId");
    	String oldPwd = ServletRequestUtils.getStringParameter(request, "oldPwd", "");
    	String newPwd = ServletRequestUtils.getStringParameter(request, "newPwd", "");
    	
    	int result = scsccService.modifyPwd(userId, oldPwd, newPwd);
    	response.setContentType("text/x-json;charset=UTF-8"); 
    	response.getWriter().print(result);
    }
    
    public void modifyPwdForWeb(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	SessionData sd = (SessionData)request.getSession().getAttribute("sessionData");
    	long userId = (Long)sd.getEtonUser().getId();
    	String oldPwd = ServletRequestUtils.getStringParameter(request, "oldPassword", "");
    	String newPwd = ServletRequestUtils.getStringParameter(request, "newPassword", "");
    	
    	int result = scsccService.modifyPwd(userId, oldPwd, newPwd);
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
    */
    
}
