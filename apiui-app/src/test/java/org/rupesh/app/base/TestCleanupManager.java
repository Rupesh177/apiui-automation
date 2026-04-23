package org.rupesh.app.base;

import org.rupesh.app.api.client.TestDataClient;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.core.featureFlag.FeatureFlagContext;

public class TestCleanupManager {

    private final TestDataClient testDataClient = new TestDataClient();

    public void cleanupCreatedResources() {

        String userId = (String) TestContext.get("userId");

        if (userId != null) {
            testDataClient.deleteUser(userId);
        }
    }

    public void clearExecutionContexts() {
        FeatureFlagContext.clear();
        TestContext.clear();
    }
}