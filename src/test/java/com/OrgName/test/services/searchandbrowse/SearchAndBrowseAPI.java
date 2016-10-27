package com.salmon.test.services.searchandbrowse;

import com.google.gson.GsonBuilder;
import com.jayway.restassured.response.Response;
import com.salmon.test.enums.ServiceEndPoints;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.ApiHelper;
import com.salmon.test.framework.helpers.utils.StringHelper;
import com.salmon.test.models.api.ArrayAdapterFactory;
import com.salmon.test.models.api.searchandbrowse.CatalogEntryView;
import com.salmon.test.models.api.searchandbrowse.CatalogGroup;
import com.salmon.test.models.api.searchandbrowse.ProductsByCategoryData;
import com.salmon.test.sql.products.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jelsen on 26/09/2016.
 */

/**
 * Created by jelsen on 26/09/2016.
 */
public class SearchAndBrowseAPI extends ApiHelper {

    private static Logger logger = LoggerFactory.getLogger(SearchAndBrowseAPI.class);
    private ProductDAO productDAO;
    public SearchAndBrowseAPI(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    /**
     * @Description This method returns all top level categories.
     * @param
     * @return response object which is in json/xml format depends on the query parameters
     */
    public CatalogGroup getTopCategories(String storeId, Map<String,String> queryParams, String protocol, String expectedHttpStatusCode){
        Response response = null;
        CatalogGroup catalogGroup = null;
        //Gson gson = new Gson();
        Integer port;
        // null query params cause errors
        if (queryParams == null) {
            queryParams= new HashMap<>();
        }
        // change protocol
        if ( protocol.equals("https") ) {
            ChangeProtocol("https");
            port = TestConstants.HTTPS_PORT.getIntValue();
        } else {
            ChangeProtocol("http");
            port = TestConstants.HTTP_PORT.getIntValue();
        }
        // construct url and call endpoint
        String url = ServiceEndPoints.GET_TOP_CATEGORIES.getUrl().replace("{storeId}",storeId);
        response =givenConfig().log().all()
                .port(port)
                .queryParams(queryParams)
                .when().get(url);
        response.then().spec(responseSpecBuilder(expectedHttpStatusCode));
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
        catalogGroup = gson.fromJson(response.asString(), CatalogGroup.class);
        return catalogGroup;
    }

    public CatalogGroup findCategoryByIdentifier(String storeId, String categoryIdentifier, Map<String,String> queryParams, String protocol, String  expectedHttpStatusCode) {
        Response response = null;
        CatalogGroup catalogGroup = null;
        Integer port;
        // null query params cause errors
        if (queryParams == null) {
            queryParams= new HashMap<>();
        }
        // change protocol
        if ( protocol.equals("https") ) {
            ChangeProtocol("https");
            port = TestConstants.HTTPS_PORT.getIntValue();
        } else {
            ChangeProtocol("http");
            port = TestConstants.HTTP_PORT.getIntValue();
        }
        // construct url and call endpoint
        String url = ServiceEndPoints.GET_CATEGORY_DETAILS_FOR_IDENTIFIER.getUrl().replace("{storeId}",storeId).replace("{categoryIdentifier}",categoryIdentifier);
        response =givenConfig().log().all()
                .port(port)
                .queryParams(queryParams)
                .when().get(url);
        response.then().spec(responseSpecBuilder(expectedHttpStatusCode));
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
        catalogGroup = gson.fromJson(response.asString(), CatalogGroup.class);
        return catalogGroup;
    }

    public CatalogGroup findCategoryByUniqueId(String storeId, String categoryId, Map<String,String> queryParams, String protocol, String  expectedHttpStatusCode) {
        Response response = null;
        CatalogGroup catalogGroup = null;
        Integer port;
        // null query params cause errors
        if (queryParams == null) {
            queryParams= new HashMap<>();
        }
        // change protocol
        if ( protocol.equals("https") ) {
            ChangeProtocol("https");
            port = TestConstants.HTTPS_PORT.getIntValue();
        } else {
            ChangeProtocol("http");
            port = TestConstants.HTTP_PORT.getIntValue();
        }
        // construct url and call endpoint
        String url = ServiceEndPoints.GET_CATEGORY_DETAILS_FOR_ID.getUrl().replace("{storeId}",storeId).replace("{categoryId}",categoryId);
        response =givenConfig().log().all()
                .port(port)
                .queryParams(queryParams)
                .when().get(url);
        response.then().spec(responseSpecBuilder(expectedHttpStatusCode));
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
        catalogGroup = gson.fromJson(response.asString(), CatalogGroup.class);
        return catalogGroup;
    }


    public CatalogGroup findCategoryByUniqueIds(String storeId, Map<String,String> queryParams, String protocol, String  expectedHttpStatusCode) {
        Response response = null;
        CatalogGroup catalogGroup = null;
        Integer port;
        // null query params cause errors
        if (queryParams == null) {
            queryParams= new HashMap<>();
        }
        // change protocol
        if ( protocol.equals("https") ) {
            ChangeProtocol("https");
            port = TestConstants.HTTPS_PORT.getIntValue();
        } else {
            ChangeProtocol("http");
            port = TestConstants.HTTP_PORT.getIntValue();
        }
        // construct url and call endpoint
        String url = ServiceEndPoints.GET_CATEGORY_DETAILS_FOR_IDS.getUrl().replace("{storeId}",storeId);
        response =givenConfig().log().all()
                .port(port)
                .queryParams(queryParams)
                .when().get(url);
        response.then().spec(responseSpecBuilder(expectedHttpStatusCode));
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ArrayAdapterFactory()).create();
        catalogGroup = gson.fromJson(response.asString(), CatalogGroup.class);
        return catalogGroup;
    }
    /**
     * @Description This method returns all products under the category and subcategories by deep search.
     * It does not only return products in the current category.
     * There is no control over the limit of each subcategory under the category facet.
     * @param queryParams
     * @return response object which is in json/xml format depends on the query parameters
     */
    public Response productViewByCategory(String storeId, String categoryId, Map<String,String> queryParams){
        Response response = null;
        try {
            String url = ServiceEndPoints.GET_PRODUCTVIEW_BY_CATEGORY.getUrl().replace("{storeId}",storeId).replace("{categoryId}",categoryId);
            response = getResponse(url,queryParams);
            response.then().spec(getResponseSpecBuilder());
        }catch(Exception e){
            logger.info("Exception while invoking this request: "+e);
            assertThat(false).as("Exception while invoking this request: "+e).isTrue();
        }
        return response;
    }

    /**
     * get the response as an object from products by category id API
     * @param storeIdentifier store identifier
     * @param categoryId category id
     * @param queryParams query params as hashmap
     * @return ProductsByCategoryData with the response from the API
     */
    public ProductsByCategoryData findProductsByCategory(String storeIdentifier, String categoryId, Map<String,String> queryParams){
        Response response = null;
        ProductsByCategoryData productsByCategoryData = null;
        Gson gson = new Gson();
        try {
            String url = ServiceEndPoints.GET_PRODUCTS_BY_CATEGORY.getUrl().replace("{storeIdentifier}",storeIdentifier).replace("{categoryId}",categoryId);
            response = getResponse(url,queryParams);
            response.then().spec(getResponseSpecBuilder());
            productsByCategoryData = gson.fromJson(response.asString(), ProductsByCategoryData.class);
        }catch(Exception e){
            logger.info("Exception while invoking the request: "+e);
            assertThat(false).as("Exception while invoking the request: "+e).isTrue();
        }
        return productsByCategoryData;
    }

    /**
     * This is to process the products data from the database and prepare ProductsByCategoryData object to compare with API response
     * @param catalogId catalogId
     * @param categoryId Category Id
     * @param language language Id
     * @return ProductsByCategoryData object
     */
    public ProductsByCategoryData findProductsByCategoryFromDatabase(String catalogId,String categoryId,String language){

        ProductsByCategoryData productsByCategoryData = new ProductsByCategoryData();
        List<CatalogEntryView> CatalogEntryViewList = null;
        List<Map<String,Object>> productsDataList = null;
         try{
             productsDataList = productDAO.getProductsForCategoryWithoutPagination(catalogId,categoryId,language);
             productsByCategoryData.setRecordSetCount(String.valueOf(productsDataList.size()));
             CatalogEntryViewList = new ArrayList<CatalogEntryView>();
             CatalogEntryView catalogEntryView;
             for(Map productsMap:productsDataList){
                 catalogEntryView = new CatalogEntryView();
                 catalogEntryView.setPartNumber(StringHelper.returnEmptyStringIfNull(productsMap.get("PARTNUMBER")));
                 catalogEntryView.setMfPartNumber(StringHelper.returnEmptyStringIfNull(productsMap.get("MFPARTNUMBER")));
                 catalogEntryView.setField4(StringHelper.returnEmptyStringIfNull(productsMap.get("FIELD4")));
                 catalogEntryView.setExtendedName(StringHelper.returnEmptyStringIfNull(productsMap.get("EXTENDEDNAME")));
                 CatalogEntryViewList.add(catalogEntryView);
             }
             productsByCategoryData.setCatalogEntryView(CatalogEntryViewList);

        }catch(Exception e){
             logger.info("Exception while processing data from database: "+e);
             assertThat(false).as("Exception while processing data from database: "+e).isTrue();

         }
        return productsByCategoryData;
    }


}
