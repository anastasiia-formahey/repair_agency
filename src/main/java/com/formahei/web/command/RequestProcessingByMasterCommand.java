package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserRequestDAO;
import com.formahei.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestProcessingByMasterCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        int id =  Integer.parseInt(req.getParameter("idRequest"));

            String state = req.getParameter("state");
            requestService.updateRequestByMaster(id, state);
            req.getSession().removeAttribute("state");
            req.getSession().setAttribute("state", state);
            return new ViewRequestsCommand().execute(req, resp);

    }
}
