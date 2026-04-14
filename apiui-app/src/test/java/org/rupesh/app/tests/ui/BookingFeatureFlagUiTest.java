package org.rupesh.app.tests.ui;

import org.rupesh.app.actions.BookingActions;
import org.rupesh.app.core.featureFlag.FeatureFlagContext;
import org.rupesh.app.dataprovider.FeatureFlagDataProvider;
import org.rupesh.app.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingFeatureFlagUiTest {

    @Test(dataProvider = "featureToggle", dataProviderClass = FeatureFlagDataProvider.class)
    public void bookingFlowFeatureToggleUiTest(boolean enabled) {

        FeatureFlagContext.put("NEW_BOOKING_FLOW", enabled);

        driver().open(Config.getBaseUrl());

        BookingActions bookingActions = new BookingActions();

        bookingActions.validateFlowVisibility(enabled);
        bookingActions.completeBooking("DEL", "BOM");

        Assert.assertTrue(
                bookingActions.isBookingConfirmed(),
                "Booking should be confirmed"
        );
    }
}
