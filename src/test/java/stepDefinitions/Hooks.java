package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before("@DeletePlace")
    public void beforeScenario(Scenario scenario) {
        if (StepDefinition.placeId.get() == null) {
            System.out.println("placeId is null. Execute beforeScenario of @DeletePlace");
            StepDefinition stepDefinition = new StepDefinition();
            stepDefinition.addPlaceAPIWith("fake name", "fake language", "fake address");
            stepDefinition.userCallsWithRequest("AddPlaceAPI", "POST");
            stepDefinition.apiCallIsSuccessfulWithStatusCodeAndContentType(200, "application/json");
            stepDefinition.getPlaceApiWithTheCreatedPlace_id();
        }
    }

    @After("@DeletePlace")
    public void afterScenario(Scenario scenario) {

    }
}
