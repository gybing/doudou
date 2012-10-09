package com.doudoumobile.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.doudoumobile.model.Lesson;

public class LessonServiceTest {

	//LessonService ls = ServiceLocator.getLessonService();
	
	//@Test
	public void testGetLesson() {
	}

	@Test
	public void testAddLesson() {
		Lesson l = new Lesson();
		l.setTitle("Title1");
		l.setCreatedTime(new Date());
		l.setCurriculumId((long)1);
		l.setBeginDate(new java.sql.Date(System.currentTimeMillis()));
		l.setEndDate(new java.sql.Date(System.currentTimeMillis()));

		//ls.addLesson(l);
		
		assertTrue(l.getId() > 0);
	}

}
