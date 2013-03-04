package com.doudoumobile.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.ServiceLocator;

public class SettingController extends MultiActionController{
	EtonService etonService;

	private SettingController() {
		etonService = (EtonService)ServiceLocator.getService("etonService");
	}
	
}
