package com.formahei.web.command;

import com.formahei.dao.FeedbackDAO;
import com.formahei.dao.RequestDAO;
import com.formahei.entity.Feedback;
import com.formahei.entity.RepairRequest;
import com.formahei.service.FeedbackService;
import com.formahei.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class CreateFeedbackCommand implements Command {

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
        String stars = req.getParameter(Constants.RATING);
        int idRequest = Integer.parseInt(req.getParameter(Constants.ID_REQUEST));
        String masterLogin = req.getParameter(Constants.MASTER);
        String description = req.getParameter(Constants.DESCRIPTION);
        String dateTime = String.valueOf(LocalDateTime.now());
        Feedback feedback = new Feedback(description,
                    dateTime,
                    idRequest,
                    masterLogin,
                    Integer.parseInt(stars));
           feedbackService.createFeedback(feedback);
        req.getSession().removeAttribute("message");
        req.getSession().setAttribute("message", "Feedback added");
           return new CommandResult("user_home_page.jsp", true);
    }
}
