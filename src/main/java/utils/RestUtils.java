package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class RestUtils {

    static {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    public static Response getPetById(String petId) {
        return given().pathParam("petId", petId)
                .when().get("/pet/{petId}");
    }

    public static Response postPet(String requestBody) {
        return given().contentType("application/json").body(requestBody)
                .when().post("/pet");
    }

    public static Response putPet(String requestBody) {
        return given().contentType("application/json").body(requestBody)
                .when().put("/pet");
    }

    public static Response deletePet(String petId) {
        return given().pathParam("petId", petId)
                .when().delete("/pet/{petId}");
    }

    public static String createPetRequestBody(long id, String name, String status) {
        return "{\n" +
                "  \"id\": " + id + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"" + name + "\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"" + status + "\"\n" +
                "}";
    }
}
