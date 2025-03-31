package cucumber.options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features"
        , plugin = "json:target/jsonReports/cucumber-report.json"
        , glue = {"stepDefinitions"}
        , snippets = CAMELCASE
        , dryRun = false
        , monochrome = true
        , tags = "@AddPlace or @DeletePlace"
)
public class TestRunner {
}
