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
import resources.APIResources;
import resources.TestDataBuilder;
import resources.Utils;

import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils {
    //    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    JsonPath jsonPath;
    TestDataBuilder testDataBuilder = new TestDataBuilder();

    @Given("Add Place API with {string} {string} {string}")
    public void addPlaceAPIWith(String name, String language, String address) {
        RequestSpecification req = new Utils().getRequestSpecification("Add Place API");
        System.out.println("\n########### Request ###########");
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
        Assert.assertEquals(jsonPath.getString(key), expectedValue);
    }

}
