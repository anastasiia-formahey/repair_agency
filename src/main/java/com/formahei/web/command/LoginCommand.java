package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.PasswordEncoder;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class LoginCommand implements Command {

    private static final Logger log = Logger.getLogger(LoginCommand.class);

    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        log.debug("LoginCommand starts");

        User user;
        String login = req.getParameter(Constants.LOGIN);
        String password = req.getParameter(Constants.PASSWORD);
        String errorMessage;
        String attribute = Constants.ERROR_MESSAGE;
        if(login.length() < 1 || password.length() < 1){
            errorMessage = "Fields login and password can't be empty";
            req.setAttribute(attribute, errorMessage);
            return new CommandResult(Path.PAGE_LOGIN, false);
        }else {
        user = UserDAO.getInstance().findUserByLogin(login);
            if(user == null){
                errorMessage = "User not registered";
                req.setAttribute(attribute, errorMessage);
                log.trace("LoginCommand finished. " + errorMessage);
                return new CommandResult(Path.PAGE_LOGIN, false);
            }else if(!Objects.equals(PasswordEncoder.encode(password), user.getPass())){
            errorMessage = "Password incorrect";
            req.setAttribute(attribute, errorMessage);
                log.trace("LoginCommand finished. " + errorMessage);
                return new CommandResult(Path.PAGE_LOGIN, false);
            }else if(user.getStatus().equals("blocked")){
                errorMessage = "Your account is blocked.";
                req.setAttribute(attribute, errorMessage);
                log.trace("LoginCommand finished. " + errorMessage);
                return new CommandResult(Path.PAGE_LOGIN, false);
            }
        }
        HttpSession session = req.getSession();
        session.setAttribute(Constants.LOGIN, user.getLogin());
        session.setAttribute("firstName", user.getFirstName());
        session.setAttribute("lastName", user.getLastName());
        session.setAttribute(Constants.EMAIL, user.getEmail());
        session.setAttribute(Constants.ROLE, user.getRole());
        session.setAttribute("user", user.getFirstName() + " " + user.getLastName() +
                " (" + user.getRole().toLowerCase(Locale.ROOT) + ")");
        session.setAttribute(Constants.ACCOUNT, user.getAccount() + " UAH");

        log.debug("LoginCommand finished");
        return new PersonalPageCommand().execute(req, resp);
    }
}
