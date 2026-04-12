package org.rupesh.app.core.ai.generator;


import org.rupesh.app.core.ai.client.OpenAIClient;
import org.rupesh.app.core.ai.prompt.APIPromptBuilder;

public class APITestGenerator {

    public String generate(String apiSpec) {

        String prompt = APIPromptBuilder.build(apiSpec);

        return OpenAIClient.call(prompt);
    }
}