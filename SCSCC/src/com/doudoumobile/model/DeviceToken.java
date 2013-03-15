package com.doudoumobile.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

@SuppressWarnings("serial")
public class DeviceToken implements Serializable{
	
    @Id
    @Column(name = "userId", nullable = false)
	private int userId;
	
    @Column(name = "deviceTokenId", nullable = false, length = 255)
    private String deviceTokenId;
    
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDeviceTokenId() {
		return deviceTokenId;
	}
	public void setDeviceTokenId(String deviceTokenId) {
		this.deviceTokenId = deviceTokenId;
	}
	
}