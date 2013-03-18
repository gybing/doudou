package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.SCSCCUser;

public interface SCSCCUserDao {
	
	SCSCCUser verifyEtonUser(String userName, String passWd);
	
	void modifyPwd(long userId , String passWd);
	
	SCSCCUser getUserById(long userId);
	
	SCSCCUser addUser(SCSCCUser user);
	
	List<SCSCCUser> getAllUser();
	
	List<SCSCCUser> getContactList(String userid);

	void updateUser(SCSCCUser user);
	
	void delete(long id);
	
	public void resetPwd(long id, String resetPwd) ;
	
	boolean checkExists(String userName);
}
