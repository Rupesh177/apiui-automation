package org.rupesh.app.core.ai.generator;


import org.rupesh.app.core.ai.client.OpenAIClient;
import org.rupesh.app.core.ai.prompt.UIPromptBuilder;

public class UITestGenerator {

    public String generate(String uiFlow) {

        String prompt = UIPromptBuilder.build(uiFlow);

        return OpenAIClient.call(prompt);
    }
}