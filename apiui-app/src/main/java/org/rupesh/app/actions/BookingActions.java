package org.rupesh.app.actions;

import org.rupesh.app.pages.BookingPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookingActions {

    private static final Logger log =
            LoggerFactory.getLogger(BookingActions.class);

    private final BookingPage bookingPage;

    public BookingActions() {
        this.bookingPage = new BookingPage();
    }

    public void validateFlowVisibility(boolean newFlowEnabled) {

        log.info("Validating booking flow. New flow enabled={}", newFlowEnabled);

        if (newFlowEnabled) {

            if (!bookingPage.isNewFlowVisible()) {
                log.error("New booking flow NOT visible");
                throw new AssertionError("New booking flow should be visible");
            }

            log.info("New booking flow is visible");

        } else {

            if (!bookingPage.isLegacyFlowVisible()) {
                log.error("Legacy booking flow NOT visible");
                throw new AssertionError("Legacy booking flow should be visible");
            }

            log.info("Legacy booking flow is visible");
        }
    }

    public void completeBooking(String source, String destination) {

        log.info("Starting booking: {} → {}", source, destination);

        bookingPage.bookFlight(source, destination);

        log.info("Booking action completed");
    }

    public boolean isBookingConfirmed() {

        boolean result = bookingPage.isBookingConfirmed();

        log.info("Booking confirmation status: {}", result);

        return result;
    }
}