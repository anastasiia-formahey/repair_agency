package com.formahei.dao;

import com.formahei.entity.Feedback;
import com.formahei.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        int id;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_INTO_FEEDBACK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, feedback.getDescription());
            preparedStatement.setString(2, feedback.getDateTime());
            preparedStatement.setInt(3, feedback.getIdRequest());
            preparedStatement.setString(4, feedback.getMasterLogin());
            preparedStatement.setInt(5, feedback.getStars());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                id = resultSet.getInt(1);
                feedback.setId(id);
            }
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            e.printStackTrace();
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
        }
        return feedback.getId();
    }

    public List<Feedback> findAllFeedbacks() {
        ArrayList<Feedback> feedbacks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_FROM_FEEDBACK);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Feedback feedback = new Feedback(
                        resultSet.getString("description"),
                        resultSet.getString("dateTime"),
                        resultSet.getInt("id_request"),
                        resultSet.getString("master_login"),
                        resultSet.getInt("stars")
                );
                feedback.setId(resultSet.getInt("id"));
                feedbacks.add(feedback);
            }
        resultSet.close();

        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            e.printStackTrace();
        }
        finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
        }
        return feedbacks;
    }
}
