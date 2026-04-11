package rupesh.apiui.TestGenerator;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rupesh.apiui.listeners.RetryAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestFileWriter {

    private static final Logger log =
            LoggerFactory.getLogger(TestFileWriter.class);

    public static void write(String packagePath,
                             String className,
                             String code) {

        try {
            // -------------------------------
            // BUILD DIRECTORY PATH
            // -------------------------------
            String baseDir = "src/test/java/rupesh/apiui/tests/aigenerated";
            String fullPath = baseDir + packagePath.replace(".", "/");

            Path dirPath = Path.of(fullPath);
            Files.createDirectories(dirPath);

            // -------------------------------
            // CREATE FILE
            // -------------------------------
            Path filePath = dirPath.resolve(className + ".java");

            Files.writeString(filePath, code);

            log.info("✅ Test generated at: " + filePath);

        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to write test file", e);
        }
    }
}
