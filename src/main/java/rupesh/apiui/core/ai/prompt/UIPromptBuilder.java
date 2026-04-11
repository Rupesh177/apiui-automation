package rupesh.apiui.core.ai.prompt;


public class UIPromptBuilder {

    public static String build(String uiFlow) {

        return """
        Generate a Selenium test using:
        - Driver interface (click, type, getText)
        - Page Object Model
        - Clean structure

        UI Flow:
        """ + uiFlow;
    }
}
