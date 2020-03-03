package steps;

import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import requests.RequestsManager;
import utils.ApiProperties;
import utils.DateUtils;
import utils.FakeUtils;
import utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

public  class BaseApiSteps {

    Map<String, String> headers = new HashMap<>();
    Map<String, String> transactionMap = new HashMap<>();
    ApiProperties apiProp = new ApiProperties();
    public static String token;
    Response response;
    RequestsManager executeRequest;
    public static String base_uri ;
    public static JSONObject body;
    public JsonUtils jsonUtils;
    public FakeUtils fakeUtils;
    public DateUtils dateUtils;



    public BaseApiSteps() {
        setHeaders();
        this.executeRequest = new RequestsManager();
        this.base_uri = apiProp.getProp("base_uri");
        this.jsonUtils = new JsonUtils();
        this.fakeUtils = new FakeUtils();
        this.dateUtils = new DateUtils();
    }

    private void setHeaders() {
        headers.put("Accept", "application/json");
        headers.put("Content-type", "application/json");
        headers.put("Authorization", "Bearer " + token);
    }
}
