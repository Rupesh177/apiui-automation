package org.rupesh.app.core.ai.generator;

public class AITestGenerator {

    public static String generateApiTest(String apiSpec) {
        return new APITestGenerator().generate(apiSpec);
    }

    public static String generateUiTest(String uiFlow) {
        return new UITestGenerator().generate(uiFlow);
    }
}