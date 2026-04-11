package rupesh.apiui.api.services;

import io.restassured.response.Response;
import rupesh.apiui.api.client.ApiClient;

public class BookingService {

    private final ApiClient client = new ApiClient();

    public Response searchFlight() {
        return client.get("/flights/search");
    }

    public Response createBooking(Object requestBody) {

        Response response = client.post("/booking", requestBody);
        return response;
    }
}