package org.rupesh.app.data;

import org.rupesh.app.api.model.BookingRequest;
import org.rupesh.app.api.model.LoginRequest;


public class TestDataFactory {

    private static final String BOOKING_TEMPLATE = "testdata/booking.json";
    private static final String LOGIN_TEMPLATE = "testdata/login.json";

    private TestDataFactory() {
    }

    // -------------------------------
    // BOOKING
    // -------------------------------
    public static BookingRequest createBookingRequest(BookingRequest override) {

        BookingRequest base =
                TestDataLoader.load(BOOKING_TEMPLATE, BookingRequest.class);

        if (override == null) {
            return base;
        }

        if (override.getSource() != null) {
            base.setSource(override.getSource());
        }

        if (override.getDestination() != null) {
            base.setDestination(override.getDestination());
        }

        // future safe: add more fields here

        return base;
    }

    // -------------------------------
    // LOGIN
    // -------------------------------
    public static LoginRequest createLoginRequest() {
        return TestDataLoader.load(LOGIN_TEMPLATE, LoginRequest.class);
    }
}