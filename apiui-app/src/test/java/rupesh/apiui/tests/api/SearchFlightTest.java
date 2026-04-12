package rupesh.apiui.tests.api;

import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import rupesh.apiui.api.services.BookingService;
import rupesh.apiui.api.validator.SchemaValidator;
import rupesh.apiui.base.BaseTest;
import rupesh.apiui.listeners.RetryAnalyzer;

public class SearchFlightTest extends BaseTest {

    private final SchemaValidator validator= new SchemaValidator();

    @Feature("Flight Search API")
    @Test(groups = {"api"}, retryAnalyzer = RetryAnalyzer.class)
    public void searchFlightsTest() {

        Response response = new BookingService().searchFlight();
        validator.validate(response, "schema/booking-schema.json");   //Contract testing
        Assert.assertEquals(response.statusCode(), 200);
    }
}
