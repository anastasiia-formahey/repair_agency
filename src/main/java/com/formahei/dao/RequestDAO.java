package com.formahei.dao;

import com.formahei.entity.RepairRequest;
import com.formahei.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAO {
    private static final String SQL_INSERT_REQUEST = "INSERT INTO request VALUES (DEFAULT , ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_REQUEST_BY_MANAGER = "UPDATE request SET price=?, status=? WHERE id=?";
    private static final String SQL_UPDATE_REQUEST_BY_MASTER = "UPDATE request SET state=? WHERE id=?";
    private static final String SQL_FIND_REQUEST ="SELECT * FROM repair_agency.request ";
    private static final String SQL_FIND_MASTER_BY_REQUEST_ID = "SELECT user_login FROM user_request WHERE id_request=? AND user_role='MASTER'";
    private static final String SQL_FIND_REQUEST_BY_ID = "SELECT * FROM request WHERE id=?";

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

    public List<RepairRequest> findAllRequests(String orderBy, int page, int amount){
        if(orderBy == null){
            orderBy = "dateTime";
        }
        ArrayList<RepairRequest> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        User user = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement( "SELECT * FROM repair_agency.request " +
                            "left outer join  repair_agency.user_request " +
                            "on request.id = user_request.id_request where " +
                            "user_request.user_role='CLIENT' ORDER BY "+ orderBy + " LIMIT "+ page +"," + amount);

            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                user = UserDAO.getInstance().findUserByLogin(resultSet.getString("user_login"));
                if(user != null && user.getRole().equals("CLIENT")){
                    String client_id =user.getLogin();
                    int id = resultSet.getInt("id");
                    RepairRequest request = new RepairRequest.RequestBuilder()
                            .withDescription(resultSet.getString("description"))
                            .withDateTime(resultSet.getString("dateTime"))
                            .withPrice(resultSet.getInt("price"))
                            .withStatus(resultSet.getString("status"))
                            .withState(resultSet.getString("state"))
                            .withClient(client_id)
                            .withMaster(findMasterByRequestId(id))
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
        User user = null;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement( SQL_FIND_REQUEST);
            System.out.println(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                    int id = resultSet.getInt("id");
                    RepairRequest request = new RepairRequest.RequestBuilder()
                            .withDescription(resultSet.getString("description"))
                            .withDateTime(resultSet.getString("dateTime"))
                            .withPrice(resultSet.getInt("price"))
                            .withStatus(resultSet.getString("status"))
                            .withState(resultSet.getString("state"))
                            .withMaster(findMasterByRequestId(id))
                            .build();
                    request.setId(id);
                    requests.add(request);
                    System.out.println(request);
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
                            .withDescription(resultSet.getString("description"))
                            .withDateTime(resultSet.getString("dateTime"))
                            .withPrice(resultSet.getInt("price"))
                            .withStatus(resultSet.getString("status"))
                            .withState(resultSet.getString("state"))
                            .build();
                    request.setId(resultSet.getInt("id"));
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

    public String findMasterByRequestId(int requestId){
        String masterLogin = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_MASTER_BY_REQUEST_ID);
            preparedStatement.setInt(1, requestId);
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
