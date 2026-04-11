package rupesh.apiui.ui.pages;

import rupesh.apiui.core.driver.Driver;
import rupesh.apiui.core.driver.DriverManager;

public class LoginPage {

    private final Driver driver = DriverManager.getDriver();

    private final String email = "//input[@id='email']";
    private final String password = "//input[@id='password']";
    private final String loginBtn = "//button[@id='login']";
    private final String loggedInIndicator = "//div[@id='welcomeMessage']";

    public void login(String user, String pass) {
        driver.type(email, user);
        driver.type(password, pass);
        driver.click(loginBtn);
    }

    public boolean isLoggedIn() {
        try {
            String text = driver.getText(loggedInIndicator);
            return text != null && !text.isBlank();
        } catch (Exception e) {
            return false;
        }
    }
}
