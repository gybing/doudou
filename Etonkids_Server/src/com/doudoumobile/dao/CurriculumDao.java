package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.CurriculumToUser;

public interface CurriculumDao {
	List<Curriculum> getRelatedCurriculums(long userId);
	
	Curriculum addCurriculum(Curriculum c);
	
	List<Curriculum> getAllCurriculumList();
	
	void updateCurriculum(Curriculum c);
	
	List<Curriculum> getFirstClassCurriculumList();
	
	Curriculum getCurriculumById(long id);
	
	void delete(long id);
	
	void deleteChildCurriculumByParentId(long parentId);
	
	List<Curriculum> getCurriculumsByParentId(long parentId);
	
	void addCurriculumToUser(CurriculumToUser ctu);
	
	void deleteCurriculumToUserByUserId(long userId);
	
	List<CurriculumToUser> getCurriculumToUserByUserId(long userId);
}
