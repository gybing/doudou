package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.Curriculum;

public interface CurriculumDao {
	List<Curriculum> getRelatedCurriculums(long userId);
	
	Curriculum addCurriculum(Curriculum c);
	
	List<Curriculum> getAllCurriculumList();
	
	void updateCurriculum(Curriculum c);
	
	List<Curriculum> getFirstClassCurriculumList();
	
	Curriculum getCurriculumById(long id);
	
	void delete(long id);
	
	void deleteChildCurriculumByParentId(long parentId);
}
