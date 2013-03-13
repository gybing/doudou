package com.doudoumobile.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataIntegrityViolationException;

import com.doudoumobile.dao.OfUserDao;
import com.doudoumobile.model.OfUser;
import com.doudoumobile.service.UserExistsException;
import com.doudoumobile.service.UserNotFoundException;
import com.doudoumobile.service.UserService;

public class UserServiceImpl implements UserService {

    protected final Log log = LogFactory.getLog(getClass());

    private OfUserDao userDao;

    public void setUserDao(OfUserDao userDao) {
        this.userDao = userDao;
    }

    public OfUser getUser(String userId) {
        return userDao.getUser(new Long(userId));
    }

    public List<OfUser> getUsers() {
        return userDao.getUsers();
    }

    public OfUser saveUser(OfUser user) throws UserExistsException {
        try {
            return userDao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            log.warn(e.getMessage());
            return user;
        } catch (EntityExistsException e) { // needed for JPA
            e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername()
                    + "' already exists!");
        }
    }

    public OfUser getUserByUsername(String username) throws UserNotFoundException {
        return (OfUser) userDao.getUserByUsername(username);
    }

    public void removeUser(Long userId) {
        log.debug("removing user: " + userId);
        userDao.removeUser(userId);
    }

	@Override
	public OfUser verifyUser(String userName, String passWd) {
		return (OfUser) userDao.verifyUser(userName, passWd);
	}

	@Override
	public OfUser getUser2ByName(String username) {
		return userDao.getUser2ByName(username);
	}
	
	@Override
	public void updateUserAvailable(String username , boolean available) {
		userDao.updateUserAvailable(username, available);
	}

	@Override
	public void updateUser(OfUser user) {
		//userDao.updateUser(user);
	}

}
