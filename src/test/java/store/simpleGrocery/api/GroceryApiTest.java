package store.simpleGrocery.api;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class GroceryApiTest extends BaseGroceryApiTest {

    @Test
    public void apiClientsShouldSucceed() {
        Map<String, String> clintJson = new HashMap<>();
        clintJson.put("clientName", LoremIpsum.getInstance().getName());
        clintJson.put("clientEmail", LoremIpsum.getInstance().getEmail());


        given()
                .spec(getPublicApiHeader())
                .body(clintJson)
                .log().uri()
                .log().body()
                .when()
                .post("/api-clients")
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void getProductListShouldSucceed() {
        given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void getProductDetailShouldSucceed() {
        Long productId = given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .get("/products")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getLong("[0].id");

        given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .get("/products/" + productId)
                .then()
                .log().body()
                .statusCode(200);
    }


    @Test
    public void createNewCartShouldSucceed() {

        given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .post("/carts")
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void addItemToCartShouldSucceed() {

        String cartId = given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .post("/carts")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("cartId");

        Long productId = given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .get("/products")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getLong("[0].id");

        Map<String, Object> cardJson = new HashMap<>();
        cardJson.put("productId", productId);
        cardJson.put("quantity", 1);


        given()
                .spec(getPublicApiHeader())
                .body(cardJson)
                .log().uri()
                .log().body()
                .when()
                .post("/carts/{cart}/items", cartId)
                .then()
                .log().body()
                .statusCode(201);
    }


    @Test
    public void createNewOrderShouldSucceed() {

        String cartId = given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .post("/carts")
                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("cartId");

        Long productId = given()
                .spec(getPublicApiHeader())
                .log().uri()
                .when()
                .get("/products")
                .then()
//                .log().body()
                .statusCode(200)
                .extract().jsonPath().getLong("[0].id");

        Map<String, Object> cardJson = new HashMap<>();
        cardJson.put("productId", productId);
        cardJson.put("quantity", 1);

        given()
                .spec(getPublicApiHeader())
                .body(cardJson)
                .log().uri()
                .log().body()
                .when()
                .post("/carts/{cart}/items", cartId)
                .then()
                .log().body()
                .statusCode(201);

        Map<String, String> orderJson = new HashMap<>();
        orderJson.put("cartId", cartId);
        orderJson.put("customerName", LoremIpsum.getInstance().getName());

        given()
                .spec(getPrivateApiHeader())
                .body(orderJson)
                .log().uri()
                .log().body()
                .when()
                .post("/orders")
                .then()
                .log().body()
                .statusCode(201);
    }

    @Test
    public void getAllOrdersShouldSucceed() {
        given()
                .spec(getPrivateApiHeader())
                .log().uri()
                .when()
                .get("/orders")
                .then()
                .log().body()
                .statusCode(200);
    }

}
