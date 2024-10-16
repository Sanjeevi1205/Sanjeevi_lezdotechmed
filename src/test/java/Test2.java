import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;


public class Test2 {

    // Function to post a new pet to the store with a unique ID
    public static Response addNewPet(long petId) {
        // Define the request body for the new pet
        String requestBody = "{\n" +
                "  \"id\": " + petId + ",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"doggie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        // Make a POST request to add the new pet
        Response response = 
            given()
                .contentType(JSON)
                .body(requestBody)
            .when()
                .post("/pet")
            .then()
                .statusCode(200) // Assert that the status code is 200
                .extract().response();

        return response; // Return the response
    }

 // Function to delete the pet
    public static void deletePet(long petId, String apiKey) {
        // Make a DELETE request to delete the pet
        Response deleteResponse = 
            given()
                .header("api_key", apiKey)
                .pathParam("petId", petId)
            .when()
                .delete("/pet/{petId}")
            .then()
                .statusCode(200) // Assert that the status code is 200
                .extract().response();

        System.out.println("Delete Response: " + deleteResponse.asString());

        // Assert that the 'message' field contains the pet ID, indicating successful deletion
        String message = deleteResponse.jsonPath().getString("message");
        Assert.assertTrue(message.equals(String.valueOf(petId)));
        System.out.println("Pet deletion confirmed with ID: " + petId);
    }
    // Function to verify that the pet was deleted
    public static void verifyPetDeleted(long petId, String apiKey) {
        // Make a GET request to verify the pet has been deleted
        Response getResponse = 
            given()
                .header("api_key", apiKey)
                .pathParam("petId", petId)
            .when()
                .get("/pet/{petId}")
            .then()
                .statusCode(404) // Expecting a 404 since the pet should not exist
                .extract().response();

        // Assert that the response indicates the pet was not found
        String getResponseBody = getResponse.asString();
        System.out.println("GET After Deletion Response: " + getResponseBody);
        Assert.assertTrue(getResponseBody.contains("Pet not found"));
    }

    public static void main(String[] args) {

        // Set the base URI
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // API key for authorization
        String apiKey = "special-key"; // Replace with your actual API key if different

        // Generate a unique pet ID
        long petId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        // Call the function to add a new pet with a unique ID
        Response postResponse = addNewPet(petId);

        // Assert that the POST response contains the correct pet details
        String postResponseBody = postResponse.asString();
        Assert.assertTrue(postResponseBody.contains("\"name\":\"doggie\""));
        Assert.assertTrue(postResponseBody.contains("\"status\":\"available\""));
        System.out.println("Response: " + postResponseBody);

        // Delete the pet and assert the deletion
        deletePet(petId, apiKey);

        // Verify that the pet has been deleted
        verifyPetDeleted(petId, apiKey);
    }
}
