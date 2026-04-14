package org.rupesh.app.api.services;

import io.restassured.response.Response;
import org.rupesh.app.api.client.ApiClient;
import org.rupesh.app.api.model.BookingRequest;
import org.rupesh.app.core.processor.ApiLogProcessor;

public class BookingService {

    private final ApiClient client = new ApiClient();

    public Response searchFlights() {

        Response response = client.get("/flights/search");
        ApiLogProcessor.log("/flights/search", null, response);

        return response;
    }

    public Response createBooking(BookingRequest requestBody) {

        Response response = client.post("/booking", requestBody);
        ApiLogProcessor.log("/booking", requestBody, response);

        return response;
    }
}