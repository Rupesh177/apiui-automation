package org.rupesh.app.pages;

import org.rupesh.app.core.driver.Driver;
import org.rupesh.app.core.driver.DriverManager;

public class LoginPage {

    private final String emailInput = "//input[@id='email']";
    private final String passwordInput = "//input[@id='password']";
    private final String loginBtn = "//button[@id='login']";
    private final String loggedInIndicator = "//div[@id='welcomeMessage']";

    private Driver driver() {
        return DriverManager.getDriver();
    }

    public void login(String user, String pass) {
        driver().type(emailInput, user);
        driver().type(passwordInput, pass);
        driver().click(loginBtn);
    }

    public boolean isLoggedIn() {
        try {
            String text = driver().getText(loggedInIndicator);
            return text != null && !text.isBlank();
        } catch (Exception e) {
            return false;
        }
    }
}