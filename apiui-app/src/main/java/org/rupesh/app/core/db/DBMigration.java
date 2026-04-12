package org.rupesh.app.core.db;

import org.flywaydb.core.Flyway;
import org.rupesh.app.utils.Config;

public class DBMigration {

    public static void migrate() {

        Flyway.configure()
                .dataSource(
                        Config.getDbUrl(),
                        Config.getDbUser(),
                        Config.getDbPassword()
                )
                .load()
                .migrate();
    }
}