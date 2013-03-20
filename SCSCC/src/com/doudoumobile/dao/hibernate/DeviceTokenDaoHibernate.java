package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.DeviceTokenDao;
import com.doudoumobile.model.DeviceToken;

public class DeviceTokenDaoHibernate extends HibernateDaoSupport implements DeviceTokenDao {

	@Override
	public DeviceToken getDeviceTokenByUsername(String userName) {
		List<DeviceToken> ll = getHibernateTemplate().find("from DeviceToken where username = '" + userName + "'");
		if (null != ll && !ll.isEmpty()) {
			return ll.get(0);
		}
		return null;
	}
	
	@Override
	public DeviceToken saveDeviceToken(DeviceToken dt) {
        getHibernateTemplate().saveOrUpdate(dt);
        getHibernateTemplate().flush();
        return dt;
    }

}
