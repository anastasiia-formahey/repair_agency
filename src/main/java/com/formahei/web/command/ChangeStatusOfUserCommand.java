package com.formahei.web.command;

import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.UserService;

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
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = new UserService(UserDAO.getInstance());
        userService.updateStatus(req.getParameter("loginOfUser"));
        return new ViewUsersCommand().execute(req, resp);
    }
}
