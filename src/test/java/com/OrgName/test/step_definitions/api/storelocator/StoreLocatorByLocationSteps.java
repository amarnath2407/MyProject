package com.salmon.test.step_definitions.api.storelocator;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.salmon.test.services.storelocator.StoreLocatorAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by akepativ on 07/07/2016.
 */
public class StoreLocatorByLocationSteps {

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private Response response;
    private JsonPath jsonPath;
    private Map<String,String> queryParams = null;
    private StoreLocatorAPI storeLocatorAPI;
    private String storeId;

    /*public StoreLocatorByLocationSteps(StoreLocatorAPI storeLocatorAPI) {
        this.storeLocatorAPI = storeLocatorAPI;
    }

    @Given("^I have the store locator url query parameters as \"([^\"]*)\" , \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iHaveTheStoreLocatorUrlQueryParametersAsAnd(String storeIdKey, String filter, String filterValue) throws Throwable {
        queryParams = prepareQueryParams(Props.getMessage(filter),Props.getMessage(filterValue),",");
        storeId = Props.getMessage(storeIdKey);
    }

    @When("^I call the service store locator by location and get the response$")
    public void iCallTheServiceStoreLocatorByLocationAndGetTheResponse() throws Throwable {
            response = storeLocatorAPI.findGeoNodeByGeoLocation(storeId,queryParams);
            jsonPath = getJsonPath(response);
            log.info("store json:" + jsonPath.getList("PhysicalStore"));
    }

    @Then("^store details returned from the service should be verified$")
    public void storeDetailsReturnedFromTheServiceShouldBeVerified() throws Throwable {
        assertThat(jsonPath.getList("PhysicalStore")).isNotEmpty();
        assertThat(jsonPath.getList("PhysicalStore").size()).isEqualTo(2);
    }*/

}
