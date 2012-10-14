package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.EtonUserDao;
import com.doudoumobile.model.EtonUser;
import com.doudoumobile.model.User;

public class EtonUserDaoHibernate extends HibernateDaoSupport implements EtonUserDao {

	@Override
	public EtonUser verifyEtonUser(String userName, String passWd) {
		List users = getHibernateTemplate().find("from EtonUser where username=? " +
				"and password=? and available = true",new String[]{userName,passWd});			
	
		if (users == null || users.isEmpty()) {
	        return null;
	    } else {
	        return (EtonUser) users.get(0);
	    }
	}

	@Override
	public void modifyPwd(long userId, String passWd) {
		Session session = getSessionFactory().getCurrentSession();  
		session.beginTransaction();  
		Query query = session.createQuery("update EtonUser eu set eu.password = '"+ passWd + "' where id = " + userId);  
		query.executeUpdate();  
		session.getTransaction().commit();  
	}

	@Override
	public EtonUser getUserById(long userId) {
	    return (EtonUser) getHibernateTemplate().get(EtonUser.class, userId);
	}

	@Override
	public EtonUser addUser(EtonUser user) {
		getHibernateTemplate().saveOrUpdate(user);
		getHibernateTemplate().flush();
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EtonUser> getAllUser() {
		return getHibernateTemplate().find(
        "from EtonUser eu order by id desc");
	}

	@Override
	public void updateUser(EtonUser user) {
		getHibernateTemplate().update(user);
		getHibernateTemplate().flush();
		
	}
	
	
}
