package com.formahei.dao;

import com.formahei.entity.Feedback;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data access object for Feedback entity.
 * @author Anastasiia Formahei
 */
public class FeedbackDAO {

    private static final Logger log = Logger.getLogger(FeedbackDAO.class);

    private static final String SQL_INSERT_INTO_FEEDBACK =
            "INSERT INTO feedback VALUES(DEFAULT,?,?,?,?,?)";
    private static final String SQL_SELECT_ALL_FROM_FEEDBACK =
            "SELECT * FROM feedback";

    private static FeedbackDAO instance = null;
    private FeedbackDAO(){}

    public static FeedbackDAO getInstance() {
        if(instance == null){
            instance = new FeedbackDAO();
        }
        return instance;
    }

    public int insertFeedback(Feedback feedback){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        int id;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_FEEDBACK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, feedback.getDescription());
            preparedStatement.setString(2, feedback.getDateTime());
            preparedStatement.setInt(3, feedback.getIdRequest());
            preparedStatement.setString(4, feedback.getMasterLogin());
            preparedStatement.setInt(5, feedback.getStars());
            preparedStatement.executeUpdate();
            log.trace("Query execution ==> " + preparedStatement);

            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getInt(1);
                feedback.setId(id);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
            log.trace("Close connection with DBManager");
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return feedback.getId();
    }

    public List<Feedback> findAllFeedbacks() {
        log.debug("Method starts");
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_FROM_FEEDBACK);
            resultSet = preparedStatement.executeQuery();

            log.trace("Query execution ==> " + preparedStatement);
            while (resultSet.next()) {
                Feedback feedback = new Feedback(
                        resultSet.getString(Constants.DESCRIPTION),
                        resultSet.getString(Constants.DATETIME),
                        resultSet.getInt(Constants.FEEDBACK_ID),
                        resultSet.getString(Constants.FEEDBACK_MASTER_LOGIN),
                        resultSet.getInt(Constants.FEEDBACK_STARS)
                );
                feedback.setId(resultSet.getInt(Constants.ID));
                feedbacks.add(feedback);
            }
        resultSet.close();

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot execute the query ==> " + e);
            log.trace("Close connection with DBManager");
        }
        finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
            log.trace("Close connection with DBManager");
        }
        log.debug("Method finished");
        return feedbacks;
    }
}

