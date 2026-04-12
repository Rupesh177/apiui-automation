package org.rupesh.app.api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.utils.Config;

public class ApiClient {

    public Response get(String endpoint) {
        Response response = RestAssured
                .given()
                .baseUri(Config.getBaseUrl())
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();

        // store in TestContext (thread-safe)
        TestContext.put("api_request", endpoint);
        TestContext.put("api_response", response.asPrettyString());

        return response;
    }

    public Response post(String endpoint, Object body) {

        Response response = RestAssured
                .given()
                .baseUri(Config.getBaseUrl())
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();

        // -------------------------------
        // STORE IN TEST CONTEXT
        // -------------------------------
        TestContext.put("api_endpoint", endpoint);
        TestContext.put("api_request_body", body);
        TestContext.put("api_response_body", response.asPrettyString());
        TestContext.put("api_status_code", response.getStatusCode());

        return response;
    }
}

