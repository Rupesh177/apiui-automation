package rupesh.apiui.core.db;

import org.flywaydb.core.Flyway;
import rupesh.apiui.utils.Config;

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