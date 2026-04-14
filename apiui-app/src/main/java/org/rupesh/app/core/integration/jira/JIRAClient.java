package org.rupesh.app.core.integration.jira;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class JIRAClient {

    public Response createBug(String baseUrl, String token, Object body) {
        return given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/rest/api/3/issue");
    }

    public Response searchIssue(String baseUrl, String token, String jql) {
        return given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .queryParam("jql", jql)
                .when()
                .get("/rest/api/3/search");
    }

    public Response attachFile(String baseUrl,
                               String token,
                               String issueKey,
                               byte[] file,
                               String fileName) {
        return given()
                .baseUri(baseUrl)
                .header("Authorization", "Bearer " + token)
                .header("X-Atlassian-Token", "no-check")
                .multiPart("file", fileName, file)
                .when()
                .post("/rest/api/3/issue/" + issueKey + "/attachments");
    }
}