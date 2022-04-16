package com.formahei.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Data access object for User_Request entity.
 * @author Anastasiia Formahei
 */
public class UserRequestDAO {
    private static final Logger log = Logger.getLogger(UserRequestDAO.class);

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
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER_REQUEST);
            preparedStatement.setInt(1,idRequest);
            preparedStatement.setString(2, userLogin);
            preparedStatement.setString(3, userRole);
            preparedStatement.executeUpdate();
            log.trace("Query execution ==> " + preparedStatement);
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
    }
}
