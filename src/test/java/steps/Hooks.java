package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.awaitility.Awaitility;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;


public class Hooks extends BaseApiSteps {

    @Before
    public void beforeScenario(Scenario scenario) {
        if (token == null) {
            token = executeRequest.getMpToken();
        }
        Awaitility.reset();
        Awaitility.setDefaultPollDelay(100, MILLISECONDS);
        Awaitility.setDefaultPollInterval(1, SECONDS);
        Awaitility.setDefaultTimeout(60, SECONDS);

    }

    @After
    public void afterScenario(Scenario scenario) throws Exception {
    }

}
