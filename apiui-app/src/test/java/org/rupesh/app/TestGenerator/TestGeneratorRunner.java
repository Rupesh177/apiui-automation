package org.rupesh.app.TestGenerator;

import org.rupesh.app.core.ai.generator.AITestGenerator;

public class TestGeneratorRunner {

    public static void main(String[] args) {

        // -------------------------------
        // GENERATE API TEST
        // -------------------------------
        String apiCode = AITestGenerator.generateApiTest(
                "POST /booking with payload {userId, flightId}"
        );

        TestFileWriter.write(
                "api",
                "BookingApiTest",
                apiCode
        );

        // -------------------------------
        // GENERATE UI TEST
        // -------------------------------
        String uiCode = AITestGenerator.generateUiTest(
                "Login → Search → Book"
        );

        TestFileWriter.write(
                "ui",
                "BookingUiTest",
                uiCode
        );
    }
}