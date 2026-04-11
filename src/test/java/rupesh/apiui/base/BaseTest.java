package rupesh.apiui.base;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import rupesh.apiui.core.context.TestContext;
import rupesh.apiui.core.db.DBMigration;
import rupesh.apiui.core.driver.Driver;
import rupesh.apiui.core.driver.DriverFactory;
import rupesh.apiui.core.driver.DriverManager;
import rupesh.apiui.utils.Config;

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