package org.rupesh.app.core.db;

import com.zaxxer.hikari.HikariDataSource;
import org.rupesh.app.core.integration.vault.VaultService;
import org.rupesh.app.utils.Config;
import java.sql.Connection;
import org.rupesh.app.exceptionNretry.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBManager {

    private static final Logger log =
            LoggerFactory.getLogger(DBManager.class);

    private static final HikariDataSource ds;

    static {
        try {
            log.info("Initializing DB connection pool...");

            String dbPassword = Config.isVaultEnabled()
                    ? new VaultService().getSecret("db.password")
                    : Config.getDbPassword();

            ds = new HikariDataSource();
            ds.setJdbcUrl(Config.getDbUrl());
            ds.setUsername(Config.getDbUser());
            ds.setPassword(dbPassword);
            log.info("DB pool initialized successfully");

        } catch (Exception e) {
            log.error("Failed to initialize DB pool", e);
            throw new FrameworkException("DB initialization failed", e);
        }
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            log.error("Failed to get DB connection", e);
            throw new FrameworkException("Failed to get DB connection", e);
        }
    }
}