package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserDAO;
import com.formahei.entity.User;
import com.formahei.service.RequestService;
import com.formahei.service.UserService;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteCommand implements Command {

    private static final Logger log = Logger.getLogger(DeleteCommand.class);
    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("DeleteCommand starts");

        RequestService requestService = new RequestService(RequestDAO.getInstance());
        UserService userService = new UserService(UserDAO.getInstance());
        int idRequest = Integer.parseInt(req.getParameter(Constants.ID_REQUEST));
        String status = req.getParameter(Constants.STATUS);
        String login = req.getSession().getAttribute("login").toString();
        double price = Double.parseDouble(req.getParameter("price"));
        User client = userService.getUserByLogin(login);
        requestService.deleteRequest(idRequest, status, login, price + client.getAccount());

        log.debug("DeleteCommand finished");
        return new PersonalPageCommand().execute(req, resp);
    }
}
