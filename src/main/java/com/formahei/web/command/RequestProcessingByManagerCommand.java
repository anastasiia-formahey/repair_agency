package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserRequestDAO;
import com.formahei.service.RequestService;
import com.formahei.service.UserRequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestProcessingByManagerCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        UserRequestService userRequestService = new UserRequestService(UserRequestDAO.getInstance());
        String priceParam = req.getParameter("setPrice");
        int id =  Integer.parseInt(req.getParameter("idRequest"));
        if(priceParam != null){
            int price = Integer.parseInt(priceParam);
            String status = req.getParameter("status");
            requestService.updateRequestByManager(id, status, price);
            return new ViewRequestsCommand().execute(req, resp);
        }else {
            req.getSession().setAttribute("errorData", "All field must not be empty");
        }

        String masterLogin = req.getParameter("master");
        userRequestService.addUserRequest(id, masterLogin, "MASTER");

        return new ViewRequestsCommand().execute(req, resp);
    }
}
