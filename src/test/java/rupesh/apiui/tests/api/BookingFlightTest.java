package rupesh.apiui.tests.api;

import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import rupesh.apiui.api.services.BookingService;
import rupesh.apiui.core.context.TestContext;
import rupesh.apiui.utils.DBUtil;

import java.sql.ResultSet;

public class BookingFlightTest {

    @Feature("Validate Flight Booking against DB")
    @Test(groups = {"api"})
    public void validateBookingInDB() throws Exception {

        String bookingId = new BookingService().createBooking()
                .jsonPath().getString("id");

        ResultSet rs = DBUtil.executeQuery(
                "SELECT * FROM bookings WHERE id = '" + bookingId + "'"
        );

        Assert.assertTrue(rs.next());
        Assert.assertEquals(rs.getString("status"), "CONFIRMED");
        TestContext.put("bookingId", id);  // ← remember what we created
    }

    @AfterMethod
    public void cleanup() {
        String id = (String) TestContext.get("bookingId");  // ← retrieve it
        TestContext.put("bookingId", id);  // ← remember what we created
        TestContext.clear();
    }
}
