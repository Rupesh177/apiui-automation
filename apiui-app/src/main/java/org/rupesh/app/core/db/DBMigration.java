package org.rupesh.app.core.db;

import org.flywaydb.core.Flyway;
import org.rupesh.app.core.integration.vault.VaultService;
import org.rupesh.app.exceptionNretry.FrameworkException;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBMigration {

    private static final Logger log =
            LoggerFactory.getLogger(DBMigration.class);

    public static void migrate() {

        try {
            log.info("Starting DB migration...");

            String dbPassword = Config.isVaultEnabled()
                    ? new VaultService().getSecret("db.password")
                    : Config.getDbPassword();

            Flyway.configure()
                    .dataSource(
                            Config.getDbUrl(),
                            Config.getDbUser(),
                            dbPassword
                    )
                    .load()
                    .migrate();

            log.info("DB migration completed successfully");

        } catch (Exception e) {
            log.error("DB migration failed", e);
            throw new FrameworkException("DB migration failed", e);
        }
    }
}