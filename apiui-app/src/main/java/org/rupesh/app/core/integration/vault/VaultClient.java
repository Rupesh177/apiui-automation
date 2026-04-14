package org.rupesh.app.core.integration.vault;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.rupesh.app.exceptionNretry.FrameworkException;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VaultClient {

    private static final Logger log =
            LoggerFactory.getLogger(VaultClient.class);

    public String getSecret(String key) {

        try {
            Response response = given()
                    .baseUri(Config.getVaultUrl())
                    .header("X-Vault-Token", Config.getVaultToken())
                    .when()
                    .get(Config.getVaultSecretPath());

            if (response.statusCode() != 200) {
                log.error("Vault secret fetch failed. key={} status={}", key, response.statusCode());
                throw new FrameworkException("Vault fetch failed for key: " + key);
            }

            String value = response.jsonPath().getString("data.data." + key);

            if (value == null || value.isBlank()) {
                log.error("Secret not found in Vault for key={}", key);
                throw new FrameworkException("Vault secret missing: " + key);
            }

            return value;

        } catch (Exception e) {
            log.error("Vault request failed for key={}", key, e);
            throw new FrameworkException("Vault request failed", e);
        }
    }
}