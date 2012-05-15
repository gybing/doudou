package doudou.dao.impl;

import java.util.List;

import doudou.dao.DeviceTokenDao;
import doudou.util.dao.BaseEntityDao;
import doudou.util.dao.DatabaseDao;
import doudou.vo.DeviceToken;

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
