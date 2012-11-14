package com.doudoumobile.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.model.User;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.ServiceLocator;
import com.doudoumobile.service.UserService;
import com.doudoumobile.util.JsonHelper;

public class UserController extends MultiActionController{

    private UserService userService;
    private EtonService etonService;
    
    public UserController() {
    	userService = ServiceLocator.getUserService();
    	etonService = (EtonService)ServiceLocator.getService("etonService");
    }

    public void getUserList(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
    	List<User> userList = userService.getUsers();
        
    	for (User user : userList) {
            
    		logger.info(String.format("userId：%d", user.getEtonUserId()));
    		
    		user.setRealName(etonService.getUser(user.getEtonUserId()).getRealName());
            // logger.debug("user.online=" + user.isOnline());
        }
        
        response.setContentType("text/x-json;charset=UTF-8");           
        PrintWriter writer = response.getWriter();
        JSONArray object = JsonHelper.getInstance().getJsonArray(userList);
        System.out.println(object.toString());
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
