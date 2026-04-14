package org.rupesh.app.base;

import org.rupesh.app.actions.BookingActions;
import org.rupesh.app.exceptionNretry.RetryDataRegistry;
import org.testng.ITestResult;
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

import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        if (Config.shouldRunMigration()) {
            DBMigration.migrate();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(ITestResult result) {
        DriverManager.setDriver(DriverFactory.create());
        String testKey = buildTestKey(result);

        if (RetryDataRegistry.contains(testKey)) {
            Map<String, Object> cached = RetryDataRegistry.get(testKey);

            if (cached != null) {
                cached.forEach(TestContext::put);
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult result) {

        String testKey = buildTestKey(result);

        try {
            boolean retryPending = RetryDataRegistry.isRetrying(testKey);

            if (retryPending) {
                Map<String, Object> snapshot = new HashMap<>();

                String userId = (String) TestContext.get("userId");
                String userEmail = (String) TestContext.get("userEmail");

                if (userId != null) {
                    snapshot.put("userId", userId);
                }

                if (userEmail != null) {
                    snapshot.put("userEmail", userEmail);
                }

                RetryDataRegistry.save(testKey, snapshot);
                RetryDataRegistry.clearRetrying(testKey);

            } else {
                String userId = (String) TestContext.get("userId");

                if (userId != null) {
                    TestDataClient.deleteUser(userId);
                }

                RetryDataRegistry.clear(testKey);
            }

        } finally {
            DriverManager.quit();
            TestContext.clear();
        }
    }


    protected Driver driver() {
        return DriverManager.getDriver();
    }

    private String buildTestKey(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
    }

    protected BookingActions bookingActions() {
        return new BookingActions();
    }
}