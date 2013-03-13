package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.CurriculumToUser;
import com.doudoumobile.model.SCSCCUser;
import com.doudoumobile.model.Lesson;
import com.doudoumobile.model.Material;
import com.doudoumobile.model.NotificationMO;

public interface EtonService {
	public SCSCCUser verifyEtonUser(String userName, String passWd);
	
	public SCSCCUser getUser(long userId);
	
	/**
	 * 1 : 修改成功
	 * 0 : 身份验证失败
	 * */
	public int modifyPwd(long userId, String oldPwd, String newPwd);
	
	public SCSCCUser addEtonUser(SCSCCUser eu);
	
	public List<SCSCCUser> getAllEtonUserList();
	
	public void updateEtonUser(SCSCCUser eu);
	
	public void deleteUser(long id);
	
	public void resetPwd(long id, String resetPwd);
	
	public void updateLoginTime(long userId , String deviceToken);
	
	
}
