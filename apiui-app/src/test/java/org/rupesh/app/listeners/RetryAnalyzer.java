package org.rupesh.app.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.rupesh.app.utils.Config;


public class RetryAnalyzer implements IRetryAnalyzer {

    private int count = 0;
    private final int maxRetry = Config.getMaxRetry();
    private static final Logger log =
            LoggerFactory.getLogger(RetryAnalyzer.class);

    @Override
    public boolean retry(ITestResult result) {

        if (count < maxRetry) {
            count++;
            log.warn("🔁 Retrying test: {} | Attempt: {}", result.getName(), count);
            return true;
        }

        return false;
    }
}