package org.rupesh.app.base;

import org.rupesh.app.actions.BookingActions;
import org.rupesh.app.core.db.DBMigration;
import org.rupesh.app.core.driver.Driver;
import org.rupesh.app.core.driver.DriverFactory;
import org.rupesh.app.core.driver.DriverManager;
import org.rupesh.app.utils.Config;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    private final RetryLifecycleManager retryLifecycleManager =
            new RetryLifecycleManager();

    private final TestCleanupManager testCleanupManager =
            new TestCleanupManager();

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        if (Config.shouldRunMigration()) {
            DBMigration.migrate();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult result) {
        DriverManager.setDriver(DriverFactory.create());
        retryLifecycleManager.restore(result);
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) {
        try {
            retryLifecycleManager.handleAfterMethod(result);
            testCleanupManager.cleanupCreatedResources();
        } finally {
            DriverManager.quit();
            testCleanupManager.clearExecutionContexts();
        }
    }

    protected Driver driver() {
        return DriverManager.getDriver();
    }

    protected BookingActions bookingActions() {
        return new BookingActions();
    }
}