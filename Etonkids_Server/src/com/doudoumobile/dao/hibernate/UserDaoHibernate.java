package com.doudoumobile.dao.hibernate;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.doudoumobile.dao.UserDao;
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
        List users = getHibernateTemplate().find("from User where username=?",
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

}
