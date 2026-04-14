package org.rupesh.app.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rupesh.app.exceptionNretry.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;


public class TestDataLoader {

    private static final Logger log =
            LoggerFactory.getLogger(TestDataLoader.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private TestDataLoader() {
    }

    public static <T> T load(String path, Class<T> clazz) {

        try (InputStream is =
                     TestDataLoader.class.getClassLoader().getResourceAsStream(path)) {

            if (is == null) {
                log.error("Test data file not found: {}", path);
                throw new FrameworkException("File not found: " + path);
            }

            return mapper.readValue(is, clazz);

        } catch (Exception e) {
            log.error("Failed to load test data from {} into {}", path, clazz.getSimpleName(), e);
            throw new FrameworkException(
                    "Failed to load test data from: " + path + " into " + clazz.getSimpleName(),
                    e
            );
        }
    }
}