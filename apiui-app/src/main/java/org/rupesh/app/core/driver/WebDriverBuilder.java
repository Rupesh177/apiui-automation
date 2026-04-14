package org.rupesh.app.core.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.rupesh.app.exceptionNretry.FrameworkException;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class WebDriverBuilder {

    private static final Logger log =
            LoggerFactory.getLogger(WebDriverBuilder.class);

    private WebDriverBuilder() {
    }

    public static WebDriver build() {

        String browser = Config.getBrowser();
        boolean remote = Config.isRemote();
        boolean headless = Config.isHeadless();
        boolean healing = Config.isHealingEnabled();
        String gridUrl = Config.getGridUrl();

        MutableCapabilities options = getOptions(browser, headless);

        try {
            log.info("Building WebDriver. browser={} remote={} headless={} healing={}",
                    browser, remote, headless, healing);

            WebDriver rawDriver = createDriver(browser, remote, gridUrl, options);

            if (healing) {
                log.info("Healenium enabled for browser={}", browser);
                return com.epam.healenium.SelfHealingDriver.create(rawDriver);
            }

            return rawDriver;

        } catch (Exception e) {
            log.error("Driver creation failed. browser={} remote={} gridUrl={}",
                    browser, remote, gridUrl, e);
            throw new FrameworkException("Driver creation failed", e);
        }
    }

    private static MutableCapabilities getOptions(String browser, boolean headless) {

        MutableCapabilities options;

        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefox = new FirefoxOptions();
                if (headless) {
                    firefox.addArguments("--headless");
                }
                options = firefox;
                break;

            case "edge":
                EdgeOptions edge = new EdgeOptions();
                if (headless) {
                    edge.addArguments("--headless=new");
                }
                options = edge;
                break;

            default:
                ChromeOptions chrome = new ChromeOptions();
                if (headless) {
                    chrome.addArguments("--headless=new");
                }
                options = chrome;
        }

        return options;
    }

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
                return new EdgeDriver((EdgeOptions) options);

            default:
                return new ChromeDriver((ChromeOptions) options);
        }
    }
}