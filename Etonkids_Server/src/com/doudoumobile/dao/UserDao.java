package com.doudoumobile.dao;

import java.util.List;


import com.doudoumobile.model.User;
import com.doudoumobile.service.UserNotFoundException;

/** 
 * User DAO (Data Access Object) interface. 
 */
public interface UserDao {

    public User getUser(Long id);

    public User saveUser(User user);

    public void removeUser(Long id);

    public boolean exists(Long id);

    public List<User> getUsers();

    public User getUserByUsername(String username) throws UserNotFoundException;
    
    public User verifyUser(String userName, String passWd);

}
