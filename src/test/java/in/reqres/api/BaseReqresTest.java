package in.reqres.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class BaseReqresTest {

    public RequestSpecification getHeader() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .build();
    }

    public Map<String, String> getJson(String name, String job) {
        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("name", name);
        jsonBody.put("job", job);

        return jsonBody;
    }
}
