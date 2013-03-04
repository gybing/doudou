package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.UserDao;
import com.doudoumobile.model.Curriculum;
import com.doudoumobile.model.User;
import com.doudoumobile.service.UserNotFoundException;

public class UserDaoHibernate extends HibernateDaoSupport implements UserDao {

    public User getUser(Long id) {
        return (User) getHibernateTemplate().get(User.class, id);
    }

    public User saveUser(User user) {
        getHibernateTemplate().saveOrUpdate(user);
        getHibernateTemplate().flush();
        return user;
    }

    public void removeUser(Long id) {
        getHibernateTemplate().delete(getUser(id));
    }

    public boolean exists(Long id) {
        User user = (User) getHibernateTemplate().get(User.class, id);
        return user != null;
    }

    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return getHibernateTemplate().find(
                "from User u order by id desc");
    }

    @SuppressWarnings("unchecked")
    public User getUserByUsername(String username) throws UserNotFoundException {
        List users = getHibernateTemplate().find("from User where username=? and available = true",
                username);
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException("User '" + username + "' not found");
        } else {
            return (User) users.get(0);
        }
    }

	@Override
	public User verifyUser(String userName, String passWd) {
		List users = getHibernateTemplate().find("from User where username=? " +
					"and password=? and available = true",new String[]{userName,passWd});			
		
		if (users == null || users.isEmpty()) {
            return null;
        } else {
            return (User) users.get(0);
        }
	}

	@Override
	public List<String> getUserNameListByEtonId(long etonIdList) {
		List result = getHibernateTemplate().find("select username from User where eton_id=? and available = true", etonIdList);
		return result;
	}

	@Override
	public void updateLoginTime(long userId, String deviceToken) {
		String sql = "update APN_User set lastLoginTime = now() where eton_id= " + userId +
				" and apn_username= '" + (userId+"_"+deviceToken) + "' and available = true";			
	
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		session.close();
	}

	@Override
	public void updateUserAvailable(String userName , boolean available) {
		String sql = "update APN_User set available = " + available + " where apn_username = '" + userName + "'";			

		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		session.close();
	}

	@Override
	public User getUser2ByName(String username) {
		List users = getHibernateTemplate().find("from User where username=?",
                username);
        if (users == null || users.isEmpty()) {
           return null;
        } else {
            return (User) users.get(0);
        }
	}

	@Override
	public void updateUser(User user) {
		String sql = "update APN_User set available = " + user.isAvailable() 
		+ ", lastLoginTime = now(), password = '" + user.getPassword() + "' where apn_username = '" + user.getUsername() + "'";			

		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();
		session.close();
	}

}
