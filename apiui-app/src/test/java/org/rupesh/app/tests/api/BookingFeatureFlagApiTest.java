package org.rupesh.app.tests.api;

import io.restassured.response.Response;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.core.featureFlag.FeatureFlagContext;
import org.rupesh.app.dataprovider.FeatureFlagDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;


public class BookingFeatureFlagApiTest extends BaseTest {

    private final BookingService bookingService = new BookingService();

    @Test(
            groups = {"api", "featureFlag"},
            dataProvider = "featureToggle",
            dataProviderClass = FeatureFlagDataProvider.class
    )
    public void bookingFlowFeatureToggleApiTest(boolean enabled) {

        FeatureFlagContext.put("NEW_BOOKING_FLOW", enabled);

        Response response = bookingService.createBooking(
                (org.rupesh.app.api.model.BookingRequest) Map.of(
                        "from", "DEL",
                        "to", "BOM",
                        "featureEnabled", enabled
                )
        );

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Booking API should return 200"
        );

        if (enabled) {
            Assert.assertEquals(
                    response.jsonPath().getString("flowType"),
                    "NEW",
                    "Flow type should be NEW when feature flag is enabled"
            );
        } else {
            Assert.assertEquals(
                    response.jsonPath().getString("flowType"),
                    "LEGACY",
                    "Flow type should be LEGACY when feature flag is disabled"
            );
        }
    }
}