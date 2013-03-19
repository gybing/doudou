package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.SCSCCUser;

public interface SCSCCService {
	
	public SCSCCUser verifySCSCCUser(String userName, String passWd);
	
	public SCSCCUser getUser(long userId);
	
	public List<SCSCCUser> getAllSCSCCUserList();
	
	public List<SCSCCUser> getContactList(String username);
	
	public void updateLoginTime(long userId , String deviceToken);

	
}
