package org.rupesh.app.core.featureFlag;

import io.restassured.response.Response;
import org.rupesh.app.utils.Config;

import static io.restassured.RestAssured.given;

public class FeatureFlagClient {

    public boolean getFlag(String featureName) {

        Response response = given()
                .baseUri(Config.getFeatureFlagUrl())
                .get("/flags/" + featureName);

        return response.jsonPath().getBoolean("enabled");
    }
}
