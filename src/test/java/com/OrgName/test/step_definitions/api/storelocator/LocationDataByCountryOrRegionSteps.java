package com.salmon.test.step_definitions.api.storelocator;

import com.salmon.test.framework.helpers.Props;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.framework.helpers.utils.StringHelper;
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
 * Created by akepativ on 25/07/2016.
 */
public class LocationDataByCountryOrRegionSteps extends BaseStepDefinition {

    private static final Logger log = LoggerFactory.getLogger(LocationDataByCountryOrRegionSteps.class);
    private Map<String,String> queryParams =  new HashMap<String, String>();
    private StoreLocatorAPI storeLocatorAPI;
    private CountryData countryData;
    private RegionData regionData;
    private StoreDAO storeDAO;
    private List<String> storeIdentifierList;
    private List<String> attributesList = null;
    private List<String> attributesValueList = null;
    private List<String> languageList;
    private List<String> languageIdList;
    private Map<String,String> queryParamsDB;

    public LocationDataByCountryOrRegionSteps(StoreLocatorAPI storeLocatorAPI,StoreDAO storeDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
    }


    @Given("^I have the store identifiers \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiers(String storeIdentifierKey) throws Throwable {
        log.debug(" As a Customer I should be able to get all countries with cities for any specific Store \n As a Customer I should be able to get all regions with countries and cities for any specific Store");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^I call the service Location Data by Country-level and compare the country details returned for the specific store should be verified with the database$")
    public void iCallTheServiceLocationDataByCountryLevelAndCompareTheCountryDetailsReturnedForTheSpecificStoreShouldBeVerifiedWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            log.info("****** Comparing data for Store Identifier countries: "+storeIdentifier+" *****************");
            countryData = storeLocatorAPI.locationDataByCountryLevel(storeIdentifier,queryParams);
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            List<Map<String,Object>> expectedCountryData = storeDAO.getCountriesForStore(storeCASIds.get(storeIdentifier),language.getLanguageId());
            if(expectedCountryData.size() > 0) {
                List<Map<String,Object>> expectedCountryCount = storeDAO.getTotalCountriesForStore(storeCASIds.get(storeIdentifier),language.getLanguageId());
                int countriesCount = Integer.parseInt(expectedCountryCount.get(0).get("COUNTRYCOUNT").toString());
                assertThat(countriesCount).as("Countries count is wrong").isEqualTo(countryData.getData().size());
            }else{
                assertThat(countryData.getData().size()).as("Countries count should be 0").isEqualTo(0);
            }

            String expCountryCode,expCountryName,expCityName;
            Country country;
            City city;
            for(int countryDataCount=0;countryDataCount<expectedCountryData.size();countryDataCount++){
                List<Country> actualCountries = countryData.getData();
                Map<String,Object> expectedCountryMap =  expectedCountryData.get(countryDataCount);
                expCountryCode = expectedCountryMap.get("COUNTRYCODE").toString();
                expCountryName = expectedCountryMap.get("COUNTRYNAME").toString();
                expCityName = StringHelper.returnEmptyStringIfNull(expectedCountryMap.get("CITY"));
                if(expCityName != null){
                    expCityName = expCityName.trim();
                }
                boolean isCountryExpected = false;
                boolean isCityExpected = false;
                for(int actualCountryCount=0;actualCountryCount<actualCountries.size();actualCountryCount++){
                    country = actualCountries.get(actualCountryCount);
                    if(expCountryCode.equals(country.getCode())){
                        assertThat(expCountryCode).isEqualTo(country.getCode());
                        assertThat(expCountryName).isEqualTo(country.getName());
                        isCountryExpected = true;
                        if(expCityName == null) {
                            isCityExpected = true;
                            break;
                        }
                        for (int cityCount = 0; cityCount < country.getCities().size(); cityCount++) {
                            city = country.getCities().get(cityCount);
                            if(expCityName.equals(city.getName())){
                                assertThat(expCityName).isEqualTo(city.getName());
                                isCityExpected = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                assertThat(isCountryExpected).as("Country "+expCountryName+" is not coming in API repsonse for store "+storeIdentifier+"").isTrue();
                assertThat(isCityExpected).as("City " + expCityName + " is not coming in API repsonse for store " + storeIdentifier + "").isTrue();
            }
        }
    }

    @Then("^I call the service Location Data by Region-level and compare the region details returned for the specific store should be verified with the database$")
    public void iCallTheServiceLocationDataByRegionLevelAndCompareTheRegionDetailsReturnedForTheSpecificStoreShouldBeVerifiedWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList){
            log.info("****** Comparing data for Store Identifier for regions : "+storeIdentifier+" *****************");
            regionData = storeLocatorAPI.locationDataByRegionLevel(storeIdentifier,queryParams);
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            List<Map<String,Object>> expectedRegionData = storeDAO.getRegionsForStore(storeCASIds.get(storeIdentifier),language.getLanguageId());
            if(expectedRegionData.size() > 0) {
                List<Map<String,Object>> expectedRegionCount = storeDAO.getRegionsCountForStore(storeCASIds.get(storeIdentifier),null,language.getLanguageId());
                int regionsCount = Integer.parseInt(expectedRegionCount.get(0).get("REGIONCOUNT").toString());
                assertThat(regionsCount).as("Regions count is wrong").isEqualTo(regionData.getData().size());
            }else{
                assertThat(0).as("Regions count should be 0").isEqualTo(regionData.getData().size());
            }
            String expRegionCode,expRegionName,expCountryCode,expCountryName,expCityName;
            Region region;
            Country country;
            City city;
            for(int regionDataCount=0;regionDataCount<expectedRegionData.size();regionDataCount++) {
                assertThat(regionData.getData()).as("Region Data is not coming from the response").isNotEqualTo(null);
                List<Region> regions = regionData.getData();
                Map<String,Object> expectedRegionMap =  expectedRegionData.get(regionDataCount);
                expRegionCode = expectedRegionMap.get("REGIONCODE").toString();
                expRegionName = expectedRegionMap.get("REGIONNAME").toString();
                expCountryCode = expectedRegionMap.get("COUNTRYCODE").toString();
                expCountryName = expectedRegionMap.get("COUNTRYNAME").toString();
                expCityName = StringHelper.returnEmptyStringIfNull(expectedRegionMap.get("CITY"));
                if(expCityName != null){
                    expCityName = expCityName.trim();
                }
                boolean isRegionExpected = false;
                boolean isCountryExpected = false;
                boolean isCityExpected = false;
                for(int regionCount=0;regionCount<regions.size();regionCount++){
                    region = regions.get(regionCount);
                    if(expRegionCode.equals(region.getCode())) {
                        assertThat(expRegionName).isEqualTo(region.getName());
                        for (int countryCount = 0; countryCount < region.getCountries().size(); countryCount++) {
                            country = region.getCountries().get(countryCount);
                            if(expCountryCode.equals(country.getCode())){
                                assertThat(expCountryName).isEqualTo(country.getName());
                                isCountryExpected = true;
                                if(expCityName == null) {
                                    isCityExpected = true;
                                    break;
                                }
                                for (int cityCount = 0; cityCount < country.getCities().size(); cityCount++) {
                                    city = country.getCities().get(cityCount);
                                   if(expCityName.equals(city.getName())){
                                        assertThat(expCityName).isEqualTo(city.getName());
                                        isCityExpected = true;
                                    }
                                }
                            }
                        }
                        isRegionExpected = true;
                        break;
                    }
                }
                assertThat(isRegionExpected).as("Region "+expRegionName+" is not coming in the API response for storeId :"+storeIdentifier+"").isTrue();
                assertThat(isCountryExpected).as("Country "+expCountryName+" is not coming in the API response for storeId :"+storeIdentifier+"").isTrue();
                assertThat(isCityExpected).as("City "+expCityName+" is not coming in the API response for storeId :"+storeIdentifier+"").isTrue();
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with different attributes \"([^\"]*)\" and attribute values \"([^\"]*)\" and locale \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersWithDifferentAttributesAndAttributeValuesAndLocale(String storeIdentifierKey, String attributeKeys, String attributeValueKeys, String languageKey) throws Throwable {
        log.info("As a Customer I should be able to get all countries with cities for any specific Store which has different attributes \n" +
                " As a Customer I should be able to get all countries with cities for any specific Store which has different attributes");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        String attributes = Props.getMessage(attributeKeys);
        if(attributes.length() > 0) {
            attributesList = Arrays.asList(attributes.split(","));
            attributesValueList = Arrays.asList(Props.getMessage(attributeValueKeys).split(","));
        }
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
    }

    @Then("^I call the service Location Data by Country-level and compare the country details returned for the specific store with the attributes should be verified$")
    public void iCallTheServiceLocationDataByCountryLevelAndCompareTheCountryDetailsReturnedForTheSpecificStoreWithTheAttributesShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList) {
            queryParams = new HashMap<String,String>();
            queryParamsDB = new HashMap<String,String>();
            if (attributesList == null) {
                List<Map<String, Object>> attributes = storeDAO.getStoreAttributes(storeCASIds.get(storeIdentifier));
                if(attributes.size() == 0){
                    log.info("No attributes found for the storeIdentifier: "+storeIdentifier+"");
                    continue;
                }
                int maxAttributes = Integer.parseInt( Props.getMessage("stloc.max.attributes.count").toString());
                if (attributes.size() < maxAttributes) {
                    maxAttributes = attributes.size();
                }
                for (int attributeCount = 0; attributeCount < maxAttributes; attributeCount++) {
                    Map<String, Object> attributesMap = attributes.get(attributeCount);
                    queryParams.put("attr["+attributesMap.get("ATTRIDENTIFIER").toString()+"]", attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                    queryParamsDB.put(attributesMap.get("ATTRIDENTIFIER").toString(),attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                }
            }
            for (String localeName : languageList){
                log.info("****** Comparing data for Store Identifier : " + storeIdentifier + " with language : "+localeName+"  *****************");
                queryParams.put("locale",localeName);
                countryData = storeLocatorAPI.locationDataByCountryLevel(storeIdentifier, queryParams);
                Integer languageId = storeDAO.getLanguageIdByLocaleName(localeName);
                if(languageId == 0 || storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), String.valueOf(languageId)) == 0){
                    Language defLanguage = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    languageId = Integer.parseInt(defLanguage.getLanguageId());
                }
                List<Map<String, Object>> expectedCountryData = storeDAO.getCountriesWithAttributesForStore(storeCASIds.get(storeIdentifier), queryParamsDB, languageId.toString());
                if (expectedCountryData.size() > 0) {
                   List<Map<String, Object>> expectedCountryCount = storeDAO.getCountriesCountWithAttributesForStore(storeCASIds.get(storeIdentifier), queryParamsDB, languageId.toString());

                    int countriesCount = Integer.parseInt(expectedCountryCount.get(0).get("COUNTRYCOUNT").toString());
                    assertThat(countriesCount).as("Countries count is wrong for storeIdentifier:"+storeIdentifier+" with language Name: "+localeName).isEqualTo(countryData.getData().size());
                } else {
                    assertThat(countryData.getData().size()).as("Countries count should be 0 for storeIdentifier:"+storeIdentifier+" with language Name: "+localeName).isEqualTo(0);
                }

                String expCountryCode, expCountryName, expCityName;
                Country country;
                City city;
                for (int countryDataCount = 0; countryDataCount < expectedCountryData.size(); countryDataCount++) {
                    List<Country> actualCountries = countryData.getData();
                    Map<String, Object> expectedCountryMap = expectedCountryData.get(countryDataCount);
                    expCountryCode = expectedCountryMap.get("COUNTRYCODE").toString();
                    expCountryName = expectedCountryMap.get("COUNTRYNAME").toString();
                    expCityName = StringHelper.returnEmptyStringIfNull(expectedCountryMap.get("CITY"));
                    if(expCityName != null){
                        expCityName = expCityName.trim();
                    }
                    boolean isCountryExpected = false;
                    boolean isCityExpected = false;
                    for (int actualCountryCount = 0; actualCountryCount < actualCountries.size(); actualCountryCount++) {
                        country = actualCountries.get(actualCountryCount);
                        if (expCountryCode.equals(country.getCode())) {
                            assertThat(expCountryCode).isEqualTo(country.getCode());
                            assertThat(expCountryName).isEqualTo(country.getName());
                            isCountryExpected = true;
                            if(expCityName == null) {
                                isCityExpected = true;
                                break;
                            }
                            for (int cityCount = 0; cityCount < country.getCities().size(); cityCount++) {
                                city = country.getCities().get(cityCount);
                                if (expCityName.equals(city.getName())) {
                                    assertThat(expCityName).isEqualTo(city.getName());
                                    isCityExpected = true;
                                    break;
                                }
                            }
                            assertThat(isCityExpected).as("City " + expCityName + " is not coming in API repsonse for store " + storeIdentifier + "").isTrue();
                            break;
                        }
                    }
                    assertThat(isCountryExpected).as("Country " + expCountryName + " is not coming in API repsonse for store " + storeIdentifier + "").isTrue();
                }
            }
        }
    }


    @Then("^I call the service Location Data by Region-level with attributes and compare the region details  with countries and cities returned for the specific store should be verified with the database$")
    public void iCallTheServiceLocationDataByRegionLevelWithAttributesAndCompareTheRegionDetailsWithCountriesAndCitiesReturnedForTheSpecificStoreShouldBeVerifiedWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList) {
            queryParams = new HashMap<String,String>();
            queryParamsDB = new HashMap<String,String>();
            if (attributesList == null) {
                List<Map<String, Object>> attributes = storeDAO.getStoreAttributes(storeCASIds.get(storeIdentifier));
                if(attributes.size() == 0){
                    log.info("No attributes found for the storeIdentifier: "+storeIdentifier+"");
                    continue;
                }
                int maxAttributes = Integer.parseInt( Props.getMessage("stloc.max.attributes.count").toString());
                if (attributes.size() < maxAttributes) {
                    maxAttributes = attributes.size();
                }
                for (int attributeCount = 0; attributeCount < maxAttributes; attributeCount++) {
                    Map<String, Object> attributesMap = attributes.get(attributeCount);
                    queryParams.put("attr["+attributesMap.get("ATTRIDENTIFIER").toString()+"]", attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                    queryParamsDB.put(attributesMap.get("ATTRIDENTIFIER").toString(),attributesMap.get("ATTRVALUEIDENTIFIER").toString());
                }
            }
            for (String localeName : languageList){
                log.info("****** Comparing data for Store Identifier for regions : " + storeIdentifier + " with language : "+localeName+"  *****************");
                queryParams.put("locale",localeName);
                regionData = storeLocatorAPI.locationDataByRegionLevel(storeIdentifier, queryParams);
                Integer languageId = storeDAO.getLanguageIdByLocaleName(localeName);
                if(languageId == 0 || storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), String.valueOf(languageId)) == 0){
                    Language defLanguage = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    languageId = Integer.parseInt(defLanguage.getLanguageId());
                }
                List<Map<String, Object>> expectedRegionData = storeDAO.getRegionsWithAttributesForStore(storeCASIds.get(storeIdentifier),null,queryParamsDB, languageId.toString());
                if(expectedRegionData.size() > 0) {
                    List<Map<String,Object>> expectedRegionCount = storeDAO.getRegionsCountWithAttributesForStore(storeCASIds.get(storeIdentifier),null,queryParamsDB, languageId.toString());
                    int regionsCount = Integer.parseInt(expectedRegionCount.get(0).get("REGIONCOUNT").toString());
                    assertThat(regionsCount).as("Regions count is wrong").isEqualTo(regionData.getData().size());
                }else{
                    assertThat(0).as("Regions count should be 0").isEqualTo(regionData.getData().size());
                }
                String expRegionCode,expRegionName,expCountryCode,expCountryName,expCityName;
                Region region;
                Country country;
                City city;
                for(int regionDataCount=0;regionDataCount<expectedRegionData.size();regionDataCount++) {
                    assertThat(regionData.getData()).as("Region Data is not coming from the response").isNotEqualTo(null);
                    List<Region> regions = regionData.getData();
                    Map<String,Object> expectedRegionMap =  expectedRegionData.get(regionDataCount);
                    expRegionCode = expectedRegionMap.get("REGIONCODE").toString();
                    expRegionName = expectedRegionMap.get("REGIONNAME").toString();
                    expCountryCode = expectedRegionMap.get("COUNTRYCODE").toString();
                    expCountryName = expectedRegionMap.get("COUNTRYNAME").toString();
                    expCityName = StringHelper.returnEmptyStringIfNull(expectedRegionMap.get("CITY"));
                    if(expCityName != null){
                        expCityName = expCityName.trim();
                    }
                    boolean isRegionExpected = false;
                    boolean isCountryExpected = false;
                    boolean isCityExpected = false;
                    for(int regionCount=0;regionCount<regions.size();regionCount++){
                        region = regions.get(regionCount);
                        if(expRegionCode.equals(region.getCode())) {
                            assertThat(expRegionCode).isEqualTo(region.getCode());
                            assertThat(expRegionName).isEqualTo(region.getName());
                            for (int countryCount = 0; countryCount < region.getCountries().size(); countryCount++) {
                                country = region.getCountries().get(countryCount);
                                if(expCountryCode.equals(country.getCode())){
                                    assertThat(expCountryCode).isEqualTo(country.getCode());
                                    assertThat(expCountryName).isEqualTo(country.getName());
                                    isCountryExpected = true;
                                    if(expCityName == null) {
                                        isCityExpected = true;
                                        break;
                                    }
                                    for (int cityCount = 0; cityCount < country.getCities().size(); cityCount++) {
                                        city = country.getCities().get(cityCount);
                                       if(expCityName.equals(city.getName())){
                                            assertThat(expCityName).isEqualTo(city.getName());
                                            isCityExpected = true;
                                        }
                                    }
                                }
                            }
                            isRegionExpected = true;
                            break;
                        }
                    }
                    assertThat(isRegionExpected).as("Region "+expRegionName+" is not coming in the API response for storeId :"+storeIdentifier+"").isTrue();
                    assertThat(isCountryExpected).as("Country "+expCountryName+" is not coming in the API response for storeId :"+storeIdentifier+"").isTrue();
                    assertThat(isCityExpected).as("City "+expCityName+" is not coming in the API response for storeId :"+storeIdentifier+"").isTrue();
                }
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with query params locale \"([^\"]*)\" and langId \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersWithQueryParamsLocaleAndLangId(String storeIdentifierKey, String languageKey, String languageIdKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
        languageIdList = Arrays.asList(Props.getMessage(languageIdKey).split(","));
    }

    @Then("^I call the service Location Data by Region-level with query params locale and langId and compare the region details returned for the specific store should be verified with the database$")
    public void iCallTheServiceLocationDataByRegionLevelWithQueryParamsLocaleAndLangIdAndCompareTheRegionDetailsReturnedForTheSpecificStoreShouldBeVerifiedWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList) {
            queryParams = new HashMap<String,String>();
            queryParamsDB = new HashMap<String,String>();
            for (String localeName : languageList){
                for(String expLangId:languageIdList) {
                    log.info("****** Comparing data for Store Identifier for regions : " + storeIdentifier + " with language : " + localeName + "  *****************");
                    queryParams.put("locale", localeName);
                    queryParams.put("langId", expLangId);
                    regionData = storeLocatorAPI.locationDataByRegionLevel(storeIdentifier, queryParams);
                    String languageId = null;
                    if (storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), expLangId) == 0) {
                        log.info("language Id inside");
                        Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                        languageId = language.getLanguageId();
                    }else{
                        languageId = expLangId;
                    }
                    List<Map<String,Object>> expectedRegionData = storeDAO.getRegionsForStore(storeCASIds.get(storeIdentifier),languageId);
                    if(expectedRegionData.size() > 0) {
                        List<Map<String,Object>> expectedRegionCount = storeDAO.getRegionsCountForStore(storeCASIds.get(storeIdentifier),null,languageId);
                        int regionsCount = Integer.parseInt(expectedRegionCount.get(0).get("REGIONCOUNT").toString());
                        assertThat(regionsCount).as("Regions count is wrong").isEqualTo(regionData.getData().size());
                    }else{
                        assertThat(0).as("Regions count should be 0").isEqualTo(regionData.getData().size());
                    }
                    String expRegionCode, expRegionName, expCountryCode, expCountryName, expCityName;
                    Region region;
                    Country country;
                    City city;
                    for (int regionDataCount = 0; regionDataCount < expectedRegionData.size(); regionDataCount++) {
                        assertThat(regionData.getData()).as("Region Data is not coming from the response").isNotEqualTo(null);
                        List<Region> regions = regionData.getData();
                        Map<String, Object> expectedRegionMap = expectedRegionData.get(regionDataCount);
                        expRegionCode = expectedRegionMap.get("REGIONCODE").toString();
                        expRegionName = expectedRegionMap.get("REGIONNAME").toString();
                        expCountryCode = expectedRegionMap.get("COUNTRYCODE").toString();
                        expCountryName = expectedRegionMap.get("COUNTRYNAME").toString();
                        expCityName = StringHelper.returnEmptyStringIfNull(expectedRegionMap.get("CITY"));
                        if(expCityName != null){
                            expCityName = expCityName.trim();
                        }
                        boolean isRegionExpected = false;
                        boolean isCountryExpected = false;
                        boolean isCityExpected = false;
                        for (int regionCount = 0; regionCount < regions.size(); regionCount++) {
                            region = regions.get(regionCount);
                            if (expRegionCode.equals(region.getCode())) {
                                assertThat(expRegionCode).isEqualTo(region.getCode());
                                assertThat(expRegionName).isEqualTo(region.getName());
                                for (int countryCount = 0; countryCount < region.getCountries().size(); countryCount++) {
                                    country = region.getCountries().get(countryCount);
                                    if (expCountryCode.equals(country.getCode())) {
                                        assertThat(expCountryCode).isEqualTo(country.getCode());
                                        assertThat(expCountryName).isEqualTo(country.getName());
                                        isCountryExpected = true;
                                        if(expCityName == null) {
                                            isCityExpected = true;
                                            break;
                                        }
                                        for (int cityCount = 0; cityCount < country.getCities().size(); cityCount++) {
                                            city = country.getCities().get(cityCount);
                                            if (expCityName.equals(city.getName())) {
                                                assertThat(expCityName).isEqualTo(city.getName());
                                                isCityExpected = true;
                                            }
                                        }
                                    }
                                }
                                isRegionExpected = true;
                                break;
                            }
                        }
                        assertThat(isRegionExpected).as("Region " + expRegionName + " is not coming in the API response for storeId :" + storeIdentifier + "").isTrue();
                        assertThat(isCountryExpected).as("Country " + expCountryName + " is not coming in the API response for storeId :" + storeIdentifier + "").isTrue();
                        assertThat(isCityExpected).as("City " + expCityName + " is not coming in the API response for storeId :" + storeIdentifier + "").isTrue();
                    }
                }
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with query parameters locale \"([^\"]*)\" and langId \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersWithQueryParametersLocaleAndLangId(String storeIdentifierKey, String languageKey, String languageIdKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
        languageIdList = Arrays.asList(Props.getMessage(languageIdKey).split(","));
    }

    @Then("^I call the service Location Data by Country-level with query parameters locale and langId and compare the country details returned for the specific store should be verified with the database$")
    public void iCallTheServiceLocationDataByCountryLevelWithQueryParametersLocaleAndLangIdAndCompareTheCountryDetailsReturnedForTheSpecificStoreShouldBeVerifiedWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList) {
            queryParams = new HashMap<String,String>();
            queryParamsDB = new HashMap<String,String>();
            for (String localeName : languageList){
                for(String expLangId:languageIdList) {
                    log.info("****** Comparing data for Store Identifier : " + storeIdentifier + " with language : " + localeName + "  *****************");
                    queryParams.put("locale", localeName);
                    queryParams.put("langId", expLangId);
                    countryData = storeLocatorAPI.locationDataByCountryLevel(storeIdentifier, queryParams);
                    String languageId = null;
                    if (storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), expLangId) == 0) {
                        log.info("language Id inside");
                        Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                        languageId = language.getLanguageId();
                    }else{
                        languageId = expLangId;
                    }
                    List<Map<String,Object>> expectedCountryData = storeDAO.getCountriesForStore(storeCASIds.get(storeIdentifier),languageId);
                    if(expectedCountryData.size() > 0) {
                        List<Map<String,Object>> expectedCountryCount = storeDAO.getTotalCountriesForStore(storeCASIds.get(storeIdentifier),languageId);
                        int countriesCount = Integer.parseInt(expectedCountryCount.get(0).get("COUNTRYCOUNT").toString());
                        assertThat(countriesCount).as("Countries count is wrong").isEqualTo(countryData.getData().size());
                    }else{
                        assertThat(countryData.getData().size()).as("Countries count should be 0").isEqualTo(0);
                    }

                    String expCountryCode, expCountryName, expCityName;
                    Country country;
                    City city;
                    for (int countryDataCount = 0; countryDataCount < expectedCountryData.size(); countryDataCount++) {
                        List<Country> actualCountries = countryData.getData();
                        Map<String, Object> expectedCountryMap = expectedCountryData.get(countryDataCount);
                        expCountryCode = expectedCountryMap.get("COUNTRYCODE").toString();
                        expCountryName = expectedCountryMap.get("COUNTRYNAME").toString();
                        expCityName = StringHelper.returnEmptyStringIfNull(expectedCountryMap.get("CITY"));
                        if(expCityName != null){
                            expCityName = expCityName.trim();
                        }
                        boolean isCountryExpected = false;
                        boolean isCityExpected = false;
                        for (int actualCountryCount = 0; actualCountryCount < actualCountries.size(); actualCountryCount++) {
                            country = actualCountries.get(actualCountryCount);
                            if (expCountryCode.equals(country.getCode())) {
                                assertThat(expCountryCode).isEqualTo(country.getCode());
                                assertThat(expCountryName).isEqualTo(country.getName());
                                isCountryExpected = true;
                                if(expCityName == null) {
                                    isCityExpected = true;
                                    break;
                                }
                                for (int cityCount = 0; cityCount < country.getCities().size(); cityCount++) {
                                    city = country.getCities().get(cityCount);
                                     if (expCityName.equals(city.getName())) {
                                        assertThat(expCityName).isEqualTo(city.getName());
                                        isCityExpected = true;
                                        break;
                                    }
                                }
                                assertThat(isCityExpected).as("City " + expCityName + " is not coming in API repsonse for store " + storeIdentifier + "").isTrue();
                                break;
                            }
                        }
                        assertThat(isCountryExpected).as("Country " + expCountryName + " is not coming in API repsonse for store " + storeIdentifier + "").isTrue();
                    }
                }
            }
        }
    }
}
