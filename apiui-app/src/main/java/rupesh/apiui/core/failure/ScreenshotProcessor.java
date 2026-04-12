package rupesh.apiui.core.failure;

import rupesh.apiui.core.driver.Driver;
import rupesh.apiui.core.driver.DriverManager;

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