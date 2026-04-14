package org.rupesh.app.core.integration.jira;

import io.restassured.response.Response;
import org.rupesh.app.core.integration.vault.VaultService;
import org.rupesh.app.exceptionNretry.FrameworkException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.rupesh.app.utils.Config;

import java.util.Map;

public class JIRAService {

    private static final Logger log =
            LoggerFactory.getLogger(JIRAService.class);

    private final JIRAClient client = new JIRAClient();

    private final String baseJiraUrl = Config.getJiraUrl();
    private final String token = Config.isVaultEnabled()
            ? new VaultService().getSecret("jira.token")
            : Config.getJiraToken();
    private final String project = Config.getJiraProjectKey();

    public String createBugIfNotExists(String title, String description) {

        try {
            String jql = "project=" + project + " AND summary ~ \"" + title + "\"";

            Response search = client.searchIssue(baseJiraUrl, token, jql);

            if (search.getStatusCode() != 200) {
                throw new FrameworkException("Jira search failed with status: " + search.getStatusCode());
            }

            int count = search.jsonPath().getInt("total");

            if (count > 0) {
                String existingKey = search.jsonPath().getString("issues[0].key");
                log.warn("Jira bug already exists: {}", existingKey);
                return existingKey;
            }

            Map<String, Object> body = Map.of(
                    "fields", Map.of(
                            "project", Map.of("key", project),
                            "summary", title,
                            "description", description,
                            "issuetype", Map.of("name", "Bug")
                    )
            );

            Response response = client.createBug(baseJiraUrl, token, body);

            if (response.getStatusCode() != 201) {
                throw new FrameworkException("Jira bug creation failed with status: " + response.getStatusCode());
            }

            String issueKey = response.jsonPath().getString("key");

            log.info("Jira bug created: {}", issueKey);

            return issueKey;

        } catch (Exception e) {
            log.error("Failed to create/search Jira bug", e);
            throw new FrameworkException("Failed to create/search Jira bug", e);
        }
    }

    public void attachScreenshot(String issueKey, byte[] screenshot) {

        if (screenshot == null) {
            return;
        }

        try {
            client.attachFile(
                    baseJiraUrl,
                    token,
                    issueKey,
                    screenshot,
                    "screenshot.png"
            );

            log.info("Screenshot attached to Jira issue={}", issueKey);

        } catch (Exception e) {
            log.error("Failed to attach screenshot to {}", issueKey, e);
        }
    }

    public void attachText(String issueKey, String fileName, String content) {

        if (content == null || content.isBlank()) {
            return;
        }

        try {
            client.attachFile(
                    baseJiraUrl,
                    token,
                    issueKey,
                    content.getBytes(),
                    fileName
            );

            log.info("File attached to Jira issue={} file={}", issueKey, fileName);

        } catch (Exception e) {
            log.error("Failed to attach file {} to {}", fileName, issueKey, e);
        }
    }
}