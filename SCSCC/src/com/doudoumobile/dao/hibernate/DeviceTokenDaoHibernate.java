package com.doudoumobile.dao.hibernate;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.DeviceTokenDao;
import com.doudoumobile.model.DeviceToken;

public class DeviceTokenDaoHibernate extends HibernateDaoSupport implements DeviceTokenDao {

	@Override
	public DeviceToken getDeviceTokenByUserId(int userId) {
		return (DeviceToken) getHibernateTemplate().get(DeviceToken.class, userId);
	}
	
	@Override
	public DeviceToken saveDeviceToken(DeviceToken dt) {
        getHibernateTemplate().saveOrUpdate(dt);
        getHibernateTemplate().flush();
        return dt;
    }

}
