package com.doudoumobile.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

@SuppressWarnings("serial")
public class DeviceToken implements Serializable{
	
    @Id
    @Column(name = "userName", nullable = false)
	private String userName;
	
    @Column(name = "deviceTokenId", nullable = false, length = 255)
    private String deviceTokenId;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDeviceTokenId() {
		return deviceTokenId;
	}
	public void setDeviceTokenId(String deviceTokenId) {
		this.deviceTokenId = deviceTokenId;
	}
	
}