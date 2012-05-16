package mayaya.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DeviceToken implements Serializable{
	
	private String deviceTokenId;
	private int userId;
	
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
