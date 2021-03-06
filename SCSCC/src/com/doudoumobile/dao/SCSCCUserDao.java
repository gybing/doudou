package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.SCSCCUser;

public interface SCSCCUserDao {
	
	SCSCCUser verifyEtonUser(String userName, String passWd);
	
	void modifyPwd(long userId , String passWd);
	
	SCSCCUser getUserById(String userName);
	
	SCSCCUser addUser(SCSCCUser user);
	
	List<SCSCCUser> getAllUser();
	
	List<SCSCCUser> getContactList(String username);

	void updateUser(SCSCCUser user);
	
	//void delete(long id);
	
	//public void resetPwd(long id, String resetPwd) ;
	
	boolean checkExists(String userName);
}
