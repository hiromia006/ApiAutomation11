package in.reqres.api;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTest {

    @Test
    public void getUserListShouldSucceed() {
        given()
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void getUserListShouldSucceed2() {
        given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .basePath("/api")
                .log().uri()
                .when()
                .get("/users?page=2")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void getUserDetailShouldSucceed() {
        given()
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void singleuserNotFound() {
        given()
                .contentType(ContentType.JSON)
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void createUserShouldSucceed() {

        String jsonBody = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .basePath("/api")
                .body(jsonBody)
                .log().uri()
                .log().body()
                .when()
                .post("/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));
    }

    @Test
    public void createUserShouldSucceed2() {

        String name = LoremIpsum.getInstance().getName();
        String job = LoremIpsum.getInstance().getTitle(1);

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", name);
        jsonBody.put("job", job);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .basePath("/api")
                .body(jsonBody)
                .log().uri()
                .log().body()
                .when()
                .post("/users")
                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo(name))
                .body("job", equalTo(job));
    }

    @Test
    public void uodateUserShouldSucceed() {

        String name = LoremIpsum.getInstance().getName();
        String job = LoremIpsum.getInstance().getTitle(1);

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", name);
        jsonBody.put("job", job);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .basePath("/api")
                .body(jsonBody)
                .log().uri()
                .log().body()
                .when()
                .put("/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(name))
                .body("job", equalTo(job));
    }

    @Test
    public void uodateUserShouldSucceed2() {

        String job = LoremIpsum.getInstance().getTitle(1);

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("job", job);

        given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .basePath("/api")
                .body(jsonBody)
                .log().uri()
                .log().body()
                .when()
                .patch("/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("job", equalTo(job));
    }

    @Test
    public void deleteUser() {
        given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .basePath("/api")
                .log().uri()
                .when()
                .delete("/users/2")
                .then()
                .log().body()
                .statusCode(204);
    }
}
