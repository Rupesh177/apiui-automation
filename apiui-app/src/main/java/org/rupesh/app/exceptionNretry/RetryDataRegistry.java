package org.rupesh.app.exceptionNretry;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RetryDataRegistry {

    private static final Map<String, Map<String, Object>> registry = new ConcurrentHashMap<>();
    private static final Map<String, Boolean> retrying = new ConcurrentHashMap<>();

    private RetryDataRegistry() {
    }

    public static void save(String testKey, Map<String, Object> data) {
        registry.put(testKey, data);
    }

    public static Map<String, Object> get(String testKey) {
        return registry.get(testKey);
    }

    public static boolean contains(String testKey) {
        return registry.containsKey(testKey);
    }

    public static void markRetrying(String testKey) {
        retrying.put(testKey, true);
    }

    public static boolean isRetrying(String testKey) {
        return Boolean.TRUE.equals(retrying.get(testKey));
    }

    public static void clearRetrying(String testKey) {
        retrying.remove(testKey);
    }

    public static void clear(String testKey) {
        registry.remove(testKey);
        retrying.remove(testKey);
    }
}
