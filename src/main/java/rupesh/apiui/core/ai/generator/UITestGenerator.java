package rupesh.apiui.core.ai.generator;


import rupesh.apiui.core.ai.client.OpenAIClient;
import rupesh.apiui.core.ai.prompt.UIPromptBuilder;

public class UITestGenerator {

    public String generate(String uiFlow) {

        String prompt = UIPromptBuilder.build(uiFlow);

        return OpenAIClient.call(prompt);
    }
}