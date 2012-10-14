package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.EtonUser;

public interface EtonUserDao {
	EtonUser verifyEtonUser(String userName, String passWd);
	void modifyPwd(long userId , String passWd);
	
	EtonUser getUserById(long userId);
	
	EtonUser addUser(EtonUser user);
	
	List<EtonUser> getAllUser();
	
	void updateUser(EtonUser user);
	
	void delete(long id);
}
