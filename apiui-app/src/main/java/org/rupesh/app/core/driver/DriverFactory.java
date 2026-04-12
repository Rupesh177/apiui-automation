package org.rupesh.app.core.driver;

public class DriverFactory {

    public static Driver create() {

        String type = System.getProperty("driver", "selenium");

        if (type.equals("playwright")) {
            return new PlaywrightDriver();   // plug-in
        }

        return new SeleniumDriver();
    }
}