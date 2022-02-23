package com.formahei.service;

import com.formahei.dao.FeedbackDAO;
import com.formahei.entity.Feedback;

import java.util.List;

public class FeedbackService {
    FeedbackDAO feedbackDAO;

    public FeedbackService(FeedbackDAO feedbackDAO){
        this.feedbackDAO = feedbackDAO;
    }

    public int createFeedback(Feedback feedback){
        return feedbackDAO.getInstance().insertFeedback(feedback);
    }

    public List<Feedback> findAllFeedbacks() {
        return feedbackDAO.findAllFeedbacks();
    }


}
