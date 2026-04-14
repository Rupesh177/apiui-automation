package org.rupesh.app.core.failure;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FailureContext {

    private final String testName;
    private final Throwable error;

    private final Map<String, Object> data = new ConcurrentHashMap<>();

    public FailureContext(String testName, Throwable error) {
        this.testName = testName;
        this.error = error;
    }

    public String getTestName() {
        return testName;
    }

    public Throwable getError() {
        return error;
    }

    public void put(String key, Object value) {
        if (key != null && value != null) {
            data.put(key, value);
        }
    }

    public Object get(String key) {
        return data.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> type) {
        Object value = data.get(key);
        return value != null ? (T) value : null;
    }

    @Override
    public String toString() {
        return "FailureContext{" +
                "testName='" + testName + '\'' +
                ", error=" + (error != null ? error.getMessage() : null) +
                ", dataKeys=" + data.keySet() +
                '}';
    }
}