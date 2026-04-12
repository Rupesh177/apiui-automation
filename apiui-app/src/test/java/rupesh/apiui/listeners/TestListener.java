package rupesh.apiui.listeners;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import rupesh.apiui.core.failure.*;
import rupesh.apiui.core.monitoring.MetricsCollector;
import rupesh.apiui.core.monitoring.MetricsServer;
import rupesh.apiui.utils.Config;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {

    private static final Logger log =
            LoggerFactory.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {

        log.error("❌ Test Failed: {}", result.getName(), result.getThrowable());
        MetricsCollector.fail();

        // -------------------------------
        // BUILD FAILURE CONTEXT
        // -------------------------------
        FailureContext context = new FailureContext(
                result.getName(),
                result.getThrowable()
        );

        // -------------------------------
        // BUILD PROCESSOR PIPELINE
        // -------------------------------
        List<FailureProcessor> processors = new ArrayList<>();

        // Always capture screenshot (if driver exists)
        processors.add(new ScreenshotProcessor());

        // Add Jira-related processors only if enabled
        if (Config.isJiraEnabled()) {
            processors.add(new JiraProcessor());
            processors.add(new ApiLogProcessor());
        }

        // -------------------------------
        // EXECUTE FAILURE HANDLER
        // -------------------------------
        FailureHandler handler = new FailureHandler(processors);
        handler.handle(context);

        // -------------------------------
        // ALLURE ATTACHMENT (Adapter responsibility)
        // -------------------------------
        try {
            byte[] screenshot = (byte[]) context.get("screenshot");

            if (screenshot != null) {
                Allure.addAttachment(
                        "Screenshot",
                        new ByteArrayInputStream(screenshot)
                );
            }

        } catch (Exception e) {
            log.warn("⚠ Failed to attach screenshot to Allure");
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        MetricsCollector.pass();
        log.info("PASSED");
    }

    @Override
    public void onStart(ITestContext context) {
        if (Config.isMetricsEnabled()) {
            MetricsServer.start();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        MetricsCollector.skip();
    }
}
