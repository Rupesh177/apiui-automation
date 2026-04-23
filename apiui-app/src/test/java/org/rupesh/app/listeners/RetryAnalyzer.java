package org.rupesh.app.listeners;

import org.rupesh.app.base.TestKeyBuilder;
import org.rupesh.app.exceptionNretry.RetryDataRegistry;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log =
            LoggerFactory.getLogger(RetryAnalyzer.class);

    private static final String RETRY_COUNT = "retryCount";

    private final int maxRetry = Config.getMaxRetry();

    @Override
    public boolean retry(ITestResult result) {

        Integer currentCount = (Integer) result.getAttribute(RETRY_COUNT);

        if (currentCount == null) {
            currentCount = 0;
        }

        if (currentCount < maxRetry) {
            currentCount++;
            result.setAttribute(RETRY_COUNT, currentCount);

            String testKey = TestKeyBuilder.build(result);
            RetryDataRegistry.markRetrying(testKey);

            log.info("Retrying test: {} | Attempt: {}", result.getName(), currentCount);
            return true;
        }

        log.warn("Max retry reached for test: {}", result.getName());
        return false;
    }
}