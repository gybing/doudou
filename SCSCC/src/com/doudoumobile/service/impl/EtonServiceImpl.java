package com.doudoumobile.service.impl;

import java.util.List;

import org.hibernate.SessionFactory;

import com.doudoumobile.dao.OfUserDao;
import com.doudoumobile.dao.SCSCCUserDao;
import com.doudoumobile.model.OfUser;
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

	/**
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
	*/

	/**
	 * @return 返回结果：
	 * 1 -> 添加成功
	 * 0 -> 用户名已存在
	 * -1 -> 程序错误
	 * */
	@Override
	public int addSCSCCUser(SCSCCUser eu) {
		//TODO transation
		if (scsccUserDao.checkExists(eu.getUserName())) {
			return 0;
		} else {
			if (null != scsccUserDao.addUser(eu)) {
				OfUser user = new OfUser();
				user.setUsername(eu.getUserName());
				user.setEncryptedPassword(eu.getPassWd());
				userDao.saveUser(user);
				return 1;
			} else {
				return -1;
			}
			
		}
	}

	@Override
	public List<SCSCCUser> getAllEtonUserList() {
		return scsccUserDao.getAllUser();
	}

	@Override
	public void updateEtonUser(SCSCCUser eu) {
		scsccUserDao.updateUser(eu);
	}

	/**
	@Override
	public void deleteUser(long id) {
		scsccUserDao.delete(id);
	}


	@Override
	public void resetPwd(long id, String resetPwd) {
		scsccUserDao.resetPwd(id, resetPwd);
	}
	*/

	@Override
	public void updateLoginTime(long userId , String deviceToken) {
		userDao.updateLoginTime(userId, deviceToken);
	}


}
