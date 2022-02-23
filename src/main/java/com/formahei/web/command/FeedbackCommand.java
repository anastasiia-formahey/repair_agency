package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.entity.RepairRequest;
import com.formahei.utils.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FeedbackCommand implements Command {


    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idRequest = Integer.parseInt(req.getParameter("idRequest"));
        String masterLogin = req.getParameter("master");
        req.getSession().setAttribute("idRequest", idRequest);
        req.getSession().setAttribute("masterLogin", masterLogin);
        return Path.PAGE_CLIENT_CREATE_FEEDBACK;
    }
}
