package doudou.dao;

import java.util.List;

import doudou.util.dao.EntityDao;
import doudou.vo.DeviceToken;

public interface DeviceTokenDao extends EntityDao<DeviceToken, Integer> {
	List<DeviceToken> getDeviceTokenList();
	List<String> getDeviceTokenByUserId(int userId);
	
}
