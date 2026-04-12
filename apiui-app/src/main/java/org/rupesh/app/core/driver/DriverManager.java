package org.rupesh.app.core.driver;

public class DriverManager {

    private static final ThreadLocal<Driver> driver = new ThreadLocal<>();

    public static void setDriver(Driver d) {
        driver.set(d);
    }

    public static Driver getDriver() {
        return driver.get();
    }

    public static void quit() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}