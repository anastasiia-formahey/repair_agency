package com.formahei.web.command;

import com.formahei.dao.FeedbackDAO;
import com.formahei.entity.Feedback;
import com.formahei.service.FeedbackService;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFeedbackCommand implements Command {

    private static final Logger log = Logger.getLogger(ViewFeedbackCommand.class);

    /**
     * Execution method for command
     *
     * @param req
     * @param resp
     * @return Address to go after command executed
     */
    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.debug("ViewFeedbackCommand starts");
        FeedbackService feedbackService = new FeedbackService(FeedbackDAO.getInstance());
        List<Feedback> feedbacks = feedbackService.findAllFeedbacks();
        String login = req.getSession().getAttribute(Constants.LOGIN).toString();
        List<Feedback> feedbackByMaster = new ArrayList<>();
        for (Feedback feedback: feedbacks) {
            if(feedback.getMasterLogin().equals(login)){
                feedbackByMaster.add(feedback);
            }
        }
        req.getSession().setAttribute("listOfFeedbacks", feedbackByMaster);
        log.debug("ViewFeedbackCommand finished");
        return new CommandResult("view_feedbacks.jsp", false);
    }
}
