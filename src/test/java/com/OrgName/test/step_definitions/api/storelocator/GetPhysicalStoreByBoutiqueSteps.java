package com.salmon.test.step_definitions.api.storelocator;

import com.jayway.restassured.response.Response;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.Props;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.salmon.test.framework.helpers.ApiHelper.StoreIdentifierToStoreId;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 01/08/2016.
 */
public class GetPhysicalStoreByBoutiqueSteps extends BaseStepDefinition {

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private Response response;
    private Map<String,String> queryParams = null;
    private Map<String,String> queryParamsDB = null;
    private StoreLocatorAPI storeLocatorAPI;
    private List<String> storeIdentifierList;
    private List<String> languageList;
    private List<String> languageIdList;
    private PhysicalStoreLocatorData physicalStoreLocatorData;
    private StoreLocatorDAO storeLocatorDAO;
    private StoreDAO storeDAO;
    private String boutiqueIdentifier;
    private List<Map<String,Object>> physicalStores;

   public GetPhysicalStoreByBoutiqueSteps(StoreLocatorAPI storeLocatorAPI, StoreLocatorDAO storeLocatorDAO, StoreDAO storeDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeLocatorDAO = storeLocatorDAO;
        this.storeDAO = storeDAO;
    }

    @Given("^I have the store identifier \"([^\"]*)\" and boutique identifier to get the physical store data$")
    public void iHaveTheStoreIdentifierAndBoutiqueIdentifierToGetThePhysicalStoreData(String storeIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^I call the service Get Physical Store Data and compare the response returned from the API with the database$")
    public void iCallTheServiceGetPhysicalStoreDataAndCompareTheResponseReturnedFromTheAPIWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            log.info("Comparing store data for Store Identifier:"+storeIdentifier);
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            physicalStores = storeLocatorDAO.getPhysicalStoresForBrand(catalogAssetId,"1");
            int physicalStoreCount = Integer.parseInt(Props.getMessage("stloc.physicalStoreCount"));
            if(physicalStoreCount > physicalStores.size()){
                physicalStoreCount = physicalStores.size();
            }
            for(int i=0;i<physicalStoreCount;i++){
                physicalStoreLocatorData = storeLocatorAPI.getPhysicalStore(storeIdentifier,physicalStores.get(i).get("IDENTIFIER").toString(),queryParams);
                log.info("XXXXX storeIds = "+storeIds.toString());
                Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                queryParamsDB = new HashMap<String,String>();
                queryParamsDB.put("STLOC_ID",physicalStores.get(i).get("STLOC_ID").toString());
                String noOfStores = Props.getMessage("stloc.limitStores");
                StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId,language.getLanguageId(),noOfStores,"1","boutique",queryParamsDB);
                //log.info("Actual storeLocator Data:"+storeLocator);
                //log.info("Expected storeLocator Data:"+expectedStoreData);
                comapreStoreInformation(expectedStoreData,physicalStoreLocatorData);
            }

        }
    }

    @Given("^I have the store identifier \"([^\"]*)\" , boutique identifier  and locale \"([^\"]*)\" to get the physical store data$")
    public void iHaveTheStoreIdentifierBoutiqueIdentifierAndLocaleToGetThePhysicalStoreData(String storeIdentifierKey, String languageKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
    }

    @Then("^I call the service Get Physical Store Data with query parameter locale and compare the response returned from the API with the database$")
    public void iCallTheServiceGetPhysicalStoreDataWithQueryParameterLocaleAndCompareTheResponseReturnedFromTheAPIWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            physicalStores = storeLocatorDAO.getPhysicalStoresForBrand(catalogAssetId,"1");
            int physicalStoreCount = Integer.parseInt(Props.getMessage("stloc.physicalStoreCount"));
            if(physicalStoreCount > physicalStores.size()){
                physicalStoreCount = physicalStores.size();
            }
            for(String locale:languageList) {

                log.info("Comparing store data for Store Identifier:"+storeIdentifier +" with language: "+locale);
                String languageId = String.valueOf(storeDAO.getLanguageIdByLocaleName(locale));
                log.info("language Id:" + languageId);
                if (languageId.equals("0") || storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), languageId) == 0) {
                    log.info("language Id inside");
                    Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    languageId = language.getLanguageId();
                }
                for (int i = 0; i < physicalStoreCount; i++) {
                    queryParamsDB = new HashMap<String, String>();
                    queryParams = new HashMap<String,String>();
                    queryParams.put("locale",locale);
                    queryParamsDB.put("STLOC_ID", physicalStores.get(i).get("STLOC_ID").toString());
                    physicalStoreLocatorData = storeLocatorAPI.getPhysicalStore(storeIdentifier, physicalStores.get(i).get("IDENTIFIER").toString(), queryParams);
                    String noOfStores = Props.getMessage("stloc.limitStores");
                    StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, languageId, noOfStores, "1", "boutique", queryParamsDB);
                    //log.info("Actual storeLocator Data:"+storeLocator);
                    //log.info("Expected storeLocator Data:"+expectedStoreData);
                    comapreStoreInformation(expectedStoreData, physicalStoreLocatorData);
                }
            }
        }
    }

    @Given("^I have the store identifier \"([^\"]*)\" and wrong \"([^\"]*)\" to get the physical store data$")
    public void iHaveTheStoreIdentifierAndWrongToGetThePhysicalStoreData(String storeIdentifierKey, String boutiqueIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        boutiqueIdentifier = Props.getMessage(boutiqueIdentifierKey);
    }

    @Then("^I call the service Get Physical Store Data with wrong boutique identifier and compare the error response returned from the API$")
    public void iCallTheServiceGetPhysicalStoreDataWithWrongBoutiqueIdentifierAndCompareTheErrorResponseReturnedFromTheAPI() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            log.info("Comparing store data for Store Identifier with negative scenario:"+storeIdentifier);
            physicalStoreLocatorData = storeLocatorAPI.getPhysicalStore(storeIdentifier,boutiqueIdentifier,queryParams, TestConstants.STATUS_CODE_NOT_FOUND.getStringValue());
            List<ErrorType> errors = physicalStoreLocatorData.getErrors();
            assertThat(errors.size()).as("Error message is not coming").isNotZero();
            assertThat(errors.size()).as("Error message count should be 1").isEqualTo(1);
            assertThat(errors.get(0).getErrorMessage()).as("error message : ABC165 was not found : is not coming in the API error response").contains("ABC165 was not found");
        }
    }


    @Given("^I have the store identifier \"([^\"]*)\" , boutique identifier  and locale \"([^\"]*)\" and langId \"([^\"]*)\" to get the physical store data$")
    public void iHaveTheStoreIdentifierBoutiqueIdentifierAndLocaleAndLangIdToGetThePhysicalStoreData(String storeIdentifierKey, String languageKey, String languageIdKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
        languageIdList = Arrays.asList(Props.getMessage(languageIdKey).split(","));
    }

    @Then("^I call the service Get Physical Store Data with query parameter locale and langId and compare the response returned from the API with the database$")
    public void iCallTheServiceGetPhysicalStoreDataWithQueryParameterLocaleAndLangIdAndCompareTheResponseReturnedFromTheAPIWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            physicalStores = storeLocatorDAO.getPhysicalStoresForBrand(catalogAssetId,"1");
            int physicalStoreCount = Integer.parseInt(Props.getMessage("stloc.physicalStoreCount"));
            if(physicalStoreCount > physicalStores.size()){
                physicalStoreCount = physicalStores.size();
            }
            for(String locale:languageList) {
                for(String expLangId:languageIdList) {
                    log.info("Comparing store data for Store Identifier:" + storeIdentifier + " with language: " + locale +" and langId:"+expLangId);
                    String languageId = null;

                        if (storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), expLangId) == 0) {
                            log.info("language Id inside");
                            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                            languageId = language.getLanguageId();
                        }
                    else{
                        languageId = expLangId;
                    }
                    for (int i = 0; i < physicalStoreCount; i++) {
                        queryParamsDB = new HashMap<String, String>();
                        queryParams = new HashMap<String, String>();
                        queryParams.put("locale", locale);
                        queryParams.put("langId", expLangId);
                        queryParamsDB.put("STLOC_ID", physicalStores.get(i).get("STLOC_ID").toString());
                        physicalStoreLocatorData = storeLocatorAPI.getPhysicalStore(storeIdentifier, physicalStores.get(i).get("IDENTIFIER").toString(), queryParams);
                        String noOfStores = Props.getMessage("stloc.limitStores");
                        StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, languageId, noOfStores, "1", "boutique", queryParamsDB);
                        //log.info("Actual storeLocator Data:"+storeLocator);
                        //log.info("Expected storeLocator Data:"+expectedStoreData);
                        comapreStoreInformation(expectedStoreData, physicalStoreLocatorData);
                    }
                }
            }
        }
    }

    private void comapreStoreInformation(StoreLocator expectedStoreData,PhysicalStoreLocatorData physicalStoreLocatorData){

        StorelocatorItem expStoreItem;
        StorelocatorItem actStoreItem;
        for(int storeItem=0;storeItem<expectedStoreData.getData().size();storeItem++){
            expStoreItem = expectedStoreData.getData().get(storeItem);
            actStoreItem = physicalStoreLocatorData.getData();
            assertThat(expStoreItem.getIdentifier()).as("Store Identifier is wrong").isEqualTo(actStoreItem.getIdentifier());
            assertThat(expStoreItem.getActive()).as("Store active is wrong").isEqualTo(actStoreItem.getActive());
            assertThat(expStoreItem.getStoreName()).as("Store Name is wrong").isEqualTo(actStoreItem.getStoreName());
            assertThat(expStoreItem.getStoreNumber()).as("Store Number is wrong").isEqualTo(actStoreItem.getStoreNumber());
            assertThat(expStoreItem.getAddress().getCity()).as("Store city is wrong").isEqualTo(actStoreItem.getAddress().getCity());
            assertThat(expStoreItem.getAddress().getCountryCode()).as("Store country code is wrong").isEqualTo(actStoreItem.getAddress().getCountryCode());
            assertThat(expStoreItem.getAddress().getCountryName()).as("Store country name is wrong").isEqualTo(actStoreItem.getAddress().getCountryName());
            assertThat(expStoreItem.getAddress().getLine1()).as("Store address line1 is wrong").isEqualTo(actStoreItem.getAddress().getLine1());
            assertThat(expStoreItem.getAddress().getLine2()).as("Store address line2 is wrong").isEqualTo(actStoreItem.getAddress().getLine2());
            assertThat(expStoreItem.getAddress().getLine3()).as("Store address line3 is wrong").isEqualTo(actStoreItem.getAddress().getLine3());
            assertThat(expStoreItem.getAddress().getLine3()).as("Store address line3 is wrong").isEqualTo(actStoreItem.getAddress().getLine3());
            assertThat(expStoreItem.getAddress().getPostCode()).as("Store postal code is wrong").isEqualTo(actStoreItem.getAddress().getPostCode());
            assertThat(expStoreItem.getAddress().getRegionCode()).as("Store region code is wrong").isEqualTo(actStoreItem.getAddress().getRegionCode());
            assertThat(expStoreItem.getAddress().getRegionName()).as("Store region name is wrong").isEqualTo(actStoreItem.getAddress().getRegionName());
            assertThat(expStoreItem.getAddress().getState()).as("Store state is wrong").isEqualTo(actStoreItem.getAddress().getState());
            assertThat(expStoreItem.getSpatialData().getLatitude()).as("Store latitude is wrong").isEqualTo(actStoreItem.getSpatialData().getLatitude());
            assertThat(expStoreItem.getSpatialData().getLongitude()).as("Store longitude is wrong").isEqualTo(actStoreItem.getSpatialData().getLongitude());

            //Check contacts
            for(ContactDetails expContactDetails:expStoreItem.getContacts()){
                assertThat(expStoreItem.getContacts().size()).as("Store contacts count is wrong").isEqualTo(actStoreItem.getContacts().size());
                boolean isContactsExist = false;
                for(ContactDetails actContactDetails:actStoreItem.getContacts()){
                    if(expContactDetails.getIdentifier().equals(actContactDetails.getIdentifier())){
                        assertThat(expContactDetails.getValue()).as("Contacts value is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getValue());
                        assertThat(expContactDetails.getName().getId()).as("Contact name id is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getName().getId());
                        assertThat(expContactDetails.getName().getName()).as("Contact name name is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getName().getName());
                        assertThat(expContactDetails.getType().getName()).as("Contact type name is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getType().getName());
                        assertThat(expContactDetails.getType().getCode()).as("Contact type code is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getType().getCode());
                        isContactsExist = true;
                        break;
                    }
                }
                assertThat(isContactsExist).as("Contact identifier: "+expContactDetails.getIdentifier()+" for physical store Identifier : "+expStoreItem.getIdentifier()+" does not exist in the api response").isTrue();
            }

            //check attributes
            for(Attribute expAttribute:expStoreItem.getAttributes()){
                assertThat(expStoreItem.getAttributes().size()).as("Store attributes count is wrong").isEqualTo(actStoreItem.getAttributes().size());
                boolean isAttributeExist = false;
                for(Attribute actAttribute:actStoreItem.getAttributes()){
                    if(expAttribute.getIdentifier().equals(actAttribute.getIdentifier())){
                        assertThat(expAttribute.getName()).as("Attribute name is wrong for Physical store identifier: "+expStoreItem.getIdentifier()+" ").isEqualTo(actAttribute.getName());
                        assertThat(expAttribute.getType()).as("Attribute type is wrong for Physical store identifier: "+expStoreItem.getIdentifier()+" ").isEqualTo(actAttribute.getType());
                        for(AttributeValues expAttributeValue:expAttribute.getValues()){
                            boolean isAttributeValueExist = false;
                            for(AttributeValues actAttributeValue:actAttribute.getValues()){
                                if(expAttributeValue.getIdentifier().equals(actAttributeValue.getIdentifier())){
                                    assertThat(expAttributeValue.getValue()).as("Attribute value value is wrong for Physical store identifier: "+expStoreItem.getIdentifier()+" ").isEqualTo(actAttributeValue.getValue());
                                    isAttributeValueExist = true;
                                    break;
                                }
                            }
                            assertThat(isAttributeValueExist).as("Attribute value Identifier: "+expAttributeValue.getIdentifier()+" for Physical store identifier: "+expStoreItem.getIdentifier()+" doest not exist in the response").isTrue();
                        }
                        isAttributeExist = true;
                        break;
                    }
                }
                assertThat(isAttributeExist).as("Attribute Identifier: "+expAttribute.getIdentifier()+" for Physical store identifier: "+expStoreItem.getIdentifier()+" doest not exist in the response").isTrue();
            }

            //check openingTimes
            for(StoreOpeningHours expOpeningHours:expStoreItem.getOpeningTimes()){
                assertThat(expStoreItem.getOpeningTimes().size()).as("Store Opening Times count is wrong for Physical store identifier : "+expStoreItem.getIdentifier()+"").isEqualTo(actStoreItem.getOpeningTimes().size());
                boolean isOpeningDayExist = false;
                for(StoreOpeningHours actStoreOpeningHours:actStoreItem.getOpeningTimes()){
                    if(expOpeningHours.getDayNumber().equals(actStoreOpeningHours.getDayNumber())){
                        List<OpeningTimeSlots> expOpeningTimeSlotses = expOpeningHours.getSlots();
                        List<OpeningTimeSlots> actOpeningTimeSlotses = actStoreOpeningHours.getSlots();
                        assertThat(expOpeningTimeSlotses.size()).as("Opening Time slots for Day:"+ expOpeningHours.getDayNumber() +" is wrong").isEqualTo(actOpeningTimeSlotses.size());
                        for(OpeningTimeSlots expOpeningTimeSlot:expOpeningTimeSlotses){
                            boolean isOpeningSlotExist = false;
                            for(OpeningTimeSlots actOpeningTimeSlot:actOpeningTimeSlotses){
                                if(expOpeningTimeSlot.getSlot() == actOpeningTimeSlot.getSlot()){
                                    assertThat(expOpeningTimeSlot.getOpenTime()).as(" Opening Time for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store identifier : "+expStoreItem.getIdentifier()+" is coming wrong in the response").isEqualTo(actOpeningTimeSlot.getOpenTime());
                                    assertThat(expOpeningTimeSlot.getCloseTime()).as(" Closing Time for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store identifier : "+expStoreItem.getIdentifier()+" is coming wrong in the response").isEqualTo(actOpeningTimeSlot.getCloseTime());
                                    assertThat(expOpeningTimeSlot.getOpenTimeHour()).as(" Opening Time Hour for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store identifier : "+expStoreItem.getIdentifier()+" is coming wrong in the response").isEqualTo(actOpeningTimeSlot.getOpenTimeHour());
                                    assertThat(expOpeningTimeSlot.getCloseTimeHour()).as(" Closing Time Hour for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store identifier : "+expStoreItem.getIdentifier()+" is coming wrong in the response").isEqualTo(actOpeningTimeSlot.getCloseTimeHour());
                                    assertThat(expOpeningTimeSlot.getOpenTimeMinute()).as(" Opening Time Minute for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store identifier : "+expStoreItem.getIdentifier()+" is coming wrong in the response").isEqualTo(actOpeningTimeSlot.getOpenTimeMinute());
                                    assertThat(expOpeningTimeSlot.getCloseTimeMinute()).as(" Closing Time Minute for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store identifier : "+expStoreItem.getIdentifier()+" is coming wrong in the response").isEqualTo(actOpeningTimeSlot.getCloseTimeMinute());
                                    isOpeningSlotExist = true;
                                }
                            }
                            assertThat(isOpeningSlotExist).as("Slots: "+expOpeningTimeSlot.getSlot()+" for Physical store : "+expStoreItem.getIdentifier()+" doest not exist in the response").isTrue();
                        }
                        isOpeningDayExist = true;
                        break;
                    }
                }
                assertThat(isOpeningDayExist).as("Openig Day: "+expOpeningHours.getDayNumber()+" for Physical store : "+expStoreItem.getIdentifier()+" doest not exist in the response").isTrue();
            }

        }

    }

}
