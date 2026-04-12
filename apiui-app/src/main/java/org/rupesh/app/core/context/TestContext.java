package org.rupesh.app.core.context;

import java.util.Map;
import java.util.HashMap;

public class TestContext {

    private static final ThreadLocal<Map<String, Object>> data =
            ThreadLocal.withInitial(HashMap::new);

    public static void put(String key, Object value) {
        data.get().put(key, value);
    }

    public static Object get(String key) {
        return data.get().get(key);
    }

    public static void clear() {
        data.remove();
    }
}