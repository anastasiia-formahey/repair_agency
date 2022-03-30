package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserRequestDAO;
import com.formahei.entity.RepairRequest;
import com.formahei.entity.Role;
import com.formahei.service.RequestService;
import com.formahei.service.UserRequestService;
import com.formahei.utils.Constants;
import com.formahei.utils.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateRequestCommand implements Command {
    /**
     * Execution method for command

     * @return Address to go after command executed
     */
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        UserRequestService userRequestService = new UserRequestService(UserRequestDAO.getInstance());
        String userLogin = req.getSession().getAttribute(Constants.LOGIN).toString();
        if(req.getParameter(Constants.CREATE_REQUEST).isEmpty()){
            req.removeAttribute(Constants.ERROR_MESSAGE);
            req.setAttribute(Constants.ERROR_MESSAGE, "The description of request can't be empty");
        }else {
        RepairRequest request = new RepairRequest.RequestBuilder()
                .withDescription(req.getParameter(Constants.CREATE_REQUEST))
                .withDateTime(String.valueOf(LocalDateTime.now()))
                .withClient(userLogin)
                .build();
        req.getSession().setAttribute(Constants.REQUEST, request);

        int idRequest = requestService.addRequest(request);
            userRequestService.addUserRequest(idRequest, userLogin, Role.CLIENT.name());
        }
        return new CommandResult(Path.PAGE_CLIENT_CREATE_REQUEST, true);
    }
}
