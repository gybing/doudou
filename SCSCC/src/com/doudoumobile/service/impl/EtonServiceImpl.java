package com.doudoumobile.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.doudoumobile.dao.OfUserDao;
import com.doudoumobile.dao.SCSCCUserDao;
import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.service.EtonService;

public class EtonServiceImpl implements EtonService{

	SCSCCUserDao scsccUserDao;
	OfUserDao userDao;
	SessionFactory sessionFactory;
	String zipDesPath = "";
	
	public void setZipDesPath(String zipDesPath) {
		this.zipDesPath = zipDesPath;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setUserDao(OfUserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setScsccUserDao(SCSCCUserDao scsccUserDao) {
		this.scsccUserDao = scsccUserDao;
	}
	
	@Override
	public SCSCCUser verifyEtonUser(String userName, String passWd) {
		return (SCSCCUser) scsccUserDao.verifyEtonUser(userName, passWd);
	}

	@Override
	public int modifyPwd(long userId, String oldPwd, String newPwd) {
		SCSCCUser user = scsccUserDao.getUserById(userId);
		if (null != user && user.getPassWd().equals(oldPwd)) {
			scsccUserDao.modifyPwd(userId , newPwd);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public SCSCCUser getUser(long userId) {
		SCSCCUser user = scsccUserDao.getUserById(userId);
		return user;
	}

	@Override
	public SCSCCUser addEtonUser(SCSCCUser eu) {
		return scsccUserDao.addUser(eu);
	}

	@Override
	public List<SCSCCUser> getAllEtonUserList() {
		return scsccUserDao.getAllUser();
	}

	@Override
	public void updateEtonUser(SCSCCUser eu) {
		scsccUserDao.updateUser(eu);
	}

	@Override
	public void deleteUser(long id) {
		scsccUserDao.delete(id);
	}


	@Override
	public void resetPwd(long id, String resetPwd) {
		scsccUserDao.resetPwd(id, resetPwd);
	}

	@Override
	public void updateLoginTime(long userId , String deviceToken) {
		userDao.updateLoginTime(userId, deviceToken);
	}


}
