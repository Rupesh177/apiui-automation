package org.rupesh.app.core.failure;

import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.rupesh.app.core.integration.jira.JIRAService;
import org.rupesh.app.utils.Config;

public class JiraProcessor implements FailureProcessor {

    private static final Logger log =
            LoggerFactory.getLogger(JiraProcessor.class);

    private final JIRAService jiraService = new JIRAService();

    @Override
    public void process(FailureContext context) {

        if (!Config.isJiraEnabled()) {
            return;
        }

        try {
            String testName = context.getTestName();

            String title = "Automation Failure: " + testName;

            String desc = context.getError() != null
                    ? context.getError().toString()
                    : "No error message";

            // -------------------------------
            // CREATE OR FETCH ISSUE
            // -------------------------------
            String issueKey = jiraService.createBugIfNotExists(title, desc);

            // share with other processors
            context.put("jiraKey", issueKey);

            // -------------------------------
            //  ADD ALLURE LINK
            // -------------------------------
            Allure.link(
                    "Jira Bug",
                    Config.getJiraUrl() + "/browse/" + issueKey
            );

            // -------------------------------
            // ATTACH SCREENSHOT
            // -------------------------------
            byte[] screenshot = (byte[]) context.get("screenshot");

            if (screenshot != null) {
                jiraService.attachScreenshot(issueKey, screenshot);
                log.info("📎 Screenshot attached to {}", issueKey);
            }

            // -------------------------------
            // ATTACH API LOGS
            // -------------------------------
            String request = (String) context.get("apiRequest");
            String response = (String) context.get("apiResponse");

            if (request != null) {
                jiraService.attachText(issueKey, "request.txt", request);
            }

            if (response != null) {
                jiraService.attachText(issueKey, "response.txt", response);
            }

            log.info("🐞 Jira processing completed for {}", issueKey);

        } catch (Exception e) {
            log.error("❌ Jira processing failed", e);
        }
    }
}