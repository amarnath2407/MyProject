package com.salmon.test.step_definitions.api.storelocator;

import com.salmon.test.framework.helpers.Props;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.api.storelocator.*;
import com.salmon.test.services.storelocator.StoreLocatorAPI;
import com.salmon.test.sql.store.StoreDAO;
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
 * Created by veeranank on 24/08/2016.
 */
public class SearchPhysicalStoreByLatitudeAndLongitudeSteps extends BaseStepDefinition {
    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private StoreLocator storeLocator;
    private StorelocatorItem storeLocatorItem;
    private StoreLocatorAPI storeLocatorAPI;
    private Map<String,String> queryParams = null;
    private StoreDAO storeDAO;
    private String stloc_LimitStores;
    private String stloc_OffsetStores;
    private String stloc_Radius;
    private String stloc_RadiusUom;
    private String stloc_RadiusUomDB;
    List<String> storeIdentifierList;
    List<String> languageList,languageIdList;
    List<String> attributesLatitude = null;
    List<String> attributesLongitude = null;
    Map<String,String> queryParamsDB = null;

    public SearchPhysicalStoreByLatitudeAndLongitudeSteps(StoreLocator storeLocator, StoreLocatorAPI storeLocatorAPI,StoreDAO storeDAO){
        super(storeDAO);
        this.storeLocator = storeLocator;
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
    }

    @Given("^I have the store id \"([^\"]*)\" to search for physical store with latitude and longitude$")
    public void iHaveTheStoreIdToSearchForPhysicalStoreWithLatitudeAndLongitude(String storeIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^I call the service Physical Store Data by latitude and longitude and compare the details against the database$")
    public void iCallTheServicePhysicalStoreDataByLatitudeAndLongitudeAndCompareTheDetailsAgainstTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            log.info("Comparing store data with latitude and longitude for Store Identifier:"+storeIdentifier);
            queryParamsDB = new HashMap<String,String>();
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            stloc_LimitStores = Props.getMessage("stloc.limitStores");
            stloc_OffsetStores = Props.getMessage("stloc.offsetStores");
            stloc_Radius = Props.getMessage("stloc.radius");
            stloc_RadiusUom = Props.getMessage("stloc.radiusUom");
            if (stloc_RadiusUom.equals("KM")){
                stloc_RadiusUomDB = "1.60934";
            }else{
                stloc_RadiusUomDB = "1";
            }
            List<Map<String, Object>> attributesLatLong = storeDAO.getLatitudeAndLongitudeForStore(catalogAssetId);
            Map<String, Object> attributesLatLongMap = attributesLatLong.get(0);
            String latitude = attributesLatLongMap.get("LATITUDE").toString();
            String longitude = attributesLatLongMap.get("LONGITUDE").toString();
            queryParamsDB.put("Latitude", attributesLatLongMap.get("LATITUDE").toString());
            queryParamsDB.put("Longitude",attributesLatLongMap.get("LONGITUDE").toString());
            queryParamsDB.put("Radius",stloc_Radius);
            queryParamsDB.put("RadiusUom",stloc_RadiusUomDB);
            queryParamsDB.put("pageSize",stloc_LimitStores);
            queryParamsDB.put("pageNumber",stloc_OffsetStores);
            storeLocator = storeLocatorAPI.searchPhysicalStoreWithLatitudeAndLongitude(storeIdentifier,latitude,longitude,queryParams);
            StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataWithLatitudeAndLongitudeFromDB(catalogAssetId,language.getLanguageId(),"1", queryParamsDB,null,null);
            compareStoreInformation(expectedStoreData,storeLocator);
        }
    }


    @Given("^I have the store id \"([^\"]*)\" to search for physical store with latitude and longitude and locale \"([^\"]*)\"$")
    public void iHaveTheStoreIdToSearchForPhysicalStoreWithLatitudeAndLongitudeAndLocale(String storeIdentifierKey, String languageKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
    }

    @Then("^I call the service Physical Store Data by latitude and longitude with query params radius \"([^\"]*)\" and radiusUom \"([^\"]*)\" then compare the details against the database$")
    public void iCallTheServicePhysicalStoreDataByLatitudeAndLongitudeWithQueryParamsRadiusAndRadiusUomThenCompareTheDetailsAgainstTheDatabase(String radiusKey, String radiusUomKey) throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            for(String locale:languageList) {
                log.info("Comparing store data with latitude and longitude for Store Identifier:" + storeIdentifier +" with Locale: "+locale);
                queryParams = new HashMap<String, String>();
                queryParamsDB = new HashMap<String, String>();
                String languageId = String.valueOf(storeDAO.getLanguageIdByLocaleName(locale));
                log.info("language Id:"+languageId);
                if(languageId.equals("0") || storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier),languageId) == 0){
                    Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    languageId = language.getLanguageId();
                }
                stloc_LimitStores = Props.getMessage("stloc.limitStores");
                stloc_OffsetStores = Props.getMessage("stloc.offsetStores");
                stloc_Radius = Props.getMessage(radiusKey);
                stloc_RadiusUom = Props.getMessage(radiusUomKey);
                if (stloc_RadiusUom.equals("KM")) {
                    stloc_RadiusUomDB = "1.60934";
                } else {
                    stloc_RadiusUomDB = "1";
                }
                List<Map<String, Object>> attributesLatLong = storeDAO.getLatitudeAndLongitudeForStore(catalogAssetId);
                Map<String, Object> attributesLatLongMap = attributesLatLong.get(0);
                String latitude = attributesLatLongMap.get("LATITUDE").toString();
                String longitude = attributesLatLongMap.get("LONGITUDE").toString();
                queryParams.put("locale",locale);
                queryParams.put("radius",stloc_Radius);
                queryParams.put("radiusUom",stloc_RadiusUom);
                queryParamsDB.put("Latitude", attributesLatLongMap.get("LATITUDE").toString());
                queryParamsDB.put("Longitude", attributesLatLongMap.get("LONGITUDE").toString());
                queryParamsDB.put("Radius", stloc_Radius);
                queryParamsDB.put("RadiusUom", stloc_RadiusUomDB);
                queryParamsDB.put("pageSize", stloc_LimitStores);
                queryParamsDB.put("pageNumber", stloc_OffsetStores);
                storeLocator = storeLocatorAPI.searchPhysicalStoreWithLatitudeAndLongitude(storeIdentifier,latitude,longitude,queryParams);
                StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataWithLatitudeAndLongitudeFromDB(catalogAssetId, languageId, "1", queryParamsDB, null,null);
                compareStoreInformation(expectedStoreData, storeLocator);
            }
        }
    }


    @Then("^I call the service Physical Store Data by latitude and longitude with query params radius \"([^\"]*)\",radiusUom \"([^\"]*)\" and attributes then compare the details against the database$")
    public void iCallTheServicePhysicalStoreDataByLatitudeAndLongitudeWithQueryParamsRadiusRadiusUomAndAttributesThenCompareTheDetailsAgainstTheDatabase(String radiusKey, String radiusUomKey) throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            log.info("Comparing store data with latitude and longitude for Store Identifier with attributes:" + storeIdentifier);
            queryParams = new HashMap<String, String>();
            queryParamsDB = new HashMap<String, String>();
            Map<String,String> queryParamsAttribute =  new HashMap<String, String>();
            List<Map<String, Object>> attributes = storeDAO.getStoreAttributes(catalogAssetId);
            if(attributes.size() == 0){
                log.info("No attributes found for the storeIdentifier: "+storeIdentifier+"");
                continue;
            }
            int maxAttributes = Integer.parseInt( Props.getMessage("stloc.max.attributes.count"));
            if (attributes.size() < maxAttributes) {
                maxAttributes = attributes.size();
            }
            for (int attributeCount = 0; attributeCount < maxAttributes; attributeCount++) {
                Map<String, Object> attributesMap = attributes.get(attributeCount);
                queryParams.put("attr["+attributesMap.get("ATTRIDENTIFIER").toString()+"]", attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                queryParamsAttribute.put(attributesMap.get("ATTRIDENTIFIER").toString(),attributesMap.get("ATTRVALUEIDENTIFIER").toString());
            }

            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            stloc_LimitStores = Props.getMessage("stloc.limitStores");
            stloc_OffsetStores = Props.getMessage("stloc.offsetStores");
            stloc_Radius = Props.getMessage(radiusKey);
            stloc_RadiusUom = Props.getMessage(radiusUomKey);
            if (stloc_RadiusUom.equals("KM")) {
                stloc_RadiusUomDB = "1.60934";
            } else {
                stloc_RadiusUomDB = "1";
            }
            List<Map<String, Object>> attributesLatLong = storeDAO.getLatitudeAndLongitudeForStore(catalogAssetId);
            Map<String, Object> attributesLatLongMap = attributesLatLong.get(0);
            String latitude = attributesLatLongMap.get("LATITUDE").toString();
            String longitude = attributesLatLongMap.get("LONGITUDE").toString();
            queryParams.put("radius",stloc_Radius);
            queryParams.put("radiusUom",stloc_RadiusUom);
            queryParamsDB.put("Latitude", attributesLatLongMap.get("LATITUDE").toString());
            queryParamsDB.put("Longitude", attributesLatLongMap.get("LONGITUDE").toString());
            queryParamsDB.put("Radius", stloc_Radius);
            queryParamsDB.put("RadiusUom", stloc_RadiusUomDB);
            queryParamsDB.put("pageSize", stloc_LimitStores);
            queryParamsDB.put("pageNumber", stloc_OffsetStores);
            storeLocator = storeLocatorAPI.searchPhysicalStoreWithLatitudeAndLongitude(storeIdentifier,latitude,longitude,queryParams);
            StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataWithLatitudeAndLongitudeFromDB(catalogAssetId, language.getLanguageId(), "1", queryParamsDB, "radiusAttributes",queryParamsAttribute);
            compareStoreInformation(expectedStoreData, storeLocator);
        }
    }


    @Then("^I call the service Physical Store Data by latitude and longitude with query params radius \"([^\"]*)\",radiusUom \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\" and \"([^\"]*)\" and attributes then compare the details against the database$")
    public void iCallTheServicePhysicalStoreDataByLatitudeAndLongitudeWithQueryParamsRadiusRadiusUomAndAndAttributesThenCompareTheDetailsAgainstTheDatabase(String radiusKey, String radiusUomKey, String limitStoresKey, String offsetStoresKey, String includeInactive) throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            log.info("Comparing store data with latitude and longitude for Store Identifier with attributes:" + storeIdentifier);
            queryParams = new HashMap<String, String>();
            queryParamsDB = new HashMap<String, String>();
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            stloc_LimitStores = Props.getMessage(limitStoresKey);
            stloc_OffsetStores = Props.getMessage(offsetStoresKey);
            stloc_Radius = Props.getMessage(radiusKey);
            stloc_RadiusUom = Props.getMessage(radiusUomKey);
            if (stloc_RadiusUom.equals("KM")) {
                stloc_RadiusUomDB = "1.60934";
            } else {
                stloc_RadiusUomDB = "1";
            }
            String isActive = "1";
            if(includeInactive.equals("true")){
                isActive = "0,1";
            }
            List<Map<String, Object>> attributesLatLong = storeDAO.getLatitudeAndLongitudeForStore(catalogAssetId);
            Map<String, Object> attributesLatLongMap = attributesLatLong.get(0);
            String latitude = attributesLatLongMap.get("LATITUDE").toString();
            String longitude = attributesLatLongMap.get("LONGITUDE").toString();
            queryParams.put("radius",stloc_Radius);
            queryParams.put("radiusUom",stloc_RadiusUom);
            queryParams.put("limit",stloc_LimitStores);
            queryParams.put("offset",stloc_OffsetStores);
            queryParams.put("includeInactive",includeInactive);
            queryParams.put("radiusUom",stloc_RadiusUom);
            queryParamsDB.put("Latitude", attributesLatLongMap.get("LATITUDE").toString());
            queryParamsDB.put("Longitude", attributesLatLongMap.get("LONGITUDE").toString());
            queryParamsDB.put("Radius", stloc_Radius);
            queryParamsDB.put("RadiusUom", stloc_RadiusUomDB);
            queryParamsDB.put("pageSize", stloc_LimitStores);
            queryParamsDB.put("pageNumber", stloc_OffsetStores);
            storeLocator = storeLocatorAPI.searchPhysicalStoreWithLatitudeAndLongitude(storeIdentifier,latitude,longitude,queryParams);
            StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataWithLatitudeAndLongitudeFromDB(catalogAssetId, language.getLanguageId(), isActive, queryParamsDB, null,null);
            compareStoreInformation(expectedStoreData, storeLocator);
        }
    }

    @Given("^I have the store id \"([^\"]*)\" to search for physical store with latitude and longitude and query parameters locale \"([^\"]*)\" and langId \"([^\"]*)\"$")
    public void iHaveTheStoreIdToSearchForPhysicalStoreWithLatitudeAndLongitudeAndQueryParametersLocaleAndLangId(String storeIdentifierKey, String languageKey, String languageIdKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
        languageIdList = Arrays.asList(Props.getMessage(languageIdKey).split(","));
    }

    @Then("^I call the service Physical Store Data by latitude and longitude with query parameters locale and langId and compare the results against the database$")
    public void iCallTheServicePhysicalStoreDataByLatitudeAndLongitudeWithQueryParametersLocaleAndLangIdAndCompareTheResultsAgainstTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            for(String locale:languageList) {
                for (String expLangId : languageIdList) {
                    log.info("Comparing store data with latitude and longitude for Store Identifier:" + storeIdentifier + " with Locale: " + locale +" and langID: "+expLangId);
                    String languageId = null;
                    if (storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), expLangId) == 0) {
                        log.info("language Id inside");
                        Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                        languageId = language.getLanguageId();
                    } else {
                        languageId = expLangId;
                    }
                    queryParams = new HashMap<String, String>();
                    queryParamsDB = new HashMap<String, String>();
                    stloc_LimitStores = Props.getMessage("stloc.limitStores");
                    stloc_OffsetStores = Props.getMessage("stloc.offsetStores");
                    stloc_Radius = Props.getMessage("stloc.radius");
                    stloc_RadiusUom = Props.getMessage("stloc.radiusUom");
                    if (stloc_RadiusUom.equals("KM")) {
                        stloc_RadiusUomDB = "1.60934";
                    } else {
                        stloc_RadiusUomDB = "1";
                    }
                    List<Map<String, Object>> attributesLatLong = storeDAO.getLatitudeAndLongitudeForStore(catalogAssetId);
                    Map<String, Object> attributesLatLongMap = attributesLatLong.get(0);
                    String latitude = attributesLatLongMap.get("LATITUDE").toString();
                    String longitude = attributesLatLongMap.get("LONGITUDE").toString();
                    queryParams.put("locale", locale);
                    queryParams.put("langId", expLangId);
                    queryParams.put("radius", stloc_Radius);
                    queryParams.put("radiusUom", stloc_RadiusUom);
                    queryParamsDB.put("Latitude", attributesLatLongMap.get("LATITUDE").toString());
                    queryParamsDB.put("Longitude", attributesLatLongMap.get("LONGITUDE").toString());
                    queryParamsDB.put("Radius", stloc_Radius);
                    queryParamsDB.put("RadiusUom", stloc_RadiusUomDB);
                    queryParamsDB.put("pageSize", stloc_LimitStores);
                    queryParamsDB.put("pageNumber", stloc_OffsetStores);
                    storeLocator = storeLocatorAPI.searchPhysicalStoreWithLatitudeAndLongitude(storeIdentifier, latitude, longitude, queryParams);
                    StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataWithLatitudeAndLongitudeFromDB(catalogAssetId, languageId, "1", queryParamsDB, null, null);
                    compareStoreInformation(expectedStoreData, storeLocator);
                }
            }
        }
    }

    public void compareStoreInformation(StoreLocator expectedStoreData,StoreLocator storeLocator){
        assertThat(expectedStoreData.getData().size()).as("Store count is wrong").isEqualTo(storeLocator.getData().size());
        StorelocatorItem expStoreItem;
        StorelocatorItem actStoreItem;
        for(int storeItem=0;storeItem<expectedStoreData.getData().size();storeItem++){
            expStoreItem = expectedStoreData.getData().get(storeItem);
            actStoreItem = storeLocator.getData().get(storeItem);
            assertThat(expStoreItem.getIdentifier()).as("Store Identifier is wrong").isEqualTo(actStoreItem.getIdentifier());
            assertThat(expStoreItem.getActive()).as("Store active is wrong").isEqualTo(actStoreItem.getActive());
            assertThat(expStoreItem.getStoreName()).as("Store Name is wrong").isEqualTo(actStoreItem.getStoreName());
            assertThat(expStoreItem.getStoreNumber()).as("Store Number is wrong").isEqualTo(actStoreItem.getStoreNumber());
           // assertThat(expStoreItem.getGeoDistance().getDistance()).as("Store Distance is wrong").isEqualTo(actStoreItem.getGeoDistance().getDistance());
            assertThat(stloc_RadiusUom).as("Store RadiusUom is wrong").isEqualTo(actStoreItem.getGeoDistance().getDistanceUom());
            assertThat(queryParamsDB.get("Longitude")).as("Store Start Location -Longitude is wrong").isEqualTo(actStoreItem.getGeoDistance().getStartLocation().getLongitude());
            assertThat(queryParamsDB.get("Latitude")).as("Store Start Location -Latitude is wrong").isEqualTo(actStoreItem.getGeoDistance().getStartLocation().getLatitude());
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
                assertThat(isOpeningDayExist).as("Opening Day: "+expOpeningHours.getDayNumber()+" for Physical store : "+expStoreItem.getIdentifier()+" doest not exist in the response").isTrue();
            }

        }

    }
}

