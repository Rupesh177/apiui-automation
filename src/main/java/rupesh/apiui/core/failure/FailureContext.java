package rupesh.apiui.core.failure;


import java.util.HashMap;
import java.util.Map;

public class FailureContext {

    private final String testName;
    private final Throwable error;
    private final Map<String, Object> data = new HashMap<>();

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
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }
}