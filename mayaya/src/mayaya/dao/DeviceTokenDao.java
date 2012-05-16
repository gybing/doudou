package mayaya.dao;

import java.util.List;

import mayaya.util.dao.EntityDao;
import mayaya.vo.DeviceToken;

public interface DeviceTokenDao extends EntityDao<DeviceToken, Integer> {
	List<DeviceToken> getDeviceTokenList();
	List<String> getDeviceTokenByUserId(int userId);
	
}
