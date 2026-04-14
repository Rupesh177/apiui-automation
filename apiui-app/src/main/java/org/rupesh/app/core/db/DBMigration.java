package org.rupesh.app.core.db;

import org.flywaydb.core.Flyway;
import org.rupesh.app.core.integration.vault.VaultService;
import org.rupesh.app.utils.Config;

public class DBMigration {

    public static void migrate() {

        String dbPassword = Config.isVaultEnabled()
                ? new VaultService().getSecret("db.password")
                : Config.getDbPassword();

        Flyway.configure()
                .dataSource(
                        Config.getDbUrl(),
                        Config.getDbUser(),
                        dbPassword)
                .load()
                .migrate();
    }
}