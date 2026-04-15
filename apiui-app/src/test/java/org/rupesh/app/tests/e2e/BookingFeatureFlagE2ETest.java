package org.rupesh.app.tests.e2e;

import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.rupesh.app.api.model.BookingRequest;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.core.featureFlag.FeatureFlagContext;
import org.rupesh.app.core.messaging.MessageManager;
import org.rupesh.app.data.TestDataFactory;
import org.rupesh.app.dataprovider.FeatureFlagDataProvider;
import org.rupesh.app.pages.BookingPage;

import org.rupesh.app.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BookingFeatureFlagE2ETest extends BaseTest {

    private final BookingService bookingService = new BookingService();

    @Feature("Booking flow with feature toggle")
    @Test(
            groups = {"e2e", "ui", "api", "kafka"},
            dataProvider = "featureToggle",
            dataProviderClass = FeatureFlagDataProvider.class
    )
    public void bookingFlowFeatureToggleE2E(boolean enabled) {

        // -------------------------------
        // OVERRIDE FEATURE FLAG
        // -------------------------------
        FeatureFlagContext.put("NEW_BOOKING_FLOW", enabled);

        // -------------------------------
        // API - CREATE BOOKING
        // -------------------------------
        BookingRequest override = new BookingRequest();
        override.setSource("DEL");
        override.setDestination("BOM");

        BookingRequest request = TestDataFactory.createBookingRequest(override);

        Response response = bookingService.createBooking(request);

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Booking API failed"
        );

        String bookingId = response.jsonPath().getString("id");
        TestContext.put("bookingId", bookingId);

        // -------------------------------
        // KAFKA - VALIDATE EVENT
        // -------------------------------
        List<String> events = MessageManager.get().consume("booking-events");

        Assert.assertTrue(
                events.stream().anyMatch(e -> e.contains(bookingId)),
                "Booking event not found in Kafka"
        );

        // -------------------------------
        // UI - VALIDATE FLOW
        // -------------------------------
        driver().open(Config.getBaseUrl());

        BookingPage bookingPage = new BookingPage();

        if (enabled) {
            Assert.assertTrue(
                    bookingPage.isNewFlowVisible(),
                    "New booking flow should be visible"
            );
        } else {
            Assert.assertTrue(
                    bookingPage.isLegacyFlowVisible(),
                    "Legacy booking flow should be visible"
            );
        }
    }
}