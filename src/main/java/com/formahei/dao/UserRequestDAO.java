package com.formahei.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRequestDAO {
    private static final String SQL_INSERT_USER_REQUEST = "INSERT INTO user_request VALUES(?,?,?)";

    private static UserRequestDAO instance = null;
    private UserRequestDAO(){}

    // singleton
    public static synchronized UserRequestDAO getInstance(){
        if(instance == null){
            instance = new UserRequestDAO();
        }
        return instance;
    }

    public void addUserRequest(int idRequest, String userLogin, String userRole){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER_REQUEST);
            preparedStatement.setInt(1,idRequest);
            preparedStatement.setString(2, userLogin);
            preparedStatement.setString(3, userRole);
            preparedStatement.executeUpdate();
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
    }
}
