package org.rupesh.app.core.failure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.core.integration.jira.JIRAService;

public class ApiLogProcessor implements FailureProcessor {

    private static final Logger log =
            LoggerFactory.getLogger(ApiLogProcessor.class);

    private final JIRAService jiraService = new JIRAService();

    @Override
    public void process(FailureContext context) {

        try {
            // -------------------------------
            // FETCH API DATA
            // -------------------------------
            String endpoint = (String) TestContext.get("api_endpoint");
            String request = (String) TestContext.get("api_request_body");
            String response = (String) TestContext.get("api_response_body");
            Integer status = (Integer) TestContext.get("api_status_code");

            // If no API data → skip
            if (endpoint == null && response == null) {
                return;
            }

            // -------------------------------
            // GET JIRA ISSUE KEY
            // -------------------------------
            String issueKey = (String) context.get("jiraKey");

            if (issueKey == null) {
                log.warn("⚠ No Jira issue key found, skipping API logs");
                return;
            }

            // -------------------------------
            // BUILD LOG CONTENT
            // -------------------------------
            StringBuilder logData = new StringBuilder();

            logData.append("Endpoint: ").append(endpoint).append("\n\n");

            if (request != null) {
                logData.append("Request:\n")
                        .append(request)
                        .append("\n\n");
            }

            if (response != null) {
                logData.append("Response:\n")
                        .append(response)
                        .append("\n\n");
            }

            if (status != null) {
                logData.append("Status Code: ")
                        .append(status);
            }

            // -------------------------------
            // ATTACH TO JIRA
            // -------------------------------
            jiraService.attachText(
                    issueKey,
                    "api-log.txt",
                    logData.toString()
            );

            log.info("📎 API logs attached to Jira: {}", issueKey);

        } catch (Exception e) {
            log.error("❌ Failed to attach API logs", e);
        }
    }
}