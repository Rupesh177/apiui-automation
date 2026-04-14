package org.rupesh.app.core.featureFlag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeatureFlagService {

    private static final Logger log =
            LoggerFactory.getLogger(FeatureFlagService.class);

    private final FeatureFlagClient client = new FeatureFlagClient();

    public boolean isEnabled(String featureName) {

        Boolean overridden = FeatureFlagContext.get(featureName);

        if (overridden != null) {
            log.info("Feature flag override applied. feature={} enabled={}", featureName, overridden);
            return overridden;
        }

        boolean enabled = client.getFlag(featureName);

        log.info("Feature flag fetched from service. feature={} enabled={}", featureName, enabled);

        return enabled;
    }
}
