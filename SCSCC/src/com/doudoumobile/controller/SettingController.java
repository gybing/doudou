package com.doudoumobile.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.SCSCCService;

public class SettingController extends MultiActionController{
	
	SCSCCService scsccService;
	
	public void setSCSCCService(SCSCCService scsccService) {
		this.scsccService = scsccService;
	}

	private SettingController() {
	}
	
}
