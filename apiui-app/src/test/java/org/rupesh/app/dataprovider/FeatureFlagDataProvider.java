package org.rupesh.app.dataprovider;

import org.testng.annotations.DataProvider;

public class FeatureFlagDataProvider {

    @DataProvider(name = "featureToggle")
    public static Object[][] featureToggle() {
        return new Object[][]{
                {true},
                {false}
        };
    }
}