package com.formahei.service;

import com.formahei.dao.FeedbackDAO;
import com.formahei.entity.Feedback;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FeedbackService {

    private static final Logger log = Logger.getLogger(FeedbackService.class);

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
