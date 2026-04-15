package org.rupesh.app.testGenerator;

import org.rupesh.app.core.ai.generator.AITestGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGeneratorRunner {

    private static final Logger log =
            LoggerFactory.getLogger(TestGeneratorRunner.class);

    public static void main(String[] args) {

        log.info("Starting AI test generation...");

        // -------------------------------
        // GENERATE API TEST
        // -------------------------------
        String apiCode = AITestGenerator.generateApiTest(
                "POST /booking with payload {userId, flightId}"
        );

        TestFileWriter.write(
                "org.rupesh.app.tests.aigenerated.api",
                "BookingApiTest",
                apiCode
        );

        log.info("API test generated successfully");

        // -------------------------------
        // GENERATE UI TEST
        // -------------------------------
        String uiCode = AITestGenerator.generateUiTest(
                "Login → Search → Book"
        );

        TestFileWriter.write(
                "org.rupesh.app.tests.aigenerated.ui",
                "BookingUiTest",
                uiCode
        );

        log.info("UI test generated successfully");
        log.info("AI test generation completed");
    }
}