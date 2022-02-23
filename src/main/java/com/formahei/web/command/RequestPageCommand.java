package com.formahei.web.command;

import com.formahei.dao.RequestDAO;
import com.formahei.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestPageCommand implements Command {
    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestService requestService = new RequestService(RequestDAO.getInstance());
        String currentPageParam = req.getParameter("currentPage");
        int currentPage = (currentPageParam == null || Integer.parseInt(currentPageParam) < 1) ? 1 : Integer.parseInt(currentPageParam);
        int rows = requestService.getNumberOfResponsesRows();
        return "manager_view_requests.jsp";
    }
}
