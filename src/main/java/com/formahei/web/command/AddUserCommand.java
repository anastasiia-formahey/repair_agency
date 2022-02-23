package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.Role;
import com.formahei.entity.User;
import com.formahei.service.UserService;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserCommand implements Command {
    public String execute(HttpServletRequest req, HttpServletResponse resp){
        UserService userService = new UserService(UserDAO.getInstance());
        User user = new User();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String role = req.getParameter("role");
        String errorMessage;
        String path;
        if(login.length() < 1 || password.length() < 1 ||firstName.length() < 1
                || lastName.length() < 1|| email.length() < 1){
            errorMessage = "All fields must not be empty";
            req.setAttribute(Constants.ERROR_MESSAGE, errorMessage);
            path = Path.PAGE_ADD_USER_BY_ADMIN;
        }else {
            user.setLogin(login);
            user.setPass(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setAccount(0);
            user.setStatus("unblocked");
            user.setRole(role);
            if(userService.getUserByLogin(login) == null){
                userService.addUser(user);
                path = Path.PAGE_ADMIN_HOME;
            }else {
                errorMessage = "User has already exists";
                req.setAttribute(Constants.ERROR_MESSAGE, errorMessage);
                path = Path.PAGE_ADD_USER_BY_ADMIN;
            }
        }
        return path;
    }
}
