package mayaya.service.impl;

import mayaya.service.DeviceTokenService;
import mayaya.vo.DeviceToken;
import junit.framework.TestCase;

public class DeviceTokenServiceImplTest extends TestCase {

	DeviceTokenService s = DeviceTokenServiceImpl.getInstance();
	
	public void testAddDeviceToken() {
		DeviceToken dt = new DeviceToken();
		dt.setDeviceTokenId("asdfasdfasdfasdfasdfsaf");
		dt.setUserId(4);
		s.addDeviceToken(dt);
	}

}
