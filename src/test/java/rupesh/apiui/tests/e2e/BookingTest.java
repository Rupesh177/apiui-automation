package rupesh.apiui.tests.e2e;

import org.testng.Assert;
import org.testng.annotations.Test;
import rupesh.apiui.api.services.BookingService;
import rupesh.apiui.base.BaseTest;
import rupesh.apiui.core.context.TestContext;
import rupesh.apiui.core.messaging.MessageManager;
import rupesh.apiui.listeners.RetryAnalyzer;

import java.util.List;


public class BookingTest extends BaseTest {

    private final BookingService bookingService= new BookingService();

    @Test(groups = {"ui"}, retryAnalyzer = RetryAnalyzer.class)
    public void endToEndFlow() {

        // API
        String bookingId = bookingService.createBooking()
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