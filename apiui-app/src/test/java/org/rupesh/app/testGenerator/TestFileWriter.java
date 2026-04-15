package org.rupesh.app.testGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class TestFileWriter {

    private static final Logger log =
            LoggerFactory.getLogger(TestFileWriter.class);

    public static void write(String packageName,
                             String className,
                             String code) {

        try {
            // -------------------------------
            // BASE DIRECTORY (DYNAMIC)
            // -------------------------------
            String baseDir = System.getProperty("user.dir") + "/src/test/java";

            // -------------------------------
            // PACKAGE → PATH
            // -------------------------------
            String packagePath = packageName.replace(".", "/");
            Path dirPath = Path.of(baseDir, packagePath);

            Files.createDirectories(dirPath);

            // -------------------------------
            // FILE PATH
            // -------------------------------
            Path filePath = dirPath.resolve(className + ".java");

            // -------------------------------
            // PREVENT ACCIDENTAL OVERWRITE
            // -------------------------------
            if (Files.exists(filePath)) {
                log.warn("Test already exists, skipping: {}", filePath);
                return;
            }

            // -------------------------------
            // WRITE FILE
            // -------------------------------
            Files.writeString(filePath, code, StandardCharsets.UTF_8);

            log.info("Test generated at: {}", filePath);

        } catch (IOException e) {
            throw new RuntimeException("Failed to write test file", e);
        }
    }
}