package com.griddynamics.cloud.learning.dao.connection;

import com.griddynamics.cloud.learning.dao.DbException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnectionManager {

    private static final Logger logger = Logger.getLogger(DBConnectionManager.class.getName());
    private static final Properties properties = new Properties();
    private static final String resourceFilePath = "db/database.properties";
    private static final HikariDataSource dataSource;

    static {
        File file = new File(DBConnectionManager.class.getClassLoader().getResource(resourceFilePath).getFile());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            properties.load(reader);
            HikariConfig config = new HikariConfig(properties);
            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            final String msg = "Unable to establish a connection to a database.";
            logger.log(Level.SEVERE, msg, e);
            throw new DbException(msg, e);
        }
    }

    private DBConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}