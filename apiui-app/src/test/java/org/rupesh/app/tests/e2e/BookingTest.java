package org.rupesh.app.tests.e2e;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.core.messaging.MessageManager;
import org.rupesh.app.listeners.RetryAnalyzer;
import org.rupesh.app.tests.api.BookingFlightTest;

import java.util.List;


public class BookingTest extends BaseTest {

    private final BookingService bookingService= new BookingService();

    @Test(groups = {"ui"}, retryAnalyzer = RetryAnalyzer.class)
    public void endToEndFlow() {

        // API
        String bookingId = bookingService.createBooking(new BookingFlightTest())
                .jsonPath().getString("id");

        TestContext.put("bookingId", bookingId);

        // Kafka
        List<String> events = MessageManager.get()
                .consume("booking-events");

        Assert.assertTrue(
                events.stream().anyMatch(e -> e.contains(bookingId)),
                "Booking event not found in Kafka"
        );

        // UI
        driver().open("https://www.makemytrip.com");
        // validate UI
    }
}