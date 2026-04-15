package org.rupesh.app.tests.api;

import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.api.validator.SchemaValidator;
import org.rupesh.app.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchFlightTest extends BaseTest {

    private final SchemaValidator validator = new SchemaValidator();
    private final BookingService bookingService = new BookingService();

    @Feature("Flight Search API")
    @Test(groups = {"api"})
    public void searchFlightsTest() {

        Response response = bookingService.searchFlights();

        validator.validate(response, "schema/booking-schema.json");

        Assert.assertEquals(
                response.statusCode(),
                200,
                "Flight search API returned unexpected status code"
        );
    }
}