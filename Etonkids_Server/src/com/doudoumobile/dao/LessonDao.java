package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.Lesson;

public interface LessonDao {

    public Lesson getLesson(Long id);

    public Lesson addLesson(Lesson lesson);
    
    public List<Lesson> getAllLessons();
    
    public List<Lesson> getRelatedLessonByCurrId(long curriculumId);

	public void updateLesson(Lesson l);

	public void delete(long id);
}
