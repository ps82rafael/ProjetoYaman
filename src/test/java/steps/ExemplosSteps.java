package steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.*;

public class ExemplosSteps extends BaseApiSteps {

    private static final Logger LOG = LoggerFactory.getLogger(ExemplosSteps.class);
    private String respTransactionToken;

    @Given("^that I have a valid MP token$")
    public void that_I_have_a_valid_MP_token() {
        assertNotNull(token);
    }

    @When("^I send a post request with all valid data$")
    public void i_send_a_post_request_with_all_valid_data() throws Throwable {
        setTransactionBody();
        body = jsonUtils.setJsonValueByKey("transaction.json", transactionMap);
        response = executeRequest.postWithBody(body, base_uri, headers);
    }


    @Then("^the transaction data should be displayed$")
    public void the_transaction_data_should_be_displayed() {
        respTransactionToken = response.jsonPath().getString("brinkspayTransactionId");
        assertEquals(body.getString("externalIdentifier"), response.jsonPath().getString("externalIdentifier"));
        assertNotNull(respTransactionToken);
    }

    @Then("^the Status code should be \"([^\"]*)\"$")
    public void the_Status_code_should_be(String expectedStatusCode) {
        assertEquals(Integer.parseInt(expectedStatusCode), response.statusCode());
    }


    @When("^I send a post request without \"([^\"]*)\" for \"([^\"]*)\"$")
    public void i_send_a_post_request_without_for(String field, String scenario) throws Throwable {
        LOG.info("Starting the post transaction scenario : " + scenario);
        setTransactionBody();
        body = jsonUtils.setJsonValueByKey("transaction.json", transactionMap);
        jsonUtils.removeValuesByKey(body, field);
        response = executeRequest.postWithBody(body, base_uri, headers);
    }

    @When("^I send a post request with invalid \"([^\"]*)\" for \"([^\"]*)\" for \"([^\"]*)\"$")
    public void i_send_a_post_request_with_invalid_for_for(String value, String field, String scenario)
            throws Throwable {
        LOG.info("Starting the post transaction scenario : " + scenario);
        setTransactionBody();
        transactionMap.replace(field, value);
        body = jsonUtils.setJsonValueByKey("transaction.json", transactionMap);
        response = executeRequest.postWithBody(body, base_uri, headers);
    }

    @Then("^the transaction data should not be displayed$")
    public void the_transaction_data_should_not_be_displayed() {
        assertNull(response.jsonPath().getString("brinkspayTransactionId"));
    }

    @Then("^the transaction status should be \"([^\"]*)\"$")
    public void the_transaction_status_should_be(String expectedtransactionStatus) {
        String partnerId = body.getJSONObject("transaction").getString("partnerId");
        String transactionId = response.jsonPath().getString("brinkspayTransactionId");
        await().untilAsserted(() -> assertEquals(expectedtransactionStatus,
                executeRequest.getTransctionStatus(base_uri + "/" + transactionId + "/partner/"
                        + partnerId + "/status", headers).jsonPath().getString("status")));
    }


    @Given("^that I have a invalid MP token$")
    public void that_I_have_a_invalid_MP_token() throws Throwable {
        setTransactionBody();
        body = jsonUtils.setJsonValueByKey("transaction.json", transactionMap);
        headers.replace("Authorization", "bearer invalid");
        response = executeRequest.postWithBody(body, base_uri, headers);
    }

    @Given("^that I have no MP token$")
    public void that_I_have_no_MP_token() throws Throwable {
        setTransactionBody();
        body = jsonUtils.setJsonValueByKey("transaction.json", transactionMap);
        headers.remove("Authorization");
        response = executeRequest.postWithBody(body, base_uri, headers);
    }

    @When("^I send a post request with a \"([^\"]*)\", \"([^\"]*)\" and a \"([^\"]*)\" for \"([^\"]*)\"$")
    public void i_send_a_post_request_with_a_and_a_for(String fieldKey, String fieldValue, String invalidAmount, String scenario) throws Throwable {
        LOG.info("Starting the post transaction scenario : " + scenario);
        setTransactionBody();
        transactionMap.replace(fieldKey, fieldValue);
        transactionMap.replace("amount", invalidAmount);
        body = jsonUtils.setJsonValueByKey("transaction.json", transactionMap);
        response = executeRequest.postWithBody(body, base_uri, headers);
    }

    private void setTransactionBody() {
        transactionMap.put("externalIdentifier", fakeUtils.UUID());
        transactionMap.put("accountHolderId", apiProp.getProp("accountHolderId"));
        transactionMap.put("storeTransactionId", fakeUtils.UUID());
        transactionMap.put("deviceCode", apiProp.getProp("storePdv"));
        transactionMap.put("partnerId", apiProp.getProp("partnerId"));
        transactionMap.put("id", apiProp.getProp("idTransaction"));
        transactionMap.put("amount", "1");
        transactionMap.put("token", apiProp.getProp("partnerId") + apiProp.getProp("idTransaction") +
                fakeUtils.NumericRandomWithLength(8));
        transactionMap.put("entryDate", dateUtils.getActualDate());
    }
}
