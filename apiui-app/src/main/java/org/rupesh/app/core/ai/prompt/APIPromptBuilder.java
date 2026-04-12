package org.rupesh.app.core.ai.prompt;


public class APIPromptBuilder {

    public static String build(String apiSpec) {

        return """
        Generate a TestNG + RestAssured test using:
        - ApiClient from framework
        - Assertions
        - Clean code

        API Spec:
        """ + apiSpec;
    }
}