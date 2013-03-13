package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.OfUser;

public interface UserService {

    public OfUser getUser(String userId);

    public List<OfUser> getUsers();

    public OfUser saveUser(OfUser user) throws UserExistsException;

    public OfUser getUserByUsername(String username) throws UserNotFoundException;

    public void removeUser(Long userId);
    
    public OfUser verifyUser(String userName, String passWd);
    
    public OfUser getUser2ByName(String username);
    
    public void updateUserAvailable(String username , boolean available);
    
    public void updateUser(OfUser user);
}
