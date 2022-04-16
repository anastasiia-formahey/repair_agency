package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserRequestDAO;
import com.formahei.service.RequestService;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestProcessingByMasterCommand implements Command {

    private static final Logger log = Logger.getLogger(RequestProcessingByMasterCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        log.debug("RequestProcessingByMasterCommand starts");
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        int id =  Integer.parseInt(req.getParameter(Constants.ID_REQUEST));

            String state = req.getParameter(Constants.STATE);
            requestService.updateRequestByMaster(id, state);
            req.getSession().removeAttribute(Constants.STATE);
            req.getSession().setAttribute(Constants.STATE, state);

        log.debug("RequestProcessingByMasterCommand finished");

            return new ViewRequestsCommand().execute(req, resp);
    }
}
