package rupesh.apiui.core.integration.jira;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rupesh.apiui.utils.Config;

import java.util.Map;

public class JIRAService {

    private static final Logger log =
            LoggerFactory.getLogger(JIRAService.class);

    private final JIRAClient client = new JIRAClient();

    String baseJiraUrl = Config.getJiraUrl();
    String token = Config.getJiraToken();
    String project = Config.getJiraProjectKey();

    // -------------------------------
    // CREATE BUG (WITH DUP CHECK)
    // -------------------------------
    public String createBugIfNotExists(String title, String description) {

        try {
            // -------------------------------
            // SEARCH EXISTING ISSUE
            // -------------------------------
            String jql = "project=" + project + " AND summary ~ \"" + title + "\"";

            Response search = client.searchIssue(baseJiraUrl, token, jql);

            int count = search.jsonPath().getInt("total");

            if (count > 0) {
                String existingKey =
                        search.jsonPath().getString("issues[0].key");

                log.warn("⚠ Jira bug already exists: {}", existingKey);

                return existingKey;
            }

            // -------------------------------
            // CREATE NEW ISSUE
            // -------------------------------
            Map<String, Object> body = Map.of(
                    "fields", Map.of(
                            "project", Map.of("key", project),
                            "summary", title,
                            "description", description,
                            "issuetype", Map.of("name", "Bug")
                    )
            );

            Response response = client.createBug(baseJiraUrl, token, body);

            String issueKey = response.jsonPath().getString("key");

            log.info("🐞 Jira bug created: {}", issueKey);

            return issueKey;

        } catch (Exception e) {
            log.error("❌ Failed to create/search Jira bug", e);
            throw new RuntimeException(e);
        }
    }

    // -------------------------------
    // ATTACH SCREENSHOT
    // -------------------------------
    public void attachScreenshot(String issueKey, byte[] screenshot) {

        if (screenshot == null) return;

        try {
            client.attachFile(
                    baseJiraUrl,
                    token,
                    issueKey,
                    screenshot,
                    "screenshot.png"
            );

        } catch (Exception e) {
            log.error("❌ Failed to attach screenshot to {}", issueKey, e);
        }
    }

    // -------------------------------
    // ATTACH TEXT (API LOGS)
    // -------------------------------
    public void attachText(String issueKey,
                           String fileName,
                           String content) {

        if (content == null || content.isEmpty()) return;

        try {
            client.attachFile(
                    baseJiraUrl,
                    token,
                    issueKey,
                    content.getBytes(),
                    fileName
            );

        } catch (Exception e) {
            log.error("❌ Failed to attach file {} to {}",
                    fileName, issueKey, e);
        }
    }
}