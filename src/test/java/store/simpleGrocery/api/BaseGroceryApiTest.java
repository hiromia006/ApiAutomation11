package store.simpleGrocery.api;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseGroceryApiTest {
    public RequestSpecification getPublicApiHeader() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://simple-grocery-api.store/")
                .build();
    }

    public RequestSpecification getPrivateApiHeader() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://simple-grocery-api.store/")
                .addHeader("Authorization", "Bearer 87a310ce7ddd027c6ef3211cb7255adf9edfaeb968608feddbd677daff1b2787")
//                .addHeader("Authorization", "Bearer "+getBearerToken())
                .build();
    }

    public String getBearerToken() {
        Map<String, String> clintJson = new HashMap<>();
        clintJson.put("clientName", LoremIpsum.getInstance().getName());
        clintJson.put("clientEmail", LoremIpsum.getInstance().getEmail());


        return given()
                .spec(getPublicApiHeader())
                .body(clintJson)
                .log().uri()
                .log().body()
                .when()
                .post("/api-clients")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("accessToken");
    }
}
