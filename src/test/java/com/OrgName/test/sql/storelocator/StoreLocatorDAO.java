package com.salmon.test.sql.storelocator;

import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.models.api.storelocator.Attribute;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 13/07/2016.
 */
public class StoreLocatorDAO extends DatabaseHelper {

    private static final Logger log = LoggerFactory.getLogger(StoreLocatorDAO.class);

    public List<Attribute> getStoreLocatorAttributes(String sqlQuery, List queryInputs) throws Exception{
        ResultSetHandler<List<Attribute>> storeLocatorAttributeBean = new BeanListHandler<Attribute>(Attribute.class);
        try {
            return getQueryRunner().query(setUpConnection(), sqlQuery, storeLocatorAttributeBean,queryInputs.get(0),queryInputs.get(1));
        } catch (SQLException e) {
            log.error("Exception",e);
        }
        return null;
    }

    /**
     * @description This is to get the List of map objects for any query you execute
     * @param sqlQuery
     * @return list of maps
     * @throws Exception
     */
    public List<Map<String, Object>> getMapListFromDB(String sqlQuery) throws Exception{
        List<Map<String, Object>> map = null;
        Connection conn = null;
        try {
            conn = setUpConnection();
            map = getQueryRunner().query(conn, sqlQuery, new MapListHandler());
            return map;
        } catch (SQLException e) {
            log.error("Exception",e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    public List<Map<String,Object>> getStoreData(String storeId, String languageId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_REGIONS_COUNT_WITH_ATTRIBUTES_FOR_STORE.getQuery().replace("|STORE_ID|", storeId).
                    replace("|LANGUAGE_ID|", languageId));
            sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
            sqlQuery.append(" )");
            log.debug("sqlQuery getCountriesCountWithAttributesForStore:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.info("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the Physical stores basic Information
     * @param storeCASId
     * @param noofStores
     * @return list of maps with store data
     */
    public List<Map<String,Object>> getPhysicalStoresForSite(String storeCASId,String noofStores,String isActive,String queryParameter,Map<String,String> queryParameterValues){
        StringBuilder sqlQuery =  new StringBuilder();
        int pageSizeOffset = 1;
        String offset = "0";
        try {
            if(noofStores != null && queryParameterValues.get("pageNumber") != null){
                pageSizeOffset = Integer.parseInt(noofStores);
                offset = String.valueOf((Integer.parseInt(queryParameterValues.get("pageNumber"))-1)*pageSizeOffset);
            }
            if(queryParameter == null){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_FOR_SITE.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|NO_OF_ROWS|", noofStores).replace("|IS_ACTIVE|", isActive).replace("|OFFSET|",offset));

            }else if(queryParameter.equals("region")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_WITH_REGION_FOR_SITE.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|NO_OF_ROWS|", noofStores).replace("|IS_ACTIVE|", isActive).replace("|REGION_CODE|",queryParameterValues.get("regionCode")).replace("|OFFSET|",offset));

            }else if(queryParameter.equals("country")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_WITH_COUNTRY_FOR_SITE.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|NO_OF_ROWS|", noofStores).replace("|IS_ACTIVE|", isActive).replace("|COUNTRY_CODE|",queryParameterValues.get("countryCode")).replace("|OFFSET|",offset));

            }else if(queryParameter.equals("city")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_WITH_CITY_FOR_SITE.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|NO_OF_ROWS|", noofStores).replace("|IS_ACTIVE|", isActive).replace("|CITY|",queryParameterValues.get("city")).
                                replace("|COUNTRY_CODE|",queryParameterValues.get("countryCode")).replace("|REGION_CODE|",queryParameterValues.get("regionCode")).replace("|OFFSET|",offset));

            }else if(queryParameter.equals("regionAttributes")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_WITH_REGION_AND_ATTRIBUTES.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|IS_ACTIVE|", isActive).replace("|REGION_CODE|",queryParameterValues.get("regionCode")));
                for (Map.Entry<String, String> entry : queryParameterValues.entrySet())
                {
                    if(!entry.getKey().equals("regionCode") && !entry.getKey().equals("pageNumber")) {
                        log.info(entry.getKey() + "/" + entry.getValue());
                        sqlQuery.append(" " + DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeCASId)
                                .replace("|ATTR_IDENTIFIER|", entry.getKey()).replace("|ATTRVAL_IDENTIFIER|", entry.getValue()) + " ");
                        sqlQuery.append(" INTERSECT ");
                    }
                }
                sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
                sqlQuery.append(" ) order by STORENAME) OFFSET "+offset+" rows fetch next  "+noofStores+" rows only");
            }else if(queryParameter.equals("attributes")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_WITH_ATTRIBUTES.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|IS_ACTIVE|", isActive));
                for (Map.Entry<String, String> entry : queryParameterValues.entrySet())
                {
                    if(!entry.getKey().equals("pageNumber")) {
                        log.info(entry.getKey() + "/" + entry.getValue());
                        sqlQuery.append(" " + DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeCASId)
                                .replace("|ATTR_IDENTIFIER|", entry.getKey()).replace("|ATTRVAL_IDENTIFIER|", entry.getValue()) + " ");
                        sqlQuery.append(" INTERSECT ");
                    }
                }
                sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
                sqlQuery.append(" ) order by STORENAME) OFFSET "+offset+" rows fetch next  "+noofStores+" rows only");
            }else if(queryParameter.equals("countryAttributes")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_WITH_COUNTRY_AND_ATTRIBUTES.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|IS_ACTIVE|", isActive).replace("|COUNTRY_CODE|",queryParameterValues.get("countryCode")).replace("|CITY|",queryParameterValues.get("city")));
                for (Map.Entry<String, String> entry : queryParameterValues.entrySet())
                {
                    if(!entry.getKey().equals("countryCode") && !entry.getKey().equals("city") && !entry.getKey().equals("pageNumber")) {
                        log.info(entry.getKey() + "/" + entry.getValue());
                        sqlQuery.append(" " + DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeCASId)
                                .replace("|ATTR_IDENTIFIER|", entry.getKey()).replace("|ATTRVAL_IDENTIFIER|", entry.getValue()) + " ");
                        sqlQuery.append(" INTERSECT ");
                    }
                }
                sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
                sqlQuery.append(" ) order by STORENAME) OFFSET "+offset+" rows fetch next  "+noofStores+" rows only");
            }else if(queryParameter.equals("regionlimit")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_WITH_REGION_FOR_SITE.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|NO_OF_ROWS|", noofStores).replace("|IS_ACTIVE|", isActive).replace("|REGION_CODE|",queryParameterValues.get("regionCode")).replace("|OFFSET|",offset));
            }else if(queryParameter.equals("boutique")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_INFO_FOR_IDENTIFIER.getQuery().replace("|STORECAS_ID|", storeCASId)
                        .replace("|IS_ACTIVE|", isActive).replace("|STLOC_ID|",queryParameterValues.get("STLOC_ID")));
            }
            log.debug("sqlQuery getPhysicalStoresForSite:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the Physical store basic information
     * @param storeCASId
     * @param languageId
     * @param isActive
     * @return
     */
    public List<Map<String,Object>> getPhysicalStoreInformation(String storeCASId,String languageId,String isActive){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_INFO.getQuery().replace("|STORECAS_ID|", storeCASId)
                    .replace("|LANGUAGE_ID|",languageId).replace("|IS_ACTIVE|",isActive));
            log.debug("sqlQuery getPhysicalStoreInformation:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the Physical store contacts
     * @param storeCASId
     * @param languageId
     * @param isActive
     * @return
     */
    public List<Map<String,Object>> getPhysicalStoreContactInformation(String storeCASId,String languageId,String isActive){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_CONTACT_INFO.getQuery().replace("|STORECAS_ID|", storeCASId)
                    .replace("|LANGUAGE_ID|",languageId).replace("|IS_ACTIVE|",isActive));
            log.debug("sqlQuery getPhysicalStoreContactInformation:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the Physical store attributes
     * @param storeCASId
     * @param languageId
     * @param isActive
     * @return
     */
    public List<Map<String,Object>> getPhysicalStoreAttributes(String storeCASId,String languageId,String isActive){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_ATTRIBUTES.getQuery().replace("|STORECAS_ID|", storeCASId)
                    .replace("|LANGUAGE_ID|",languageId).replace("|IS_ACTIVE|",isActive));
            log.debug("sqlQuery getPhysicalStoreAttributes:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the Physical store attribute values
     * @param storeCASId
     * @param languageId
     * @param isActive
     * @return
     */
    public List<Map<String,Object>> getPhysicalStoreAttributeValues(String storeCASId,String languageId,String isActive){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_ATTRIBUTE_VALUES.getQuery().replace("|STORECAS_ID|", storeCASId)
                    .replace("|LANGUAGE_ID|",languageId).replace("|IS_ACTIVE|",isActive));
            log.debug("sqlQuery getPhysicalStoreAttributeValues:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the all physical store opening times related to one store catalog identifier
     * @param storeCASId
     * @param isActive
     * @return
     */
    public List<Map<String,Object>> getPhysicalStoreOpeningHours(String storeCASId,String isActive){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_OPENING_HOURS.getQuery().replace("|STORECAS_ID|", storeCASId)
                    .replace("|IS_ACTIVE|",isActive));
            log.debug("sqlQuery getPhysicalStoreOpeningHours:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     *
     * @param stloc_id
     * @param DayNumber
     * @return
     */
    public List<Map<String,Object>> getPhysicalStoreTimeSlots(String stloc_id,String DayNumber){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_TIME_SLOTS.getQuery().replace("|STLOC_ID|", stloc_id)
                    .replace("|DAY_NUMBER|",DayNumber));
            log.debug("sqlQuery getPhysicalStoreTimeSlots:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    public List<Map<String,Object>> getPhysicalStoresWithLatitudeAndLongitudeForSite(String storeCASId, String isActive,String queryParam, Map<String, String> queryParameterValues,Map<String, String> queryParameterAttributes){
        StringBuilder sqlQuery = new StringBuilder();
        int pageSizeOffset = 1;
        String offset = "0";
        try {
            if(queryParameterValues.get("pageSize") != null){
                pageSizeOffset = Integer.parseInt(queryParameterValues.get("pageSize"));
                offset = String.valueOf((Integer.parseInt(queryParameterValues.get("pageNumber"))-1)*pageSizeOffset);
            }
            if(queryParam == null) {
                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_FOR_GIVEN_LATITUDE_AND_LONGITUDE.getQuery().
                        replace("|STORECAS_ID|", storeCASId).replace("|LATITUDE|", queryParameterValues.get("Latitude")).
                        replace("|LONGITUDE|", queryParameterValues.get("Longitude")).replace("|IS_ACTIVE|", isActive).
                        replace("|RADIUS|", queryParameterValues.get("Radius")).replace("|OFFSET|", offset).
                        replace("|LIMIT|", queryParameterValues.get("pageSize")).replace("|RADIUS_UOM|", queryParameterValues.get("RadiusUom")));

            }else if(queryParam.equals("radiusAttributes")){

                sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_FOR_GIVEN_LATITUDE_AND_LONGITUDE_WITH_ATTRIBUTES.getQuery().
                        replace("|STORECAS_ID|", storeCASId).replace("|LATITUDE|", queryParameterValues.get("Latitude")).
                        replace("|LONGITUDE|", queryParameterValues.get("Longitude")).replace("|IS_ACTIVE|", isActive).
                        replace("|RADIUS|", queryParameterValues.get("Radius")).replace("|RADIUS_UOM|", queryParameterValues.get("RadiusUom")));
                for (Map.Entry<String, String> entry : queryParameterAttributes.entrySet())
                {
                        log.info(entry.getKey() + "/" + entry.getValue());
                        sqlQuery.append(" " + DatabaseQueries.GET_STLOC_ID_FOR_ATTRIBUTE.getQuery().replace("|STORE_ID|", storeCASId)
                                .replace("|ATTR_IDENTIFIER|", entry.getKey()).replace("|ATTRVAL_IDENTIFIER|", entry.getValue()) + " ");
                        sqlQuery.append(" INTERSECT ");
                }
                sqlQuery.replace(sqlQuery.length()-10,sqlQuery.length(),"");
                sqlQuery.append(" ) ORDER BY distance,STORENAME ASC)OFFSET "+""+offset+" rows fetch next "+queryParameterValues.get("pageSize")+" rows only ");
            }
            log.debug("SqlQuery getPhysicalStoresWithLatitudeAndLongitudeForSite:"+sqlQuery.toString());
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * This is to get the Physical store list for storeCASID
     * @param storeCASId store catalog asset Id
     * @param isActive active flag
     * @return list of Maps with physical stores
     */
    public List<Map<String,Object>> getPhysicalStoresForBrand(String storeCASId,String isActive){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORES_FOR_BRAND.getQuery().replace("|STORECAS_ID|", storeCASId)
                    .replace("|IS_ACTIVE|",isActive));
            log.debug("sqlQuery getPhysicalStoresForBrand:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the basic information for the specific physical store based on identifier
     * @param identifier physical store identifier
     * @return list of map for a physical store
     */
    public List<Map<String,Object>> getPhysicalStoreInformationByIdentifier(String identifier){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_INFO_BY_IDENTIFIER.getQuery().replace("|IDENTIFIER|", identifier));
            log.debug("sqlQuery getPhysicalStoreInformationByIdentifier:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get attributes for a specific brand
     * @param catalogAssetId catalog asset id
     * @return List of maps with attributes
     */
    public List<Map<String,Object>> getPhysicalStoreAttributesFoBrand(String catalogAssetId,String pageNumber,String noOfRows){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_ATTRIBUTES_FOR_BRAND.getQuery().replace("|STORECAS_ID|", catalogAssetId)
                    .replace("|OFFSET|", pageNumber).replace("|NO_OF_ROWS|", noOfRows));
            log.debug("sqlQuery getPhysicalStoreAttributesFoBrand:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get attribute values for specific catalog asset DI awith attribute id
     * @param catalogAssetId catalog asset id
     * @param attributeId attribute id
     * @return List of maps with attribute values for attribute
     */
    public List<Map<String,Object>> getPhysicalStoreAttributeValuesForAttributeWithBrand(String catalogAssetId,String attributeId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_ATTRIBUTES_VALUES_FOR_ATTRIBUTE_WITH_BRAND.getQuery().replace("|STORECAS_ID|", catalogAssetId)
                    .replace("|ATTR_ID|", attributeId));
            log.debug("sqlQuery getPhysicalStoreAttributesFoBrand:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.error("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the attributes for a physical store by physical store identifier
     * @param identifier physical store identifier
     * @return List of maps with attributes
     */
    public List<Map<String,Object>> getPhysicalStoreAttributesByIdentifier(String identifier) {
        StringBuilder sqlQuery = new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_ATTRIBUTES_FOR_PHYSICAL_STORE_BY_IDENTIFIER.getQuery().replace("|IDENTIFIER|", identifier));
            log.debug("sqlQuery getPhysicalStoreAttributesByIdentifier:" + sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Exception while getting data from database: " + e);
            assertThat(false).as("Exception while getting data from database: " + e).isTrue();
        }
        return null;
    }

    /**
     * Get the attribute values for a physical store by physical store identifier for attribute
     * @param identifier physical store identifier
     * @param attributeId attribute Id
     * @return List of maps with attribute values
     */
    public List<Map<String,Object>> getPhysicalStoreAttributeValuesByIdentifier(String identifier,String attributeId) {
        StringBuilder sqlQuery = new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_ATTRIBUTE_VALUES_FOR_PHYSICAL_STORE_BY_IDENTIFIER.getQuery().replace("|IDENTIFIER|", identifier)
                    .replace("|ATTR_ID|", attributeId));
            log.debug("sqlQuery getPhysicalStoreAttributeValuesByIdentifier:" + sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Exception while getting data from database: " + e);
            assertThat(false).as("Exception while getting data from database: " + e).isTrue();
        }
        return null;
    }

    /**
     * Get contacts for physical store by identifier
     * @param identifier physical store identifier
     * @return List of maps with contacts
     */
    public List<Map<String,Object>> getPhysicalStoreContactsByIdentifier(String identifier) {
        StringBuilder sqlQuery = new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_CONTACTS_FOR_PHYSICAL_STORE_BY_IDENTIFIER.getQuery().replace("|IDENTIFIER|", identifier));
            log.debug("sqlQuery getPhysicalStoreContactsByIdentifier:" + sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Exception while getting data from database: " + e);
            assertThat(false).as("Exception while getting data from database: " + e).isTrue();
        }
        return null;
    }

    /**
     * Get contacts with the number of rows passed
     * @param noOfRows no of rows
     * @return list os maps with contact details
     */
    public List<Map<String,Object>> getContactsByRows(String noOfRows,String pageNumber) {
        StringBuilder sqlQuery = new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_CONTACTS_BY_ROWS.getQuery().replace("|NO_OF_ROWS|", noOfRows).replace("|OFFSET|",pageNumber));
            log.debug("sqlQuery getContactsByRows:" + sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Exception while getting data from database: " + e);
            assertThat(false).as("Exception while getting data from database: " + e).isTrue();
        }
        return null;
    }

    public List<Map<String,Object>> getPhysicalStoreOpeningDaysByidentifier(String identifier) {
        StringBuilder sqlQuery = new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PHYSICAL_STORE_OPENING_DAYS_BY_IDENTIFIER.getQuery().replace("|IDENTIFIER|", identifier));
            log.debug("sqlQuery getPhysicalStoreOpeningDaysByidentifier:" + sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Exception while getting data from database: " + e);
            assertThat(false).as("Exception while getting data from database: " + e).isTrue();
        }
        return null;
    }

}
