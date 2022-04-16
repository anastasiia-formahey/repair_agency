package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.UserService;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;
import com.formahei.service.UserValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddUserCommand implements Command {
    private static final Logger log = Logger.getLogger(AddUserCommand.class);

    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp){
        log.debug("AddUserCommand starts");

        UserService userService = new UserService(UserDAO.getInstance());
        User user = new User();
        String login = req.getParameter(Constants.LOGIN);
        String password = req.getParameter(Constants.PASSWORD);
        String firstName = req.getParameter(Constants.FIRST_NAME);
        String lastName = req.getParameter(Constants.LAST_NAME);
        String email = req.getParameter(Constants.EMAIL);
        String role = req.getParameter(Constants.ROLE);
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
            String valid = new UserValidator().validate(user);
            if(valid == null){
                if(userService.getUserByLogin(login) == null){
                    userService.addUser(user);
                    path = Path.PAGE_ADMIN_HOME;
                }else {
                    errorMessage = "User has already exists";
                    req.setAttribute(Constants.ERROR_MESSAGE, errorMessage);
                    path = Path.PAGE_ADD_USER_BY_ADMIN;
                }
            }else {
                errorMessage = "Invalid data";
                req.setAttribute(Constants.ERROR_MESSAGE, errorMessage);
                path = Path.PAGE_ADD_USER_BY_ADMIN;
            }
        }

        log.debug("AddUserCommand finished");
        return new CommandResult(path, true);
    }
}
