package org.rupesh.app.api.db;

import org.rupesh.app.core.db.DBClient;
import org.rupesh.app.core.db.SqlDBClient;
import org.rupesh.app.exceptionNretry.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;


public class BookingRepository {

    private static final Logger log =
            LoggerFactory.getLogger(BookingRepository.class);

    private final DBClient dbClient;

    public BookingRepository() {
        this.dbClient = new SqlDBClient();
    }

    public String getBookingStatus(String bookingId) {

        log.info("Fetching booking status for bookingId={}", bookingId);

        String query = "SELECT status FROM bookings WHERE id = '" + bookingId + "'";

        List<Map<String, Object>> result = dbClient.executeQuery(query);

        if (result.isEmpty()) {
            log.error("No booking found for bookingId={}", bookingId);
            throw new FrameworkException("No booking found for id: " + bookingId);
        }

        Object status = result.get(0).get("status");

        String bookingStatus = status != null ? status.toString() : null;

        log.info("Booking status fetched for bookingId={} status={}", bookingId, bookingStatus);

        return bookingStatus;
    }
}