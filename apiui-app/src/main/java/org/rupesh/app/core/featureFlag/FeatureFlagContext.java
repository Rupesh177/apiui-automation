package org.rupesh.app.core.featureFlag;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureFlagContext {

    private static final ThreadLocal<Map<String, Boolean>> CONTEXT =
            ThreadLocal.withInitial(ConcurrentHashMap::new);

    private FeatureFlagContext() {
    }

    public static void put(String flagName, boolean enabled) {
        CONTEXT.get().put(flagName, enabled);
    }

    public static Boolean get(String flagName) {
        return CONTEXT.get().get(flagName);
    }

    public static boolean contains(String flagName) {
        return CONTEXT.get().containsKey(flagName);
    }

    public static void clear() {
        CONTEXT.remove();
    }
}