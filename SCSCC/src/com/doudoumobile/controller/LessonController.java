package com.doudoumobile.controller;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.doudoumobile.service.EtonService;
import com.doudoumobile.service.ServiceLocator;

public class LessonController extends MultiActionController{
	EtonService etonService;
	
	private LessonController() {
		//lessonService = (LessonService)ServiceLocator.getService("lessonService");
		etonService = (EtonService)ServiceLocator.getService("etonService");
	}
	
}
