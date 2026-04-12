package rupesh.apiui.core.driver;

import org.openqa.selenium.By;

public interface Driver {

    void open(String url);

    void click(String locator);

    void type(String locator, String text);

    String getText(String locator);

    void click(By by);

    void type(By by, String text);

    String getText(By by);

    byte[] takescreenshot();

    void quit();
}