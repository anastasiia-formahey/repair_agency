package com.formahei.dao;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataBase Manager. Works with MySQL DataBase
 * @author Anastasiia Formahei
 * */

public class DBManager {

    private static final Logger log = Logger.getLogger(DBManager.class);
    private static DBManager instance = null;

    private DBManager(){}

      // singleton
    public static synchronized DBManager getInstance(){
        if(instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    /**
     *This method returns a DB connection from the Pool Connection
     * @return A DataBase connection
     * */

    public Connection getConnection(){
        Connection connection = null;
        try{
            Context envContext = new InitialContext();
            DataSource dataSource = (DataSource) envContext.lookup("java:comp/env/jdbc/repair_agency");
            connection = dataSource.getConnection();

        }catch (NamingException e) {
            log.error("Cannot obtain a connection from the pool", e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * This method commits and close the given connection
     * @param connection Connection to be committed and closed
     *
     * */
    public void commitAndClose(Connection connection){
        try{
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method rollbacks and close the given connection
     * @param connection Connection to be rollback and closed
     *
     * */
    public void rollbackAndClose(Connection connection){
        try{
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}