package com.salmon.test.sql.products;

import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.sql.storelocator.StoreLocatorDAO;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Developer on 14/10/2016.
 */
public class ProductDAO extends DatabaseHelper{

    private static final Logger log = LoggerFactory.getLogger(ProductDAO.class);

    /**
     * Get the Categoris for the brand Catalog id with the offset and no of rows specified
     * @param catalogId Catalog id
     * @param offset offset ( which row to start)
     * @param noOfRows no of rows to return
     * @return List of Maps with Category Ids
     */
    public List<Map<String,Object>> getCategoriesForBrand(String catalogId,String offset,String noOfRows){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_CATEGORIES_FOR_CATALOG_ID.getQuery().replace("|CATALOG_D|", catalogId).
                    replace("|OFFSET|", offset).replace("|NO_OF_ROWS|", noOfRows));
            log.debug("sqlQuery getCategoriesForBrand:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.info("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    /**
     * Get the list of products under CatalogEntry view without SKU
     * @param catalogId catalog Id
     * @param categoryId Category id
     * @param languageId language Id
     * @return List of Maps with Products information
     */
    public List<Map<String,Object>> getProductsForCategoryWithoutPagination(String catalogId,String categoryId,String languageId){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PRODUCTS_FOR_CATEGORY_WITHOUT_PAGINATION.getQuery().replace("|CATALOG_ID|", catalogId).
                    replace("|CATEGORY_ID|", categoryId).replace("|LANGUAGE_ID|", languageId));
            log.debug("sqlQuery getProductsForCategory:"+sqlQuery);
            return getProductDetails(sqlQuery.toString());
        }catch (SQLException e) {
            log.info("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }catch (Exception e) {
            log.info("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }
    public List<Map<String,Object>> getProductsForCategoryWithPagination(String catalogId,String categoryId,String languageId,String pageSize,String pageNumber){
        StringBuilder sqlQuery =  new StringBuilder();
        try {
            sqlQuery.append(DatabaseQueries.GET_PRODUCTS_FOR_CATEGORY_WITH_PAGINATION.getQuery().replace("|CATALOG_D|", catalogId).
                    replace("|CATEGORY_ID|", categoryId).replace("|LANGUAGE_ID|", languageId));
            log.debug("sqlQuery getProductsForCategory:"+sqlQuery);
            return DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        }catch (SQLException e) {
            log.info("Exception while getting data from database: "+e);
            assertThat(false).as("Exception while getting data from database: "+e).isTrue();
        }
        return null;
    }

    private List<Map<String,Object>> getProductDetails(String sqlQuery) throws Exception {
        ResultSetHandler<List<Map<String,Object>>> productDescriptionWcsModelBean = new ResultSetHandler<List<Map<String,Object>>>() {
            public List<Map<String,Object>> handle(ResultSet rs) throws SQLException {
                List<Map<String,Object>> productsList = new ArrayList<Map<String,Object>>();
                while (rs.next()) {
                    Map<String,Object> productsMap = new HashMap<String,Object>();
                    String partNumber = rs.getString("PARTNUMBER");
                    String mfpartNumber = rs.getString("MFPARTNUMBER");
                    String field4 = rs.getString("FIELD4");
                    Clob extendedNameClob = rs.getClob("EXTENDEDNAME");
                    productsMap.put("MFPARTNUMBER",mfpartNumber);
                    productsMap.put("PARTNUMBER",partNumber);
                    productsMap.put("FIELD4",field4);
                    productsMap.put("EXTENDEDNAME",extendedNameClob.getSubString(1, (int) extendedNameClob.length()));
                    productsList.add(productsMap);
                }
                return productsList;
            }
        };
        try {
            return getQueryRunner().query(setUpConnection(), sqlQuery, productDescriptionWcsModelBean);
        } catch (SQLException e) {
            log.error("Exception",e);
        }
        return null;
    }
}
