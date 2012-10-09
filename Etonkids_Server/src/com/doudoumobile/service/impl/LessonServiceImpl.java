
package com.doudoumobile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.doudoumobile.dao.CurriculumDao;
import com.doudoumobile.dao.LessonDao;
import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.Lesson;
import com.doudoumobile.service.LessonService;

public class LessonServiceImpl implements LessonService{

	private LessonDao lessonDao;
	private CurriculumDao curriculumDao;
	
	public void setLessonDao(LessonDao lessonDao) {
		this.lessonDao = lessonDao; 
	}
	
	public void setCurriculumDao(CurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
	}
	
    protected final Log log = LogFactory.getLog(getClass());

	@Override
	public Lesson getLesson(long id) {
		return lessonDao.getLesson(id);
	}
	@Override
	public Lesson addLesson(Lesson lesson) {
		return lessonDao.addLesson(lesson);
	}
	@Override
	public List<Curriculum> getRelatedCurriculums(long userId) {
		List<Curriculum> result = curriculumDao.getRelatedCurriculums(userId);
		return result;
	}

	@Override
	public List<Lesson> getRelatedLessonByCurrId(long curriculumId) {
		List<Lesson> result = lessonDao.getRelatedLessonByCurrId(curriculumId);
		return result;
	}
    

}
