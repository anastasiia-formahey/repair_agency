package com.formahei.dao;

import com.formahei.entity.EntityMapper;
import com.formahei.entity.User;
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
    private static final String SQL_UPDATE_ACCOUNT = "UPDATE user SET account=? WHERE login=?";

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
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
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
        } catch (SQLException e) {
            DBManager.getInstance().rollbackAndClose(connection);
            log.error("Cannot insert user into DB ", e);
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
        }
        return true;
    }

    public User findUserByLogin(String login){
        User user = null;
        ResultSet resultSet;
        try(Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)){
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            UserMapper userMapper = new UserMapper();
            if(resultSet.next()){
                user = userMapper.mapRow(resultSet);
            }
            resultSet.close();
        }catch (SQLException e) {
            log.error("Cannot find user in DB ", e);
        }
        return user;
    }

    public List<User> findUserByRole(String role){
        ArrayList<User> users = new ArrayList<>();
        User user = null;
        ResultSet resultSet;
        Connection connection =null;
        PreparedStatement preparedStatement = null;
        try {
         connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ROLE);
            preparedStatement.setString(1, role);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                UserMapper userMapper = new UserMapper();
                user = userMapper.mapRow(resultSet);
                users.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error("Cannot find user in DB ", e);
        }finally {
            try {
                assert preparedStatement != null;
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBManager.getInstance().commitAndClose(connection);
        }
        return users;
    }

    public boolean updateStatus(String login, String status){
        if(findUserByLogin(login).getStatus().equals("blocked")){
            status = "unblocked";
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_STATUS);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, login);
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
        return true;
    }

    public boolean updateAccount(String login, double amount){
        User user = findUserByLogin(login);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_ACCOUNT);
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, user.getLogin());
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
        return true;
    }

    private static class UserMapper implements EntityMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet) {
            try {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPass(resultSet.getString("password"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                user.setAccount(resultSet.getDouble("account"));
                user.setStatus(resultSet.getString("status"));
                return user;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }

}
