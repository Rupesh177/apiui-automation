package org.rupesh.app.core.processor;

import io.restassured.response.Response;
import org.rupesh.app.core.context.TestContext;
import org.rupesh.app.core.failure.FailureContext;
import org.rupesh.app.core.integration.jira.JIRAService;
import org.rupesh.app.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiLogProcessor implements FailureProcessor {

    private static final Logger log =
            LoggerFactory.getLogger(ApiLogProcessor.class);

    private final JIRAService jiraService = new JIRAService();

    // -------------------------------
    // CAPTURE API LOGS DURING RUNTIME
    // -------------------------------
    public static void log(String endpoint, Object requestBody, Response response) {

        if (endpoint != null) {
            TestContext.put("api_endpoint", endpoint);
        }

        if (requestBody != null) {
            TestContext.put("api_request_body", String.valueOf(requestBody));
        }

        if (response != null) {
            TestContext.put("api_response_body", response.asPrettyString());
            TestContext.put("api_status_code", response.getStatusCode());
        }
    }

    // -------------------------------
    // PROCESS API LOGS ON FAILURE
    // -------------------------------
    @Override
    public void process(FailureContext context) {

        if (!Config.isJiraEnabled()) {
            return;
        }

        try {
            String endpoint = (String) TestContext.get("api_endpoint");
            String request = (String) TestContext.get("api_request_body");
            String response = (String) TestContext.get("api_response_body");
            Integer status = (Integer) TestContext.get("api_status_code");

            if (endpoint == null && request == null && response == null && status == null) {
                log.debug("No API data found in TestContext. Skipping API log attachment.");
                return;
            }

            String issueKey = (String) context.get("jiraKey");

            if (issueKey == null || issueKey.isBlank()) {
                log.warn("No Jira issue key found. Skipping API log attachment.");
                return;
            }

            StringBuilder attachmentContent = new StringBuilder();

            if (endpoint != null) {
                attachmentContent.append("Endpoint: ")
                        .append(endpoint)
                        .append("\n\n");
            }

            if (request != null) {
                attachmentContent.append("Request:\n")
                        .append(request)
                        .append("\n\n");
            }

            if (response != null) {
                attachmentContent.append("Response:\n")
                        .append(response)
                        .append("\n\n");
            }

            if (status != null) {
                attachmentContent.append("Status Code: ")
                        .append(status)
                        .append("\n");
            }

            if (attachmentContent.length() == 0) {
                log.debug("API log content is empty. Skipping Jira attachment.");
                return;
            }

            jiraService.attachText(
                    issueKey,
                    "api-log.txt",
                    attachmentContent.toString()
            );

            log.info("API logs attached to Jira issue={}", issueKey);

        } catch (Exception e) {
            log.error("Failed to attach API logs to Jira", e);
        }
    }
}