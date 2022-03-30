package com.formahei.dao;

import com.formahei.entity.RepairRequest;
import com.formahei.entity.Role;
import com.formahei.entity.User;
import com.formahei.utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data access object for Request entity.
 * @author Anastasiia Formahei
 */
public class RequestDAO {
    private static final String SQL_INSERT_REQUEST = "INSERT INTO request VALUES (DEFAULT , ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_REQUEST_BY_MANAGER = "UPDATE request SET price=?, status=? WHERE id=?";
    private static final String SQL_UPDATE_REQUEST_BY_MASTER = "UPDATE request SET state=? WHERE id=?";
    private static final String SQL_FIND_REQUEST ="SELECT * FROM repair_agency.request";
    private static final String SQL_FIND_USER_BY_REQUEST_ID = "SELECT user_login FROM user_request WHERE id_request=? AND user_role=?";
    private static final String SQL_FIND_REQUEST_BY_ID = "SELECT * FROM request WHERE id=?";
    private static final String SQL_FIND_REQUEST_BY_LOGIN = "SELECT * FROM user_request WHERE user_login=? AND user_role=?";

    private static RequestDAO instance = null;
    private RequestDAO(){}

    // singleton
    public static synchronized RequestDAO getInstance(){
        if(instance == null){
            instance = new RequestDAO();
        }
        return instance;
    }

    public int addRequest(RepairRequest repairRequest){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, repairRequest.getDescription());
            preparedStatement.setString(2, String.valueOf(repairRequest.getDateTime()));
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, "");
            preparedStatement.setString(5, "");
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                repairRequest.setId(id);
            }
            resultSet.close();
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
        return repairRequest.getId();
    }

    public List<RepairRequest> findAllRequests(String query, String orderBy, int startPage, int amount, String role){
        ArrayList<RepairRequest> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        User user;
        String sqlQueryFull = "SELECT * FROM repair_agency.request " +
                "left outer join  repair_agency.user_request " +
                "on request.id = user_request.id_request where " +
                "user_request.user_role= " + "'" + role + "'" + query +" ORDER BY "+ orderBy + " LIMIT "+ startPage +"," + amount;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement( sqlQueryFull);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = UserDAO.getInstance().findUserByLogin(resultSet.getString("user_login"));
                if(user != null){
                    int id = resultSet.getInt(Constants.ID);
                    RepairRequest request = new RepairRequest.RequestBuilder()
                            .withDescription(resultSet.getString(Constants.DESCRIPTION))
                            .withDateTime(resultSet.getString(Constants.DATETIME))
                            .withPrice(resultSet.getInt(Constants.PRICE))
                            .withStatus(resultSet.getString(Constants.STATUS))
                            .withState(resultSet.getString(Constants.STATE))
                            .withClient(findUserByRequestId(id, Role.CLIENT.name()))
                            .withMaster(findUserByRequestId(id, Role.MASTER.name()))
                            .build();
                    request.setId(id);
                    requests.add(request);
                    System.out.println(request);
                }
            }
            resultSet.close();
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
        return requests;
    }
    public List<RepairRequest> findAllRequests(){
        ArrayList<RepairRequest> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_REQUEST);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                    int id = resultSet.getInt(Constants.ID);
                    RepairRequest request = new RepairRequest.RequestBuilder()
                            .withDescription(resultSet.getString(Constants.DESCRIPTION))
                            .withDateTime(resultSet.getString(Constants.DATETIME))
                            .withPrice(resultSet.getInt(Constants.PRICE))
                            .withStatus(resultSet.getString(Constants.STATUS))
                            .withState(resultSet.getString(Constants.STATE))
                            .withMaster(findUserByRequestId(id, Role.MASTER.name()))
                            .build();
                    request.setId(id);
                    requests.add(request);
            }
            resultSet.close();
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
        return requests;
    }
    public List<Integer> findAllRequests(String userLogin, String role){
        ArrayList<Integer> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_REQUEST_BY_LOGIN);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, role);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id_request");
               requests.add(id);
            }
            resultSet.close();
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
        return requests;
    }

    public RepairRequest findRequestByID(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        RepairRequest request = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_REQUEST_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                   request = new RepairRequest.RequestBuilder()
                            .withDescription(resultSet.getString(Constants.DESCRIPTION))
                            .withDateTime(resultSet.getString(Constants.DATETIME))
                            .withPrice(resultSet.getInt(Constants.PRICE))
                            .withStatus(resultSet.getString(Constants.STATUS))
                            .withState(resultSet.getString(Constants.STATE))
                            .build();
                    request.setId(resultSet.getInt(Constants.ID));
                }
            resultSet.close();
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
        return request;
    }

    public void updateRequestByManager(int id, String status, int price){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_REQUEST_BY_MANAGER);
            preparedStatement.setInt(1, price);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3,id);
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

    public String findUserByRequestId(int requestId, String role){
        String masterLogin = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_REQUEST_ID);
            preparedStatement.setInt(1, requestId);
            preparedStatement.setString(2, role);
            resultSet = preparedStatement.executeQuery();
           if(resultSet.next()){
               masterLogin = resultSet.getString("user_login");
           }
           resultSet.close();
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
        return masterLogin;
    }

    public void updateRequestByMaster(int id, String state) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_REQUEST_BY_MASTER);
            preparedStatement.setString(1, state);
            preparedStatement.setInt(2,id);
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
