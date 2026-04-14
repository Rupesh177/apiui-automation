package org.rupesh.app.api.db;

import org.rupesh.app.core.db.DBClient;
import org.rupesh.app.core.db.SqlDBClient;

import java.util.List;
import java.util.Map;

public class BookingRepository {

    private final DBClient dbClient;

    public BookingRepository() {
        this.dbClient = new SqlDBClient();
    }

    public String getBookingStatus(String bookingId) {

        String query = "SELECT status FROM bookings WHERE id = '" + bookingId + "'";

        List<Map<String, Object>> result = dbClient.executeQuery(query);

        if (result.isEmpty()) {
            throw new RuntimeException("No booking found for id: " + bookingId);
        }

        Object status = result.get(0).get("status");
        return status != null ? status.toString() : null;
    }
}