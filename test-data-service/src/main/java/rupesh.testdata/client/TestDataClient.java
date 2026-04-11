package rupesh.apiui.api.client;

import io.restassured.response.Response;
import rupesh.apiui.core.context.TestContext;
import rupesh.apiui.utils.Config;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class TestDataClient {

    public static Map<String, String> createUser() {

        Response response = given()
                .baseUri(Config.getTestDataUrl())
                .post("/testdata/user");

        String id = response.jsonPath().getString("id");
        String email = response.jsonPath().getString("email");

        TestContext.put("userId", id);

        return Map.of(
                "id", id,
                "email", email,
                "password", "pass"
        );
    }

    public static void deleteUser(String id) {
        given()
                .baseUri(Config.getTestDataUrl())
                .delete("/testdata/user/" + id);
    }
}