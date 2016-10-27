package com.salmon.test.step_definitions.api.storelocator;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.api.errors.ErrorType;
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
 * Created by Developer on 09/09/2016.
 */
public class PutPhysicalStoreByBoutiqueSteps extends BaseStepDefinition{

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private StoreDAO storeDAO;
    private StoreLocatorAPI storeLocatorAPI;
    private List<String> storeIdentifierList;
    private String requestPayLoad;
    private List<String> boutiqueIdentifierList;

    public PutPhysicalStoreByBoutiqueSteps(StoreLocatorAPI storeLocatorAPI,StoreDAO storeDAO,StoreLocatorDAO storeLocatorDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
    }

    @Given("^I have the store identifier \"([^\"]*)\" with boutique identifier \"([^\"]*)\" to update physical store$")
    public void iHaveTheStoreIdentifierWithBoutiqueIdentifierToUpdatePhysicalStore(String storeIdentifierKey, String boutiqueIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        boutiqueIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(boutiqueIdentifierKey).split(","));
    }

    @Then("^I call the service Put Physical Store and check whether the physical store is updated successfully from the database$")
    public void iCallTheServicePutPhysicalStoreAndCheckWhetherThePhysicalStoreIsUpdatedSuccessfullyFromTheDatabase() throws Throwable {
        for(int i=0;i<storeIdentifierList.size();i++){
            String boutiqueIdentifier = boutiqueIdentifierList.get(i);
            String storeIdentifier = storeIdentifierList.get(i);
            PhysicalStorePayLoad physicalStorePayLoad = storeLocatorAPI.preparePhysicalStoreUpdatePayLoad(storeIdentifier,boutiqueIdentifier,null,storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,boutiqueIdentifier,requestPayLoad,"PUT");
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue())).isEqualTo(response.getStatusCode());
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(boutiqueIdentifier);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+boutiqueIdentifier+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }


    @Then("^I call the service Put Physical Store with attributes and check whether the physical store is updated successfully in the database$")
    public void iCallTheServicePutPhysicalStoreWithAttributesAndCheckWhetherThePhysicalStoreIsUpdatedSuccessfullyInTheDatabase() throws Throwable {
        for(int i=0;i<storeIdentifierList.size();i++){
            String boutiqueIdentifier = boutiqueIdentifierList.get(i);
            String storeIdentifier = storeIdentifierList.get(i);
            PhysicalStorePayLoad physicalStorePayLoad = storeLocatorAPI.preparePhysicalStoreUpdatePayLoad(storeIdentifier,boutiqueIdentifier,"attributes",storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,boutiqueIdentifier,requestPayLoad,"PUT");
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue())).isEqualTo(response.getStatusCode());
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(boutiqueIdentifier);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+boutiqueIdentifier+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }


    @Then("^I call the service Put Physical Store with attributes and contacts and check whether the physical store is updated successfully in the database$")
    public void iCallTheServicePutPhysicalStoreWithAttributesAndContactsAndCheckWhetherThePhysicalStoreIsUpdatedSuccessfullyInTheDatabase() throws Throwable {
        for(int i=0;i<storeIdentifierList.size();i++){
            String boutiqueIdentifier = boutiqueIdentifierList.get(i);
            String storeIdentifier = storeIdentifierList.get(i);
            PhysicalStorePayLoad physicalStorePayLoad = storeLocatorAPI.preparePhysicalStoreUpdatePayLoad(storeIdentifier,boutiqueIdentifier,"attributescontacts",storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,boutiqueIdentifier,requestPayLoad,"PUT");
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue())).isEqualTo(response.getStatusCode());
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(boutiqueIdentifier);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+boutiqueIdentifier+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }

    @Then("^I call the service Put Physical Store with attributes,contacts and opening times and check whether the physical store is updated successfully in the database$")
    public void iCallTheServicePutPhysicalStoreWithAttributesContactsAndOpeningTimesAndCheckWhetherThePhysicalStoreIsUpdatedSuccessfullyInTheDatabase() throws Throwable {
        for(int i=0;i<storeIdentifierList.size();i++){
            String boutiqueIdentifier = boutiqueIdentifierList.get(i);
            String storeIdentifier = storeIdentifierList.get(i);
            PhysicalStorePayLoad physicalStorePayLoad = storeLocatorAPI.preparePhysicalStoreUpdatePayLoad(storeIdentifier,boutiqueIdentifier,"attributescontactsopeningtimes",storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,boutiqueIdentifier,requestPayLoad,"PUT");
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue())).isEqualTo(response.getStatusCode());
            PhysicalStorePayLoad expPhysicalStorePayLoad = storeLocatorAPI.getExpectedPhysicalStoreData(boutiqueIdentifier);
            assertThat(expPhysicalStorePayLoad.getStoreName()).as("New Physical store Identifier "+boutiqueIdentifier+" is not created in database").isNotNull();
            storeLocatorAPI.comparePhysicalStoreInformation(expPhysicalStorePayLoad,physicalStorePayLoad);
        }
    }

    @Then("^I call the service Put Physical Store with attributes and check whether the physical store is giving the proper error message$")
    public void iCallTheServicePutPhysicalStoreWithAttributesAndCheckWhetherThePhysicalStoreIsGivingTheProperErrorMessage() throws Throwable {
        for(int i=0;i<storeIdentifierList.size();i++){
            String boutiqueIdentifier = boutiqueIdentifierList.get(i);
            String storeIdentifier = storeIdentifierList.get(i);
            PhysicalStorePayLoad physicalStorePayLoad = storeLocatorAPI.preparePhysicalStoreUpdatePayLoad(storeIdentifier,boutiqueIdentifier,"attributes",storeCASIds.get(storeIdentifier));
            requestPayLoad = new Gson().toJson(physicalStorePayLoad);
            Response response = storeLocatorAPI.putboutiqueOperationsResponse(storeIdentifier,boutiqueIdentifier,requestPayLoad,"NEGPUT");
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_BAD_REQUEST.getStringValue())).isEqualTo(response.getStatusCode());
           // assertThat(response.erros).isEqualTo(response.getStatusCode());
            PhysicalStoreLocatorData errorTypeList = new Gson().fromJson(response.asString(), PhysicalStoreLocatorData.class);
            ErrorType errorType =  errorTypeList.getErrors().get(0);
            assertThat(errorType.getErrorMessage()).contains("Incomplete or invalid parameters were specified");
            List<String> errorParameterMessage = errorType.getErrorParameters();
            assertThat(errorParameterMessage.get(0)).contains("Boutique identifier does not exist");

        }
    }
}
