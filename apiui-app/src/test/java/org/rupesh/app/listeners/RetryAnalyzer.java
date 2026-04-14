package org.rupesh.app.listeners;

import org.rupesh.app.exceptionNretry.RetryDataRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.rupesh.app.utils.Config;


public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log =
            LoggerFactory.getLogger(RetryAnalyzer.class);

    private int count = 0;
    private final int maxRetry = Config.getMaxRetry();

    @Override
    public boolean retry(ITestResult result) {

        if (count < maxRetry) {
            count++;

            String testKey = buildTestKey(result);
            RetryDataRegistry.markRetrying(testKey);

            log.warn("Retrying test: {} | Attempt: {}", result.getName(), count);
            return true;
        }

        return false;
    }

    private String buildTestKey(ITestResult result) {
        return result.getTestClass().getName() + "#" + result.getMethod().getMethodName();
    }
}