package com.doudoumobile.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.service.EtonService;

public class SettingController extends MultiActionController{
	
	EtonService etonService;

	public void setEtonService(EtonService etonService) {
		this.etonService = etonService;
	}

	private SettingController() {
	}
	
}
