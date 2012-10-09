package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.Material;

public interface EtonService {
	public EtonUser verifyEtonUser(String userName, String passWd);
	
	public EtonUser getUser(long userId);
	
	/**
	 * 1 : 修改成功
	 * 0 : 身份验证失败
	 * */
	public int modifyPwd(long userId, String oldPwd, String newPwd);
	
	public List<Material> getMaterialListByLessonId(long lessonId);
}
