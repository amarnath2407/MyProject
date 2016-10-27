package com.salmon.test.step_definitions.api.storelocator;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.api.storelocator.PhysicalStorePayLoad;
import com.salmon.test.services.storelocator.StoreLocatorAPI;
import com.salmon.test.sql.store.StoreDAO;
import com.salmon.test.sql.storelocator.StoreLocatorDAO;
import com.salmon.test.step_definitions.api.utility.BaseStepDefinition;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Developer on 28/09/2016.
 */
public class PhysicalStoreJourneySteps extends BaseStepDefinition {

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private StoreDAO storeDAO;
    private StoreLocatorAPI storeLocatorAPI;
    private List<String> storeIdentifierList;

    public PhysicalStoreJourneySteps(StoreLocatorAPI storeLocatorAPI,StoreLocatorDAO storeLocatorDAO,StoreDAO storeDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
    }

    @Given("^I have the store identifier \"([^\"]*)\" to create  physical store$")
    public void iHaveTheStoreIdentifierToCreatePhysicalStore(String storeIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^Prepare the Payload and call the servicees Post,Put and delete Physical Store Data and check whether the new physical store operations are happening properly in database for the brand$")
    public void prepareThePayloadAndCallTheServiceesPostPutAndDeletePhysicalStoreDataAndCheckWhetherTheNewPhysicalStoreOperationsAreHappeningProperlyInDatabaseForTheBrand() throws Throwable {
        String requestPayLoad;
        PhysicalStorePayLoad physicalStorePayLoad;
        for(String storeIdentifier:storeIdentifierList){
            physicalStorePayLoad = storeLocatorAPI.preparePhysicalStorePayLoad("attributescontactsopeningtimes",storeIdentifier,storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            log.info("Post Boutique Payload: "+ requestPayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,null,requestPayLoad,"POST");
            String physicalStoreId = response.body().jsonPath().get("data.identifier");
            log.info("Crated storeIdentifier:"+physicalStoreId);
            assertThat(physicalStoreId).as("Newly created physical store identifier shouldn't be null").isNotNull();
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(physicalStoreId);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+physicalStoreId+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
            //update
            physicalStorePayLoad = storeLocatorAPI.preparePhysicalStoreUpdatePayLoad(storeIdentifier,physicalStoreId,"attributescontactsopeningtimes",storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            Response updateResponse = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,physicalStoreId,requestPayLoad,"PUT");
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue())).isEqualTo(updateResponse.getStatusCode());
            PhysicalStorePayLoad expPhysicalStoreUpdatePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(physicalStoreId);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+physicalStoreId+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStoreUpdatePayLoad,physicalStorePayLoad);
            //delete
            Response deleteResponse = storeLocatorAPI.DeleteBoutique(storeIdentifier,physicalStoreId);
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue())).isEqualTo(deleteResponse.getStatusCode());
        }
    }
}
