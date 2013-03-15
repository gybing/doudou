package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.OfUserDao;
import com.doudoumobile.model.OfUser;
import com.doudoumobile.service.UserNotFoundException;

public class OfUserDaoHibernate extends HibernateDaoSupport implements OfUserDao {

    public OfUser getUser(Long id) {
        return (OfUser) getHibernateTemplate().get(OfUser.class, id);
    }

    public OfUser saveUser(OfUser user) {
        getHibernateTemplate().saveOrUpdate(user);
        getHibernateTemplate().flush();
        return user;
    }

    public void removeUser(Long id) {
        getHibernateTemplate().delete(getUser(id));
    }

    public boolean exists(Long id) {
        OfUser user = (OfUser) getHibernateTemplate().get(OfUser.class, id);
        return user != null;
    }

    @SuppressWarnings("unchecked")
    public List<OfUser> getUsers() {
        return getHibernateTemplate().find(
                "from User u order by id desc");
    }

    @SuppressWarnings("unchecked")
    public OfUser getUserByUsername(String username) throws UserNotFoundException {
        List users = getHibernateTemplate().find("from User where username=? and available = true",
                username);
        if (users == null || users.isEmpty()) {
            throw new UserNotFoundException("User '" + username + "' not found");
        } else {
            return (OfUser) users.get(0);
        }
    }

	@Override
	public OfUser verifyUser(String userName, String passWd) {
		List users = getHibernateTemplate().find("from User where username=? " +
					"and password=? and available = true",new String[]{userName,passWd});			
		
		if (users == null || users.isEmpty()) {
            return null;
        } else {
            return (OfUser) users.get(0);
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
	public OfUser getUser2ByName(String username) {
		List users = getHibernateTemplate().find("from User where username=?",
                username);
        if (users == null || users.isEmpty()) {
           return null;
        } else {
            return (OfUser) users.get(0);
        }
	}

}
