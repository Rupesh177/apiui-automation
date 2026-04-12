package org.rupesh.app.core.failure;

import org.rupesh.app.core.driver.Driver;
import org.rupesh.app.core.driver.DriverManager;

public class ScreenshotProcessor implements FailureProcessor {

    @Override
    public void process(FailureContext context) {

        try {
            Driver driver = DriverManager.getDriver();

            if (driver != null) {
                byte[] screenshot = driver.takescreenshot();

                context.put("screenshot", screenshot);
            }

        } catch (Exception ignored) {}
    }
}