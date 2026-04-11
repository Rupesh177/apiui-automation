package rupesh.apiui.core.ai.generator;


import rupesh.apiui.core.ai.client.OpenAIClient;
import rupesh.apiui.core.ai.prompt.APIPromptBuilder;

public class APITestGenerator {

    public String generate(String apiSpec) {

        String prompt = APIPromptBuilder.build(apiSpec);

        return OpenAIClient.call(prompt);
    }
}