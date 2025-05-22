import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.UUID;

public class PetStoreTest {

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

    // Function to update the pet status to 'sold'
    public static void updatePetStatusToSold(long petId) {
        // Define the request body to update the pet's status to 'sold'
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
                "  \"status\": \"sold\"\n" +
                "}";

        // Make a PUT request to update the pet
        Response response = 
            given()
                .contentType(JSON)
                .body(requestBody)
            .when()
                .put("/pet")
            .then()
                .statusCode(200) // Assert that the status code is 200
                .extract().response();

        // Assert that the status is updated to 'sold'
        String responseBody = response.asString();
        Assert.assertTrue(responseBody.contains("\"status\":\"sold\""));
        System.out.println("Pet status updated to sold.");
    }

    // Function to verify that the new pet was added successfully
    public static void verifyPetAdded(long petId) {
        // Make a GET request to verify the pet's details
        Response getResponse = 
            given()
                .pathParam("petId", petId)
            .when()
                .get("/pet/{petId}")
            .then()
                .statusCode(200) // Assert that the status code is 200
                .extract().response();

        // Extract the response body for debugging
        String getResponseBody = getResponse.asString();
        System.out.println("GET Response: " + getResponseBody);

        // Assert that the response contains the correct pet details
        String petName = getResponse.jsonPath().getString("name");
        String petStatus = getResponse.jsonPath().getString("status");

        // Add logging for debugging
        System.out.println("Pet Name: " + petName);
        System.out.println("Pet Status: " + petStatus);

        // Assert name and status are correct
        Assert.assertEquals("doggie", petName);
        Assert.assertEquals("available", petStatus);
        System.out.println("New pet successfully added and verified.");
    }

    public static void main(String[] args) {

        // Set the base URI
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        // Generate a unique pet ID
        long petId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        // Call the function to add a new pet with a unique ID
        Response postResponse = addNewPet(petId);

        // Assert that the POST response contains the correct pet details
        String postResponseBody = postResponse.asString();
        Assert.assertTrue(postResponseBody.contains("\"name\":\"doggie\""));
        Assert.assertTrue(postResponseBody.contains("\"status\":\"available\""));
        System.out.println("Response: " + postResponseBody);

        // Call the function to verify the pet was added successfully
        verifyPetAdded(petId);

        // Call the function to update the pet status to 'sold'
        updatePetStatusToSold(petId);

        // Verify that the status has been updated to 'sold'
        Response getUpdatedResponse = 
            given()
                .pathParam("petId", petId)
            .when()
                .get("/pet/{petId}")
            .then()
                .statusCode(200)
                .extract().response();

        String updatedResponseBody = getUpdatedResponse.asString();
        Assert.assertTrue(updatedResponseBody.contains("\"status\":\"sold\""));
        System.out.println("Pet status verification: " + updatedResponseBody);
    }
}
