package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.Curriculum;

public interface CurriculumDao {
	List<Curriculum> getRelatedCurriculums(long userId);
	
	Curriculum addCurriculum(Curriculum c);
}
