package org.rupesh.app.exceptionNretry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryDataRegistry {

    private static final Logger log =
            LoggerFactory.getLogger(RetryDataRegistry.class);

    private static final Map<String, Map<String, Object>> registry =
            new ConcurrentHashMap<>();

    private static final Map<String, Boolean> retrying =
            new ConcurrentHashMap<>();

    private RetryDataRegistry() {
    }

    // -------------------------------
    // STORE DATA
    // -------------------------------
    public static void save(String testKey, Map<String, Object> data) {
        registry.put(testKey, new HashMap<>(data)); // defensive copy
        log.debug("Saved retry data for testKey={}", testKey);
    }

    // -------------------------------
    // FETCH DATA
    // -------------------------------
    public static Map<String, Object> get(String testKey) {

        Map<String, Object> data = registry.get(testKey);

        if (data == null) {
            return null;
        }

        log.debug("Fetching retry data for testKey={}", testKey);

        return new HashMap<>(data); // defensive copy
    }

    public static boolean contains(String testKey) {
        return registry.containsKey(testKey);
    }

    // -------------------------------
    // RETRY FLAG
    // -------------------------------
    public static void markRetrying(String testKey) {
        retrying.put(testKey, true);
        log.debug("Marked test as retrying: {}", testKey);
    }

    public static boolean isRetrying(String testKey) {
        return Boolean.TRUE.equals(retrying.get(testKey));
    }

    public static void clearRetrying(String testKey) {
        retrying.remove(testKey);
    }

    // -------------------------------
    // CLEANUP
    // -------------------------------
    public static void clear(String testKey) {
        registry.remove(testKey);
        retrying.remove(testKey);
        log.debug("Cleared retry data for testKey={}", testKey);
    }
}