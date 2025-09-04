package com.aurionpro.service;

import java.util.List;

import com.aurionpro.dao.IUserDao;
import com.aurionpro.dao.UserDaoImpl;
import com.aurionpro.model.AdminViewUserDto;
import com.aurionpro.model.User;
import com.aurionpro.utils.FormatChecker;

public class UserService {

    private IUserDao userDao;

    public UserService() {
        userDao = new UserDaoImpl();
    }

    public User login(String username, String password) {
        return userDao.checkLoginCredintials(username, password);
    }

    public String registerUser(User user) {

        if (userDao.isEmailOrMobileExists(user.getEmail(), user.getMobileNumber())) {
            return "Email or Mobile already registered.";
        }

        boolean isRegistered = userDao.registerUser(user);

        if (isRegistered) {
            return "Registration successful! Your username is: " 
                   + user.getFirstName().toLowerCase()
                   + " and password is: "
                   + user.getFirstName().toLowerCase() + "@123"
                   + " (please change at first login)";
        } else {
            return "Registration failed! Please try again.";
        }
    }

    // Get user by email
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public boolean updateUser(User user) {
        return userDao.updateUser(user);
    }

    public boolean validateCurrentPassword(int userId, String currentPassword) {
        return userDao.checkPassword(userId, currentPassword);
    }

   
    public String changePassword(int userId, String currentPassword, String newPassword) {

       
        if (!validateCurrentPassword(userId, currentPassword)) {
            return "Current password is incorrect!";
        }

       
        if (!FormatChecker.isValidPassword(newPassword)) {
            return "New password must contain at least 8 characters, "
                 + "including uppercase, lowercase, digit, and special character.";
        }

        
        userDao.updatePassword(userId, newPassword);
        return "Password updated successfully!";
    }
    
    public List<AdminViewUserDto> getAllNonAdminUsers() {
        return userDao.getAllNonAdminUsers();
    }

    public boolean updateUserActiveStatus(int userId, boolean isActive,String Reason) {
        return userDao.updateUserActivationStatus(userId, isActive,Reason);
    }

	public String getDeactivationReason(int userId) {
		// TODO Auto-generated method stub
		return null;
	}


}
