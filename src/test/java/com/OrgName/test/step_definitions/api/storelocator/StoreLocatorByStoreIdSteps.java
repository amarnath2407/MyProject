package com.salmon.test.step_definitions.api.storelocator;

import com.jayway.restassured.path.json.JsonPath;
import com.salmon.test.models.api.storelocator.StoreLocator;
import com.salmon.test.services.storelocator.StoreLocatorAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by akepativ on 08/07/2016.
 */
public class StoreLocatorByStoreIdSteps{

    private static final Logger log = LoggerFactory.getLogger(StoreLocatorByStoreIdSteps.class);
    private StoreLocator storeLocator;
    private JsonPath jsonPath;
    private String uniqueStoreId = "";
    private StoreLocatorAPI storeLocatorAPI;
    private String storeId;

    /*public StoreLocatorByStoreIdSteps(StoreLocatorAPI storeLocatorAPI) {
        this.storeLocatorAPI = storeLocatorAPI;
    }

    @Given("^I have the store locator url query parameters as \"([^\"]*)\"$")
    public void iHaveTheStoreLocatorUrlQueryParametersAs(String uniqueId) throws Throwable {
        uniqueStoreId = Props.getMessage(uniqueId);
    }

    @Given("^I have the store locator url query parameters as \"([^\"]*)\" , \"([^\"]*)\"$")
    public void iHaveTheStoreLocatorUrlQueryParametersAs(String storeIdKey, String uniqueId) throws Throwable {
        uniqueStoreId = Props.getMessage(uniqueId);
        storeId = Props.getMessage(storeIdKey);
    }
    @When("^I call the service store locator by a store unique ID and get the response$")
    public void iCallTheServiceStoreLocatorByAStoreUniqueIDAndGetTheResponse() throws Throwable {
        storeLocator =  storeLocatorAPI.findByStoreUniqueId(storeId,uniqueStoreId);
        log.info("response  from api:"+storeLocator);
    }

    @Then("^store details from service uniqueid returned from the service should be verified$")
    public void storeDetailsFromServiceUniqueidReturnedFromTheServiceShouldBeVerified() throws Throwable {

        List<Attribute> attributeList = storeLocator.getData().get(0).getAttributes();
        log.info("AttributeList size:" + attributeList.size());

       *//* List queryInputs = new ArrayList();
        queryInputs.add(0,TestConstants.STORE_LANG_EN_GB_CODE.getValue());
        queryInputs.add(1,uniqueStoreId);
        StoreLocatorDAO storeLocatorDAO = new StoreLocatorDAO();
        List<StoreLocatorAttributes> storeLocatorAttributesList = storeLocatorDAO.getStoreLocatorAttributes(DatabaseQueries.SELECT_STLOCATTR.getQuery(),queryInputs);
        log.info("Attributes size from Database:"+ storeLocatorAttributesList.size());
        for(StoreLocatorAttributes storeAttributeModel: storeLocatorAttributesList){
            log.info("DisplayName:"+storeAttributeModel.getDisplayName());
            log.info("DisplayValue:"+storeAttributeModel.getDisplayValue());

            log.info("is equaal:"+ attributeList.contains(storeAttributeModel));
        }*//*
        assertThat(attributeList).isNotEmpty();
        assertThat(storeLocator.getData().size()).isEqualTo(1);
    }*/

}
