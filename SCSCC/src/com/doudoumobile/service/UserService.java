package com.doudoumobile.service;

import java.util.List;

import com.doudoumobile.model.User;

public interface UserService {

    public User getUser(String userId);

    public List<User> getUsers();

    public User saveUser(User user) throws UserExistsException;

    public User getUserByUsername(String username) throws UserNotFoundException;

    public void removeUser(Long userId);
    
    public User verifyUser(String userName, String passWd);
    
    public User getUser2ByName(String username);
    
    public void updateUserAvailable(String username , boolean available);
    
    public void updateUser(User user);
}
