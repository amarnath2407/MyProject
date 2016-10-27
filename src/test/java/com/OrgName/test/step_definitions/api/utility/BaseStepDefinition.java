package com.salmon.test.step_definitions.api.utility;

import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.salmon.test.sql.store.StoreDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akepativ on 08/07/2016.
 */
public class BaseStepDefinition {

    private static Logger logger = LoggerFactory.getLogger(BaseStepDefinition.class);

    public static Map<String,String> storeCASIds = null;
    public static Map<String,String> storeIds = null;
    public static Map<String,String> catalogIds = null;
    private StoreDAO storeDAO;
    public BaseStepDefinition(StoreDAO storeDAO){
        this.storeDAO = storeDAO;
        if(this.storeCASIds == null) {
            logger.info("this.storeCASIds: "+this.storeCASIds);
            this.storeCASIds = storeDAO.getStoreCatalogAssetIdsMap();
            this.storeIds = storeDAO.getStoreIdsMap();
            this.catalogIds = storeDAO.getCatalogIdsMap();
        }
    }
    /**
     * @Description This is to prepare query parameters for any service depends on the keys and values passed to the method
     * @param key
     * @param value
     * @return HashMap with key and value
     */
    protected Map<String,String> prepareQueryParams(String key, String value,String delimiter){
        Map<String,String> queryParams = new HashMap<String,String>();
        String[] keys = key.split(delimiter);
        String[] values = value.split(delimiter);
        for(int i=0;i<keys.length;i++){
            queryParams.put(keys[i],values[i]);
        }
        return queryParams;
    }

    /**
     * @Description This is to prepare the list with the provided comma separated value
     * @param values
     * @return List
     */
    protected List prepareList(String values, String delimiter){
        return Arrays.asList(values.split(delimiter));
    }

    /*
    * Returns JsonPath object
    * First convert the API's response to String type with "asString()" method.
    * Then, send this String formatted json response to the JsonPath class and return the JsonPath
    */
    protected static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        logger.info("returned json: " + json);
        return new JsonPath(json);
    }
}

