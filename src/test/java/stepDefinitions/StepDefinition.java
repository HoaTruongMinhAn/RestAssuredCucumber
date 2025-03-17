package stepDefinitions;

import files.CommonMethods;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import pojo.googleAddPlace.GoogleLocation;
import pojo.googleAddPlace.GooglePlace;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StepDefinition {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    Response response;
    ValidatableResponse validatableResponse;
    JsonPath jsonPath;

    @Given("Add Place API")
    public void add_place_api() {
        GooglePlace googlePlace = new GooglePlace();
        googlePlace.setAccuracy(55);
        googlePlace.setName("Frontline house");
        googlePlace.setPhoneNumber("(+91) 983 893 3937");
        googlePlace.setAddress("29, side layout, cohen 09");
        googlePlace.setWebsite("http://google.com");
        googlePlace.setLanguage("French-IN");

        GoogleLocation googleLocation = new GoogleLocation();
        googleLocation.setLat(-38.383494);
        googleLocation.setLng(33.427362);
        googlePlace.setLocation(googleLocation);

        List<String> placeTypes = new ArrayList<String>();
        placeTypes.add("shoe park");
        placeTypes.add("shop");
        googlePlace.setTypes(placeTypes);

        System.out.println("\n########### Request ###########");
        RequestSpecification req = new RequestSpecBuilder()
                .setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123")
                .setContentType("application/json")
                .build();

        requestSpecification = given()
                .log().all()
                .spec(req)
                .body(googlePlace);
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
