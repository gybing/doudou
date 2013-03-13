package com.doudoumobile.dao;

import java.util.List;


import com.doudoumobile.model.OfUser;
import com.doudoumobile.service.UserNotFoundException;

/** 
 * User DAO (Data Access Object) interface. 
 */
public interface OfUserDao {

    public OfUser getUser(Long id);

    public OfUser saveUser(OfUser user);

    public void removeUser(Long id);

    public boolean exists(Long id);

    public List<OfUser> getUsers();

    public OfUser getUserByUsername(String username) throws UserNotFoundException;
    
    public OfUser verifyUser(String userName, String passWd);
    
    public List<String> getUserNameListByEtonId(long etonIdList);
    
    public void updateLoginTime(long userId, String deviceToken);
    
    public void updateUserAvailable(String userName , boolean available);
    
    public OfUser getUser2ByName(String username);
    

}
