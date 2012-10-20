package com.doudoumobile.service.impl;

import java.util.List;

import com.doudoumobile.dao.CurriculumDao;
import com.doudoumobile.dao.EtonUserDao;
import com.doudoumobile.dao.MaterialDao;
import com.doudoumobile.dao.SchoolDao;
import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.CurriculumToUser;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.Material;
import com.doudoumobile.model.School;
import com.doudoumobile.model.SchoolType;
import com.doudoumobile.service.EtonService;

public class EtonServiceImpl implements EtonService{

	EtonUserDao etonUserDao;
	MaterialDao materialDao;
	CurriculumDao curriculumDao;
	SchoolDao schoolDao;
	
	public void setEtonUserDao(EtonUserDao etonUserDao) {
		this.etonUserDao = etonUserDao;
	}
	
	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
	}
	
	public void setCurriculumDao(CurriculumDao curriculumDao) {
		this.curriculumDao = curriculumDao;
	}
	
	public void setSchoolDao(SchoolDao schoolDao) {
		this.schoolDao = schoolDao;
	}
	
	@Override
	public EtonUser verifyEtonUser(String userName, String passWd) {
		return (EtonUser) etonUserDao.verifyEtonUser(userName, passWd);
	}

	@Override
	public int modifyPwd(long userId, String oldPwd, String newPwd) {
		EtonUser user = etonUserDao.getUserById(userId);
		if (null != user && user.getPassWd().equals(oldPwd)) {
			etonUserDao.modifyPwd(userId , newPwd);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public EtonUser getUser(long userId) {
		EtonUser user = etonUserDao.getUserById(userId);
		return user;
	}

	@Override
	public List<Material> getMaterialListByLessonId(long lessonId) {
		return materialDao.getMaterialListByLessonId(lessonId);
	}

	@Override
	public void addCurri(Curriculum c) {
		curriculumDao.addCurriculum(c);
	}

	@Override
	public EtonUser addEtonUser(EtonUser eu) {
		return etonUserDao.addUser(eu);
	}

	@Override
	public void addSchool(School s) {
		schoolDao.addSchool(s);
	}

	@Override
	public List<Curriculum> getAllCurriculumList() {
		return curriculumDao.getAllCurriculumList();
	}

	@Override
	public void updateCurriculum(Curriculum c) {
		curriculumDao.updateCurriculum(c);
	}

	@Override
	public List<EtonUser> getAllEtonUserList() {
		return etonUserDao.getAllUser();
	}

	@Override
	public void updateEtonUser(EtonUser eu) {
		etonUserDao.updateUser(eu);
	}

	@Override
	public List<School> getAllSchool() {
		return schoolDao.getAllSchool();
	}

	@Override
	public void updateSchool(School s) {
		schoolDao.updateSchool(s);
	}

	@Override
	public List<SchoolType> getSchoolTypeList() {
		return schoolDao.getAllSchoolType();
	}

	@Override
	public List<Curriculum> getFirstClassCurriculumList() {
		return curriculumDao.getFirstClassCurriculumList();
	}

	@Override
	public Curriculum getCurriculumById(long id) {
		return curriculumDao.getCurriculumById(id);
	}

	@Override
	public School getSchoolById(long id) {
		return schoolDao.getSchoolById(id);
	}

	@Override
	public void deleteUser(long id) {
		etonUserDao.delete(id);
	}

	@Override
	public void deleteCurriculum(long id) {
		curriculumDao.delete(id);
		curriculumDao.deleteChildCurriculumByParentId(id);
	}

	@Override
	public void deleteSchool(long id) {
		schoolDao.delete(id);
	}

	@Override
	public void deleteSchoolType(long id) {
		schoolDao.deleteSchoolType(id);
	}

	@Override
	public void addSchoolType(SchoolType schoolType) {
		schoolDao.addSchoolType(schoolType);
		
	}

	@Override
	public SchoolType getSchoolTypeById(long id) {
		return schoolDao.getSchoolTypeById(id);
	}

	@Override
	public void updateSchoolType(SchoolType st) {
		schoolDao.updateSchoolType(st);
	}

	@Override
	public List<School> getSchoolByTypeId(long typeId) {
		return schoolDao.getSchoolByTypeId(typeId);
	}

	@Override
	public void addCurriToEtonUser(CurriculumToUser ctu) {
		curriculumDao.addCurriculumToUser(ctu);
	}

	@Override
	public void deleteCurriculumToUserByUserId(long userId) {
		curriculumDao.deleteCurriculumToUserByUserId(userId);
	}

	@Override
	public void resetPwd(long id, String resetPwd) {
		etonUserDao.resetPwd(id, resetPwd);
	}

}
