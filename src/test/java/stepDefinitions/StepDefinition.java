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

    @Given("Add Place API")
    public void add_place_api() {
        RequestSpecification req = new Utils().getRequestSpecification("Add Place API");
        System.out.println("\n########### Request ###########");
        requestSpecification = given()
                .log().all()
                .spec(req)
                .body(testDataBuilder.buildAddPlacePayload());
    }

    @When("User calls {string} with POST request")
    public void user_calls_with_post_request(String string) {
        response = requestSpecification.when()
                .post("maps/api/place/add/json");
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
