package mayaya.dao.impl;

import java.util.List;

import mayaya.dao.DeviceTokenDao;
import mayaya.util.dao.BaseEntityDao;
import mayaya.util.dao.DatabaseDao;
import mayaya.vo.DeviceToken;

public class DeviceTokenDaoImpl extends BaseEntityDao<DeviceToken, Integer> implements DeviceTokenDao {

	public DeviceTokenDaoImpl(DatabaseDao database) {
		super(database);
	}
	
	@Override
	public String getNamespace() {
		return "DeviceToken";
	}

	@Override
	public List<DeviceToken> getDeviceTokenList() {
		return reads("getDeviceTokenList",null);
	}

	@Override
	public List<String> getDeviceTokenByUserId(int userId) {
		return readObjects("getDeviceTokenByUserId",userId);
	}

}
