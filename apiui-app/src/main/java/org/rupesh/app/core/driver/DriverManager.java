package org.rupesh.app.core.driver;

import org.rupesh.app.exceptionNretry.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {

    private static final Logger log =
            LoggerFactory.getLogger(DriverManager.class);

    private static final ThreadLocal<Driver> DRIVER = new ThreadLocal<>();

    public static void setDriver(Driver d) {
        log.info("Setting driver for thread={}", Thread.currentThread().getId());
        DRIVER.set(d);
    }

    public static Driver getDriver() {

        Driver d = DRIVER.get();

        if (d == null) {
            log.error("Driver not initialized for thread={}", Thread.currentThread().getId());
            throw new FrameworkException("Driver not initialized. Did you forget BaseTest setup?");
        }

        return d;
    }

    public static void quit() {

        Driver d = DRIVER.get();

        if (d != null) {
            log.info("Quitting driver for thread={}", Thread.currentThread().getId());
            d.quit();
            DRIVER.remove();
        }
    }
}