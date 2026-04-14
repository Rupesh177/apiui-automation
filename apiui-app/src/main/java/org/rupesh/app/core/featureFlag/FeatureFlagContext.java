package org.rupesh.app.core.featureFlag;

import java.util.HashMap;
import java.util.Map;

public class FeatureFlagContext {

    private static final ThreadLocal<Map<String, Boolean>> context =
            ThreadLocal.withInitial(HashMap::new);

    private FeatureFlagContext() {
    }

    public static void put(String flagName, boolean enabled) {
        context.get().put(flagName, enabled);
    }

    public static Boolean get(String flagName) {
        return context.get().get(flagName);
    }

    public static void clear() {
        context.remove();
    }
}