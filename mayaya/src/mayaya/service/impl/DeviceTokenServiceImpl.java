package mayaya.service.impl;

import mayaya.dao.DaoFactory;
import mayaya.dao.DeviceTokenDao;
import mayaya.service.DeviceTokenService;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.DeviceToken;

public class DeviceTokenServiceImpl implements DeviceTokenService {

	
	private DatabaseDao myDatabaseDao;
	private DeviceTokenDao deviceTokenDao;
	
	private static DeviceTokenService instance = new DeviceTokenServiceImpl();
	
	public static DeviceTokenService getInstance(){
		return instance;
	}
	
	private DeviceTokenServiceImpl() {
		myDatabaseDao = DaoFactory.getInstance().getMyDatabaseDao();
		deviceTokenDao = myDatabaseDao.getEntityDao(DeviceTokenDao.class);
	}
	
	@Override
	public void addDeviceToken(DeviceToken dt) {
		deviceTokenDao.create(dt);
	}

}
