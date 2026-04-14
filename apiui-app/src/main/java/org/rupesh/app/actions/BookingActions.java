package org.rupesh.app.actions;


import org.rupesh.app.pages.BookingPage;

public class BookingActions {

    private final BookingPage bookingPage;

    public BookingActions() {
        this.bookingPage = new BookingPage();
    }

    public void validateFlowVisibility(boolean newFlowEnabled) {
        if (newFlowEnabled) {
            if (!bookingPage.isNewFlowVisible()) {
                throw new AssertionError("New booking flow should be visible");
            }
        } else {
            if (!bookingPage.isLegacyFlowVisible()) {
                throw new AssertionError("Legacy booking flow should be visible");
            }
        }
    }

    public void completeBooking(String source, String destination) {
        bookingPage.bookFlight(source, destination);
    }

    public boolean isBookingConfirmed() {
        return bookingPage.isBookingConfirmed();
    }
}