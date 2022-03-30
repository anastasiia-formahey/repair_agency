package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserRequestDAO;
import com.formahei.service.RequestService;
import com.formahei.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestProcessingByMasterCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        int id =  Integer.parseInt(req.getParameter(Constants.ID_REQUEST));

            String state = req.getParameter(Constants.STATE);
            requestService.updateRequestByMaster(id, state);
            req.getSession().removeAttribute(Constants.STATE);
            req.getSession().setAttribute(Constants.STATE, state);
            return new ViewRequestsCommand().execute(req, resp);
    }
}
