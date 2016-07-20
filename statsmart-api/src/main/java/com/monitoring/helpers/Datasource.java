package com.monitoring.helpers;

import com.monitoring.config.SystemConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Datasource contains all the information needed to get a database connection
 */
public class Datasource {

    private static final String JDBC_DRIVER = SystemConfig.getStringProperty("jdbc_driver");
    private static final String DB_URL = SystemConfig.getStringProperty("db_url");
    private static final String USER = SystemConfig.getStringProperty("user");
    private static final String PASS = SystemConfig.getStringProperty("pass");

    private Connection connection;

    public Datasource() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            //TODO add logging
            System.out.println("Database Connection Error");
            this.connection = null;
        }
    }

    public Connection getDatabaseConnection()  {
        return connection;
    }

}

