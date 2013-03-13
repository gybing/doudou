package com.doudoumobile.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.UserService;

public class UserController extends MultiActionController{

    private UserService userService;
    private EtonService etonService;
    
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setEtonService(EtonService etonService) {
		this.etonService = etonService;
	}

	public UserController() {
    }

}
