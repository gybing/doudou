package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.OfOfflineDao;
import com.doudoumobile.model.OfOffline;

public class OfOfflineDaoHibernate extends HibernateDaoSupport implements OfOfflineDao {

	@Override
	public List<OfOffline> getNotSendedOfflines() {
		String hql = "select oo.username,oo.messageID,oo.creationDate,oo.stanza,oo.sended from ofOffline oo where oo.sended = false";
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(hql.toString());
		query.addEntity("oo", OfOffline.class);
		List result = query.list();
		session.close();
        return (List<OfOffline>) (result);
	}

	@Override
	public void saveOffline(OfOffline ofOffline) {
		getHibernateTemplate().saveOrUpdate(ofOffline);
		getHibernateTemplate().flush();
	}


}
