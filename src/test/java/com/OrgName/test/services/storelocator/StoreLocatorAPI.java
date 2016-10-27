package com.salmon.test.services.storelocator;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import com.salmon.test.enums.ServiceEndPoints;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.ApiHelper;
import com.salmon.test.framework.helpers.Props;
import com.salmon.test.framework.helpers.utils.StringHelper;
import com.salmon.test.models.api.storelocator.*;
import com.salmon.test.services.Tokens;
import com.salmon.test.sql.store.StoreDAO;
import com.salmon.test.sql.storelocator.StoreLocatorDAO;
import com.salmon.test.step_definitions.api.loginhelper.LoginHelper;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 07/07/2016.
 */
public class StoreLocatorAPI extends ApiHelper {

    private static Logger logger = LoggerFactory.getLogger(StoreLocatorAPI.class);
    private StoreLocatorDAO storeLocatorDAO;
    private StoreDAO storeDAO;

    public StoreLocatorAPI(StoreLocatorDAO storeLocatorDAO,StoreDAO storeDAO) {
        this.storeLocatorDAO = storeLocatorDAO;
        this.storeDAO =storeDAO;
    }
    /**
     * @Description This method is to call Store locator by Location service with query parameters passed in
     * @param queryParams
     * @return response object which is in json/xml format depends on the query parameters
     */
    public Response findGeoNodeByGeoLocation(String storeId, Map<String,String> queryParams){
        Response response = null;
        try {
             String url = ServiceEndPoints.STORE_BY_LOCATION.getUrl().replace("{storeId}",storeId);
            ChangeProtocol("http");
            response = getResponse(url,queryParams);
            response.then().spec(getResponseSpecBuilder());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return response;
    }

    /**
     * @Description: This method is to call Store locator by Store Unique Id service with unique store Id
     * @param uniqueStoreId
     * @return StoreLocator object with the response from the API
     */
     public StoreLocator findByStoreUniqueId(String storeId,String uniqueStoreId){
        Response response = null;
        StoreLocator storeLocator = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.STORE_BY_ID.getUrl().replace("{storeId}",storeId)
                    .replace("{uniqueStoreId}",uniqueStoreId);
            ChangeProtocol("http");
            response =givenConfig().log().all()
                    .when().get(url);
            response.then().spec(getResponseSpecBuilder());
            storeLocator = gson.fromJson(response.asString(), StoreLocator.class);
            //logger.info("response from findByStoreUniqueId:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return storeLocator;
    }

    /**
     * @Description This method is to call Location Data - Country-level api to get all countries including cities with the store id
     * @param storeId
     * @param queryParam
     * @return Location Data object with the response from the API
     */
    public CountryData locationDataByCountryLevel(String storeId, Map<String,String> queryParam){
        Response response = null;
        CountryData countryData = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.LOCATION_DATA_BY_COUNTRY_LEVEL.getUrl().replace("{storeId}",storeId);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParam);
            response.then().spec(getResponseSpecBuilder());
            countryData = gson.fromJson(response.asString(), CountryData.class);
            //logger.info("response from locationDataByCountryLevel:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return countryData;
    }

    /**
     * @Description This method is to call Location Data - Country-level with Country code api to get all countries including cities with the store id
     * @param storeId
     * @param countryCode
     * @param queryParam
     * @return Location Data object with the response from the API
     */
    public CountryCitiesData locationDataByCountryLevelWithCountry(String storeId, String countryCode, Map<String,String> queryParam){
        Response response = null;
        CountryCitiesData countryData = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.LOCATION_DATA_BY_COUNTRY_LEVEL_WITH_COUNTRY.getUrl()
                    .replace("{storeId}",storeId).replace("{countryCode}",countryCode);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParam);
            response.then().spec(getResponseSpecBuilder());
            countryData = gson.fromJson(response.asString(), CountryCitiesData.class);
            //logger.info("response from locationDataByCountryLevelWithCountry:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return countryData;
    }

    /**
     * @Description This method is to call Location Data - Region-level api to get all regions with countries and cities with the store id
     * @param storeId
     * @param queryParam
     * @return Location Data object with the response from the API
     */
    public RegionData locationDataByRegionLevel(String storeId, Map<String,String> queryParam){
        Response response = null;
        RegionData regionData = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.LOCATION_DATA_BY_REGION_LEVEL.getUrl().replace("{storeId}",storeId);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParam);
            response.then().spec(getResponseSpecBuilder());
            regionData = gson.fromJson(response.asString(), RegionData.class);
            //logger.info("response from locationDataByRegionLevel:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return regionData;
    }

    /**
     * @Description This method is to call Location Data - Region-level with region code api to get all countries including cities with the store id
     * @param storeId
     * @param regionCode
     * @param queryParams
     * @return Region Data object with the response from the API
     */
    public RegionCountriesData locationDataByRegionLevelWithRegion(String storeId, String regionCode, Map<String,String> queryParams){
        Response response = null;
        RegionCountriesData regionCountriesData = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.LOCATION_DATA_BY_REGION_LEVEL_WITH_REGION.getUrl()
                    .replace("{storeId}",storeId).replace("{regionCode}",regionCode);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParams);
            response.then().spec(getResponseSpecBuilder());
            regionCountriesData = gson.fromJson(response.asString(), RegionCountriesData.class);
            //logger.info("response from locationDataByRegionLevelWithRegion:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return regionCountriesData;
    }

    /**
     * This is to search for Physical store information
     * @param storeId
     * @param queryParams
     * @return Store locator object with the service returned data
     */
    public StoreLocator searchPhysicalStore(String storeId, Map<String,String> queryParams){
        Response response = null;
        StoreLocator storeLocator = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.SEARCH_PHYSICAL_STORE.getUrl()
                    .replace("{storeId}",storeId);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParams);
            response.then().spec(getResponseSpecBuilder());
            storeLocator = gson.fromJson(response.asString(), StoreLocator.class);
           // logger.info("response from searchPhysicalStore:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return storeLocator;
    }

    /**
     * Description - This is to search physical store information with latitude and longitude
     * @param storeId Store indentifier
     * @param queryParams Latitude and Longitude for store search
     * @return StoreLocator object with the response from the API
     */
    public StoreLocator searchPhysicalStoreWithLatitudeAndLongitude(String storeId,String latitude,String longitude, Map<String, String> queryParams){
        Response response = null;
        StoreLocator storeLocator = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.GET_PHYSICAL_STORE_WITH_LATITUDE_AND_LONGITUDE.getUrl()
                    .replace("{storeId}",storeId).replace("{latitude}",latitude).replace("{longitude}",longitude);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParams);
            response.then().spec(getResponseSpecBuilder());
            storeLocator = gson.fromJson(response.asString(), StoreLocator.class);
            //logger.info("response from searchPhysicalStoreWithLatitudeAndLongitude:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return storeLocator;
    }

    /**
     * @description This is to get the Physical Store data
     * @param storeId (StoreEnt Id)
     * @param boutiqueId (Store locator Id )
     * @param queryParams (Language)
     * @return Store locator object with the service returned data
     */
    public PhysicalStoreLocatorData getPhysicalStore(String storeId,String boutiqueId, Map<String,String> queryParams){
        Response response = null;
        PhysicalStoreLocatorData physicalStoreLocatorData = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.GET_PHYSICAL_STORE.getUrl()
                    .replace("{storeId}",storeId).replace("{boutiqueIdentifier}",boutiqueId);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParams,true);
            response.then().spec(getResponseSpecBuilder());
            physicalStoreLocatorData = gson.fromJson(response.asString(), PhysicalStoreLocatorData.class);
           // logger.info("response from getPhysicalStore:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return physicalStoreLocatorData;
    }

    /**
     * @description This is to get the Physical Store data
     * @param storeId (StoreEnt Id)
     * @param boutiqueId (Store locator Id )
     * @param queryParams (Language)
     * @param responseCode ( Expected Response Code)
     * @return Store locator object with the service returned data
     */
    public PhysicalStoreLocatorData getPhysicalStore(String storeId,String boutiqueId, Map<String,String> queryParams,String responseCode){
        Response response = null;
        PhysicalStoreLocatorData physicalStoreLocatorData = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.GET_PHYSICAL_STORE.getUrl()
                    .replace("{storeId}",storeId).replace("{boutiqueIdentifier}",boutiqueId);
            logger.info("url:"+url);
            ChangeProtocol("http");
            response = getResponse(url,queryParams);
            response.then().spec(responseSpecBuilder(responseCode));
            physicalStoreLocatorData = gson.fromJson(response.asString(), PhysicalStoreLocatorData.class);
            // logger.info("response from getPhysicalStore:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return physicalStoreLocatorData;
    }

    /**
     * This is to get the list of stores from out of box api with latitude and longitude with radius
     * @param storeId (Store identifier)
     * @return ( StoreListAPIData object )
     */
    public StoreListAPIData findStores(String storeId){
        Response response = null;
        StoreListAPIData storeListAPIData = null;
        Gson gson = new Gson();
        Map<String,String> queryParams = new HashMap<String,String>();
        try {
            String url = ServiceEndPoints.STORE_BY_LATITUDE_LONGITUDE.getUrl().replace("{storeId}",storeId);
            logger.info("url:"+url);
            ChangeProtocol("http");
            queryParams.put("radius","2000");
            queryParams.put("siteLevelStoreSearch","false");
            response = getResponse(url,queryParams);
            response.then().spec(getResponseSpecBuilder());
            storeListAPIData = gson.fromJson(response.asString(), StoreListAPIData.class);
           // logger.info("response from getPhysicalStore:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return storeListAPIData;
    }

    /**
     * Gee the Store Locator Information from database and prepare the Store Locator Object
     * @param storeCASId Store catalog asset Id
     * @param languageId language Id
     * @param noofStores No of store you want to return
     * @param isActive is active ( 1 = active ,  0 = Inactive )
     * @return StoreLocator object
     */
    public StoreLocator getStoreLocatorDataFromDB(String storeCASId,String languageId,String noofStores, String isActive,String queryParameter,Map<String,String> queryParamterValues) throws Exception{

        List<Map<String,Object>> physicalStoresList = storeLocatorDAO.getPhysicalStoresForSite(storeCASId, noofStores, isActive, queryParameter, queryParamterValues);
        List<Map<String,Object>> physicalStoresInformationList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresContactList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresAttributesList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresAttributeValuesList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresOpeningTimesList =  new ArrayList<Map<String,Object>>();

        if(physicalStoresList.size()>0){
            physicalStoresInformationList = storeLocatorDAO.getPhysicalStoreInformation(storeCASId,languageId,isActive);
            physicalStoresContactList = storeLocatorDAO.getPhysicalStoreContactInformation(storeCASId,languageId,isActive);
            physicalStoresAttributesList = storeLocatorDAO.getPhysicalStoreAttributes(storeCASId,languageId,isActive);
            physicalStoresAttributeValuesList = storeLocatorDAO.getPhysicalStoreAttributeValues(storeCASId,languageId,isActive);
            physicalStoresOpeningTimesList = storeLocatorDAO.getPhysicalStoreOpeningHours(storeCASId,isActive);
        }
        String stloc_id = null;
        StoreLocator storeLocator = new StoreLocator();
        StorelocatorItem storelocatorItem = null;
        List<StorelocatorItem> storelocatorItemList = new ArrayList<StorelocatorItem>();
        Address address = null;
        GeoData spatialData = null;
        ContactDetails contactDetails = null;
        List<ContactDetails> contactDetailsList = null;
        Attribute attribute = null;
        List<Attribute> attributeList = null;
        AttributeValues attributeValues = null;
        List<AttributeValues> attributeValuesList = null;
        String attributeId = null;
        ContactsType contactsType = null;
        ContactsName contactsName = null;
        List<OpeningTimeSlots> openingTimeSlotsList = null;
        OpeningTimeSlots openingTimeSlots = null;
        StoreOpeningHours storeOpeningHours = null;
        List<StoreOpeningHours> storeOpeningHoursList = null;
        String pattern = "hh:MM";
        for(Map<String,Object> storeMap:physicalStoresList){
            storelocatorItem = new StorelocatorItem();
            contactDetailsList = new ArrayList<ContactDetails>();
            attributeList = new ArrayList<Attribute>();
            storeOpeningHoursList = new ArrayList<StoreOpeningHours>();
            stloc_id = storeMap.get("STLOC_ID").toString();
            storelocatorItem.setIdentifier(StringHelper.returnEmptyStringIfNull(storeMap.get("IDENTIFIER")));
            storelocatorItem.setStoreName(StringHelper.returnEmptyStringIfNull(storeMap.get("STORENAME")));
            storelocatorItem.setStoreNumber(StringHelper.returnEmptyStringIfNull(storeMap.get("STORENUMBER")));
            BigDecimal isActiveValue = (BigDecimal)storeMap.get("ACTIVE");
            if(isActiveValue.compareTo(new BigDecimal(1)) == 0){
                storelocatorItem.setActive(true);
            }else if(isActiveValue.compareTo(new BigDecimal(0)) == 0){
                storelocatorItem.setActive(false);
            }
            for(Map<String,Object> storeInformationMap:physicalStoresInformationList){
                if(stloc_id.equals(storeInformationMap.get("STLOC_ID").toString())){
                    address = new Address();
                    spatialData = new GeoData();
                    address.setLine1(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LINE1")));
                    address.setLine2(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LINE2")));
                    address.setLine3(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LINE3")));
                    address.setCity(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("CITY")));
                    address.setState(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("STATE")));
                    address.setCountryCode(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("COUNTRYCODE")));
                    address.setCountryName(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("COUNTRY_NAME")));
                    address.setPostCode(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("POSTCODE")));
                    address.setRegionCode(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("REGION_CODE")));
                    address.setRegionName(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("REGION_NAME")));
                    storelocatorItem.setAddress(address);
                    spatialData.setLatitude(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LATITUDE")));
                    spatialData.setLongitude(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LONGITUDE")));
                    storelocatorItem.setSpatialData(spatialData);
                    break;
                }
            }
            for(Map<String,Object> storeContactsMap:physicalStoresContactList){
                if(stloc_id.equals(storeContactsMap.get("STLOC_ID").toString())){
                    contactDetails = new ContactDetails();
                    contactsType = new ContactsType();
                    contactsName = new ContactsName();
                    contactDetails.setValue(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("CONATCT_VALUE")));
                    contactDetails.setIdentifier(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("IDENTIFIER")));
                    contactsName.setId(Integer.parseInt(storeContactsMap.get("CONTACTNAMEID").toString()));
                    contactsName.setName(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("CONTACT_NAME")));
                    contactsType.setCode(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("TYPE_CODE")));
                    contactsType.setName(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("TYPE_NAME")));
                    contactDetails.setName(contactsName);
                    contactDetails.setType(contactsType);
                    contactDetailsList.add(contactDetails);
                }
            }
            storelocatorItem.setContacts(contactDetailsList);
            for(Map<String,Object> storeAttributesMap:physicalStoresAttributesList){
                if(stloc_id.equals(storeAttributesMap.get("STLOC_ID").toString())){
                    attributeId = storeAttributesMap.get("ATTR_ID").toString();
                    attribute = new Attribute();
                    attributeValuesList = new ArrayList<AttributeValues>();
                    attribute.setIdentifier(StringHelper.returnEmptyStringIfNull(storeAttributesMap.get("IDENTIFIER")));
                    attribute.setName(StringHelper.returnEmptyStringIfNull(storeAttributesMap.get("ATTRIBUTE_NAME")));
                    attribute.setType(StringHelper.returnEmptyStringIfNull(storeAttributesMap.get("ATTRIBUTE_TYPE")));
                    for(Map<String,Object> storeAttributeValuesMap:physicalStoresAttributeValuesList){
                        if(stloc_id.equals(storeAttributeValuesMap.get("STLOC_ID").toString()) && attributeId.equals(storeAttributeValuesMap.get("ATTR_ID").toString())){
                            attributeValues = new AttributeValues();
                            attributeValues.setIdentifier(StringHelper.returnEmptyStringIfNull(storeAttributeValuesMap.get("ATTRIBUTE_VALUE_IDENTIFIER")));
                            attributeValues.setValue(StringHelper.returnEmptyStringIfNull(storeAttributeValuesMap.get("ATTRIBUTE_VALUE_NAME")));
                            attributeValuesList.add(attributeValues);
                        }
                    }
                    attribute.setValues(attributeValuesList);
                    attributeList.add(attribute);
                }
            }
            storelocatorItem.setAttributes(attributeList);
            //store opening times
            for(Map<String,Object> storeOpeningTimesMap:physicalStoresOpeningTimesList){
                if(stloc_id.equals(storeOpeningTimesMap.get("STLOC_ID").toString())){
                    storeOpeningHours =  new StoreOpeningHours();
                    String dayNumber = StringHelper.returnEmptyStringIfNull(storeOpeningTimesMap.get("DAY_NUMBER"));
                    storeOpeningHours.setDayNumber(dayNumber);
                    openingTimeSlotsList = new ArrayList<OpeningTimeSlots>();
                    List<Map<String,Object>> physicalStoresTimeSlotsList = storeLocatorDAO.getPhysicalStoreTimeSlots(stloc_id,dayNumber);
                    for(Map<String,Object> storeOpeningTimeSlotsMap:physicalStoresTimeSlotsList){
                            openingTimeSlots = new OpeningTimeSlots();
                            openingTimeSlots.setSlot(Integer.parseInt(StringHelper.returnEmptyStringIfNull(storeOpeningTimeSlotsMap.get("SLOT"))));
                            String openTimeHour = StringHelper.returnEmptyStringIfNull(storeOpeningTimeSlotsMap.get("OPENTIMEHOUR"));
                            String openTimeMinute = StringHelper.returnEmptyStringIfNull(storeOpeningTimeSlotsMap.get("OPENTIMEMINUTE"));
                            String closeTimeHour = StringHelper.returnEmptyStringIfNull(storeOpeningTimeSlotsMap.get("CLOSETIMEHOUR"));
                            String closeTimeMinute = StringHelper.returnEmptyStringIfNull(storeOpeningTimeSlotsMap.get("CLOSETIMEMINUTE"));
                            openingTimeSlots.setOpenTimeHour(openTimeHour);
                            openingTimeSlots.setOpenTimeMinute(openTimeMinute);
                            openingTimeSlots.setCloseTimeHour(closeTimeHour);
                            openingTimeSlots.setCloseTimeMinute(closeTimeMinute);
                            openingTimeSlots.setOpenTime(StringHelper.getFormattedTime(openTimeHour,openTimeMinute));
                            openingTimeSlots.setCloseTime(StringHelper.getFormattedTime(closeTimeHour,closeTimeMinute));
                            openingTimeSlotsList.add(openingTimeSlots);
                    }
                    storeOpeningHours.setSlots(openingTimeSlotsList);
                    storeOpeningHoursList.add(storeOpeningHours);
                }
            }
            storelocatorItem.setOpeningTimes(storeOpeningHoursList);

            storelocatorItemList.add(storelocatorItem);
        }
        storeLocator.setData(storelocatorItemList);
        //logger.info("storeLocator: "+storeLocator);
        return storeLocator;
    }

    /**
     * @Description This method is to call attributes service - attributes list for a given store
     * @param storeId
     * @param queryParam
     * @return attribute object with the service returned data
     */
    public AttributeData attributeListByStore(String storeId, Map<String,String> queryParam){
        Response response = null;
        AttributeData attributedata = null;
        Integer port;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.GET_PHYSICAL_STORE_ATTRIBUTES.getUrl().replace("{storeId}",storeId);
            logger.info("url:"+url);
//            response = getResponse(url,queryParam);
            ChangeProtocol("http");
            //port = TestConstants.HTTP_PORT.getIntValue();
            response =givenConfig().log().all()
                    .queryParams(queryParam)
                    .when().get(url);


            response.then().spec(getResponseSpecBuilder());
            attributedata = gson.fromJson(response.asString(), AttributeData.class);
           // logger.info("response from attributeListByStore:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return attributedata;
    }

    public Response putboutiqueOperationsResponse(String storeIdentifier, String boutiqueIdentifier, String payLoad, String operation) {
        Response response = null;
        String url = null;
        try {
            LoginHelper loginHelper = new LoginHelper();
            if(Tokens.WCToken == null) {
                Response loginResponse = loginHelper.login("admin", storeIdentifier);
            }
            logger.info("WCToken: "+Tokens.WCToken +" WCTrustedToken: "+Tokens.WCTrustedToken);
            if(operation.equals("POST")) {
                url = ServiceEndPoints.POST_BOUTIQUE.getUrl().replace("{storeIdentifier}", storeIdentifier);
                logger.info("url:" + url);
                ChangeProtocol("https");
                response = givenConfig().port(TestConstants.HTTPS_PORT.getIntValue()).body(payLoad).log().all().
                        header("WCToken", Tokens.WCToken).
                        header("WCTrustedToken", Tokens.WCTrustedToken).
                        post(url);
                response.then().spec(getResponseSpecBuilder());
            }else if(operation.equals("PUT")){
                url = ServiceEndPoints.PUT_BOUTIQUE.getUrl().replace("{storeIdentifier}",storeIdentifier)
                        .replace("{boutiqueIdentifier}",boutiqueIdentifier);
                logger.info("url:" + url);
                ChangeProtocol("https");
                response = givenConfig().port(TestConstants.HTTPS_PORT.getIntValue()).body(payLoad).log().all().
                        header("WCToken", Tokens.WCToken).
                        header("WCTrustedToken", Tokens.WCTrustedToken).
                        put(url);
                response.then().spec(getResponseSpecBuilder());
            }else if(operation.equals("NEGPUT")){
                url = ServiceEndPoints.PUT_BOUTIQUE.getUrl().replace("{storeIdentifier}",storeIdentifier)
                        .replace("{boutiqueIdentifier}",boutiqueIdentifier);
                logger.info("url:" + url);
                ChangeProtocol("https");
                response = givenConfig().port(TestConstants.HTTPS_PORT.getIntValue()).body(payLoad).log().all().
                        header("WCToken", Tokens.WCToken).
                        header("WCTrustedToken", Tokens.WCTrustedToken).
                        put(url);
                response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_BAD_REQUEST.getStringValue()));
            }

            // logger.info("response from attributeListByStore:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return response;
    }

    public Response DeleteBoutique(String storeIdentifier, String boutiqueIdentifier) {
        Response response = null;
        try {
            LoginHelper loginHelper = new LoginHelper();
            if(Tokens.WCToken == null) {
                Response loginResponse = loginHelper.login("admin", storeIdentifier);
            }
            logger.info("WCToken: "+Tokens.WCToken +" WCTrustedToken: "+Tokens.WCTrustedToken);
            String url = ServiceEndPoints.DELETE_BOUTIQUE.getUrl().replace("{storeIdentifier}",storeIdentifier)
                    .replace("{boutiqueIdentifier}",boutiqueIdentifier);
            logger.info("url:"+url);
            ChangeProtocol("https");
            response = givenConfig().port(TestConstants.HTTPS_PORT.getIntValue()).log().all().
                    header("WCToken", Tokens.WCToken).
                    header("WCTrustedToken", Tokens.WCTrustedToken).
                    delete(url);
            response.then().spec(getResponseSpecBuilder());
            // logger.info("response from attributeListByStore:"+response.asString());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return response;
    }

    public StoreLocator getStoreLocatorDataWithLatitudeAndLongitudeFromDB(String storeCASId,String languageId,String isActive,Map<String, String> queryParamDB,String queryParameter,Map<String, String> queryParamAttributes) throws Exception{

        List<Map<String,Object>> physicalStoresList = storeLocatorDAO.getPhysicalStoresWithLatitudeAndLongitudeForSite(storeCASId, isActive,queryParameter, queryParamDB,queryParamAttributes);
        List<Map<String,Object>> physicalStoresInformationList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresContactList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresAttributesList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresAttributeValuesList =  new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> physicalStoresOpeningTimesList =  new ArrayList<Map<String,Object>>();

        if(physicalStoresList.size()>0){
            physicalStoresInformationList = storeLocatorDAO.getPhysicalStoreInformation(storeCASId,languageId,isActive);
            physicalStoresContactList = storeLocatorDAO.getPhysicalStoreContactInformation(storeCASId,languageId,isActive);
            physicalStoresAttributesList = storeLocatorDAO.getPhysicalStoreAttributes(storeCASId,languageId,isActive);
            physicalStoresAttributeValuesList = storeLocatorDAO.getPhysicalStoreAttributeValues(storeCASId,languageId,isActive);
            physicalStoresOpeningTimesList = storeLocatorDAO.getPhysicalStoreOpeningHours(storeCASId,isActive);
        }

        String stloc_id = null;
        StoreLocator storeLocator = new StoreLocator();
        StorelocatorItem storelocatorItem = null;
        List<StorelocatorItem> storelocatorItemList = new ArrayList<StorelocatorItem>();
        Address address = null;
        GeoData spatialData = null;
        GeoDistance geoDistance = null;
        GeoData startLocation = null;
        ContactDetails contactDetails = null;
        List<ContactDetails> contactDetailsList = null;
        Attribute attribute = null;
        List<Attribute> attributeList = null;
        AttributeValues attributeValues = null;
        List<AttributeValues> attributeValuesList = null;
        String attributeId = null;
        ContactsType contactsType = null;
        ContactsName contactsName = null;
        List<OpeningTimeSlots> openingTimeSlotsList = null;
        OpeningTimeSlots openingTimeSlots = null;
        StoreOpeningHours storeOpeningHours = null;
        List<StoreOpeningHours> storeOpeningHoursList = null;
        String pattern = "hh:MM";
        for(Map<String,Object> storeMap:physicalStoresList){
            storelocatorItem = new StorelocatorItem();
            contactDetailsList = new ArrayList<ContactDetails>();
            attributeList = new ArrayList<Attribute>();
            storeOpeningHoursList = new ArrayList<StoreOpeningHours>();
            stloc_id = storeMap.get("STLOC_ID").toString();
            storelocatorItem.setIdentifier(StringHelper.returnEmptyStringIfNull(storeMap.get("IDENTIFIER")));
            storelocatorItem.setStoreName(StringHelper.returnEmptyStringIfNull(storeMap.get("STORENAME")));
            storelocatorItem.setStoreNumber(StringHelper.returnEmptyStringIfNull(storeMap.get("STORENUMBER")));
            BigDecimal isActiveValue = (BigDecimal)storeMap.get("ACTIVE");
            if(isActiveValue.compareTo(new BigDecimal(1)) == 0){
                storelocatorItem.setActive(true);
            }else if(isActiveValue.compareTo(new BigDecimal(0)) == 0){
                storelocatorItem.setActive(false);
            }
            geoDistance = new GeoDistance();
            startLocation = new GeoData();
            geoDistance.setDistance(StringHelper.returnEmptyStringIfNull(storeMap.get("DISTANCE")));
            storelocatorItem.setGeoDistance(geoDistance);
            startLocation.setLongitude(StringHelper.returnEmptyStringIfNull(storeMap.get("LONGITUDE")));
            startLocation.setLatitude(StringHelper.returnEmptyStringIfNull(storeMap.get("LATITUDE")));
            storelocatorItem.setSpatialData(startLocation);
            for(Map<String,Object> storeInformationMap:physicalStoresInformationList){
                if(stloc_id.equals(storeInformationMap.get("STLOC_ID").toString())){
                    address = new Address();
                    spatialData = new GeoData();
                    address.setLine1(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LINE1")));
                    address.setLine2(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LINE2")));
                    address.setLine3(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LINE3")));
                    address.setCity(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("CITY")));
                    address.setState(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("STATE")));
                    address.setCountryCode(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("COUNTRYCODE")));
                    address.setCountryName(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("COUNTRY_NAME")));
                    address.setPostCode(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("POSTCODE")));
                    address.setRegionCode(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("REGION_CODE")));
                    address.setRegionName(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("REGION_NAME")));
                    storelocatorItem.setAddress(address);
                    spatialData.setLatitude(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LATITUDE")));
                    spatialData.setLongitude(StringHelper.returnEmptyStringIfNull(storeInformationMap.get("LONGITUDE")));
                    storelocatorItem.setSpatialData(spatialData);
                    break;
                }
            }
            for(Map<String,Object> storeContactsMap:physicalStoresContactList){
                if(stloc_id.equals(storeContactsMap.get("STLOC_ID").toString())){
                    contactDetails = new ContactDetails();
                    contactsType = new ContactsType();
                    contactsName = new ContactsName();
                    contactDetails.setValue(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("CONATCT_VALUE")));
                    contactDetails.setIdentifier(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("IDENTIFIER")));
                    contactsName.setId(Integer.parseInt(storeContactsMap.get("CONTACTNAMEID").toString()));
                    contactsName.setName(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("CONTACT_NAME")));
                    contactsType.setCode(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("TYPE_CODE")));
                    contactsType.setName(StringHelper.returnEmptyStringIfNull(storeContactsMap.get("TYPE_NAME")));
                    contactDetails.setName(contactsName);
                    contactDetails.setType(contactsType);
                    contactDetailsList.add(contactDetails);
                }
            }
            storelocatorItem.setContacts(contactDetailsList);
            for(Map<String,Object> storeAttributesMap:physicalStoresAttributesList){
                if(stloc_id.equals(storeAttributesMap.get("STLOC_ID").toString())){
                    attributeId = storeAttributesMap.get("ATTR_ID").toString();
                    attribute = new Attribute();
                    attributeValuesList = new ArrayList<AttributeValues>();
                    attribute.setIdentifier(StringHelper.returnEmptyStringIfNull(storeAttributesMap.get("IDENTIFIER")));
                    attribute.setName(StringHelper.returnEmptyStringIfNull(storeAttributesMap.get("ATTRIBUTE_NAME")));
                    attribute.setType(StringHelper.returnEmptyStringIfNull(storeAttributesMap.get("ATTRIBUTE_TYPE")));
                    for(Map<String,Object> storeAttributeValuesMap:physicalStoresAttributeValuesList){
                        if(stloc_id.equals(storeAttributeValuesMap.get("STLOC_ID").toString()) && attributeId.equals(storeAttributeValuesMap.get("ATTR_ID").toString())){
                            attributeValues = new AttributeValues();
                            attributeValues.setIdentifier(StringHelper.returnEmptyStringIfNull(storeAttributeValuesMap.get("ATTRIBUTE_VALUE_IDENTIFIER")));
                            attributeValues.setValue(StringHelper.returnEmptyStringIfNull(storeAttributeValuesMap.get("ATTRIBUTE_VALUE_NAME")));
                            attributeValuesList.add(attributeValues);
                        }
                    }
                    attribute.setValues(attributeValuesList);
                    attributeList.add(attribute);
                }
            }
            storelocatorItem.setAttributes(attributeList);
            //store opening times
            for(Map<String,Object> storeOpeningTimesMap:physicalStoresOpeningTimesList){
                if(stloc_id.equals(storeOpeningTimesMap.get("STLOC_ID").toString())){
                    storeOpeningHours =  new StoreOpeningHours();
                    String dayNumber = StringHelper.returnEmptyStringIfNull(storeOpeningTimesMap.get("DAY_NUMBER"));
                    storeOpeningHours.setDayNumber(dayNumber);
                    openingTimeSlotsList = new ArrayList<OpeningTimeSlots>();
                    List<Map<String,Object>> physicalStoresTimeSlotsList = storeLocatorDAO.getPhysicalStoreTimeSlots(stloc_id,dayNumber);
                    for(Map<String,Object> storeOpeningTimeSlotsMap:physicalStoresTimeSlotsList){
                        openingTimeSlots = new OpeningTimeSlots();
                        openingTimeSlots.setSlot(Integer.parseInt(StringHelper.returnEmptyStringIfNull(storeOpeningTimeSlotsMap.get("SLOT"))));
                        String openTimeHour = storeOpeningTimeSlotsMap.get("OPENTIMEHOUR").toString();
                        String openTimeMinute = storeOpeningTimeSlotsMap.get("OPENTIMEMINUTE").toString();
                        String closeTimeHour = storeOpeningTimeSlotsMap.get("CLOSETIMEHOUR").toString();
                        String closeTimeMinute = storeOpeningTimeSlotsMap.get("CLOSETIMEMINUTE").toString();
                        openingTimeSlots.setOpenTimeHour(openTimeHour);
                        openingTimeSlots.setOpenTimeMinute(openTimeMinute);
                        openingTimeSlots.setCloseTimeHour(closeTimeHour);
                        openingTimeSlots.setCloseTimeMinute(closeTimeMinute);
                        openingTimeSlots.setOpenTime(StringHelper.getFormattedTime(openTimeHour,openTimeMinute));
                        openingTimeSlots.setCloseTime(StringHelper.getFormattedTime(closeTimeHour,closeTimeMinute));
                        openingTimeSlotsList.add(openingTimeSlots);
                    }
                    storeOpeningHours.setSlots(openingTimeSlotsList);
                    storeOpeningHoursList.add(storeOpeningHours);
                }
            }
            storelocatorItem.setOpeningTimes(storeOpeningHoursList);

            storelocatorItemList.add(storelocatorItem);
        }
        storeLocator.setData(storelocatorItemList);
        //logger.info("storeLocator: "+storeLocator);
        return storeLocator;
    }

    public ContactsTypes getContactTypes(String storeIdentifier, String protocol,Map<String,String> queryParam) {
        Response response;
        ContactsTypes contactsTypes = null;
        Integer port;
        Gson gson = new Gson();
        try {
            if (queryParam == null) {
                queryParam= new HashMap<>();
            }
            // THE BELOW IS A REWRITE THAT MUST BE REMOVEDF WHEN REWRITES IMPLEMENTED ON APP SERVER
            String url = ServiceEndPoints.GET_CONTACT_TYPES.getUrl().replace("{storeId}",storeIdentifier);
            if ( protocol.equals("https") ) {
                ChangeProtocol("https");
                port = TestConstants.HTTPS_PORT.getIntValue();
                response =givenConfig().log().all()
                        .port(port).
                                queryParams(queryParam).
                                header("WCToken", Tokens.WCToken).
                                header("WCTrustedToken", Tokens.WCTrustedToken)
                        .when().get(url);
            } else {
                ChangeProtocol("http");
                port = TestConstants.HTTP_PORT.getIntValue();
                response =givenConfig().log().all()
                        //.port(port)
                        .queryParams(queryParam)
                        .when().get(url);
                response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_FORBIDDEN.getStringValue()));
            }


            // response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_FORBIDDEN.getStringValue()));
            contactsTypes = gson.fromJson(response.asString(), ContactsTypes.class);
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return contactsTypes;
    }

    public ContactsNames getContactNames(String storeIdentifier, String protocol,Map<String,String> queryParam) {
        Response response;
        ContactsNames contactsNames = null;
        Integer port;
        Gson gson = new Gson();
        try {
            if (queryParam == null) {
                queryParam= new HashMap<>();
            }
            // THE BELOW IS A REWRITE THAT MUST BE REMOVEDF WHEN REWRITES IMPLEMENTED ON APP SERVER
            String url = ServiceEndPoints.GET_CONTACT_NAMES.getUrl().replace("{storeId}",storeIdentifier);
            if ( protocol.equals("https") ) {
                ChangeProtocol("https");
                port = TestConstants.HTTPS_PORT.getIntValue();
                response =givenConfig().log().all()
                        .port(port).
                                queryParams(queryParam).
                                header("WCToken", Tokens.WCToken).
                                header("WCTrustedToken", Tokens.WCTrustedToken)
                        .when().get(url);
                response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_OK.getStringValue()));
            } else {
                ChangeProtocol("http");
                port = TestConstants.HTTP_PORT.getIntValue();
                response =givenConfig().log().all()
                       //.port(port)
                        .queryParams(queryParam)
                        .when().get(url);
                response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_FORBIDDEN.getStringValue()));
            }


            // response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_FORBIDDEN.getStringValue()));
            contactsNames = gson.fromJson(response.asString(), ContactsNames.class);
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return contactsNames;
    }

    public ContactsNames getContactNamesForNegative(String storeIdentifier, String protocol,Map<String,String> queryParam) {
        Response response = null;
        ContactsNames contactsNames = null;
        Integer port;
        Gson gson = new Gson();
        try {
            if (queryParam == null) {
                queryParam= new HashMap<>();
            }
            // THE BELOW IS A REWRITE THAT MUST BE REMOVEDF WHEN REWRITES IMPLEMENTED ON APP SERVER
            String url = ServiceEndPoints.GET_CONTACT_NAMES.getUrl().replace("{storeId}",storeIdentifier);
            if ( protocol.equals("https") ) {
                ChangeProtocol("https");
                port = TestConstants.HTTPS_PORT.getIntValue();
                response =givenConfig().log().all()
                        .port(port).
                                queryParams(queryParam).
                                header("WCToken", Tokens.WCToken).
                                header("WCTrustedToken", Tokens.WCTrustedToken)
                        .when().get(url);
                response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_FORBIDDEN.getStringValue()));
            }

            // response.then().spec(responseSpecBuilder(TestConstants.STATUS_CODE_FORBIDDEN.getStringValue()));
            contactsNames = gson.fromJson(response.asString(), ContactsNames.class);
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return contactsNames;
    }


    public PhysicalStorePayLoad getExpectedPhysicalStoreData(String physicalStoreId) throws Exception {

        List<Map<String, Object>> expPhysicalStoreList = storeLocatorDAO.getPhysicalStoreInformationByIdentifier(physicalStoreId);
        List<Map<String, Object>> expPhysicalStoreAttributes = storeLocatorDAO.getPhysicalStoreAttributesByIdentifier(physicalStoreId);
        List<Map<String, Object>> expPhysicalStoreContacts = storeLocatorDAO.getPhysicalStoreContactsByIdentifier(physicalStoreId);
        List<Map<String, Object>> expPhysicalStoreOpeningDays = storeLocatorDAO.getPhysicalStoreOpeningDaysByidentifier(physicalStoreId);
        PhysicalStorePayLoad physicalStoreData = new PhysicalStorePayLoad();
        List<Map<String, Object>> physicalStoreAttributeValues;
        for (Map<String, Object> physicalStore : expPhysicalStoreList) {
            physicalStoreData.setStoreNumber(StringHelper.returnEmptyStringIfNull(physicalStore.get("STORENUMBER")));
            physicalStoreData.setStoreName(StringHelper.returnEmptyStringIfNull(physicalStore.get("STORENAME")));
            Address address = new Address();
            address.setLine1(StringHelper.returnEmptyStringIfNull(physicalStore.get("LINE1")));
            address.setLine2(StringHelper.returnEmptyStringIfNull(physicalStore.get("LINE2")));
            address.setLine3(StringHelper.returnEmptyStringIfNull(physicalStore.get("LINE3")));
            address.setPostCode(StringHelper.returnEmptyStringIfNull(physicalStore.get("POSTCODE")));
            address.setCity(StringHelper.returnEmptyStringIfNull(physicalStore.get("CITY")));
            address.setState(StringHelper.returnEmptyStringIfNull(physicalStore.get("STATE")));
            address.setCountryCode(StringHelper.returnEmptyStringIfNull(physicalStore.get("COUNTRYCODE")));
            physicalStoreData.setAddress(address);
            GeoData geoData = new GeoData();
            geoData.setLatitude(StringHelper.returnEmptyStringIfNull(physicalStore.get("LATITUDE")));
            geoData.setLongitude(StringHelper.returnEmptyStringIfNull(physicalStore.get("LONGITUDE")));
            physicalStoreData.setSpatialData(geoData);

            //Attributes
            List<Attribute> attributeList = new ArrayList<Attribute>();
            Attribute attribute;
            AttributeValues attributeValues;
            List<AttributeValues> attributeValuesList = null;
            for (Map<String, Object> physicalStoreAttribute : expPhysicalStoreAttributes) {

                attribute = new Attribute();
                attributeValuesList = new ArrayList<AttributeValues>();
                attribute.setIdentifier(StringHelper.returnEmptyStringIfNull(physicalStoreAttribute.get("IDENTIFIER")));
                physicalStoreAttributeValues = storeLocatorDAO.getPhysicalStoreAttributeValuesByIdentifier(physicalStoreId, StringHelper.returnEmptyStringIfNull(physicalStoreAttribute.get("ATTR_ID")));
                for (Map<String, Object> physicalStoreAttributeValue : physicalStoreAttributeValues) {
                    attributeValues = new AttributeValues();
                    attributeValues.setIdentifier(StringHelper.returnEmptyStringIfNull(physicalStoreAttributeValue.get("IDENTIFIER")));
                    attributeValuesList.add(attributeValues);
                }
                attribute.setValues(attributeValuesList);
                attributeList.add(attribute);
            }
            physicalStoreData.setAttributes(attributeList);

            //contacts
            ContactDetails contactDetails;
            List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
            ContactsType contactsType;
            ContactsName contactsName;
            for (Map<String, Object> physicalStoreContact : expPhysicalStoreContacts) {
                contactDetails = new ContactDetails();
                contactsName = new ContactsName();
                contactsType = new ContactsType();
                contactDetails.setIdentifier(StringHelper.returnEmptyStringIfNull(physicalStoreContact.get("IDENTIFIER")));
                contactDetails.setValue(StringHelper.returnEmptyStringIfNull(physicalStoreContact.get("CONTACT_VALUE")));
                contactsType.setCode(StringHelper.returnEmptyStringIfNull(physicalStoreContact.get("CONTACT_CODE")));
                contactsName.setId(Integer.parseInt(physicalStoreContact.get("CONTACT_NAME_ID").toString()));
                contactDetails.setName(contactsName);
                contactDetails.setType(contactsType);
                contactDetailsList.add(contactDetails);
            }
            physicalStoreData.setContacts(contactDetailsList);

            StoreOpeningHours storeOpeningHours;
            List<StoreOpeningHours> storeOpeningHoursList = new ArrayList<StoreOpeningHours>();
            List<OpeningTimeSlots> openingTimeSlotsList;
            OpeningTimeSlots openingTimeSlots;
            for(Map<String,Object> storeOpeningTimesMap:expPhysicalStoreOpeningDays){
                    storeOpeningHours =  new StoreOpeningHours();
                    String dayNumber = StringHelper.returnEmptyStringIfNull(storeOpeningTimesMap.get("DAY_NUMBER"));
                    storeOpeningHours.setDayNumber(dayNumber);
                    openingTimeSlotsList = new ArrayList<OpeningTimeSlots>();
                    List<Map<String,Object>> physicalStoresTimeSlotsList = storeLocatorDAO.getPhysicalStoreTimeSlots(storeOpeningTimesMap.get("STLOC_ID").toString(),dayNumber);
                    for(Map<String,Object> storeOpeningTimeSlotsMap:physicalStoresTimeSlotsList){
                        openingTimeSlots = new OpeningTimeSlots();
                        openingTimeSlots.setSlot(Integer.parseInt(StringHelper.returnEmptyStringIfNull(storeOpeningTimeSlotsMap.get("SLOT"))));
                        String openTimeHour = storeOpeningTimeSlotsMap.get("OPENTIMEHOUR").toString();
                        String openTimeMinute = storeOpeningTimeSlotsMap.get("OPENTIMEMINUTE").toString();
                        String closeTimeHour = storeOpeningTimeSlotsMap.get("CLOSETIMEHOUR").toString();
                        String closeTimeMinute = storeOpeningTimeSlotsMap.get("CLOSETIMEMINUTE").toString();
                        openingTimeSlots.setOpenTimeHour(openTimeHour);
                        openingTimeSlots.setOpenTimeMinute(openTimeMinute);
                        openingTimeSlots.setCloseTimeHour(closeTimeHour);
                        openingTimeSlots.setCloseTimeMinute(closeTimeMinute);
                        openingTimeSlots.setOpenTime(StringHelper.getFormattedTime(openTimeHour,openTimeMinute));
                        openingTimeSlots.setCloseTime(StringHelper.getFormattedTime(closeTimeHour,closeTimeMinute));
                        openingTimeSlotsList.add(openingTimeSlots);
                    }
                    storeOpeningHours.setSlots(openingTimeSlotsList);
                    storeOpeningHoursList.add(storeOpeningHours);
            }
            physicalStoreData.setOpeningTimes(storeOpeningHoursList);
        }

        return physicalStoreData;
    }

    /**
     * Prepare openinghours for physical store
     * @return list of StoreOpeningHours object
     */
    public List<StoreOpeningHours> prepareStoreOpeningHoursData(String keyword){
        List<StoreOpeningHours> storeOpeningHoursList = new ArrayList<StoreOpeningHours>();
        StoreOpeningHours storeOpeningHours;
        List<OpeningTimeSlots> openingTimeSlotsList;
        OpeningTimeSlots openingTimeSlots1;
        OpeningTimeSlots openingTimeSlots2;
        OpeningTimeSlots openingTimeSlots3;
        for(int i=1;i<8;i++){
            storeOpeningHours = new StoreOpeningHours();
            storeOpeningHours.setDayNumber(""+i);
            openingTimeSlotsList = new ArrayList<OpeningTimeSlots>();
            if(i == 6 && keyword.equals("create")) {
                openingTimeSlots3 = new OpeningTimeSlots();
                openingTimeSlots3.setSlot(1);
                openingTimeSlots3.setOpenTimeHour("8");
                openingTimeSlots3.setOpenTimeMinute("0");
                openingTimeSlots3.setCloseTimeHour("17");
                openingTimeSlots3.setCloseTimeMinute("30");
                openingTimeSlots3.setOpenTime("08:00");
                openingTimeSlots3.setCloseTime("17:30");
                openingTimeSlotsList.add(openingTimeSlots3);
            }else if(i == 1 && keyword.equals("update")){
                openingTimeSlots3 = new OpeningTimeSlots();
                openingTimeSlots3.setSlot(1);
                openingTimeSlots3.setOpenTimeHour("8");
                openingTimeSlots3.setOpenTimeMinute("0");
                openingTimeSlots3.setCloseTimeHour("17");
                openingTimeSlots3.setCloseTimeMinute("30");
                openingTimeSlots3.setOpenTime("08:00");
                openingTimeSlots3.setCloseTime("17:30");
                openingTimeSlotsList.add(openingTimeSlots3);
            }else {
                openingTimeSlots1 = new OpeningTimeSlots();
                openingTimeSlots1.setSlot(1);
                openingTimeSlots1.setOpenTimeHour("8");
                openingTimeSlots1.setOpenTimeMinute("0");
                openingTimeSlots1.setCloseTimeHour("12");
                openingTimeSlots1.setCloseTimeMinute("0");
                openingTimeSlots1.setOpenTime("08:00");
                openingTimeSlots1.setCloseTime("12:00");
                openingTimeSlotsList.add(openingTimeSlots1);
                openingTimeSlots2 = new OpeningTimeSlots();
                openingTimeSlots2.setSlot(2);
                openingTimeSlots2.setOpenTimeHour("3");
                openingTimeSlots2.setOpenTimeMinute("0");
                openingTimeSlots2.setCloseTimeHour("17");
                openingTimeSlots2.setCloseTimeMinute("30");
                openingTimeSlots2.setOpenTime("03:00");
                openingTimeSlots2.setCloseTime("17:30");
                openingTimeSlotsList.add(openingTimeSlots2);
            }
            storeOpeningHours.setSlots(openingTimeSlotsList);
            storeOpeningHoursList.add(storeOpeningHours);
        }
        return storeOpeningHoursList;
    }

    /**
     * This is to preapre the payload to create new physical store
     * @param keyword scenarios to create store
     * @param storeIdentifier store identifier
     * @param storeCASId Catalog asset id for the store identifier
     * @return PhysicalStorePayLoad object
     */
    public PhysicalStorePayLoad preparePhysicalStorePayLoad(String keyword,String storeIdentifier,String storeCASId){

        PhysicalStorePayLoad physicalStorePayLoad = new PhysicalStorePayLoad();
        physicalStorePayLoad.setStoreName("TEST_NEW_PSTORE");
        physicalStorePayLoad.setStoreNumber("MNPS123");
        physicalStorePayLoad.setActive(true);

        Address address = new Address();
        address.setLine1("Flat 20");
        address.setLine2("Westbarnes Lane");
        address.setLine3("Raynespark");
        address.setCity("London");
        address.setPostCode("SW20 0BX");
        address.setCountryCode("GB");
        address.setRegionCode("EUR");
        physicalStorePayLoad.setAddress(address);

        GeoData spatialData = new GeoData();
        spatialData.setLatitude("51.40942");
        spatialData.setLongitude("-0.23435");
        physicalStorePayLoad.setSpatialData(spatialData);

        if(keyword != null && keyword.contains("attributes")){
            String offset = Props.getMessage("stloc.max.attributes.request.offset");
            String noOfRows = Props.getMessage("stloc.max.attributes.request.count");
            List<Map<String,Object>> physicalStoreAttributes = storeLocatorDAO.getPhysicalStoreAttributesFoBrand(storeCASId,offset,noOfRows);
            AttributeValues attributeValues;
            Attribute attribute;
            List<Attribute> attributeList = new ArrayList<Attribute>();
            List<AttributeValues> attributeValuesList;
            int attributeCount = Integer.parseInt(Props.getMessage("stloc.max.attributes.create.count"));
            if(physicalStoreAttributes.size() < attributeCount){
                attributeCount = physicalStoreAttributes.size();
            }
            for(int i=0;i<attributeCount;i++){
                Map<String,Object> physicalStoreAttributeMap = physicalStoreAttributes.get(i);
                attribute = new Attribute();
                attribute.setIdentifier(physicalStoreAttributeMap.get("IDENTIFIER").toString());
                String attributeId = physicalStoreAttributeMap.get("ATTR_ID").toString();
                List<Map<String,Object>> physicalStoreAttributeValues = storeLocatorDAO.getPhysicalStoreAttributeValuesForAttributeWithBrand(storeCASId,attributeId);
                attributeValuesList = new ArrayList<AttributeValues>();
                for(Map<String,Object> physicalStoreAttributeValue:physicalStoreAttributeValues){
                    attributeValues = new AttributeValues();
                    attributeValues.setIdentifier(physicalStoreAttributeValue.get("IDENTIFIER").toString());
                    attributeValuesList.add(attributeValues);
                }
                attribute.setValues(attributeValuesList);
                attributeList.add(attribute);
            }
            physicalStorePayLoad.setAttributes(attributeList);
        }
        if(keyword != null && keyword.contains("contacts")){
            String noOfRows = Props.getMessage("stloc.max.contacts.request.count");
            String offset = Props.getMessage("stloc.max.contacts.request.offset");
            List<Map<String,Object>> contacts = storeLocatorDAO.getContactsByRows(noOfRows,offset);
            ContactDetails contactDetails;
            List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
            ContactsType contactsType;
            ContactsName contactsName;
            for(Map<String,Object> contact:contacts){
                contactDetails = new ContactDetails();
                contactsName = new ContactsName();
                contactsType = new ContactsType();
                contactDetails.setIdentifier(StringHelper.returnEmptyStringIfNull(contact.get("IDENTIFIER")));
                contactDetails.setValue(StringHelper.returnEmptyStringIfNull(contact.get("CONTACT_VALUE")));
                contactsType.setCode(StringHelper.returnEmptyStringIfNull(contact.get("CONTACT_CODE")));
                contactsName.setId(Integer.parseInt(contact.get("CONTACT_NAME_ID").toString()));
                contactDetails.setName(contactsName);
                contactDetails.setType(contactsType);
                contactDetailsList.add(contactDetails);
            }
            physicalStorePayLoad.setContacts(contactDetailsList);
        }
        if(keyword != null && keyword.contains("openingtimes")){
            physicalStorePayLoad.setOpeningTimes(prepareStoreOpeningHoursData("create"));
        }


        return physicalStorePayLoad;
    }


    /**
     * This is to compare the Physical store information
     * @param physicalStorePayLoad
     * @param expPhysicalStorePayLoad
     */
    public void comparePhysicalStoreInformation(PhysicalStorePayLoad physicalStorePayLoad, PhysicalStorePayLoad expPhysicalStorePayLoad){

        assertThat(expPhysicalStorePayLoad.getStoreName()).as("Physical Store name is wrong").isEqualTo(physicalStorePayLoad.getStoreName());
        assertThat(expPhysicalStorePayLoad.getStoreNumber()).as("Physical Store number is wrong").isEqualTo(physicalStorePayLoad.getStoreNumber());
        assertThat(expPhysicalStorePayLoad.getAddress().getLine1()).as("Physical Store address line1 is wrong").isEqualTo(physicalStorePayLoad.getAddress().getLine1());
        assertThat(expPhysicalStorePayLoad.getAddress().getLine2()).as("Physical Store address line2 is wrong").isEqualTo(physicalStorePayLoad.getAddress().getLine2());
        assertThat(expPhysicalStorePayLoad.getAddress().getLine3()).as("Physical Store address line3 is wrong").isEqualTo(physicalStorePayLoad.getAddress().getLine3());
        assertThat(expPhysicalStorePayLoad.getAddress().getCity()).as("Physical Store city is wrong").isEqualTo(physicalStorePayLoad.getAddress().getCity());
        assertThat(expPhysicalStorePayLoad.getAddress().getState()).as("Physical Store state is wrong").isEqualTo(physicalStorePayLoad.getAddress().getState());
        assertThat(expPhysicalStorePayLoad.getAddress().getPostCode()).as("Physical Store postcode is wrong").isEqualTo(physicalStorePayLoad.getAddress().getPostCode());
        assertThat(expPhysicalStorePayLoad.getSpatialData().getLatitude()).as("Physical Store latitude is wrong").isEqualTo(physicalStorePayLoad.getSpatialData().getLatitude());
        assertThat(expPhysicalStorePayLoad.getSpatialData().getLongitude()).as("Physical Store longitude is wrong").isEqualTo(physicalStorePayLoad.getSpatialData().getLongitude());
        assertThat(expPhysicalStorePayLoad.getAddress().getCountryCode()).as("Physical Store countrycode is wrong").isEqualTo(physicalStorePayLoad.getAddress().getCountryCode());

        //check attributes
        assertThat(expPhysicalStorePayLoad.getAttributes().size()).as("Physical store attributes count is wrong").isEqualTo(physicalStorePayLoad.getAttributes().size());
        for(Attribute expAttribute:expPhysicalStorePayLoad.getAttributes()){
            boolean isAttributeExist = false;
            for(Attribute actAttribute:physicalStorePayLoad.getAttributes()){
                if(expAttribute.getIdentifier().equals(actAttribute.getIdentifier())){
                    for(AttributeValues expAttributeValue:expAttribute.getValues()){
                        boolean isAttributeValueExist = false;
                        assertThat(actAttribute.getValues()).as("Attribute values should not be empty for the newly created store expected:"+expAttributeValue).isNotNull();
                        for(AttributeValues actAttributeValue:actAttribute.getValues()){
                            if(expAttributeValue.getIdentifier().equals(actAttributeValue.getIdentifier())){
                                isAttributeValueExist = true;
                                break;
                            }
                        }
                        assertThat(isAttributeValueExist).as("Attribute value Identifier: "+expAttributeValue.getIdentifier()+" for Physical store identifier: "+expAttribute.getIdentifier()+" doest not exist in the response").isTrue();
                    }
                    isAttributeExist = true;
                    break;
                }
            }
            assertThat(isAttributeExist).as("Attribute Identifier: "+expAttribute.getIdentifier()+" doest not exist in the response").isTrue();
        }

        //check contacts
        if(expPhysicalStorePayLoad.getContacts() != null) {
            assertThat(expPhysicalStorePayLoad.getContacts().size()).as("Physical store contacts count is wrong").isEqualTo(physicalStorePayLoad.getContacts().size());
            for(ContactDetails expContactDetails:expPhysicalStorePayLoad.getContacts()){
                boolean isContactsExist = false;
                for(ContactDetails actContactDetails:physicalStorePayLoad.getContacts()){
                    if(expContactDetails.getIdentifier().equals(actContactDetails.getIdentifier())){
                        Assertions.assertThat(expContactDetails.getValue()).as("Contacts value is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getValue());
                        assertThat(expContactDetails.getName().getId()).as("Contact name id is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getName().getId());
                        assertThat(expContactDetails.getType().getCode()).as("Contact type code is wrong for physical store Identifier: "+expContactDetails.getIdentifier()+"").isEqualTo(actContactDetails.getType().getCode());
                        isContactsExist = true;
                        break;
                    }
                }
                assertThat(isContactsExist).as("Contact identifier: "+expContactDetails.getIdentifier()+" for physical store  does not exist in the database").isTrue();
            }
        }


        //check openingTimes
        if(expPhysicalStorePayLoad.getOpeningTimes() != null) {
            assertThat(expPhysicalStorePayLoad.getOpeningTimes().size()).as("Store Opening Times count is wrong for Physical store identifier ").isEqualTo(physicalStorePayLoad.getOpeningTimes().size());
            for(StoreOpeningHours expOpeningHours:expPhysicalStorePayLoad.getOpeningTimes()){
                boolean isOpeningDayExist = false;
                for(StoreOpeningHours actStoreOpeningHours:physicalStorePayLoad.getOpeningTimes()){
                    if(expOpeningHours.getDayNumber().equals(actStoreOpeningHours.getDayNumber())){
                        List<OpeningTimeSlots> expOpeningTimeSlotses = expOpeningHours.getSlots();
                        List<OpeningTimeSlots> actOpeningTimeSlotses = actStoreOpeningHours.getSlots();
                        assertThat(expOpeningTimeSlotses.size()).as("Opening Time slots for identifier:"+expPhysicalStorePayLoad.getIdentifier()+" for Day:"+ expOpeningHours.getDayNumber() +" is wrong").isEqualTo(actOpeningTimeSlotses.size());
                        for(OpeningTimeSlots expOpeningTimeSlot:expOpeningTimeSlotses){
                            boolean isOpeningSlotExist = false;
                            for(OpeningTimeSlots actOpeningTimeSlot:actOpeningTimeSlotses){
                                if(expOpeningTimeSlot.getSlot() == actOpeningTimeSlot.getSlot()){
                                    assertThat(expOpeningTimeSlot.getOpenTime()).as(" Opening Time for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store is coming wrong in the database").isEqualTo(actOpeningTimeSlot.getOpenTime());
                                    assertThat(expOpeningTimeSlot.getCloseTime()).as(" Closing Time for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store  is coming wrong in the database").isEqualTo(actOpeningTimeSlot.getCloseTime());
                                    assertThat(expOpeningTimeSlot.getOpenTimeHour()).as(" Opening Time Hour for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store is coming wrong in the database").isEqualTo(actOpeningTimeSlot.getOpenTimeHour());
                                    assertThat(expOpeningTimeSlot.getCloseTimeHour()).as(" Closing Time Hour for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store is coming wrong in the database").isEqualTo(actOpeningTimeSlot.getCloseTimeHour());
                                    assertThat(expOpeningTimeSlot.getOpenTimeMinute()).as(" Opening Time Minute for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store is coming wrong in the database").isEqualTo(actOpeningTimeSlot.getOpenTimeMinute());
                                    assertThat(expOpeningTimeSlot.getCloseTimeMinute()).as(" Closing Time Minute for slot:"+expOpeningTimeSlot.getSlot() +" for Physical store is coming wrong in the database").isEqualTo(actOpeningTimeSlot.getCloseTimeMinute());
                                    isOpeningSlotExist = true;
                                }
                            }
                            assertThat(isOpeningSlotExist).as("Slots: "+expOpeningTimeSlot.getSlot()+" for Physical store  doest not exist in the database").isTrue();
                        }
                        isOpeningDayExist = true;
                        break;
                    }
                }
                assertThat(isOpeningDayExist).as("Openig Day: "+expOpeningHours.getDayNumber()+" for Physical store doest not exist in the database").isTrue();
            }
        }

    }

    /**
     * This it to get the payload to update the physical store
     * @param storeIdentifier store identifier
     * @param boutiqueIdentifier boutique identifier
     * @param keyword scenario
     * @param storeCASId store catalog asset id
     * @return PhysicalStorePayLoad object
     */
    public PhysicalStorePayLoad preparePhysicalStoreUpdatePayLoad(String storeIdentifier,String boutiqueIdentifier,String keyword,String storeCASId){

        PhysicalStorePayLoad physicalStorePayLoad = new PhysicalStorePayLoad();
        physicalStorePayLoad.setIdentifier(boutiqueIdentifier);
        physicalStorePayLoad.setStoreName("TEST_NEW_PSTORE_UPDATE");
        physicalStorePayLoad.setStoreNumber("MNPS123U");
        physicalStorePayLoad.setActive(true);

        Address address = new Address();
        address.setLine1("Flat 7");
        address.setLine2("Westbarnes Lane update");
        address.setLine3("Raynespark update");
        address.setCity("London");
        address.setPostCode("SW20 1BX");
        address.setCountryCode("GB");
        address.setRegionCode("EUR");
        physicalStorePayLoad.setAddress(address);

        GeoData spatialData = new GeoData();
        spatialData.setLatitude("41.40942");
        spatialData.setLongitude("-1.23435");
        physicalStorePayLoad.setSpatialData(spatialData);

        if(keyword != null && keyword.contains("attributes")){
            String offset = Props.getMessage("stloc.max.attributes.update.offset");
            String noOfRows = Props.getMessage("stloc.max.attributes.update.count");
            List<Map<String,Object>> physicalStoreAttributes = storeLocatorDAO.getPhysicalStoreAttributesFoBrand(storeCASId,offset,noOfRows);
            AttributeValues attributeValues;
            Attribute attribute;
            List<Attribute> attributeList = new ArrayList<Attribute>();
            List<AttributeValues> attributeValuesList;
            int attributeCount = Integer.parseInt(Props.getMessage("stloc.max.attributes.create.count"));
            if(physicalStoreAttributes.size() < attributeCount){
                attributeCount = physicalStoreAttributes.size();
            }
            for(int i=0;i<attributeCount;i++){
                Map<String,Object> physicalStoreAttributeMap = physicalStoreAttributes.get(i);
                attribute = new Attribute();
                attribute.setIdentifier(physicalStoreAttributeMap.get("IDENTIFIER").toString());
                String attributeId = physicalStoreAttributeMap.get("ATTR_ID").toString();
                List<Map<String,Object>> physicalStoreAttributeValues = storeLocatorDAO.getPhysicalStoreAttributeValuesForAttributeWithBrand(storeCASId,attributeId);
                attributeValuesList = new ArrayList<AttributeValues>();
                for(Map<String,Object> physicalStoreAttributeValue:physicalStoreAttributeValues){
                    attributeValues = new AttributeValues();
                    attributeValues.setIdentifier(physicalStoreAttributeValue.get("IDENTIFIER").toString());
                    attributeValuesList.add(attributeValues);
                }
                attribute.setValues(attributeValuesList);
                attributeList.add(attribute);
            }
            physicalStorePayLoad.setAttributes(attributeList);
        }
        if(keyword != null && keyword.contains("contacts")){
            String noOfRows = Props.getMessage("stloc.max.contacts.update.count");
            String offset = Props.getMessage("stloc.max.contacts.update.offset");
            List<Map<String,Object>> contacts = storeLocatorDAO.getContactsByRows(noOfRows,offset);
            ContactDetails contactDetails;
            List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
            ContactsType contactsType;
            ContactsName contactsName;
            for(Map<String,Object> contact:contacts){
                contactDetails = new ContactDetails();
                contactsName = new ContactsName();
                contactsType = new ContactsType();
                contactDetails.setIdentifier(StringHelper.returnEmptyStringIfNull(contact.get("IDENTIFIER")));
                contactDetails.setValue(StringHelper.returnEmptyStringIfNull(contact.get("CONTACT_VALUE")));
                contactsType.setCode(StringHelper.returnEmptyStringIfNull(contact.get("CONTACT_CODE")));
                contactsName.setId(Integer.parseInt(contact.get("CONTACT_NAME_ID").toString()));
                contactDetails.setName(contactsName);
                contactDetails.setType(contactsType);
                contactDetailsList.add(contactDetails);
            }
            physicalStorePayLoad.setContacts(contactDetailsList);
        }
        if(keyword != null && keyword.contains("openingtimes")){
            physicalStorePayLoad.setOpeningTimes(prepareStoreOpeningHoursData("update"));
        }


        return physicalStorePayLoad;
    }

}
