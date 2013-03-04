package com.doudoumobile.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.doudoumobile.dao.SCSCCUserDao;
import com.doudoumobile.dao.LessonDao;
import com.doudoumobile.dao.UserDao;
import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.model.Lesson;
import com.doudoumobile.service.EtonService;
import com.doudoumobile.util.Config;
import com.doudoumobile.xmpp.push.NotificationManager;

public class EtonServiceImpl implements EtonService{

	SCSCCUserDao etonUserDao;
	LessonDao lessonDao;
	UserDao userDao;
	SessionFactory sessionFactory;
	String zipDesPath = "";
	
	public void setZipDesPath(String zipDesPath) {
		this.zipDesPath = zipDesPath;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setLessonDao (LessonDao lessonDao) {
		this.lessonDao = lessonDao;
	}
	public void setEtonUserDao(SCSCCUserDao etonUserDao) {
		this.etonUserDao = etonUserDao;
	}
	
	@Override
	public SCSCCUser verifyEtonUser(String userName, String passWd) {
		return (SCSCCUser) etonUserDao.verifyEtonUser(userName, passWd);
	}

	@Override
	public int modifyPwd(long userId, String oldPwd, String newPwd) {
		SCSCCUser user = etonUserDao.getUserById(userId);
		if (null != user && user.getPassWd().equals(oldPwd)) {
			etonUserDao.modifyPwd(userId , newPwd);
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public SCSCCUser getUser(long userId) {
		SCSCCUser user = etonUserDao.getUserById(userId);
		return user;
	}

	@Override
	public SCSCCUser addEtonUser(SCSCCUser eu) {
		return etonUserDao.addUser(eu);
	}

	@Override
	public List<SCSCCUser> getAllEtonUserList() {
		return etonUserDao.getAllUser();
	}

	@Override
	public void updateEtonUser(SCSCCUser eu) {
		etonUserDao.updateUser(eu);
	}

	@Override
	public void deleteUser(long id) {
		etonUserDao.delete(id);
	}


	@Override
	public void resetPwd(long id, String resetPwd) {
		etonUserDao.resetPwd(id, resetPwd);
	}

	@Override
	public boolean addLesson(Lesson lesson) {
		boolean result = true;
		if (result) {
			//TODO 根据时间
			System.out.println("Notify begins");
			notify(lesson.getCurriculumId());
		}
		Session session = sessionFactory.getCurrentSession();
		session.close();
		return result;
	}
	
	@Override
	public boolean notify(long curriculumId) {
		//curriculumDao.get
		List<Long> userIdList = null;
		System.out.println("To notify -> user id : " + userIdList);
		for (long userId : userIdList) {
			List<String> apnUserNameList = userDao.getUserNameListByEtonId(userId);
			NotificationManager nm = new NotificationManager();
			String apiKey = Config.getString("apiKey", "");
			String title = "New lesson available";
			String message = "You have got a new lesson to download";
			for (String username : apnUserNameList) {
				nm.sendNotifications(apiKey, username,userId, title, message, "");
			}
		}
		return true;
	}

	@Override
	public void updateLoginTime(long userId , String deviceToken) {
		userDao.updateLoginTime(userId, deviceToken);
	}


}
