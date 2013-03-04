package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.NotificationMO;
import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.model.User;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.NotificationService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.service.UserService;
import com.doudoumobile.util.JsonHelper;

public class UserController extends MultiActionController{

    private UserService userService;
    private EtonService etonService;
    private NotificationService ns;
    
    public UserController() {
    	userService = ServiceLocator.getUserService();
    	etonService = (EtonService)ServiceLocator.getService("etonService");
    	ns = (NotificationService)ServiceLocator.getService("notificationService");
    }

    public void getUserList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
    	Integer start = Integer.parseInt(request.getParameter("start"));
		Integer limit = Integer.parseInt(request.getParameter("limit"));
		
		List<User> pageUserList;
    	
		List<User> userList = userService.getUsers();

		
		// 分页,该页size未超过剩余size
		if (userList.size() > start + limit)
			pageUserList = userList.subList(start, limit + start);
		else
			pageUserList = userList.subList(start, userList.size());
		
		for (User user : pageUserList) {
            
    		logger.info(String.format("userId：%d", user.getEtonUserId()));
    		SCSCCUser u = etonService.getUser(user.getEtonUserId());
    		if (u != null) {
    			user.setRealName(u.getRealName());
			}
    		NotificationMO mo = ns.getLastRemoteWipeNotification(user.getUsername());
    		if (mo != null) {
    			if (mo.getStatus().equals("0")) {
    				user.setStatus("Ready To Wipe");
    			} else if (mo.getStatus().equals("1")) {
    				user.setStatus("Wipe Success");
    			} else if (mo.getStatus().equals("2")) {
    				user.setStatus("Wipe Success");
    			} else if (mo.getStatus().equals("3")) {
    				user.setStatus("Wipe Success");
    			}
    			user.setUpdateTime(mo.getUpdateTime().toString().substring(0,mo.getUpdateTime().toString().lastIndexOf(".")));
			}
            // logger.debug("user.online=" + user.isOnline());
        }
		
		Map<String, Object> userMap = new HashMap<String, Object>();
		userMap.put("totalProperty", userList.size());
		userMap.put("user", pageUserList);
		
		 
		    
		JSONObject object = JsonHelper.getInstance().getJson(userMap);
		
		response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
       
    	writer.print(object);
    }
    
}
