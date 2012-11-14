package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.Lesson;

public interface LessonService {

		public Lesson getLesson(long id);

		public Lesson addLesson(Lesson lesson);
		
		public List<Lesson> getLessonsList();
		
		public void updateLesson(Lesson l);
		
		public List<Curriculum> getRelatedCurriculums(long userId);

		public List<Lesson> getRelatedLessonByCurrId(long curriculumId);

		public void deleteUser(long id); 
		
}
