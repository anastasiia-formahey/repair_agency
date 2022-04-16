package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeStatusOfUserCommand implements Command {
    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */

    private static final Logger log = Logger.getLogger(ChangeStatusOfUserCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        log.debug("ChangeStatusOfUserCommand starts");
        UserService userService = new UserService(UserDAO.getInstance());
        userService.updateStatus(req.getParameter("loginOfUser"));

        log.debug("ChangeStatusOfUserCommand finished");
        return new ViewUsersCommand().execute(req, resp);
    }
}
