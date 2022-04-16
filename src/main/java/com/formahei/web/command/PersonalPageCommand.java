package com.formahei.web.command;

import com.formahei.dao.FeedbackDAO;
import com.formahei.dao.UserDAO;
import com.formahei.entity.Feedback;
import com.formahei.entity.User;
import com.formahei.service.FeedbackService;
import com.formahei.service.UserService;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class PersonalPageCommand implements Command {

    private static final Logger log = Logger.getLogger(PersonalPageCommand.class);
    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("PersonalPageCommand starts");
        UserService userService = new UserService(UserDAO.getInstance());
        User client = userService.getUserByLogin(req.getSession().getAttribute(Constants.LOGIN)
                .toString());
        FeedbackService feedbackService = new FeedbackService(FeedbackDAO.getInstance());
        req.getSession().removeAttribute(Constants.ACCOUNT);
        req.getSession().setAttribute(Constants.ACCOUNT, client.getAccount() + " UAH");
        String path;
        switch (client.getRole()){
            case "CLIENT": {
                path =  Path.PAGE_CLIENT_PERSONAL;
                break;
            }
            case "MANAGER":{
                path = Path.PAGE_MANAGER_HOME;
                break;
            }
            case "MASTER":{
                req.getSession().setAttribute(Constants.RATING,
                        feedbackService.getRatingByMaster().get(client.getLogin()));
                path =Path.PAGE_MASTER_HOME;
                break;
            }
            case "ADMIN":{
                path = Path.PAGE_ADMIN_HOME;
                break;
            }
            default: path = Path.PAGE_LOGIN;
        }
        log.debug("PersonalPageCommand finished");
        return new CommandResult(path, true);
    }
}
