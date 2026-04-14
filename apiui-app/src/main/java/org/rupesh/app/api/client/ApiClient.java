package org.rupesh.app.api.client;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.rupesh.app.utils.Config;

public class ApiClient {

    public Response get(String endpoint) {

        return RestAssured
                .given()
                .baseUri(Config.getBaseUrl())
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response post(String endpoint, Object body) {

        return RestAssured
                .given()
                .baseUri(Config.getBaseUrl())
                .contentType("application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
}