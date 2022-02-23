package com.formahei.web.command;

import com.formahei.dao.FeedbackDAO;
import com.formahei.entity.Feedback;
import com.formahei.service.FeedbackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackCommand implements Command {

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
        List<Feedback> feedbacks = feedbackService.findAllFeedbacks();
        String login = req.getSession().getAttribute("login").toString();
        List<Feedback> feedbackByMaster = new ArrayList<>();

        for (Feedback feedback: feedbacks) {
            if(feedback.getMasterLogin().equals(login)){
                feedbackByMaster.add(feedback);
            }
        }
        req.getSession().setAttribute("listOfFeedbacks", feedbackByMaster);
        return "view_feedbacks.jsp";
    }
}