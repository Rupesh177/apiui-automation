package org.rupesh.app.core.featureFlag;

import io.restassured.response.Response;
import org.rupesh.app.utils.Config;

import static io.restassured.RestAssured.given;

import org.rupesh.app.exceptionNretry.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureFlagClient {

    private static final Logger log =
            LoggerFactory.getLogger(FeatureFlagClient.class);

    public boolean getFlag(String featureName) {

        try {
            Response response = given()
                    .baseUri(Config.getFeatureFlagUrl())
                    .when()
                    .get("/flags/{feature}", featureName);

            int status = response.getStatusCode();

            if (status != 200) {
                log.error("Feature flag API failed. feature={} status={}", featureName, status);
                throw new FrameworkException("Feature flag API failed: " + featureName);
            }

            Boolean enabled = response.jsonPath().getBoolean("enabled");

            if (enabled == null) {
                throw new FrameworkException("Invalid feature flag response for: " + featureName);
            }

            log.info("Feature flag [{}] = {}", featureName, enabled);

            return enabled;

        } catch (Exception e) {
            log.error("Failed to fetch feature flag: {}", featureName, e);

            // fallback strategy (important)
            return false;
        }
    }
}