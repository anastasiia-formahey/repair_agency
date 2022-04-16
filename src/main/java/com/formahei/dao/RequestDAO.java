package com.formahei.dao;

import com.formahei.entity.EntityMapper;
import com.formahei.entity.RepairRequest;
import com.formahei.entity.Role;
import com.formahei.entity.User;
import com.formahei.utils.Constants;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data access object for Request entity.
 * @author Anastasiia Formahei
 */
public class RequestDAO {

    private static final Logger log = Logger.getLogger(RequestDAO.class);

    private static final String SQL_INSERT_REQUEST = "INSERT INTO request VALUES (DEFAULT , ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_REQUEST_BY_MANAGER = "UPDATE request SET price=?, status=? WHERE id=?";
    private static final String SQL_UPDATE_REQUEST_BY_MASTER = "UPDATE request SET state=? WHERE id=?";
    private static final String SQL_FIND_REQUEST ="SELECT * FROM repair_agency.request";
    private static final String SQL_FIND_USER_BY_REQUEST_ID = "SELECT user_login FROM user_request WHERE id_request=? AND user_role=?";
    private static final String SQL_FIND_REQUEST_BY_ID = "SELECT * FROM request WHERE id=?";
    private static final String SQL_FIND_REQUEST_BY_LOGIN = "SELECT * FROM user_request WHERE user_login=? AND user_role=?";
    private static final String SQL_FIND_REQUEST_BY_ID_AND_ROLE = "SELECT id_request FROM user_request WHERE id_request=? AND user_role=?";
    private static final String SQL_DELETE_REQUEST_BY_ID = "DELETE FROM user_request WHERE id_request=?";
    private static final String SQL_DELETE_REQUEST = "DELETE FROM request WHERE id=?";

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
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, repairRequest.getDescription());
            preparedStatement.setString(2, String.valueOf(repairRequest.getDateTime()));
            preparedStatement.setInt(3, 0);
            preparedStatement.setString(4, "");
            preparedStatement.setString(5, "");
            preparedStatement.executeUpdate();
            log.trace("Query execution ==> " + preparedStatement);
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                int id = resultSet.getInt(1);
                repairRequest.setId(id);
            }
            resultSet.close();
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
        return repairRequest.getId();
    }

    public List<RepairRequest> findAllRequests(String query, String orderBy, int startPage, int amount, String role){

        log.debug("Method starts");
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
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(sqlQueryFull);
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
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
                }
            }
            resultSet.close();
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
        return requests;
    }
    public List<RepairRequest> findAllRequests(){
        log.debug("Method starts");
        ArrayList<RepairRequest> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_FIND_REQUEST);
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
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
        return requests;
    }
    public List<Integer> findAllRequests(String userLogin, String role){
        log.debug("Method starts");
        ArrayList<Integer> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_FIND_REQUEST_BY_LOGIN);
            preparedStatement.setString(1, userLogin);
            preparedStatement.setString(2, role);
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
            while (resultSet.next()){
                int id = resultSet.getInt("id_request");
               requests.add(id);
            }
            resultSet.close();
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
        return requests;
    }

    public RepairRequest findRequestByID(int id){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        RepairRequest request = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_FIND_REQUEST_BY_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
            RequestMapper requestMapper = new RequestMapper();
            if (resultSet.next()){
                   request = requestMapper.mapRow(resultSet);
                }
            resultSet.close();
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
        return request;
    }

    public void updateRequestByManager(int id, String status, int price){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_UPDATE_REQUEST_BY_MANAGER);
            preparedStatement.setInt(1, price);
            preparedStatement.setString(2, status);
            preparedStatement.setInt(3,id);
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

    public String findUserByRequestId(int requestId, String role){
        log.debug("Method starts");
        String masterLogin = "";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_REQUEST_ID);
            preparedStatement.setInt(1, requestId);
            preparedStatement.setString(2, role);
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
           if(resultSet.next()){
               masterLogin = resultSet.getString("user_login");
           }
           resultSet.close();
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
        return masterLogin;
    }

    public void updateRequestByMaster(int id, String state) {
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            preparedStatement = connection.prepareStatement(SQL_UPDATE_REQUEST_BY_MASTER);
            preparedStatement.setString(1, state);
            preparedStatement.setInt(2,id);
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

    public boolean deleteRequestById(int id, String status, String login, double amount){
        log.debug("Method starts");
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try{
            connection = DBManager.getInstance().getConnection();
            log.trace("Get connection with DBManager");
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            log.trace("Transaction");
            preparedStatement = connection.prepareStatement(SQL_FIND_REQUEST_BY_ID_AND_ROLE);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2, "MASTER");
            resultSet = preparedStatement.executeQuery();
            log.trace("Query execution ==> " + preparedStatement);
            if(!resultSet.next()){

                    preparedStatement = connection.prepareStatement(SQL_DELETE_REQUEST_BY_ID);
                    preparedStatement.setInt(1, id);
                    preparedStatement.executeUpdate();
                log.trace("Query execution ==> " + preparedStatement);

            if(status.equals("Paid")) {
                preparedStatement = connection.prepareStatement(UserDAO.SQL_UPDATE_ACCOUNT);
                preparedStatement.setDouble(1, amount);
                preparedStatement.setString(2, login);
                preparedStatement.executeUpdate();
                log.trace("Query execution ==> " + preparedStatement);
            }
                preparedStatement = connection.prepareStatement(SQL_DELETE_REQUEST);
                preparedStatement.setInt(1,id);
                preparedStatement.executeUpdate();
                log.trace("Query execution ==> " + preparedStatement);
                DBManager.getInstance().commitAndClose(connection);
                log.trace("Close connection with DBManager");
                log.debug("Method finished");
                return true;
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
        return false;
    }

    private static class RequestMapper implements EntityMapper<RepairRequest> {
        @Override
        public RepairRequest mapRow(ResultSet resultSet) {
            try {
                RepairRequest request =
                        new RepairRequest.RequestBuilder()
                        .withDescription(resultSet.getString(Constants.DESCRIPTION))
                        .withDateTime(resultSet.getString(Constants.DATETIME))
                        .withPrice(resultSet.getInt(Constants.PRICE))
                        .withStatus(resultSet.getString(Constants.STATUS))
                        .withState(resultSet.getString(Constants.STATE))
                        .build();
                request.setId(resultSet.getInt(Constants.ID));
                return request;
            } catch (SQLException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
