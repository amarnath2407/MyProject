package com.salmon.test.sql.store;

import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.models.api.storelocator.Language;
import com.salmon.test.sql.storelocator.StoreLocatorDAO;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.salmon.test.framework.helpers.ApiHelper.StoreIdentifierToStoreId;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 03/08/2016.
 */
public class StoreDAO extends DatabaseHelper {

    private static final Logger log = LoggerFactory.getLogger(StoreDAO.class);

    private StoreLocatorDAO storeLocatorDAO;
    public StoreDAO(StoreLocatorDAO storeLocatorDAO) {
        this.storeLocatorDAO = storeLocatorDAO;
    }
    /**
     * @description This is to get the default Language for a physical store
     * @param storeIdentifier
     * @return Language Bean for the store with details
     * @throws Exception
     */
    public Language getDefaultLanguageForStore(String storeIdentifier) throws Exception{
        ResultSetHandler<Language> languageBean = new BeanHandler<Language>(Language.class);
        String sqlQuery = DatabaseQueries.SELECT_LANG_FOR_STORE.getQuery().replace("|STORE_ID|",storeIdentifier);
        Connection conn = null;
        try {
            conn = setUpConnection();
            return getQueryRunner().query(conn, sqlQuery , languageBean);
        } catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    /**
     * @description This is to get the language Id based on the Locale Name
     * @param localeName
     * @return Language Id as String for the passed locale name
     * @throws Exception
     */
    public int getLanguageIdByLocaleName(String localeName) throws Exception{
        ScalarHandler handler = new ScalarHandler();
        String sqlQuery = DatabaseQueries.GET_LANGUAGE_ID_BY_LOCALE_NAME.getQuery().replace("|LOCALE_NAME|",localeName);
        Connection conn = null;
        try {
            conn = setUpConnection();
            BigDecimal langId = (BigDecimal)getQueryRunner().query(conn, sqlQuery , handler);
            if(langId == null){ langId = new BigDecimal(0);}
            return langId.intValue();
        } catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return 0;
    }

    /**
     * This is to get Countries for store from database
     * @param storeIdentifier ( Store Identifier)
     * @return
     */
    public List<Map<String,Object>> getCountriesForStore(String storeIdentifier,String languageId){

        try {
            String sqlQuery = DatabaseQueries.GET_COUNTRIES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId);
            log.trace("SqlQuery getCountriesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the distinct countries count for specific store
     * @param storeIdentifier
     * @param languageId
     * @return
     */
    public List<Map<String,Object>> getTotalCountriesForStore(String storeIdentifier, String languageId){

        try {
            String sqlQuery = DatabaseQueries.GET_TOTAL_COUNTRIES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId);
            log.trace("SqlQuery getCountriesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.info("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the distinct regions count for specific store
     * @param storeIdentifier
     * @param regionCode
     * @param languageId
     * @return
     */
    public List<Map<String,Object>> getRegionsCountForStore(String storeIdentifier,String regionCode, String languageId){
        String sqlQuery = null;
        try {
            if(regionCode == null) {
                sqlQuery = DatabaseQueries.GET_REGIONS_COUNT_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                        replace("|LANGUAGE_ID|", languageId);
            }else{
                sqlQuery = DatabaseQueries.GET_COUNTRIES_BY_REGION_COUNT_FOR_STORE.getQuery().replace("|STORE_ID|",storeIdentifier).
                        replace("|LANGUAGE_ID|", languageId).replace("|REGION_CODE|",regionCode);
            }
            log.trace("SqlQuery getRegionsForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Retrieve the cities for the specific country per store
     * @param storeIdentifier store identifier
     * @param countryCode Country Id
     * @param languageId language id
     * @return list of map with country and city details
     */
    public List<Map<String,Object>> getCitiesByCountryForStore(String storeIdentifier,String countryCode,String languageId){

        try {
            String sqlQuery = DatabaseQueries.GET_CITIES_BY_COUNTRY_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId).replace("|COUNTRY_CODE|", countryCode);
            log.trace("SqlQuery getCitiesByCountryForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * This is to get regions for store from database
     * @param storeIdentifier ( Store Identifier)
     * @return list of map with regions,countries and city details
     */
    public List<Map<String,Object>> getRegionsForStore(String storeIdentifier,String languageId){

        try {
            String sqlQuery = DatabaseQueries.GET_REGIONS_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId);
            log.trace("sqlQuery getRegionsForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get all countries in one region for a specific store
     * @param storeIdentifier store identifier
     * @param regionCode Region code
     * @param languageId Laguage Id
     * @return list of map with countries and city details for one region
     */
    public List<Map<String,Object>> getCountriesByRegionForStore(String storeIdentifier,String regionCode , String languageId){

        try {
            String sqlQuery = DatabaseQueries.GET_COUNTRIES_BY_REGION_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId).replace("|REGION_CODE|",regionCode);
            log.trace("sqlQuery getCountriesByRegionForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get store attributes for number of stores
     * @return list of map with attributes for list of stores
     */
    public List<Map<String,Object>> getStoreAttributes(String storeIdentifier){
        String sqlQuery = null;
        try {
            sqlQuery = DatabaseQueries.GET_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_IDS|", storeIdentifier);
            log.trace("sqlQuery getStoreAttributes:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the list of countries with cities for the store with the list of attributes
     * @param storeIdentifier store identifier
     * @param attributes attributes with key attribute identifier and value as attribute value identifier
     * @param languageId language id
     * @return list of maps with countries and city details for specific store
     */
    public List<Map<String,Object>> getCountriesWithAttributesForStore(String storeIdentifier,Map<String,String> attributes,String languageId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_COUNTRIES_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId));
            for (Map.Entry<String, String> entry : attributes.entrySet())
            {
                log.info(entry.getKey() + "/" + entry.getValue());
                sqlQuery.append(" "+DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeIdentifier)
                        .replace("|ATTR_IDENTIFIER|",entry.getKey()).replace("|ATTRVAL_IDENTIFIER|",entry.getValue())+" ");
                sqlQuery.append(" INTERSECT ");
            }
            sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
            sqlQuery.append(" ) order by countryName,city");
            log.trace("sqlQuery getCountriesWithAttributesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the distinct countries count for a store  by passing list of attributes
     * @param storeIdentifier store identifier
     * @param attributes attributes with key attribute identifier and value as attribute value identifier
     * @param languageId language id
     * @return countires count
     */
    public List<Map<String,Object>> getCountriesCountWithAttributesForStore(String storeIdentifier,Map<String,String> attributes, String languageId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_COUNTRIES_COUNT_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId));
            for (Map.Entry<String, String> entry : attributes.entrySet())
            {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                sqlQuery.append(" "+DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeIdentifier)
                        .replace("|ATTR_IDENTIFIER|",entry.getKey()).replace("|ATTRVAL_IDENTIFIER|",entry.getValue())+" ");
                sqlQuery.append(" INTERSECT ");
            }
            sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
            sqlQuery.append(" )");
            log.trace("sqlQuery getCountriesCountWithAttributesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get all cities within one country for a store with attributes
     * @param storeIdentifier store identifier
     * @param countryCode Country code
     * @param attributes attributes with key attribute identifier and value as attribute value identifier
     * @param languageId language Id
     * @return list of maps with cities for country
     */
    public List<Map<String,Object>> getCitiesByCountryWithAttributesForStore(String storeIdentifier,String countryCode,Map<String,String> attributes,String languageId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_CITIES_BY_COUNTRY_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|",storeIdentifier).
                    replace("|LANGUAGE_ID|", languageId).replace("|COUNTRY_CODE|",countryCode));
            for (Map.Entry<String, String> entry : attributes.entrySet())
            {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                sqlQuery.append(" "+DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeIdentifier)
                        .replace("|ATTR_IDENTIFIER|",entry.getKey()).replace("|ATTRVAL_IDENTIFIER|",entry.getValue())+" ");
                sqlQuery.append(" INTERSECT ");
            }
            sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
            sqlQuery.append(" ) order by countryName,city");
            log.trace("sqlQuery getCitiesByCountryWithAttributesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the list of regions with countries and cities for store after filtering with the list of attributes passed
     * @param storeIdentifier store identifier
     * @param attributes attributes with key attribute identifier and value as attribute value identifier
     * @param regionCode Region Code
     * @param languageId language Id
     * @return list of maps with regions, countries and cities
     */
    public List<Map<String,Object>> getRegionsWithAttributesForStore(String storeIdentifier,String regionCode,Map<String,String> attributes,String languageId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            if(regionCode == null) {
                sqlQuery.append(DatabaseQueries.GET_REGIONS_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                        replace("|LANGUAGE_ID|", languageId));
            }else{
                sqlQuery.append(DatabaseQueries.GET_COUNTRIES_BY_REGION_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                        replace("|LANGUAGE_ID|", languageId).replace("|REGION_CODE|",regionCode));
            }
            for (Map.Entry<String, String> entry : attributes.entrySet())
            {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                sqlQuery.append(" "+DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeIdentifier)
                        .replace("|ATTR_IDENTIFIER|",entry.getKey()).replace("|ATTRVAL_IDENTIFIER|",entry.getValue())+" ");
                sqlQuery.append(" INTERSECT ");
            }
            sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
            sqlQuery.append(" ) order by regionName,countryName,city");
            log.trace("sqlQuery getRegionsWithAttributesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the regions count with attributes for a store
     * @param storeIdentifier store identifier
     * @param attributes attributes with key attribute identifier and value as attribute value identifier
     * @param languageId language Id
     * @return list of maps with regions count
     */
    public List<Map<String,Object>> getRegionsCountWithAttributesForStore(String storeIdentifier,String regionCode,Map<String,String> attributes,String languageId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            if(regionCode == null) {
                sqlQuery.append(DatabaseQueries.GET_REGIONS_COUNT_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                        replace("|LANGUAGE_ID|", languageId));
            }else{
                sqlQuery.append(DatabaseQueries.GET_REGIONS_COUNT_BY_REGION_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|", storeIdentifier).
                        replace("|LANGUAGE_ID|", languageId).replace("|REGION_CODE|",regionCode));
            }
            for (Map.Entry<String, String> entry : attributes.entrySet())
            {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                sqlQuery.append(" "+DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeIdentifier)
                        .replace("|ATTR_IDENTIFIER|",entry.getKey()).replace("|ATTRVAL_IDENTIFIER|",entry.getValue())+" ");
                sqlQuery.append(" INTERSECT ");
            }
            sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
            sqlQuery.append(" )");
            log.trace("sqlQuery getCountriesCountWithAttributesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the Store catalog asset Id for the eSiteId passed
     * @param eSiteIdentifier
     * @return Store catalog asset id as a String
     */
    public String getStoreCatalogAssetId(String eSiteIdentifier){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_STORE_CATALOG_ASSET_ID.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(eSiteIdentifier)));
            log.trace("sqlQuery getStoreCatalogAssetId:"+sqlQuery);
            List<Map<String,Object>> storeCatalogAssetList= DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
            Map<String,Object> storeCatagoryAssetMap = storeCatalogAssetList.get(0);
            return storeCatagoryAssetMap.get("RELATEDSTORE_ID").toString();
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get all store casId's for all esite id's
     * @return List of hashMap with store cas id's for all esite ids
     */
    public Map<String,String> getStoreCatalogAssetIdsMap(){
        StringBuilder sqlQuery =  new StringBuilder();
        Map<String,String> catalogAssetMap = new HashMap<String,String>();
        try {
            sqlQuery.append(DatabaseQueries.GET_STORE_CATALOG_ASSET_ID_MAP.getQuery());
            log.info("sqlQuery getStoreCatalogAssetIdsMap:"+sqlQuery);
            List<Map<String,Object>> storeCatalogAssetList= DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
            for(Map<String,Object> storeCatalogAssetMap:storeCatalogAssetList){
                catalogAssetMap.put(storeCatalogAssetMap.get("STORE_ID").toString(),storeCatalogAssetMap.get("STORECAS_ID").toString());
            }
            return catalogAssetMap;
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get all store id's for store identifiers
     * @return maps with store identifier as key ans store id as value
     */
    public Map<String,String> getStoreIdsMap(){
        StringBuilder sqlQuery =  new StringBuilder();
        Map<String,String> catalogAssetMap = new HashMap<String,String>();
        try {
            sqlQuery.append(DatabaseQueries.GET_STORE_ID_MAP.getQuery());
            log.info("sqlQuery getStoreIdsMap:"+sqlQuery);
            List<Map<String,Object>> storeIdList= DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
            for(Map<String,Object> storeIdMap:storeIdList){
                if(storeIdMap.get("STORE_IDENTIFIER") !=null && storeIdMap.get("STORE_ID") != null) {
                    catalogAssetMap.put(storeIdMap.get("STORE_IDENTIFIER").toString(), storeIdMap.get("STORE_ID").toString());
                }
            }
            return catalogAssetMap;
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }


    /**
     * Description - Get list of Attributes for a store with a given storeIdentifier and Language ID
     * @param storeId - Store CAS ID
     * @param stlocattrField - Store attribute field value
     * @param languageIdentifier - Language ID
     * @return - List of attributes for a store
     */
    public List<Map<String,Object>> getStoreAttributesWithLanguage(String storeId, String stlocattrField, String languageIdentifier){
        String sqlQuery = null;
        try {
            sqlQuery = DatabaseQueries.GET_ATTRIBUTES_FOR_STORE_WITH_LANGUAGE.getQuery().
                    replace("|STORECAS_ID|", storeId).replace("|LANGUAGE_ID|", languageIdentifier).
                    replace("|STLOCATTR_FIELD1|", stlocattrField);
            log.trace("sqlQuery getStoreAttributes:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Description - Get count of list of Attributes for a store with a given storeIdentifier and Language ID
     * @param storeId - Store CAS ID
     * @param stlocattrField - Store attribute field value
     * @param languageIdentifier - Language ID
     * @return - Count of list of attributes for a store
     */
    public List<Map<String,Object>> getStoreAttributesWithLanguageCount(String storeId, String stlocattrField, String languageIdentifier){
        String sqlQuery = null;
        try {
            sqlQuery = DatabaseQueries.GET_ATTRIBUTES_COUNT_FOR_STORE_WITH_LANGUAGE.getQuery().
                    replace("|STORECAS_ID|", storeId).replace("|LANGUAGE_ID|", languageIdentifier).
                    replace("|STLOCATTR_FIELD1|", stlocattrField);
            log.trace("SqlQuery getStoreAttributesCount:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.info("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Description - Check language supported for store
     * @param storeId
     * @param languageId
     * @return - Count (integer)
     * @throws Exception
     */
    public int checkLanguageSupportedForStore(String storeId,String languageId) throws Exception{
        ScalarHandler handler = new ScalarHandler();
        String sqlQuery = DatabaseQueries.CHECK_LANGUAGE_SUPPORTED_FOR_STORE.getQuery().replace("|STORE_ID|",storeId).
                replace("|LANGUAGE_ID|",languageId);
        Connection conn = null;
        try {
            conn = setUpConnection();
            BigDecimal languageCount = (BigDecimal)getQueryRunner().query(conn, sqlQuery , handler);
            if(languageCount == null){ languageCount = new BigDecimal(0);}
            return languageCount.intValue();
        } catch (SQLException e) {
            log.error("Exception while getting data from database: ",e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return 0;
    }


    /**
     * Description - Getting Latitude and Longitude for a given store
     * @param storeIdentifier - Store CAS ID
     * @return - List of stores
     */
    public List<Map<String,Object>> getLatitudeAndLongitudeForStore(String storeIdentifier){
        String sqlQuery = null;
        try {
            sqlQuery = DatabaseQueries.GET_LATITUDE_AND_LONGITUDE_FOR_STORE.getQuery().
                    replace("|STORECAS_ID|", storeIdentifier);
            log.trace("SqlQuery getStoreAttributesCount:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Description - Getting count of the physical stores returned
     * @param storeIdentifier
     * @param queryParamDB
     * @return Count
     */
    public List<Map<String,Object>> getPhysicalStoresCountForGivenLatitudeAndLongitude(String storeIdentifier, Map<String, String> queryParamDB){
        String sqlQuery = null;
        try {
            sqlQuery = DatabaseQueries.GET_PHYSICAL_STORE_COUNT_FOR_GIVEN_LATITUDE_AND_LONGITUDE.getQuery().
                    replace("|STORECAS_ID|", storeIdentifier.replace("|LATITUDE|", queryParamDB.get("Latitude")).
                            replace("|LONGITUDE|", queryParamDB.get("Longitude")).replace("|IS_ACTIVE|", queryParamDB.get("Active")).
                            replace("|RADIUS|", queryParamDB.get("Radius")).replace("|OFFSET|", queryParamDB.get("Offset")).
                            replace("|LIMIT|", queryParamDB.get("Limit")).replace("|RADIUS_UOM|", queryParamDB.get("RadiusUom")));
            log.trace("SqlQuery getStoreAttributesCount:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery);
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the Catalog Id's for store CAS id
     * @return maps with key as Store CAS id and values as Catalog Id
     */
    public Map<String,String> getCatalogIdsMap(){
        StringBuilder sqlQuery =  new StringBuilder();
        Map<String,String> catalogMap = new HashMap<String,String>();
        try {
            sqlQuery.append(DatabaseQueries.GET_CATALOG_ID_FOR_BRANDS.getQuery());
            log.info("sqlQuery getCatalogIdsMap:"+sqlQuery);
            List<Map<String,Object>> catalogIdList= DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
            for(Map<String,Object> catalogIdMap:catalogIdList){
                if(catalogIdMap.get("CATALOG_ID") !=null && catalogIdMap.get("STOREENT_ID") != null) {
                    catalogMap.put(catalogIdMap.get("STOREENT_ID").toString(), catalogIdMap.get("CATALOG_ID").toString());
                }
            }
            return catalogMap;
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }


}