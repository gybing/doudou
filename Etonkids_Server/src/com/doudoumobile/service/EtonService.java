package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.CurriculumToUser;
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

	public EtonUser addEtonUser(EtonUser eu);
	
	public List<EtonUser> getAllEtonUserList();
	
	public void updateEtonUser(EtonUser eu);
	
	public void addSchool(School s);
	
	public List<School> getAllSchool();
	
	public void updateSchool(School s);
	
	public List<SchoolType> getSchoolTypeList();
	
	public List<Curriculum> getFirstClassCurriculumList();
	
	public Curriculum getCurriculumById(long id);
	
	public School getSchoolById(long id);
	
	public void deleteUser(long id);
	public void deleteCurriculum(long id);
	public void deleteSchool(long id);
	public void deleteSchoolType(long id);
	public void addSchoolType(SchoolType schoolType);
	public SchoolType getSchoolTypeById(long id);
	public void updateSchoolType(SchoolType st);
	
	public List<School> getSchoolByTypeId(long typeId);
	
	public void addCurriToEtonUser(CurriculumToUser ctu);
	
	public void deleteCurriculumToUserByUserId(long userId);
	
}
