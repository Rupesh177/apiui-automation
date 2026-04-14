package org.rupesh.app.data;

import org.rupesh.app.api.model.BookingRequest;
import org.rupesh.app.api.model.LoginRequest;

public class TestDataFactory {

    private TestDataFactory() {}

    // -------------------------------
    // BOOKING
    // -------------------------------
    public static BookingRequest createBookingRequest(String source, String destination) {

        BookingRequest request = TestDataLoader.load("schema/booking.json", BookingRequest.class);

        if (source != null) {
            request.setSource(source);
        }

        if (destination != null) {
            request.setDestination(destination);
        }

        return request;
    }

    // -------------------------------
    // LOGIN (future use)
    // -------------------------------
    public static LoginRequest createLoginRequest() {
        return TestDataLoader.load("schema/login.json", LoginRequest.class);
    }
}