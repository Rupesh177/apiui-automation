package org.rupesh.app.tests.ui;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.rupesh.app.api.client.TestDataClient;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.listeners.RetryAnalyzer;
import org.rupesh.app.pages.LoginPage;
import org.rupesh.app.utils.Config;

import java.util.Map;


public class LoginTest extends BaseTest {

    @Epic("MakeMyTrip")
    @Feature("Login")
    @Story("User login flow")
    @Test(groups = {"ui"}, retryAnalyzer = RetryAnalyzer.class)
    public void loginTest() {

        // -------------------------------
        // TEST DATA (from service)
        // -------------------------------
        Map<String, String> user =
                TestDataClient.createUser();

        String email = user.get("email");
        String password = user.get("password");

        TestContext.put("email", email);

        // -------------------------------
        // UI FLOW
        // -------------------------------
        driver().open(Config.getBaseUrl());

        LoginPage loginPage = new LoginPage();

        loginPage.login(email, password);

        // -------------------------------
        // ASSERTION (IMPORTANT)
        // -------------------------------
        Assert.assertTrue(
                loginPage.isLoggedIn(),
                "User login failed"
        );
    }
}