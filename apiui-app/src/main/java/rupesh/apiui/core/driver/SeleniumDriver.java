package rupesh.apiui.core.driver;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumDriver implements Driver {

    private WebDriver driver;

    public SeleniumDriver() {
        this.driver = WebDriverBuilder.build();
    }

    @Override
    public void open(String url) {
        driver.get(url);
    }

    // -------------------------------
    // STRING (BACKWARD COMPATIBILITY)
    // -------------------------------
    @Override
    public void click(String locator) {
        click(By.xpath(locator));
    }

    @Override
    public void type(String locator, String text) {
        type(By.xpath(locator), text);
    }

    @Override
    public String getText(String locator) {
        return getText(By.xpath(locator));
    }

    // -------------------------------
    // BY (RECOMMENDED)
    // -------------------------------
    @Override
    public void click(By by) {
        driver.findElement(by).click();
    }

    @Override
    public void type(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    @Override
    public String getText(By by) {
        return driver.findElement(by).getText();
    }

    @Override
    public byte[] takescreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void quit() {
        driver.quit();
    }

    public WebDriver getRawDriver() {
        return driver;
    }

    private WebElement find(By by) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}