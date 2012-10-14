package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.Material;
import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;

public interface EtonService {
	public EtonUser verifyEtonUser(String userName, String passWd);
	
	public EtonUser getUser(long userId);
	
	/**
	 * 1 : 修改成功
	 * 0 : 身份验证失败
	 * */
	public int modifyPwd(long userId, String oldPwd, String newPwd);
	
	public List<Material> getMaterialListByLessonId(long lessonId);
	
	public void addCurri(Curriculum c);
	
	public List<Curriculum> getAllCurriculumList();
	
	public void updateCurriculum(Curriculum c);

	public void addEtonUser(EtonUser eu);
	
	public List<EtonUser> getAllEtonUserList();
	
	public void updateEtonUser(EtonUser eu);
	
	public void addSchool(School s);
	
	public List<School> getAllSchool();
	
	public void updateSchool(School s);
	
	public List<SchoolType> getSchoolTypeList();
	
}
