package stepDefinitions;

import files.CommonMethods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import pojo.googleAddPlace.GooglePlace;
import resources.APIResources;
import resources.TestDataBuilder;
import resources.Utils;

import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils {
    private RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;
    private Response response;
    private ValidatableResponse validatableResponse;
    private JsonPath jsonPath;
    private TestDataBuilder testDataBuilder = new TestDataBuilder();
    private GooglePlace googlePlace;
    private String placeId;

    @Given("Add Place API with {string} {string} {string}")
    public void addPlaceAPIWith(String name, String language, String address) {
        RequestSpecification req = getRequestSpecification("Add Place API");
        System.out.println("\n########### Request Add Place API ###########");
        requestSpecification = given()
                .log().all()
                .spec(req)
                .body(testDataBuilder.buildAddPlacePayload(name, language, address));
    }

    @When("User calls {string} with {string} request")
    public void userCallsWithRequest(String apiName, String httpMethod) {
        APIResources apiResource = APIResources.valueOf(apiName);
        response = requestSpecification.when()
                .request(httpMethod, apiResource.getResource());
    }

    @Then("API call is successful with status code {int}")
    public void api_call_is_successful_with_status_code(Integer int1) {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .build();

        System.out.println("\n########### Response ###########");
        validatableResponse = response.then().spec(responseSpecification).log().all();

        jsonPath = CommonMethods.rawToJson(validatableResponse.extract().asString());
        System.out.println("\nResponse body: ");
        jsonPath.prettyPrint();
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
        Assert.assertEquals(getResponseValue(response, key), expectedValue);
    }


    @When("GetPlaceAPI with the created place_id")
    public void getPlaceApiWithTheCreatedPlace_id() {
        System.out.println("\n########### Request GetPlaceAPI ###########");
        placeId = getResponseValue(response, "place_id");
        requestSpecification = given()
                .log().all()
                .spec(getRequestSpecification("Get Place API"))
                .queryParams("place_id", placeId);
    }

    @Then("Verify place_Id created maps to {string} {string} {string} using {string}")
    public void verifyPlace_IdCreatedMapsToUsing(String name, String language, String address, String apiName) {


        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .build();

        System.out.println("\n########### Response ###########");
        validatableResponse = response.then().spec(responseSpecification).log().all();

        jsonPath = CommonMethods.rawToJson(validatableResponse.extract().asString());
        System.out.println("\nResponse body: ");
        jsonPath.prettyPrint();

        //Pojo
        String actualName = jsonPath.getString("name");
        String actualLanguage = jsonPath.getString("language");
        String actualAddress = jsonPath.getString("address");
        Assert.assertEquals(actualName, name);
        Assert.assertEquals(actualLanguage, language);
        Assert.assertEquals(actualAddress, address);
//        Assert.assertEquals(actualName, googlePlace.getName());
//        Assert.assertEquals(actualLanguage, googlePlace.getLanguage());
//        Assert.assertEquals(actualAddress, googlePlace.getAddress());
    }

}
