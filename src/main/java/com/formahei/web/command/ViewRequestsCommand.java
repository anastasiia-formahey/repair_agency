package com.formahei.web.command;

import com.formahei.dao.FeedbackDAO;
import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserDAO;
import com.formahei.entity.Feedback;
import com.formahei.entity.RepairRequest;
import com.formahei.entity.User;
import com.formahei.service.FeedbackService;
import com.formahei.service.Pagination;
import com.formahei.service.RequestService;
import com.formahei.service.UserService;
import com.formahei.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewRequestsCommand implements Command {

    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FeedbackService feedbackService = new FeedbackService(FeedbackDAO.getInstance());
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        UserService userService = new UserService(UserDAO.getInstance());

        String currentPageParam = req.getParameter("currentPage");
        String orderBy = req.getParameter("orderBy");

        String userLoginInSession = req.getSession().getAttribute(Constants.LOGIN).toString();
        String role = req.getSession().getAttribute(Constants.ROLE).toString();

        int currentPage = Pagination.getCurrentPage(currentPageParam);
        int rows = requestService.getNumberOfResponsesRows(userLoginInSession, role);

        Pagination.setPagination(req, currentPage, rows, orderBy);
        Pagination.setPagination(req, currentPage, rows, orderBy);

        return new CommandResult(requestService.viewRequestsByRole(req, userService,
                feedbackService.findAllFeedbacks(), new ArrayList<>(),
                orderBy, userLoginInSession, role, currentPage, 0),
                false);
    }
}
