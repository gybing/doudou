package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.Lesson;
import com.doudoumobile.model.NotificationMO;
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
    		
    		user.setRealName(etonService.getUser(user.getEtonUserId()).getRealName());
    		NotificationMO mo = ns.getLastRemoteWipeNotification(user.getUsername());
    		if (mo != null) {
    			if (mo.getStatus().equals("0")) {
    				user.setStatus("STATUS_NOT_SEND");
    			} else if (mo.getStatus().equals("1")) {
    				user.setStatus("STATUS_SEND");
    			} else if (mo.getStatus().equals("2")) {
    				user.setStatus("STATUS_RECEIVE");
    			} else if (mo.getStatus().equals("3")) {
    				user.setStatus("STATUS_READ");
    			}
    			user.setUpdateTime(mo.getUpdateTime().toString());
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
    
    public void remoteWipe(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	
		String apn_userName =  ServletRequestUtils.getStringParameter(request, "apn_userName", "");

    	boolean succeeded = etonService.remoteWipe(apn_userName);
    	
    	if(succeeded == true)
    		response.getWriter().print("{success:true}");

    	else
    		response.getWriter().print("{success:false}");

    	
    }

    

}
