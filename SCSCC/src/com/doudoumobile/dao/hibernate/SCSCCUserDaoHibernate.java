package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.SCSCCUserDao;
import com.doudoumobile.model.SCSCCUser;

public class SCSCCUserDaoHibernate extends HibernateDaoSupport implements SCSCCUserDao {

	@Override
	public SCSCCUser verifyEtonUser(String userName, String passWd) {
		List users = getHibernateTemplate().find("from EtonUser where username=? " +
				"and password=? and available = true",new String[]{userName,passWd});			
	
		if (users == null || users.isEmpty()) {
	        return null;
	    } else {
	        return (SCSCCUser) users.get(0);
	    }
	}

	@Override
	public void modifyPwd(long userId, String passWd) {
		Session session = getSessionFactory().getCurrentSession();  
		session.beginTransaction();  
		Query query = session.createQuery("update EtonUser set password = '"+ passWd + "' where id = " + userId);  
		query.executeUpdate();  
		session.getTransaction().commit();  
	}

	@Override
	public SCSCCUser getUserById(long userId) {
	    return (SCSCCUser) getHibernateTemplate().get(SCSCCUser.class, userId);
	}

	@Override
	public SCSCCUser addUser(SCSCCUser user) {
		getHibernateTemplate().saveOrUpdate(user);
		getHibernateTemplate().flush();
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SCSCCUser> getAllUser() {
		return getHibernateTemplate().find(
        "from EtonUser eu order by id desc");
	}

	@Override
	public void updateUser(SCSCCUser user) {
		getHibernateTemplate().update(user);
		getHibernateTemplate().flush();
		
	}

	@Override
	public void delete(long id) {
		getHibernateTemplate().delete(getUserById(id));
		
	}

	@Override
	public void resetPwd(long id, String resetPwd) {
		SCSCCUser user = getUserById(id);
		user.setPassWd(resetPwd);
		updateUser(user);
	}
	
	
}
