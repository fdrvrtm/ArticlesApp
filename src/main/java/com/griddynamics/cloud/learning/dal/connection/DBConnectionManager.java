package com.griddynamics.cloud.learning.dal.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DBConnectionManager {

    private static final Properties properties = new Properties();
    private static HikariDataSource dataSource;

    static {

        File file = new File(Objects.requireNonNull(DBConnectionManager.class.getClassLoader().getResource("db/database.properties")).getFile());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            properties.load(reader);
            HikariConfig config = new HikariConfig(properties);
            dataSource = new HikariDataSource(config);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DBConnectionManager() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
