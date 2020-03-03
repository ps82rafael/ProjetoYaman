package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author Marcelo
 */

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"json:target/cucumber.json"},
        features = {"src/test/resources/features"},
        glue = {"steps"},
        tags = {"@test"})


public class RunTest {

}
