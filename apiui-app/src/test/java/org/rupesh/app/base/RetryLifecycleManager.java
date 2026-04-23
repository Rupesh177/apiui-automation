package org.rupesh.app.base;

import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.exceptionNretry.RetryDataRegistry;
import org.testng.ITestResult;

import java.util.HashMap;
import java.util.Map;

public class RetryLifecycleManager {

    public void restore(ITestResult result) {

        String testKey = TestKeyBuilder.build(result);

        if (!RetryDataRegistry.contains(testKey)) {
            return;
        }

        Map<String, Object> cached = RetryDataRegistry.get(testKey);

        if (cached != null) {
            cached.forEach(TestContext::put);
        }
    }

    public void handleAfterMethod(ITestResult result) {

        String testKey = TestKeyBuilder.build(result);

        if (RetryDataRegistry.isRetrying(testKey)) {
            RetryDataRegistry.save(testKey, snapshot());
            RetryDataRegistry.clearRetrying(testKey);
        } else {
            RetryDataRegistry.clear(testKey);
        }
    }

    private Map<String, Object> snapshot() {

        Map<String, Object> snapshot = new HashMap<>();

        copyIfPresent(snapshot, "userId");
        copyIfPresent(snapshot, "userEmail");
        copyIfPresent(snapshot, "bookingId");
        copyIfPresent(snapshot, "email");

        return snapshot;
    }

    private void copyIfPresent(Map<String, Object> target, String key) {
        Object value = TestContext.get(key);
        if (value != null) {
            target.put(key, value);
        }
    }
}