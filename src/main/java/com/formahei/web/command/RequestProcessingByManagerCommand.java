package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserRequestDAO;
import com.formahei.entity.Role;
import com.formahei.service.RequestService;
import com.formahei.service.UserRequestService;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestProcessingByManagerCommand implements Command {

    private static final Logger log = Logger.getLogger(RequestProcessingByManagerCommand.class);
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        log.debug("RequestProcessingByManagerCommand starts");
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        UserRequestService userRequestService = new UserRequestService(UserRequestDAO.getInstance());
        String priceParam = req.getParameter("setPrice");
        int id =  Integer.parseInt(req.getParameter(Constants.ID_REQUEST));
        if(priceParam != null){
            int price = Integer.parseInt(priceParam);
            String status = req.getParameter(Constants.STATUS);
            requestService.updateRequestByManager(id, status, price);
            String masterLogin = req.getParameter(Constants.MASTER);
            userRequestService.addUserRequest(id, masterLogin, Role.MASTER.name());
        }else {
            req.getSession().setAttribute(Constants.ERROR_MESSAGE, "All field must not be empty");
            log.trace("All field must not be empty");
            log.debug("RequestProcessingByManagerCommand finished");
            return new ViewRequestsCommand().execute(req, resp);
        }
        log.debug("RequestProcessingByManagerCommand finished");
        return new ViewRequestsCommand().execute(req, resp);
    }
}
