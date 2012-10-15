package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;

public interface SchoolDao {
	public void addSchool(School s);
	public List<School> getAllSchool();
	public void updateSchool(School s);
	
	public List<SchoolType> getAllSchoolType();
	
	public School getSchoolById(long id);
	
	public SchoolType getSchoolTypeById(long id);
	
	public void delete(long id);
	
	public void deleteSchoolType(long id);
	
	public void addSchoolType(SchoolType st);
	
	public void updateSchoolType(SchoolType st);
	
	public List<School> getSchoolByTypeId(long typeId);
}
