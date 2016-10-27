package com.salmon.test.step_definitions.api.storelocator;

import com.salmon.test.framework.helpers.Props;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 01/08/2016.
 */
public class SearchPhysicalStoreSteps extends BaseStepDefinition{

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private Map<String,String> queryParams = null;
    private Map<String,String> queryParamsDB = null;
    private StoreLocatorAPI storeLocatorAPI;
    private StoreLocator storeLocator;
    StoreDAO storeDAO;
    StoreLocatorDAO storeLocatorDAO;
    private List<String> storeIdentifierList,languageList,regionList,countryList,cityList,attributesList,attributesValueList;
    private String limit,includeInactive,Offset;
    private List<String> languageIdList;

    public SearchPhysicalStoreSteps(StoreLocatorAPI storeLocatorAPI,StoreDAO storeDAO,StoreLocatorDAO storeLocatorDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
        this.storeLocatorDAO = storeLocatorDAO;
    }

    @Given("^I have the store identifiers \"([^\"]*)\" to search for physical store$")
    public void iHaveTheStoreIdentifiersToSearchForPhysicalStore(String storeIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^I call the service Physical Store Data - Search and compare the store details will be returned for the stores for brand should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchAndCompareTheStoreDetailsWillBeReturnedForTheStoresForBrandShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            log.info("Comparing store data for Store Identifier:"+storeIdentifier);
            storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier,queryParams);
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String noOfStores = Props.getMessage("stloc.limitStores");
            queryParams = new HashMap<String,String>();
            queryParams.put("offset",Props.getMessage("stloc.offsetStores"));
            StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId,language.getLanguageId(),noOfStores,"1",null,queryParams);
            //log.info("Actual storeLocator Data:"+storeLocator);
            //log.info("Expected storeLocator Data:"+expectedStoreData);
            comapreStoreInformation(expectedStoreData,storeLocator);
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams locale \"([^\"]*)\",regionCode \"([^\"]*)\"  to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsLocaleRegionCodeToSearchForPhysicalStore(String storeIdentifierKey, String languageKey, String regionKey) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameters locale and Region code");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
        regionList = Arrays.asList(Props.getMessage(regionKey).split(","));
    }

    @Then("^I call the service Physical Store Data - Search api and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = Props.getMessage("stloc.limitStores");
            for(String locale:languageList) {
                String languageId = String.valueOf(storeDAO.getLanguageIdByLocaleName(locale));
                log.info("language Id:"+languageId);
                if(languageId.equals("0") || storeDAO.checkLanguageSupportedForStore(storeIds.get(storeIdentifier),languageId) == 0){
                    log.info("language Id inside");
                    Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    languageId = language.getLanguageId();
                }
                for(String regionCode:regionList) {
                    log.info("Comparing store data for Store Identifier:" + storeIdentifier +" with Region Code:"+regionCode +" and language Id: "+locale);
                    queryParams = new HashMap<String,String>();
                    queryParams.put("locale",locale);
                    queryParams.put("regionCode",regionCode);
                    queryParams.put("offset",Props.getMessage("stloc.offsetStores"));
                    storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                    StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, languageId, noOfStores, "1","region",queryParams);
                    //log.info("Actual storeLocator Data:"+storeLocator);
                    //log.info("Expected storeLocator Data:"+expectedStoreData);
                    comapreStoreInformation(expectedStoreData, storeLocator);
                }
            }
        }
    }


    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams countryCode \"([^\"]*)\"  to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsCountryCodeToSearchForPhysicalStore(String storeIdentifierKey, String countryKey) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameter Country code");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        countryList = Arrays.asList(Props.getMessage(countryKey).split(","));
    }

    @Then("^I call the service Physical Store Data - Search api with country code and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiWithCountryCodeAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = Props.getMessage("stloc.limitStores");
            for(String countryCode:countryList) {
                log.info("Comparing store data for Store Identifier:" + storeIdentifier +" with Country Code:"+countryCode );
                queryParams = new HashMap<String,String>();
                queryParams.put("countryCode",countryCode);
                queryParams.put("offset",Props.getMessage("stloc.offsetStores"));
                storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, language.getLanguageId(), noOfStores, "1","country",queryParams);
                //log.info("Actual storeLocator Data:"+storeLocator);
                //log.info("Expected storeLocator Data:"+expectedStoreData);
                comapreStoreInformation(expectedStoreData, storeLocator);
            }
        }
    }


    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams Region Code \"([^\"]*)\", Country Code \"([^\"]*)\" and City Name \"([^\"]*)\" to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsRegionCodeCountryCodeAndCityNameToSearchForPhysicalStore(String storeIdentifierKey, String regionKey, String countryKey, String cityKey) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code, Country code and city");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        regionList = Arrays.asList(Props.getMessage(regionKey).split(","));
        countryList = Arrays.asList(Props.getMessage(countryKey).split(","));
        cityList = Arrays.asList(Props.getMessage(cityKey).split(","));
    }

    @Then("^I call the service Physical Store Data - Search api with region,country code and city name and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiWithRegionCountryCodeAndCityNameAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = Props.getMessage("stloc.limitStores");
            for(String regionCode:regionList) {
                for (String countryCode : countryList) {
                    for (String cityName : cityList) {
                        log.info("Comparing store data for Store Identifier:" + storeIdentifier + " with Region Code:"+regionCode+" Country Code:" + countryCode +" and cityName: "+cityName+"");
                        queryParams = new HashMap<String, String>();
                        queryParams.put("regionCode", regionCode);
                        queryParams.put("countryCode", countryCode);
                        queryParams.put("city", cityName);
                        queryParams.put("offset",Props.getMessage("stloc.offsetStores"));
                        storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                        StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, language.getLanguageId(), noOfStores, "1", "city", queryParams);
                        //log.info("Actual storeLocator Data:"+storeLocator);
                        //log.info("Expected storeLocator Data:"+expectedStoreData);
                        comapreStoreInformation(expectedStoreData, storeLocator);
                    }
                }
            }
        }
    }


    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams Region Code \"([^\"]*)\", attributes \"([^\"]*)\" and attribute values \"([^\"]*)\" to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsRegionCodeAttributesAndAttributeValuesToSearchForPhysicalStore(String storeIdentifierKey, String regionKey, String attributeKeys, String attributeValueKeys) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code and attributes");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        regionList = Arrays.asList(Props.getMessage(regionKey).split(","));
        String attributes = Props.getMessage(attributeKeys);
        if(attributes.length() > 0) {
            attributesList = Arrays.asList(attributes.split(","));
            attributesValueList = Arrays.asList(Props.getMessage(attributeValueKeys).split(","));
        }
    }

    @Then("^I call the service Physical Store Data - Search api with region,attributes and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiWithRegionAttributesAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = Props.getMessage("stloc.limitStores");
            queryParams = new HashMap<String, String>();
            queryParamsDB = new HashMap<String, String>();
            if (attributesList == null) {
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
                    queryParamsDB.put(attributesMap.get("ATTRIDENTIFIER").toString(),attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                }
            }
            for(String regionCode:regionList) {
                log.info("Comparing store data for Store Identifier:" + storeIdentifier + " with Region Code:"+regionCode+"");
                queryParams.put("regionCode", regionCode);
                queryParamsDB.put("regionCode", regionCode);
                queryParamsDB.put("offset",Props.getMessage("stloc.offsetStores"));
                storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, language.getLanguageId(), noOfStores, "1", "regionAttributes", queryParamsDB);
                //log.info("Actual storeLocator Data:"+storeLocator);
                //log.info("Expected storeLocator Data:"+expectedStoreData);
                comapreStoreInformation(expectedStoreData, storeLocator);
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams attributes \"([^\"]*)\" and attribute values \"([^\"]*)\" to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsAttributesAndAttributeValuesToSearchForPhysicalStore(String storeIdentifierKey, String attributeKeys, String attributeValueKeys) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code and attributes");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        String attributes = Props.getMessage(attributeKeys);
        if(attributes.length() > 0) {
            attributesList = Arrays.asList(attributes.split(","));
            attributesValueList = Arrays.asList(Props.getMessage(attributeValueKeys).split(","));
        }
    }

    @Then("^I call the service Physical Store Data - Search api with attributes and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiWithAttributesAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = Props.getMessage("stloc.limitStores");
            queryParams = new HashMap<String, String>();
            queryParamsDB = new HashMap<String, String>();
            if (attributesList == null) {
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
                    queryParamsDB.put(attributesMap.get("ATTRIDENTIFIER").toString(),attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                }
            }
                log.info("Comparing store data for Store Identifier:" + storeIdentifier + " with attributes ");
                storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                queryParamsDB.put("offset",Props.getMessage("stloc.offsetStores"));
                StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, language.getLanguageId(), noOfStores, "1", "attributes", queryParamsDB);
                //log.info("Actual storeLocator Data:"+storeLocator);
                //log.info("Expected storeLocator Data:"+expectedStoreData);
                comapreStoreInformation(expectedStoreData, storeLocator);
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams Country Code \"([^\"]*)\" and City Name \"([^\"]*)\" , attributes \"([^\"]*)\" and attribute values \"([^\"]*)\" to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsCountryCodeAndCityNameAttributesAndAttributeValuesToSearchForPhysicalStore(String storeIdentifierKey, String countryKey, String cityKey, String attributeKeys, String attributeValueKeys) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameters country code, city name and attributes");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        countryList = Arrays.asList(Props.getMessage(countryKey).split(","));
        cityList = Arrays.asList(Props.getMessage(cityKey).split(","));
        String attributes = Props.getMessage(attributeKeys);
        if(attributes.length() > 0) {
            attributesList = Arrays.asList(attributes.split(","));
            attributesValueList = Arrays.asList(Props.getMessage(attributeValueKeys).split(","));
        }
    }

    @Then("^I call the service Physical Store Data - Search api with country code, city and attributes and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiWithCountryCodeCityAndAttributesAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = Props.getMessage("stloc.limitStores");
            queryParams = new HashMap<String, String>();
            queryParamsDB = new HashMap<String, String>();
            if (attributesList == null) {
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
                    queryParamsDB.put(attributesMap.get("ATTRIDENTIFIER").toString(),attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                }
            }
            for (String countryCode : countryList) {
                for (String cityName : cityList) {
                    log.info("Comparing store data for Store Identifier:" + storeIdentifier + " with Country Code:" + countryCode +" and cityName: "+cityName+"");
                    queryParams.put("countryCode", countryCode);
                    queryParams.put("city", cityName);
                    queryParamsDB.put("countryCode", countryCode);
                    queryParamsDB.put("city", cityName);
                    queryParamsDB.put("pageNumber",Props.getMessage("stloc.offsetStores"));
                    storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                    StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, language.getLanguageId(), noOfStores, "1", "countryAttributes", queryParamsDB);
                    //log.info("Actual storeLocator Data:"+storeLocator);
                    //log.info("Expected storeLocator Data:"+expectedStoreData);
                    comapreStoreInformation(expectedStoreData, storeLocator);
                }
            }
         }
    }



    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams regionCode \"([^\"]*)\", limit \"([^\"]*)\" and includeInactive \"([^\"]*)\" and  to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsRegionCodeLimitAndIncludeInactiveAndToSearchForPhysicalStore(String storeIdentifierKey, String regionKey, String limitValue, String includeInactiveValue) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code ,limit and include inactive");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        regionList = Arrays.asList(Props.getMessage(regionKey).split(","));
        includeInactive = includeInactiveValue;
        limit = Props.getMessage(limitValue);

    }

    @Then("^I call the service Physical Store Data - Search api with region code , limit and include inactive attributes and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiWithRegionCodeLimitAndIncludeInactiveAttributesAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = limit;
            String isActive = "1";
            if(includeInactive.equals("true")){
                isActive = "0,1";
            }
            queryParams = new HashMap<String,String>();
            for (String regionCode : regionList) {
                    log.info("Comparing store data for Store Identifier:" + storeIdentifier + " with Region Code:" + regionCode +" with pageSize:"+limit+" and includeInactive: "+ includeInactive+"");
                    queryParams.put("regionCode", regionCode);
                    queryParams.put("pageSize", noOfStores);
                    queryParams.put("includeInactive", includeInactive);
                    queryParams.put("pageNumber",Props.getMessage("stloc.offsetStores"));
                    storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                    StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, language.getLanguageId(), noOfStores, isActive, "regionlimit", queryParams);
                    //log.info("Actual storeLocator Data:"+storeLocator);
                    //log.info("Expected storeLocator Data:"+expectedStoreData);
                    comapreStoreInformation(expectedStoreData, storeLocator);
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with query params locale  \"([^\"]*)\" and langId \"([^\"]*)\" to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsLocaleAndLangIdToSearchForPhysicalStore(String storeIdentifierKey, String languageKey, String languageIdKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
        languageIdList = Arrays.asList(Props.getMessage(languageIdKey).split(","));
    }

    @Then("^I call the service Physical Store Data - Search with query params locale and langId and compare the store details will be returned for the stores for brand should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchWithQueryParamsLocaleAndLangIdAndCompareTheStoreDetailsWillBeReturnedForTheStoresForBrandShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = Props.getMessage("stloc.limitStores");
            for(String locale:languageList) {
                for(String expLangId:languageIdList) {
                    String languageId = null;
                    if (storeDAO.checkLanguageSupportedForStore(storeIds.get(storeIdentifier), expLangId) == 0) {
                        log.info("language Id inside");
                        Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                        languageId = language.getLanguageId();
                    }else{
                        languageId = expLangId;
                    }
                    log.info("Comparing store data for Store Identifier:" + storeIdentifier  + " and language Id: " + locale);
                    queryParams = new HashMap<String, String>();
                    queryParams.put("locale", locale);
                    queryParams.put("langId", expLangId);
                    queryParams.put("pageNumber",Props.getMessage("stloc.offsetStores"));
                    storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
                    StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, languageId, noOfStores, "1", null, queryParams);
                    comapreStoreInformation(expectedStoreData, storeLocator);
                }
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with queryParams Offset \"([^\"]*)\", limit \"([^\"]*)\" and includeInactive \"([^\"]*)\" and  to search for physical store$")
    public void iHaveTheStoreIdentifiersWithQueryParamsOffsetLimitAndIncludeInactiveAndToSearchForPhysicalStore(String storeIdentifierKey, String offsetKey, String limitValue, String includeInactiveValue) throws Throwable {
        log.info("As a Customer I should be able to search for physical stores with store identifier and different query parameters pageNumber ,limit and include inactive");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        Offset = Props.getMessage(offsetKey);
        includeInactive = includeInactiveValue;
        limit = Props.getMessage(limitValue);
    }

    @Then("^I call the service Physical Store Data - Search api with offset , limit and include inactive attributes and compare the store details returned in ascending order by name after sending different query params should be verified$")
    public void iCallTheServicePhysicalStoreDataSearchApiWithOffsetLimitAndIncludeInactiveAttributesAndCompareTheStoreDetailsReturnedInAscendingOrderByNameAfterSendingDifferentQueryParamsShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            String catalogAssetId = storeCASIds.get(storeIdentifier);
            String noOfStores = limit;
            String isActive = "1";
            if(includeInactive.equals("true")){
                isActive = "0,1";
            }
            queryParams = new HashMap<String,String>();
            log.info("Comparing store data for Store Identifier:" + storeIdentifier + " with pageNumber:" + Offset +" with pageSize:"+limit+" and includeInactive: "+ includeInactive+"");
            queryParams.put("pageSize", noOfStores);
            queryParams.put("includeInactive", includeInactive);
            queryParams.put("pageNumber",Offset);
            storeLocator = storeLocatorAPI.searchPhysicalStore(storeIdentifier, queryParams);
            StoreLocator expectedStoreData = storeLocatorAPI.getStoreLocatorDataFromDB(catalogAssetId, language.getLanguageId(), noOfStores, isActive, null, queryParams);
            //log.info("Actual storeLocator Data:"+storeLocator);
            //log.info("Expected storeLocator Data:"+expectedStoreData);
            comapreStoreInformation(expectedStoreData, storeLocator);
        }
    }

    public void comapreStoreInformation(StoreLocator expectedStoreData,StoreLocator storeLocator){

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
            if(expStoreItem.getAddress() != null) {
                assertThat(expStoreItem.getAddress().getCity()).as("Store city is wrong").isEqualTo(actStoreItem.getAddress().getCity());
                assertThat(expStoreItem.getAddress().getCountryCode()).as("Store country code for store identifier "+expStoreItem.getIdentifier()+"  is wrong").isEqualTo(actStoreItem.getAddress().getCountryCode());
                assertThat(expStoreItem.getAddress().getCountryName()).as("Store country name for store identifier "+expStoreItem.getIdentifier()+"  is wrong").isEqualTo(actStoreItem.getAddress().getCountryName());
                assertThat(expStoreItem.getAddress().getLine1()).as("Store address line1 is wrong").isEqualTo(actStoreItem.getAddress().getLine1());
                assertThat(expStoreItem.getAddress().getLine2()).as("Store address line2 is wrong").isEqualTo(actStoreItem.getAddress().getLine2());
                assertThat(expStoreItem.getAddress().getLine3()).as("Store address line3 is wrong").isEqualTo(actStoreItem.getAddress().getLine3());
                assertThat(expStoreItem.getAddress().getLine3()).as("Store address line3 is wrong").isEqualTo(actStoreItem.getAddress().getLine3());
                assertThat(expStoreItem.getAddress().getPostCode()).as("Store postal code for store identifier "+expStoreItem.getIdentifier()+" is wrong").isEqualTo(actStoreItem.getAddress().getPostCode());
                assertThat(expStoreItem.getAddress().getRegionCode()).as("Store region code for store identifier "+expStoreItem.getIdentifier()+" is wrong").isEqualTo(actStoreItem.getAddress().getRegionCode());
                assertThat(expStoreItem.getAddress().getRegionName()).as("Store region name for store identifier "+expStoreItem.getIdentifier()+"  is wrong").isEqualTo(actStoreItem.getAddress().getRegionName());
                assertThat(expStoreItem.getAddress().getState()).as("Store state is wrong").isEqualTo(actStoreItem.getAddress().getState());
            }else{
                assertThat(actStoreItem.getAddress()).as("Address for the "+expStoreItem.getIdentifier()+" should be null").isNull();
            }
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
