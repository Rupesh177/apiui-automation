package org.rupesh.app.dataprovider;

import org.testng.annotations.DataProvider;

public class FeatureFlagDataProvider {

    @DataProvider(name = "featureToggle")
    public static Object[][] featureToggle() {
        return new Object[][]{
                {"NEW_BOOKING_FLOW", true},
                {"NEW_BOOKING_FLOW", false}
        };
    }
}