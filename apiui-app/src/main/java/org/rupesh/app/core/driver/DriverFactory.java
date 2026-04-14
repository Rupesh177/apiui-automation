package org.rupesh.app.core.driver;

import org.rupesh.app.exceptionNretry.FrameworkException;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {

    private static final Logger log =
            LoggerFactory.getLogger(DriverFactory.class);

    public static Driver create() {

        String type = Config.get("driver", "selenium").toLowerCase();

        log.info("Initializing driver type={}", type);

        switch (type) {

            case "playwright":
                return new PlaywrightDriver();

            case "selenium":
                return new SeleniumDriver();

            default:
                log.error("Unsupported driver type={}", type);
                throw new FrameworkException("Unsupported driver: " + type);
        }
    }
}