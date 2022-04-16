package com.formahei.dao;

import com.formahei.entity.EntityMapper;
import com.formahei.entity.User;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for User entity.
 * @author Anastasiia Formahei
 */

public class UserDAO {

    private static final Logger log = Logger.getLogger(UserDAO.class);

    private static final String SQL_INSERT_USER =
            "INSERT INTO `repair_agency`.`user` VALUES (?,?,?,?,?,?,?,?)";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    private static final String SQL_FIND_USER_BY_ROLE = "SELECT * FROM user WHERE role=?";
    private static final String SQL_UPDATE_STATUS = "UPDATE user SET status=? WHERE login=?";
    public static final String SQL_UPDATE_ACCOUNT = "UPDATE user SET account=? WHERE login=?";

    private static UserDAO instance = null;
    private UserDAO(){}

    // singleton
    public static synchronized UserDAO getInstance(){
        if(instance == null){
            instance = new UserDAO();
        }
        return instance;
    }

    public boolean addUser(User user){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_INSERT_USER);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setDouble(7, user.getAccount());
            preparedStatement.setString(8, user.getStatus());
            preparedStatement.executeUpdate();
            log.trace("Query execution ==> " + preparedStatement);
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot insert user into DB ", e);
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
        return true;
    }

    public User findUserByLogin(String login){
        log.debug("Method starts");
        User user = null;
        ResultSet resultSet;
        try(Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)){
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
            UserMapper userMapper = new UserMapper();
            if(resultSet.next()){
                user = userMapper.mapRow(resultSet);
            }
            resultSet.close();
        }catch (SQLException e) {
            log.error("Cannot find user in DB ", e);
        }
        log.debug("Method finished");
        return user;
    }

    public List<User> findUserByRole(String role){
        log.debug("Method starts");
        ArrayList<User> users = new ArrayList<>();
        User user;
        ResultSet resultSet;
        Connection connection =null;
        PreparedStatement preparedStatement = null;
        try {
         connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ROLE);
            preparedStatement.setString(1, role);
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
            while(resultSet.next()){
                UserMapper userMapper = new UserMapper();
                user = userMapper.mapRow(resultSet);
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot find user in DB ", e);
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
        return users;
    }

    public boolean updateStatus(String login, String status){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, login);
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
        return true;
    }

    public boolean updateAccount(String login, double amount){
        log.debug("Method starts");
        User user = findUserByLogin(login);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, user.getLogin());
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
        return true;
    }

    private static class UserMapper implements EntityMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet) {
            try {
                User user = new User();
                user.setLogin(resultSet.getString(Constants.LOGIN));
                user.setPass(resultSet.getString(Constants.PASSWORD));
                user.setFirstName(resultSet.getString(Constants.FIRST_NAME));
                user.setLastName(resultSet.getString(Constants.LAST_NAME));
                user.setEmail(resultSet.getString(Constants.EMAIL));
                user.setRole(resultSet.getString(Constants.ROLE));
                user.setAccount(resultSet.getDouble(Constants.ACCOUNT));
                user.setStatus(resultSet.getString(Constants.STATUS));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
