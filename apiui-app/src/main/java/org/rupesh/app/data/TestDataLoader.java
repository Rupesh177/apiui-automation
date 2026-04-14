package org.rupesh.app.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class TestDataLoader {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T load(String path, Class<T> clazz) {
        try (InputStream is =
                     TestDataLoader.class.getClassLoader().getResourceAsStream(path)) {

            if (is == null) {
                throw new RuntimeException("File not found: " + path);
            }

            return mapper.readValue(is, clazz);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data from: " + path, e);
        }
    }
}