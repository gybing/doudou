package com.doudoumobile.dao;

import com.doudoumobile.model.DeviceToken;

public interface DeviceTokenDao {
	DeviceToken getDeviceTokenByUserId(int userId);
	
	DeviceToken saveDeviceToken(DeviceToken dt);
}
