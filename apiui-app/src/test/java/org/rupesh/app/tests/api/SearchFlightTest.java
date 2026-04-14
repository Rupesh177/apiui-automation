package org.rupesh.app.tests.api;

import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.api.validator.SchemaValidator;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.listeners.RetryAnalyzer;

public class SearchFlightTest extends BaseTest {

    private final SchemaValidator validator= new SchemaValidator();

    @Feature("Flight Search API")
    @Test(groups = {"api"}, retryAnalyzer = RetryAnalyzer.class)
    public void searchFlightsTest() {

        Response response = new BookingService().searchFlights();
        validator.validate(response, "schema/booking-schema.json");   //Contract testing
        Assert.assertEquals(response.statusCode(), 200);
    }
}
