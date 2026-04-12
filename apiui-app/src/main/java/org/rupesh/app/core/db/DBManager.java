package org.rupesh.app.core.db;

import com.zaxxer.hikari.HikariDataSource;
import org.rupesh.app.utils.Config;

import java.sql.Connection;

public class DBManager {

    private static final HikariDataSource ds;

    static {
        ds = new HikariDataSource();
        ds.setJdbcUrl(Config.getDbUrl());
        ds.setUsername(Config.getDbUser());
        ds.setPassword(Config.getDbPassword());
        ds.setMaximumPoolSize(10);
    }

    public static Connection getConnection() throws Exception {
        return ds.getConnection();
    }
}