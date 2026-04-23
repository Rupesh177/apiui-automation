package org.rupesh.app.tests.e2e;

import org.rupesh.app.api.model.BookingRequest;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.core.messaging.MessageManager;
import org.rupesh.app.data.TestDataFactory;
import org.rupesh.app.utils.Config;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BookingTest extends BaseTest {

    private final BookingService bookingService = new BookingService();

    @Test(groups = {"e2e", "api", "ui", "kafka"})
    public void endToEndFlow() {

        // API
        BookingRequest override = new BookingRequest();
        override.setSource("DEL");
        override.setDestination("BOM");

        BookingRequest request = TestDataFactory.createBookingRequest(override);

        String bookingId = bookingService
                .createBooking(request)
                .jsonPath()
                .getString("id");

        TestContext.put("bookingId", bookingId);

        // Kafka
        List<String> events = MessageManager.get().consume("booking-events");

        Assert.assertTrue(
                events.stream().anyMatch(e -> e.contains(bookingId)),
                "Booking event not found in Kafka"
        );

        // UI
        driver().open(Config.getUiBaseUrl());

        bookingActions().completeBooking("DEL", "BOM");

        Assert.assertTrue(
                bookingActions().isBookingConfirmed(),
                "Booking should be confirmed in UI"
        );
    }
}