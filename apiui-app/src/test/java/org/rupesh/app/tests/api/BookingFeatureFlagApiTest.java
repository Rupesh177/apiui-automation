package org.rupesh.app.tests.api;

import io.restassured.response.Response;
import org.rupesh.app.api.client.ApiClient;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.core.featureFlag.FeatureFlagContext;
import org.rupesh.app.dataprovider.FeatureFlagDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class BookingFeatureFlagApiTest extends BaseTest {

    private final ApiClient apiClient = new ApiClient();

    @Test(dataProvider = "featureToggle", dataProviderClass = FeatureFlagDataProvider.class)
    public void bookingFlowFeatureToggleApiTest(boolean enabled) {

        FeatureFlagContext.put("NEW_BOOKING_FLOW", enabled);

        Response response = apiClient.post(
                "/booking",
                Map.of("from", "DEL", "to", "BOM", "featureEnabled", enabled)
        );

        Assert.assertEquals(response.getStatusCode(), 200);

        if (enabled) {
            Assert.assertEquals(response.jsonPath().getString("flowType"), "NEW");
        } else {
            Assert.assertEquals(response.jsonPath().getString("flowType"), "LEGACY");
        }
    }
}