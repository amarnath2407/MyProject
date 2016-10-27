package com.salmon.test.step_definitions.api.storelocator;

import com.jayway.restassured.response.Response;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.services.storelocator.StoreLocatorAPI;
import com.salmon.test.sql.store.StoreDAO;
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
public class DeletePhysicalStoreByBoutiqueSteps extends BaseStepDefinition {

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private StoreDAO storeDAO;
    private StoreLocatorAPI storeLocatorAPI;
    private List<String> storeIdentifierList;
    private String requestPayLoad;
    private List<String> boutiqueIdentifierList;

    public DeletePhysicalStoreByBoutiqueSteps(StoreLocatorAPI storeLocatorAPI,StoreDAO storeDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
    }


    @Given("^I have the store identifier \"([^\"]*)\" with boutique identifier \"([^\"]*)\" to delete physical store$")
    public void iHaveTheStoreIdentifierWithBoutiqueIdentifierToDeletePhysicalStore(String storeIdentifierKey, String boutiqueIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        boutiqueIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(boutiqueIdentifierKey).split(","));
    }

    @Then("^I call the service Delete Physical Store and check whether the physical store is deleted successfully from the database$")
    public void iCallTheServiceDeletePhysicalStoreAndCheckWhetherThePhysicalStoreIsDeletedSuccessfullyFromTheDatabase() throws Throwable {
        for(int i=0;i<storeIdentifierList.size();i++){
            String boutiqueIdentifier = boutiqueIdentifierList.get(i);
            String storeIdentifier = storeIdentifierList.get(i);
            Response response = storeLocatorAPI.DeleteBoutique(storeIdentifier,boutiqueIdentifier);
            assertThat(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue())).isEqualTo(response.getStatusCode());
        }
    }

}
