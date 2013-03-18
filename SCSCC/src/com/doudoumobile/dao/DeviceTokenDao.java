package com.doudoumobile.dao;

import com.doudoumobile.model.DeviceToken;

public interface DeviceTokenDao {
	DeviceToken getDeviceTokenByUsername(String userName);
	
	DeviceToken saveDeviceToken(DeviceToken dt);
}
