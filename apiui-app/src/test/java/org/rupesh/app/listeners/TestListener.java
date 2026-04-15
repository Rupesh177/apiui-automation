package org.rupesh.app.listeners;

import io.qameta.allure.Allure;
import org.rupesh.app.core.failure.FailureContext;
import org.rupesh.app.core.failure.FailureHandler;
import org.rupesh.app.core.monitoring.MetricsCollector;
import org.rupesh.app.core.monitoring.MetricsServer;
import org.rupesh.app.core.processor.ApiLogProcessor;
import org.rupesh.app.core.processor.FailureProcessor;
import org.rupesh.app.core.processor.JiraProcessor;
import org.rupesh.app.core.processor.ScreenshotProcessor;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {

    private static final Logger log =
            LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        if (Config.isMetricsEnabled()) {
            MetricsServer.start();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        log.error("Test failed: {}", result.getName(), result.getThrowable());
        MetricsCollector.fail();

        FailureContext context = new FailureContext(
                result.getName(),
                result.getThrowable()
        );

        List<FailureProcessor> processors = new ArrayList<>();
        processors.add(new ScreenshotProcessor());

        if (Config.isJiraEnabled()) {
            processors.add(new JiraProcessor());
            processors.add(new ApiLogProcessor());
        }

        FailureHandler handler = new FailureHandler(processors);
        handler.handle(context);

        try {
            byte[] screenshot = (byte[]) context.get("screenshot");

            if (screenshot != null) {
                Allure.addAttachment(
                        "Screenshot",
                        new ByteArrayInputStream(screenshot)
                );
            }

        } catch (Exception e) {
            log.warn("Failed to attach screenshot to Allure", e);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        MetricsCollector.pass();
        log.info("Test passed: {}", result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        MetricsCollector.skip();
        log.warn("Test skipped: {}", result.getName());
    }
}