package requests;

import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ApiProperties;

import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class RequestsManager {

    Response response;
    ApiProperties apiProp = new ApiProperties();
    private static final Logger LOG = LoggerFactory.getLogger(RequestsManager.class);


    public Response postWithBody(JSONObject body, String url, Map<String, String> headers) {
        response = given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .body(body.toString())
                .post(url);

        loggerRequestData(url, headers.toString(), body.toString());

        return response;
    }


    public Response getTransctionStatus(String url, Map<String, String> headers) {
        response = given()
                .relaxedHTTPSValidation()
                .headers(headers)
                .get(url);

        loggerRequestData(url, headers.toString(), "");

        return response;
    }

    public String getMpToken() {
        response = given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("username", apiProp.getProp("user_mp_token"))
                .formParam("password", apiProp.getProp("password_mp_token"))
                .request()
                .post(apiProp.getProp("token_uri"));

        return response.jsonPath().getString("data.token");
    }

    private void loggerRequestData(String url, String headers, String body) {
        LOG.info("Request url : " + url);
        LOG.info("Request headers : " + headers);
        LOG.info("Request body :" + body);
        LOG.info(" ################ Response data ################# ");
        LOG.info("Response Status : " + response.statusCode());
        LOG.info(response.asString());
        LOG.info(" ################################################ ");
    }
}
