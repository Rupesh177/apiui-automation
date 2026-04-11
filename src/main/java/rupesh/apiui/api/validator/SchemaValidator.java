package rupesh.apiui.api.validator;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidator {

    public void validate(Response response, String schemaPath) {
        response.then()
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }
}