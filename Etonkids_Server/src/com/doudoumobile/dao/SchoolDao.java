package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;

public interface SchoolDao {
	public void addSchool(School s);
	public List<School> getAllSchool();
	public void updateSchool(School s);
	
	public List<SchoolType> getAllSchoolType();
}
