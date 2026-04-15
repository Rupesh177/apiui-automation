package org.rupesh.app.tests.api;

import io.qameta.allure.Feature;
import org.rupesh.app.api.db.BookingRepository;
import org.rupesh.app.api.model.BookingRequest;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.base.BaseTest;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.data.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingFlightTest extends BaseTest {

    private final BookingService bookingService = new BookingService();
    private final BookingRepository bookingRepository = new BookingRepository();

    @Feature("Validate Flight Booking against DB")
    @Test(groups = {"api"})
    public void validateBookingInDB() {

        BookingRequest override = new BookingRequest();
        override.setSource("DEL");
        override.setDestination("BOM");

        BookingRequest request = TestDataFactory.createBookingRequest(override);

        String bookingId = bookingService
                .createBooking(request)
                .jsonPath()
                .getString("id");

        TestContext.put("bookingId", bookingId);

        String status = bookingRepository.getBookingStatus(bookingId);

        Assert.assertEquals(
                status,
                "CONFIRMED",
                "Booking status mismatch in DB"
        );
    }
}