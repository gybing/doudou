package com.doudoumobile.service.impl;

import java.util.List;

import com.doudoumobile.dao.EtonUserDao;
import com.doudoumobile.dao.MaterialDao;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.Material;
import com.doudoumobile.service.EtonService;

public class EtonServiceImpl implements EtonService{

	EtonUserDao etonUserDao;
	MaterialDao materialDao;
	
	public void setEtonUserDao(EtonUserDao etonUserDao) {
		this.etonUserDao = etonUserDao;
	}
	
	public void setMaterialDao(MaterialDao materialDao) {
		this.materialDao = materialDao;
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
	
	

}
