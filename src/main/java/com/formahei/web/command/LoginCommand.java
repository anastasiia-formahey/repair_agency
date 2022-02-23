package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;

public class LoginCommand implements Command {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    public String execute(HttpServletRequest req, HttpServletResponse resp){
        User user = null;
        String path;
        String login = req.getParameter(Constants.LOGIN);
        String password = req.getParameter(Constants.PASSWORD);
        String errorMessage;
        String attribute = Constants.ERROR_MESSAGE;
        if(login.length() < 1 || password.length() < 1){
            errorMessage = "Fields login and password can't be empty";
            req.setAttribute(attribute, errorMessage);
        }else {
        user = UserDAO.getInstance().findUserByLogin(login);
            if(user == null){
                errorMessage = "User not registered";
                req.setAttribute(attribute, errorMessage);
                return  Path.PAGE_LOGIN;
            }else if(!Objects.equals(password, user.getPass())){
            errorMessage = "Password incorrect";
            req.setAttribute(attribute, errorMessage);
            return Path.PAGE_LOGIN;
        }
        }
        HttpSession session = req.getSession();
        assert user != null;
        session.setAttribute("login", user.getLogin());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        session.setAttribute("email", user.getEmail());
        session.setAttribute("role", user.getRole());
        session.setAttribute("user", user.getFirstName() + " " + user.getLastName() +
                " (" + user.getRole().toLowerCase(Locale.ROOT) + ")");
        session.setAttribute("account", user.getAccount() + " UAH");

        switch (user.getRole()){
            case "CLIENT": {
                path = Path.PAGE_CLIENT_HOME;
                break;
            }
            case "MANAGER":{
                path = Path.PAGE_MANAGER_HOME;
                break;
            }
            case "MASTER":{
                path = Path.PAGE_MASTER_HOME;
                break;
            }
            case "ADMIN":{
                path = Path.PAGE_ADMIN_HOME;
                break;
            }
            default: path = Path.PAGE_LOGIN;
        }
        return path;
    }
}
