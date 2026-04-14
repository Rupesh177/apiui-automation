package org.rupesh.app.core.integration.vault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.rupesh.app.utils.Config;


public class VaultService {

    private static final Logger log =
            LoggerFactory.getLogger(VaultService.class);

    private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

    private final VaultClient vaultClient = new VaultClient();

    public String getSecret(String key) {

        if (!Config.isVaultEnabled()) {
            throw new RuntimeException("Vault is disabled but secret was requested: " + key);
        }

        return CACHE.computeIfAbsent(key, k -> {
            log.info("Fetching secret from Vault for key={}", k);
            return vaultClient.getSecret(k);
        });
    }

    public void clearCache() {
        CACHE.clear();
    }
}