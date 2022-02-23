package com.formahei.service;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public boolean addUser(User user){
        return userDAO.addUser(user);
    }

    public List<User> getUserByRole(String role){
        return userDAO.findUserByRole(role);
    }

    public User getUserByLogin(String login){
       return userDAO.findUserByLogin(login);
    }

    public boolean updateAccount(String login, double amount){
        return userDAO.updateAccount(login, amount);
    }

    public boolean updateStatus(String login){
        return userDAO.updateStatus(login, "blocked");
    }

    public List<User> findUserByRole(String role) {
        return userDAO.findUserByRole(role);
    }
}
