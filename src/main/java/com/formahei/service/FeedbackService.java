package com.formahei.service;

import com.formahei.dao.FeedbackDAO;
import com.formahei.entity.Feedback;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeedbackService {
    FeedbackDAO feedbackDAO;

    public FeedbackService(FeedbackDAO feedbackDAO){
        this.feedbackDAO = feedbackDAO;
    }

    public void createFeedback(Feedback feedback){
        feedbackDAO.insertFeedback(feedback);
    }

    public List<Feedback> findAllFeedbacks() {
        return feedbackDAO.findAllFeedbacks();
    }
    public Map<Object, Double> getRatingByMaster(){
        List<Feedback> feedbacks = findAllFeedbacks();
        return feedbacks.stream().collect(Collectors
                        .groupingBy(Feedback::getMasterLogin,
                                Collectors.averagingDouble(Feedback::getStars)));

    }

}
