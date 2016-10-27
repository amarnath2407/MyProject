package com.salmon.test.framework.helpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.enums.TestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Every Api Step definition class should extend this class
 */

public class ApiHelper {
    private static final Logger log = LoggerFactory.getLogger(ApiHelper.class);
    private static Gson gson;
    static {
         RestAssured.baseURI = UrlBuilder.getBasePathURI().toString();
    }

    protected static RequestSpecification givenCoreMediaConfig() {
        RestAssured.baseURI = UrlBuilder.getCoreMediaBasePathURI().toString();
        RestAssured.useRelaxedHTTPSValidation();
        return given().urlEncodingEnabled(false).
                header("Accept-Language", "en").header("Content-Type", "application/json");
    }

    protected static RequestSpecification givenConfig() {
        RestAssured.useRelaxedHTTPSValidation();
        int portNumber = TestConstants.HTTP_PORT.getIntValue();
        return given().urlEncodingEnabled(false).
                header("Accept-Language", "en").header("Content-Type", "application/json");
    }

    /**
     *  Change protocol
     * @param newProtocol
     */
    public static void ChangeProtocol( String newProtocol) {
        if ( newProtocol.equals("http") ) {
            RestAssured.baseURI = RestAssured.baseURI.replace("https:", "http:");
        } else {
            RestAssured.baseURI = RestAssured.baseURI.replace("http:", "https:");
        }
    }

    /**
     * Internal rewrite from store identifier to store id
     * @param identifier
     * @return store id
     */
    public static String StoreIdentifierToStoreId(String identifier) {
        try {
            String sqlQuery = DatabaseQueries.GET_STORE_ID.getQuery().replace("|identifier|", identifier);
            log.trace("sqlQuery StoreIdentifierToStoreId:"+sqlQuery);
            List<Map<String,Object>> databaseResult = DatabaseHelper.executeDatabaseQuery(sqlQuery);
            return databaseResult.get(0).get("STOREENT_ID").toString();
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    //Specify all one time default Gson config
    public static Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
     // if uncommented will also create Json for fields which are null
     //   gsonBuilder.serializeNulls();
        gson = gson(gsonBuilder);
        return gson;
    }

    //Custom Gson config to override Default Gson  configuration
    public static Gson gson(GsonBuilder gsonBuilder) {
        gson = gsonBuilder.create();
        return gson;
    }

    /**
     * @Description This is to get the Response expectations for all get Requests
     * @return ResponseSpecBuilder
     */
    protected ResponseSpecification getResponseSpecBuilder(){
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(Integer.parseInt(TestConstants.STATUS_CODE_GET_OK.getStringValue()));
        builder.expectContentType(TestConstants.CONTENT_TYPE_JSON.getStringValue());
        return builder.build();
    }

    protected static ResponseSpecification responseSpecBuilder(String responseCode){
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(Integer.parseInt(responseCode));
        builder.expectContentType(TestConstants.CONTENT_TYPE_JSON.getStringValue());
        return builder.build();
    }
    /**
     * This is to get the response object byt checking whether the query parameters exist or not
     * @param url ( Endpoint url)
     * @param queryParam ( Query parameters hashmap)
     * @return Rest assured response object
     */
    protected Response getResponse(String url,Map<String,String> queryParam){
        if(queryParam == null){
            return givenConfig().log().all().when().get(url);
        }else{
            return givenConfig().queryParams(queryParam).log().all()
                    .when().get(url);
        }
    }

    /**
     * This is to get the response object byt checking whether the query parameters exist or not
     * @param url ( Endpoint url)
     * @param queryParam ( Query parameters hashmap)
     * @param isEncodingRequired (Encoding required)
     * @return Rest assured response object
     */
    protected Response getResponse(String url,Map<String,String> queryParam,boolean isEncodingRequired){
        if(queryParam == null){
            return givenConfig().urlEncodingEnabled(isEncodingRequired).log().all().when().get(url);
        }else{
            return givenConfig().urlEncodingEnabled(isEncodingRequired).queryParams(queryParam).log().all()
                    .when().get(url);
        }
    }


}