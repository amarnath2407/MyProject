package com.salmon.test.step_definitions.api.storelocator;

import com.jayway.restassured.response.Response;
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

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 29/07/2016.
 */
public class LocationDataByCountryOrRegionWCORSteps extends BaseStepDefinition{

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private Response response;
    private Map<String,String> queryParams = null;
    private StoreLocatorAPI storeLocatorAPI;
    private String regionCode;
    private CountryData countryData;
    private CountryCitiesData countryCityData;
    private RegionCountriesData regionCountriesData;
    private StoreDAO storeDAO;
    private List<String> storeIdentifierList;
    private List<String> countryList;
    private List<String> regionList;
    private List<String> attributesList = null;
    private List<String> attributesValueList = null;
    private List<String> languageList;
    private Map<String,String> queryParamsDB;

    public LocationDataByCountryOrRegionWCORSteps(StoreLocatorAPI storeLocatorAPI,StoreDAO storeDAO) {
        super(storeDAO);
        this.storeLocatorAPI = storeLocatorAPI;
        this.storeDAO = storeDAO;
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersWith(String storeIdentifierKey, String countriesKey) throws Throwable {
        log.info("As a Customer I should be able to get all cities in a country for a specific Store");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        countryList =  Arrays.asList(Props.getMessage(countriesKey).split(","));
    }

    @Then("^I call the service Location Data by Country-level with country code and compare the country details returned for the specific store withing the country should be verified with the database$")
    public void iCallTheServiceLocationDataByCountryLevelWithCountryCodeAndCompareTheCountryDetailsReturnedForTheSpecificStoreWithingTheCountryShouldBeVerifiedWithTheDatabase() throws Throwable {

        for(String storeIdentifier : storeIdentifierList){
            for(String countryCode : countryList){
                log.info("****** Comparing data for Store Identifier : "+storeIdentifier+" With Country code: "+countryCode+"  *****************");
                countryCityData = storeLocatorAPI.locationDataByCountryLevelWithCountry(storeIdentifier, countryCode , queryParams);
                Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                List<Map<String,Object>> expectedCountryData = storeDAO.getCitiesByCountryForStore(storeCASIds.get(storeIdentifier),countryCode,language.getLanguageId());
                Country country = countryCityData.getData();
                if(expectedCountryData.size() > 0) {
                    assertThat(expectedCountryData.size()).as("Cities count is wrong").isEqualTo(country.getCities().size());
                }else{
                    assertThat(countryCityData.getData().getCities()).as("Cities count should be 0").isNull();
                }
                String expCountryCode,expCountryName,expCityName;
                City city;
                for(int countryDataCount=0;countryDataCount<expectedCountryData.size();countryDataCount++){
                    List<City> actualCities = country.getCities();
                    Map<String,Object> expectedCountryMap =  expectedCountryData.get(countryDataCount);
                    expCountryCode = expectedCountryMap.get("COUNTRYCODE").toString();
                    expCountryName = expectedCountryMap.get("COUNTRYNAME").toString();
                    expCityName = StringHelper.returnEmptyStringIfNull(expectedCountryMap.get("CITY"));
                    if(expCityName != null){
                        expCityName = expCityName.trim();
                    }
                    boolean isCityExpected = false;
                    assertThat(expCountryCode).isEqualTo(country.getCode());
                    assertThat(expCountryName).isEqualTo(country.getName());
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
                    assertThat(isCityExpected).as("City " + expCityName + " is not coming in API repsonse for store " + storeIdentifier + " and Country: "+countryCode).isTrue();
                }
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with region code \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersWithRegionCode(String storeIdentifierKey, String regionsKey) throws Throwable {
        log.info("As a Customer I should be able to get all countries with cities on a region for any specific Store");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        regionList =  Arrays.asList(Props.getMessage(regionsKey).split(","));
    }

    @Then("^I call the service Location Data by Region-level with region code and compare the country details returned for the specific store withing the region should be verified with the database$")
    public void iCallTheServiceLocationDataByRegionLevelWithRegionCodeAndCompareTheCountryDetailsReturnedForTheSpecificStoreWithingTheRegionShouldBeVerifiedWithTheDatabase() throws Throwable {
        for(String storeIdentifier : storeIdentifierList){
            for(String regionCode : regionList){
                log.info("****** Comparing data for Store Identifier: "+storeIdentifier+" With Region Code "+regionCode+"  *****************");
                regionCountriesData = storeLocatorAPI.locationDataByRegionLevelWithRegion(storeIdentifier,regionCode,queryParams);
                Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                List<Map<String,Object>> expectedRegionData = storeDAO.getCountriesByRegionForStore(storeCASIds.get(storeIdentifier),regionCode,language.getLanguageId());
                if(expectedRegionData.size() > 0) {
                    List<Map<String,Object>> expectedRegionCount = storeDAO.getRegionsCountForStore(storeCASIds.get(storeIdentifier),regionCode,language.getLanguageId());
                    int regionsCount = Integer.parseInt(expectedRegionCount.get(0).get("COUNTRYCOUNT").toString());
                    assertThat(regionsCount).as("Region countries count is wrong").isEqualTo(regionsCount);
                }else{
                    assertThat(regionCountriesData.getData().getCode()).as("Regions countries count should be 0").isNull();
                }
                String expRegionCode,expRegionName,expCountryCode,expCountryName,expCityName;
                Region region;
                Country country;
                City city;
                for(int regionDataCount=0;regionDataCount<expectedRegionData.size();regionDataCount++) {
                    assertThat(regionCountriesData.getData()).as("Region Data is not coming from the response").isNotEqualTo(null);
                    List<Country> countries = regionCountriesData.getData().getCountries();
                    Map<String,Object> expectedRegionMap =  expectedRegionData.get(regionDataCount);
                    expRegionCode = expectedRegionMap.get("REGIONCODE").toString();
                    expRegionName = expectedRegionMap.get("REGIONNAME").toString();
                    expCountryCode = expectedRegionMap.get("COUNTRYCODE").toString();
                    expCountryName = expectedRegionMap.get("COUNTRYNAME").toString();
                    expCityName = StringHelper.returnEmptyStringIfNull(expectedRegionMap.get("CITY"));
                    if(expCityName != null){
                        expCityName = expCityName.trim();
                    }
                    boolean isCountryExpected = false;
                    boolean isCityExpected = false;
                    region = regionCountriesData.getData();
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
                    assertThat(isCountryExpected).as("Country "+expCountryName+" is not coming in the API response for storeIdentifier :"+ storeIdentifier +"").isTrue();
                    assertThat(isCityExpected).as("City "+expCityName+" is not coming in the API response for storeIdentifier :"+ storeIdentifier +"").isTrue();
                }
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with \"([^\"]*)\" different attributes \"([^\"]*)\" attribute values \"([^\"]*)\" and locales \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersWithDifferentAttributesAttributeValuesAndLocales(String storeIdentifierKey, String countriesKey, String attributeKeys, String attributeValueKeys, String languageKey) throws Throwable {
        log.info("As a Customer I should be able to get all countries with cities for any specific Store within the country which has different attributes");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        countryList =  Arrays.asList(Props.getMessage(countriesKey).split(","));
        String attributes = Props.getMessage(attributeKeys);
        if(attributes.length() > 0) {
            attributesList = Arrays.asList(attributes.split(","));
            attributesValueList = Arrays.asList(Props.getMessage(attributeValueKeys).split(","));
        }
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
    }

    @Then("^I call the service Location Data by Country-level with country code and compare the country details returned for the specific store withing the country with the attributes should be verified$")
    public void iCallTheServiceLLocationDataByCountryLevelWithCountryCodeAndCompareTheCountryDetailsReturnedForTheSpecificStoreWithingTheCountryWithTheAttributesShouldBeVerified() throws Throwable {
        for(String storeIdentifier:storeIdentifierList) {
            queryParams = new HashMap<String,String>();
            queryParamsDB = new HashMap<String,String>();
            if (attributesList == null) {
                List<Map<String, Object>> attributes = storeDAO.getStoreAttributes(storeCASIds.get(storeIdentifier));
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
            for(String countryCode : countryList) {
                for (String localeName : languageList) {
                    log.info("****** Comparing data for Store Identifier : " + storeIdentifier + " with country code:"+countryCode+"  with language : " + localeName + "  *****************");
                    queryParams.put("locale", localeName);
                    countryCityData = storeLocatorAPI.locationDataByCountryLevelWithCountry(storeIdentifier,countryCode,queryParams);
                    Integer languageId = storeDAO.getLanguageIdByLocaleName(localeName);
                    if (languageId == 0 || storeDAO.checkLanguageSupportedForStore(storeIds.get(storeIdentifier), String.valueOf(languageId)) == 0){
                        Language defLanguage = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                        languageId = Integer.parseInt(defLanguage.getLanguageId());
                    }
                    List<Map<String, Object>> expectedCountryData = storeDAO.getCitiesByCountryWithAttributesForStore(storeCASIds.get(storeIdentifier), countryCode, queryParamsDB, languageId.toString());
                    Country country = countryCityData.getData();
                    if(expectedCountryData.size() > 0) {
                        assertThat(expectedCountryData.size()).as("Cities count is wrong").isEqualTo(country.getCities().size());
                    }else{
                        assertThat(countryCityData.getData().getCode()).as("Cities count should be 0").isNull();
                    }
                    String expCountryCode,expCountryName,expCityName;
                    City city;
                    for(int countryDataCount=0;countryDataCount<expectedCountryData.size();countryDataCount++){
                        List<City> actualCities = country.getCities();
                        Map<String,Object> expectedCountryMap =  expectedCountryData.get(countryDataCount);
                        expCountryCode = expectedCountryMap.get("COUNTRYCODE").toString();
                        expCountryName = expectedCountryMap.get("COUNTRYNAME").toString();
                        expCityName = StringHelper.returnEmptyStringIfNull(expectedCountryMap.get("CITY"));
                        if(expCityName != null){
                            expCityName = expCityName.trim();
                        }
                        boolean isCityExpected = false;
                        assertThat(expCountryCode).isEqualTo(country.getCode());
                        assertThat(expCountryName).isEqualTo(country.getName());
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
                        assertThat(isCityExpected).as("City " + expCityName + " is not coming in API repsonse for store " + storeIdentifier + " and Country: "+countryCode).isTrue();
                    }
                }
            }
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with region Code \"([^\"]*)\" different attributes \"([^\"]*)\" attribute values \"([^\"]*)\" and locales \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersWithRegionCodeDifferentAttributesAttributeValuesAndLocales(String storeIdentifierKey, String regionKey, String attributeKeys, String attributeValueKeys, String languageKey) throws Throwable {
        log.info("As a Customer I should be able to get all countries with cities for any specific Store within the country which has different attributes");
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        regionList =  Arrays.asList(Props.getMessage(regionKey).split(","));
        String attributes = Props.getMessage(attributeKeys);
        if(attributes.length() > 0) {
            attributesList = Arrays.asList(attributes.split(","));
            attributesValueList = Arrays.asList(Props.getMessage(attributeValueKeys).split(","));
        }
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
    }

    @Then("^I call the service Location Data by Region-level with region code with attributes and compare the region details  with countries and cities returned for the specific store should be verified with the database$")
    public void iCallTheServiceLocationDataByRegionLevelWithRegionCodeWithAttributesAndCompareTheRegionDetailsWithCountriesAndCitiesReturnedForTheSpecificStoreShouldBeVerifiedWithTheDatabase() throws Throwable {
        for(String storeIdentifier:storeIdentifierList) {
            queryParams = new HashMap<String,String>();
            queryParamsDB = new HashMap<String,String>();
            if (attributesList == null) {
                List<Map<String, Object>> attributes = storeDAO.getStoreAttributes(storeCASIds.get(storeIdentifier));
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
                for (String localeName : languageList) {
                    log.info("****** Comparing data for Store Identifier for regions : " + storeIdentifier + " with region Code:"+regionCode+" with language : " + localeName + "  *****************");
                    queryParams.put("locale", localeName);
                    regionCountriesData = storeLocatorAPI.locationDataByRegionLevelWithRegion(storeIdentifier, regionCode, queryParams);
                    Integer languageId = storeDAO.getLanguageIdByLocaleName(localeName);
                    if (languageId == 0 || storeDAO.checkLanguageSupportedForStore(storeIds.get(storeIdentifier), String.valueOf(languageId)) == 0){
                        Language defLanguage = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                        languageId = Integer.parseInt(defLanguage.getLanguageId());
                    }
                    List<Map<String, Object>> expectedRegionData = storeDAO.getRegionsWithAttributesForStore(storeCASIds.get(storeIdentifier), regionCode, queryParamsDB, languageId.toString());
                    if(expectedRegionData.size() > 0) {
                        List<Map<String,Object>> expectedRegionCount = storeDAO.getRegionsCountWithAttributesForStore(storeCASIds.get(storeIdentifier),regionCode, queryParamsDB, languageId.toString());
                        int regionsCount = Integer.parseInt(expectedRegionCount.get(0).get("COUNTRYCOUNT").toString());
                        assertThat(regionsCount).as("Region countries count is wrong").isEqualTo(regionsCount);
                    }else{
                        assertThat(regionCountriesData.getData().getCode()).as("Region countries count should be 0").isNull();
                    }
                    String expRegionCode,expRegionName,expCountryCode,expCountryName,expCityName;
                    Region region;
                    Country country;
                    City city;
                    for(int regionDataCount=0;regionDataCount<expectedRegionData.size();regionDataCount++) {
                        assertThat(regionCountriesData.getData()).as("Region Data is not coming from the response").isNotEqualTo(null);
                        List<Country> countries = regionCountriesData.getData().getCountries();
                        Map<String,Object> expectedRegionMap =  expectedRegionData.get(regionDataCount);
                        expRegionCode = expectedRegionMap.get("REGIONCODE").toString();
                        expRegionName = expectedRegionMap.get("REGIONNAME").toString();
                        expCountryCode = expectedRegionMap.get("COUNTRYCODE").toString();
                        expCountryName = expectedRegionMap.get("COUNTRYNAME").toString();
                        expCityName = StringHelper.returnEmptyStringIfNull(expectedRegionMap.get("CITY"));
                        if(expCityName != null){
                            expCityName = expCityName.trim();
                        }
                        boolean isCountryExpected = false;
                        boolean isCityExpected = false;
                        region = regionCountriesData.getData();
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
                        assertThat(isCountryExpected).as("Country "+expCountryName+" is not coming in the API response for storeIdentifier :"+ storeIdentifier +"").isTrue();
                        assertThat(isCityExpected).as("City "+expCityName+" is not coming in the API response for storeIdentifier :"+storeIdentifier +"").isTrue();
                    }
                }
            }
        }
    }
}
