package org.rupesh.app.base;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.rupesh.app.api.client.TestDataClient;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.core.db.DBMigration;
import org.rupesh.app.core.driver.Driver;
import org.rupesh.app.core.driver.DriverFactory;
import org.rupesh.app.core.driver.DriverManager;
import org.rupesh.app.utils.Config;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        if (Config.shouldRunMigration()) {
            DBMigration.migrate();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        DriverManager.setDriver(DriverFactory.create());
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {

        try {
            // -------------------------------
            // TEST DATA CLEANUP
            // -------------------------------
            String userId = (String) TestContext.get("userId");

            if (userId != null) {
                TestDataClient.deleteUser(userId);
            }

        } finally {
            // -------------------------------
            // DRIVER CLEANUP
            // -------------------------------
            DriverManager.quit();

            // -------------------------------
            // CONTEXT CLEANUP
            // -------------------------------
            TestContext.clear();
        }
    }

    protected Driver driver() {
        return DriverManager.getDriver();
    }
}