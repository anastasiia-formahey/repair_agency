package com.formahei.service;

import com.formahei.dao.RequestDAO;
import com.formahei.entity.Feedback;
import com.formahei.entity.RepairRequest;
import com.formahei.entity.User;
import com.formahei.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class RequestService {
    RequestDAO requestDAO;
    public RequestService(RequestDAO requestDAO){
        this.requestDAO = requestDAO;
    }

    public int addRequest(RepairRequest request) {
        return requestDAO.addRequest(request);
    }

    public void updateRequestByManager(int id, String status, int price) {
        requestDAO.updateRequestByManager(id, status, price);
    }

    public RepairRequest findRequestByID(int id) {
        return requestDAO.findRequestByID(id);
    }

    public void updateRequestByMaster(int id, String state) {
        requestDAO.updateRequestByMaster(id, state);
    }

    public List<RepairRequest> findAllRequests(String userLogin, String orderBy,
                                               int startPage, int amount, String role) {
        if(orderBy == null || orderBy.length() == 0){
            orderBy = "dateTime DESC";
        }
        String query = "";
        if(userLogin != null){
            query = "and user_request.user_login = '" + userLogin +"'";
        }
        startPage = startPage * 5 - amount;
        return requestDAO.findAllRequests(query, orderBy, startPage, amount, role);
    }

    public int getNumberOfResponsesRows(String userLogin, String userRole) {
        if(userRole.equals("ADMIN") || userRole.equals("MANAGER")){
            return requestDAO.findAllRequests().size();
        }else{
            return requestDAO.findAllRequests(userLogin, userRole).size();
        }
    }

    public static void getListOfRequests(List<RepairRequest> requests, List<Feedback> feedbacks, List<RepairRequest> requestsDTO, int stars) {
        for (RepairRequest repairRequest : requests) {
            for (Feedback feedback: feedbacks) {
                if (repairRequest.getId() == feedback.getIdRequest()) {
                    stars = feedback.getStars();
                }
            }
                requestsDTO.add(repairRequest.setFeedback(stars));
            stars = 0;
        }
    }

    public String viewRequestsByRole(HttpServletRequest req, UserService userService,
                                     List<Feedback> feedbacks, List<RepairRequest> requestsDTO,
                                     String orderBy, String userLoginInSession, String role,
                                     int currentPage, int stars) {
        switch (role) {
            case "ADMIN":
                RequestService.getListOfRequests(
                        findAllRequests(null, orderBy, currentPage, Pagination.RECORDS_PER_PAGE, "CLIENT")
                        , feedbacks, requestsDTO, stars);
                req.getSession().setAttribute(Constants.LIST_OF_REQUESTS, requestsDTO);
                return "admin_view_requests.jsp";
            case "CLIENT":
                RequestService.getListOfRequests(
                        findAllRequests(userLoginInSession, orderBy, currentPage, Pagination.RECORDS_PER_PAGE, role),
                        feedbacks, requestsDTO, stars);
                req.getSession().setAttribute(Constants.LIST_OF_REQUESTS, requestsDTO);
                return "viewRequests.jsp";
            case "MANAGER":
                RequestService.getListOfRequests(
                        findAllRequests(null, orderBy, currentPage, Pagination.RECORDS_PER_PAGE, "CLIENT"),
                        feedbacks, requestsDTO, stars);
                req.getSession().setAttribute(Constants.LIST_OF_REQUESTS, requestsDTO);
                List<User> masters = userService.findUserByRole("MASTER");
                req.getSession().setAttribute("listOfMaster", masters);
                return "manager_view_requests.jsp";
            case "MASTER":
                RequestService.getListOfRequests(
                        findAllRequests(userLoginInSession, orderBy, currentPage, Pagination.RECORDS_PER_PAGE, role),
                        feedbacks, requestsDTO, stars);
                req.getSession().setAttribute(Constants.LIST_OF_REQUESTS, requestsDTO);
                return "master_view_requests.jsp";
            default:
                return "viewRequests.jsp";
        }
    }

    public boolean deleteRequest(int id, String status, String login, double amount){
        return requestDAO.deleteRequestById( id, status, login, amount);
    }

}
