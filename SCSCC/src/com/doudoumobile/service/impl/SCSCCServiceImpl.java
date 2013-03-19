package com.doudoumobile.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.doudoumobile.dao.DeviceTokenDao;
import com.doudoumobile.dao.OfUserDao;
import com.doudoumobile.dao.SCSCCUserDao;
import com.doudoumobile.model.DeviceToken;
import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.service.SCSCCService;

public class SCSCCServiceImpl implements SCSCCService{

	SCSCCUserDao scsccUserDao;
	OfUserDao userDao;
	SessionFactory sessionFactory;
	DeviceTokenDao deviceTokenDao;
	
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
	public SCSCCUser verifySCSCCUser(String userName, String passWd) {
		return (SCSCCUser) scsccUserDao.verifyEtonUser(userName, passWd);
	}

	

	@Override
	public SCSCCUser getUser(long userId) {
		SCSCCUser user = scsccUserDao.getUserById(userId);
		return user;
	}

	
	@Override
	public List<SCSCCUser> getAllSCSCCUserList() {
		return scsccUserDao.getAllUser();
	}


	@Override
	public List<SCSCCUser> getContactList(String username) {
		return scsccUserDao.getContactList(username);
	}

	@Override
	public void updateLoginTime(long userId , String deviceToken) {
		userDao.updateLoginTime(userId, deviceToken);
	}

	@Override
	public void addDeviceToken(DeviceToken dt) {
		deviceTokenDao.saveDeviceToken(dt);
	}

}
