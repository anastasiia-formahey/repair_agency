package com.formahei.web.command;

import com.formahei.dao.FeedbackDAO;
import com.formahei.dao.RequestDAO;
import com.formahei.dao.UserDAO;
import com.formahei.entity.Feedback;
import com.formahei.entity.RepairRequest;
import com.formahei.entity.User;
import com.formahei.service.FeedbackService;
import com.formahei.service.RequestService;
import com.formahei.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewRequestsCommand implements Command {
    private static final String LIST_OF_REQUESTS = "listOfRequests";
    private static final int RECORDS_PER_PAGE = 5;

    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FeedbackService feedbackService = new FeedbackService(FeedbackDAO.getInstance());
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        UserService userService = new UserService(UserDAO.getInstance());
        String currentPageParam = req.getParameter("currentPage");
        int currentPage = (currentPageParam == null || getInt(currentPageParam) < 1) ? 1 : getInt(currentPageParam);
        int rows = requestService.getNumberOfResponsesRows();

        req.setAttribute("responses", resp);
        setPagination(req, currentPage, rows);
        List <RepairRequest> requests = requestService.findAllRequests(req.getParameter("orderBy"), currentPage-1,5);
        List <Feedback> feedbacks = feedbackService.findAllFeedbacks();
        List <RepairRequest> requestsDTO = new ArrayList<>();
        List <RepairRequest> requestsForClient = new ArrayList<>();
        List <RepairRequest> requestsForMaster = new ArrayList<>();
        String userLoginInSession = req.getSession().getAttribute("login").toString();
        req.getSession().setAttribute(LIST_OF_REQUESTS, requests);
        String role = req.getSession().getAttribute("role").toString();
        int stars = 0;
        for (RepairRequest repairRequest : requests) {
            for (Feedback feedback: feedbacks) {
                if (repairRequest.getId() == feedback.getIdRequest()) {
                    stars = feedback.getStars();
                }
            }
                requestsDTO.add(repairRequest.setFeedback(stars));
            stars = 0;
        }
        switch (role) {
            case "ADMIN":
                req.getSession().setAttribute(LIST_OF_REQUESTS, requestsDTO);
                return "admin_view_requests.jsp";
            case "CLIENT":
                for (RepairRequest repairRequest : requestsDTO) {
                    if (repairRequest.getClient().equals(userLoginInSession)){
                        requestsForClient.add(repairRequest);
                    }
                }
                req.getSession().setAttribute(LIST_OF_REQUESTS, requestsForClient);
                return "viewRequests.jsp";
            case "MANAGER":
                req.getSession().setAttribute(LIST_OF_REQUESTS, requestsDTO);
                List<User> masters = userService.findUserByRole("MASTER");
                req.getSession().setAttribute("listOfMaster", masters);
                return "manager_view_requests.jsp";
            case "MASTER":
                for (RepairRequest repairRequest: requestsDTO) {
                    if(repairRequest.getMaster().equals(userLoginInSession)){
                        requestsForMaster.add(repairRequest);
                    }
                }
                req.getSession().setAttribute(LIST_OF_REQUESTS, requestsForMaster);
                return "master_view_requests.jsp";
            default: return "viewRequests.jsp";
        }
    }
    public static void setPagination(HttpServletRequest request, int currentPage, int rows) {
        int nOfPages = rows / RECORDS_PER_PAGE;

        if (nOfPages % RECORDS_PER_PAGE > 0 && rows % RECORDS_PER_PAGE != 0) {
            nOfPages += 1;
        }
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", RECORDS_PER_PAGE);
    }
    public static int getInt(String currentPageParam) {
        int current;
        try {
            current = Integer.parseInt(currentPageParam);
        } catch (NumberFormatException e) {
            current = -1;
        }
        return current;
    }
}
