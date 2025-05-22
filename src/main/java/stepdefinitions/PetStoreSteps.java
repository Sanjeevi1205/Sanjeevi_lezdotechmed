package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.Assert;
import utils.RestUtils;
import utils.LoggerUtil;
import io.restassured.response.Response;

public class PetStoreSteps {

    private Response response;
    private long petId;

    @Given("I create a new pet with ID {string} and name {string}")
    public void iCreateANewPetWithIDAndName(String id, String name) {
        petId = Long.parseLong(id);
        String requestBody = RestUtils.createPetRequestBody(petId, name, "available");
        response = RestUtils.postPet(requestBody);
        LoggerUtil.logResponse("POST", response);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("I verify the pet with ID {string} exists")
    public void iVerifyThePetWithIDExists(String id) {
        response = RestUtils.getPetById(id);
        LoggerUtil.logResponse("GET", response);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(response.asString().contains(id));
    }

    @When("I update the status of pet with ID {string} to {string}")
    public void iUpdateTheStatusOfPetWithIDTo(String id, String status) {
        String requestBody = RestUtils.createPetRequestBody(Long.parseLong(id), "doggie", status);
        response = RestUtils.putPet(requestBody);
        LoggerUtil.logResponse("PUT", response);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("I verify the pet with ID {string} has status {string}")
    public void iVerifyThePetWithIDHasStatus(String id, String status) {
        response = RestUtils.getPetById(id);
        LoggerUtil.logResponse("GET", response);
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(response.asString().contains("\"status\":\"" + status + "\""));
    }

    @When("I delete the pet with ID {string}")
    public void iDeleteThePetWithID(String id) {
        response = RestUtils.deletePet(id);
        LoggerUtil.logResponse("DELETE", response);
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("I verify the pet with ID {string} is deleted")
    public void iVerifyThePetWithIDIsDeleted(String id) {
        response = RestUtils.getPetById(id);
        LoggerUtil.logResponse("GET", response);
        Assert.assertEquals(404, response.getStatusCode());
    }
}
