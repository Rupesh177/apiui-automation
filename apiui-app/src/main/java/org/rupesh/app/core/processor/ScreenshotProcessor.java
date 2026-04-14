package org.rupesh.app.core.processor;

import org.rupesh.app.core.driver.Driver;
import org.rupesh.app.core.driver.DriverManager;
import org.rupesh.app.core.failure.FailureContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScreenshotProcessor implements FailureProcessor {

    private static final Logger log =
            LoggerFactory.getLogger(ScreenshotProcessor.class);

    @Override
    public void process(FailureContext context) {

        try {
            Driver driver = DriverManager.getDriver();

            if (driver == null) {
                log.warn("Driver is null. Skipping screenshot capture.");
                return;
            }

            byte[] screenshot = driver.takescreenshot();

            if (screenshot != null && screenshot.length > 0) {
                context.put("screenshot", screenshot);
                log.debug("Screenshot captured successfully.");
            } else {
                log.warn("Screenshot captured but empty.");
            }

        } catch (Exception e) {
            log.error("Failed to capture screenshot", e);
        }
    }
}