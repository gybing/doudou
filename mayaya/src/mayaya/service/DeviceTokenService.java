package mayaya.service;

import mayaya.vo.DeviceToken;

public interface DeviceTokenService {
	void addDeviceToken(DeviceToken dt);
	void updateDeviceTokenInactive(String deviceToken);
}
