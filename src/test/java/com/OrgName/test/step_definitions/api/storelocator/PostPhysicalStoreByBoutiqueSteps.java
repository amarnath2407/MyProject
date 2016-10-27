package com.salmon.test.step_definitions.api.storelocator;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.api.storelocator.*;
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
 * Created by Developer on 07/09/2016.
 */
public class PostPhysicalStoreByBoutiqueSteps extends BaseStepDefinition{

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private StoreLocatorDAO storeLocatorDAO;
    private StoreDAO storeDAO;
    private StoreLocatorAPI storeLocatorAPI;
    private List<String> storeIdentifierList;
    private String requestPayLoad;
    private PhysicalStorePayLoad physicalStorePayLoad;

    public PostPhysicalStoreByBoutiqueSteps(StoreLocatorAPI storeLocatorAPI,StoreLocatorDAO storeLocatorDAO,StoreDAO storeDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
        this.storeLocatorDAO =  storeLocatorDAO;
    }

    @Given("^I have the store identifier \"([^\"]*)\" to create new physical store$")
    public void iHaveTheStoreIdentifierToCreateNewPhysicalStore(String storeIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^I have the Payload generated and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand$")
    public void iHaveThePayloadGeneratedAndCallTheServicePostPhysicalStoreDataCreateAndCheckWhetherTheNewPhysicalStoreIsCreatedInDatabaseForTheBrand() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            physicalStorePayLoad = storeLocatorAPI.preparePhysicalStorePayLoad(null,storeIdentifier,storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            log.info("Post Boutique Payload: "+ requestPayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,null,requestPayLoad,"POST");
            String physicalStoreId = response.body().jsonPath().get("data.identifier");
            log.info("Crated storeIdentifier:"+physicalStoreId);
            assertThat(physicalStoreId).as("Newly created physical store identifier shouldn't be null").isNotNull();
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(physicalStoreId);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+physicalStoreId+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }

    @Then("^I have the Payload generated with attributes and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand$")
    public void iHaveThePayloadGeneratedWithAttributesAndCallTheServicePostPhysicalStoreDataCreateAndCheckWhetherTheNewPhysicalStoreIsCreatedInDatabaseForTheBrand() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            physicalStorePayLoad = storeLocatorAPI.preparePhysicalStorePayLoad("attributes",storeIdentifier,storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            log.info("Post Boutique Payload: "+ requestPayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,null,requestPayLoad,"POST");
            String physicalStoreId = response.body().jsonPath().get("data.identifier");
            log.info("Crated storeIdentifier:"+physicalStoreId);
            assertThat(physicalStoreId).as("Newly created physical store identifier shouldn't be null").isNotNull();
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(physicalStoreId);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+physicalStoreId+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }


    @Then("^I have the Payload generated with attributes and contacts and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand$")
    public void iHaveThePayloadGeneratedWithAttributesAndContactsAndCallTheServicePostPhysicalStoreDataCreateAndCheckWhetherTheNewPhysicalStoreIsCreatedInDatabaseForTheBrand() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            physicalStorePayLoad = storeLocatorAPI.preparePhysicalStorePayLoad("attributescontacts",storeIdentifier,storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            log.info("Post Boutique Payload with attributes and contacts: "+ requestPayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,null,requestPayLoad,"POST");
            String physicalStoreId = response.body().jsonPath().get("data.identifier");
            log.info("Crated storeIdentifier:"+physicalStoreId);
            assertThat(physicalStoreId).as("Newly created physical store identifier shouldn't be null").isNotNull();
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(physicalStoreId);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+physicalStoreId+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }

    @Then("^I have the Payload generated with attributes,contacts and opening times and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand$")
    public void iHaveThePayloadGeneratedWithAttributesContactsAndOpeningTimesAndCallTheServicePostPhysicalStoreDataCreateAndCheckWhetherTheNewPhysicalStoreIsCreatedInDatabaseForTheBrand() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            physicalStorePayLoad = storeLocatorAPI.preparePhysicalStorePayLoad("attributescontactsopeningtimes",storeIdentifier,storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            log.info("Post Boutique Payload with attributes and contacts: "+ requestPayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,null,requestPayLoad,"POST");
            String physicalStoreId = response.body().jsonPath().get("data.identifier");
            log.info("Crated storeIdentifier:"+physicalStoreId);
            assertThat(physicalStoreId).as("Newly created physical store identifier shouldn't be null").isNotNull();
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(physicalStoreId);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+physicalStoreId+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }

}
