package org.rupesh.app.core.driver;


import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.rupesh.app.utils.Config;

import java.net.URL;

public class WebDriverBuilder {

    public static WebDriver build() {

        String browser = Config.getBrowser();
        boolean remote = Config.isRemote();
        boolean headless = Config.isHeadless();
        boolean healing = Config.isHealingEnabled();
        String gridUrl = Config.getGridUrl();

        MutableCapabilities options = getOptions(browser, headless);

        try {
            WebDriver rawDriver = createDriver(browser, remote, gridUrl, options);

            // -------------------------------
            // HEALENIUM WRAPPING
            // -------------------------------
            if (healing) {
                System.out.println("🩹 Healenium ENABLED");
                return com.epam.healenium.SelfHealingDriver.create(rawDriver);
            }

            return rawDriver;

        } catch (Exception e) {
            throw new RuntimeException("Driver creation failed", e);
        }
    }

    // -------------------------------
    // OPTIONS BUILDER
    // -------------------------------
    private static MutableCapabilities getOptions(String browser, boolean headless) {

        MutableCapabilities options;

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefox = new FirefoxOptions();
                if (headless) firefox.addArguments("--headless");
                options = firefox;
                break;

            case "edge":
                EdgeOptions edge = new EdgeOptions();
                if (headless) edge.addArguments("--headless=new");
                options = edge;
                break;

            default:
                ChromeOptions chrome = new ChromeOptions();
                if (headless) chrome.addArguments("--headless=new");
                options = chrome;
        }

        return options;
    }

    // -------------------------------
    // DRIVER CREATION
    // -------------------------------
    private static WebDriver createDriver(String browser,
                                          boolean remote,
                                          String gridUrl,
                                          MutableCapabilities options) throws Exception {

        if (remote) {
            return new RemoteWebDriver(new URL(gridUrl), options);
        }

        switch (browser.toLowerCase()) {
            case "firefox":
                return new FirefoxDriver((FirefoxOptions) options);

            case "edge":
                return new org.openqa.selenium.edge.EdgeDriver((EdgeOptions) options);

            default:
                return new ChromeDriver((ChromeOptions) options);
        }
    }
}