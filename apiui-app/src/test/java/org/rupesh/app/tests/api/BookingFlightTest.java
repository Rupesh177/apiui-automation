package org.rupesh.app.tests.api;

import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.rupesh.app.api.services.BookingService;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.utils.DBUtil;

import java.util.List;
import java.util.Map;

public class BookingFlightTest {

    @Feature("Validate Flight Booking against DB")
    @Test(groups = {"api"})
    public void validateBookingInDB() throws Exception {

        String bookingId = new BookingService().createBooking(new BookingFlightTest())
                .jsonPath().getString("id");

        List<Map<String, Object>> rs = new DBUtil().executeQuery(
                "SELECT * FROM bookings WHERE id = '" + bookingId + "'"
        );

//        Assert.assertTrue(rs.next());
//        Assert.assertEquals(rs.getString("status"), "CONFIRMED");
//        TestContext.put("bookingId", id);  // ← remember what we created
    }

    @AfterMethod
    public void cleanup() {
        String id = (String) TestContext.get("bookingId");  // ← retrieve it
        TestContext.put("bookingId", id);  // ← remember what we created
        TestContext.clear();
    }
}
