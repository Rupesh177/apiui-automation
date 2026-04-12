package org.rupesh.app.api.client;

import io.restassured.response.Response;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.utils.Config;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class TestDataClient {

    public static Map<String, String> createUser() {

        String existingUserId = TestContext.get("userId").toString();
        String existingEmail = TestContext.get("userEmail").toString();

        if (existingUserId != null && existingEmail != null) {
            return Map.of(
                    "id", existingUserId,
                    "email", existingEmail,
                    "password", "pass"
            );
        }

        Response response = given()
                .baseUri(Config.getTestDataUrl())
                .post("/testdata/user");

        String id = response.jsonPath().getString("id");
        String email = response.jsonPath().getString("email");

        TestContext.put("userId", id);
        TestContext.put("userEmail", email);

        return Map.of(
                "id", id,
                "email", email,
                "password", "pass"
        );
    }

    public static void deleteUser(String id) {
        given()
                .baseUri(Config.getTestDataUrl())
                .delete("/testdata/user/" + id);
    }
}