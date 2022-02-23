package com.formahei.web.command;

import com.formahei.service.UserService;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;
import com.formahei.dao.UserDAO;
import com.formahei.entity.Role;
import com.formahei.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command{
    private static final Logger log = Logger.getLogger(RegisterCommand.class);

    public String execute(HttpServletRequest req, HttpServletResponse resp){
        UserService userService = new UserService(UserDAO.getInstance());
        User user = new User();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String errorMessage;
        String path;
        if(login.length() < 1 || password.length() < 1 ||firstName.length() < 1
                || lastName.length() < 1|| email.length() < 1){
            errorMessage = "All fields must not be empty";
            req.setAttribute(Constants.ERROR_MESSAGE, errorMessage);
            path = Path.PAGE_REGISTER;
        }else {
        user.setLogin(login);
        user.setPass(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAccount(0);
        user.setStatus("unblocked");
        user.setRole(Role.CLIENT.name());
            if(userService.getUserByLogin(login) == null){
                userService.addUser(user);
                path = Path.PAGE_LOGIN;
            }else {
                errorMessage = "User has already exists";
                req.setAttribute(Constants.ERROR_MESSAGE, errorMessage);
                path = Path.PAGE_REGISTER;
            }
        }
        return path;
    }
}
