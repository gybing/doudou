package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.OfOfflineDao;
import com.doudoumobile.model.OfOffline;

public class OfOfflineDaoHibernate extends HibernateDaoSupport implements OfOfflineDao {

	@Override
	public List<OfOffline> getNotSendedOfflines() {
		return getHibernateTemplate().find("from OfOffline oo where sended = false");
	}

	@Override
	public void saveOffline(OfOffline ofOffline) {
		getHibernateTemplate().saveOrUpdate(ofOffline);
		getHibernateTemplate().flush();
	}


}
