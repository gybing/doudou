package com.doudoumobile.dao;

import java.util.List;

import com.doudoumobile.model.OfOffline;

public interface OfOfflineDao {
	List<OfOffline> getNotSendedOfflines();
	
	void saveOffline(OfOffline ofOffline);
	
	int getOOCountByUsername(String username);
}
