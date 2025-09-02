package com.aurionpro.dao;

import java.util.List;

import com.aurionpro.model.User;

public interface IUserDao {
    boolean registerUser(User user);  // insert into users + auth
    public boolean isEmailOrMobileExists(String email, String mobileNumber);  // check duplicate email
    User getUserByEmail(String email); // fetch user by email
    User checkLoginCredintials(String userName,String password);
    public boolean updateUser(User user);
    public boolean checkPassword(int userId, String currentPassword);
    public void updatePassword(int userId, String newPassword);
    public List<User> getAllNonAdminUsers();
    public boolean updateUserActivationStatus(int userId, boolean isActive, String reason);
}
