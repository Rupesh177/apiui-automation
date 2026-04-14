package org.rupesh.app.tests.api;

import io.qameta.allure.Feature;
import org.rupesh.app.api.db.BookingRepository;
import org.rupesh.app.api.model.BookingRequest;
import org.rupesh.app.data.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.utils.DBUtil;

import java.util.List;
import java.util.Map;

public class BookingFlightTest {

    @Feature("Validate Flight Booking against DB")
    @Test
    public void validateBookingInDB() {

        BookingService bookingService = new BookingService();
        BookingRepository bookingRepository = new BookingRepository();

        BookingRequest request = TestDataFactory.createBookingRequest("DEL", "BOM");

        String bookingId = bookingService
                .createBooking(request)
                .jsonPath()
                .getString("id");

        TestContext.put("bookingId", bookingId);

        String status = bookingRepository.getBookingStatus(bookingId);

        Assert.assertEquals(status, "CONFIRMED");
    }
}