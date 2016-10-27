package com.salmon.test.dataload.product;

import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.models.dataload.wcs.product.ProductDescriptionWcsModel;
import org.apache.commons.dbutils.ResultSetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Veeranank on 05/09/2016.
 */
public class ProductDescriptionSQL extends DatabaseHelper {

    private static final Logger log = LoggerFactory.getLogger(ProductDescriptionSQL.class);

    public List<ProductDescriptionWcsModel> getProductDescriptionDetails(String sqlQuery) throws Exception {
        ResultSetHandler<List<ProductDescriptionWcsModel>> productDescriptionWcsModelBean = new ResultSetHandler<List<ProductDescriptionWcsModel>>() {
            public List<ProductDescriptionWcsModel> handle(ResultSet rs) throws SQLException {
                List<ProductDescriptionWcsModel> productDescriptionWcsModelList = new ArrayList<ProductDescriptionWcsModel>();
                while (rs.next()) {
                    ProductDescriptionWcsModel productDescriptionWcsModel = new ProductDescriptionWcsModel();
                    String languageId = rs.getString("LANGUAGE_ID");
                    String partNumber = rs.getString("PARTNUMBER");
                    Clob extendedNameClob = rs.getClob("EXTENDEDNAME");
                    Clob editorialDescriptionClob =rs.getClob("EDITORIALDESCRIPTION");
                    Clob detailsAndCareClob = rs.getClob("DETAILSANDCARE");
                    Clob sizeAndFitClob = rs.getClob("SIZEANDFIT");
                    Clob longDesriptionClob = rs.getClob("LONGDESCRIPTION");
                    String published = rs.getString("PUBLISHED");
                    productDescriptionWcsModel.setLanguageId(languageId);
                    productDescriptionWcsModel.setPartNumber(partNumber);
                    productDescriptionWcsModel.setExtendedName(extendedNameClob.getSubString(1, (int) extendedNameClob.length()));
                    productDescriptionWcsModel.setEditorialDescription(editorialDescriptionClob.getSubString(1, (int) editorialDescriptionClob.length()));
                    productDescriptionWcsModel.setDetailsAndCare(detailsAndCareClob.getSubString(1, (int) detailsAndCareClob.length()));
                    productDescriptionWcsModel.setSizeAndFit(sizeAndFitClob.getSubString(1, (int) sizeAndFitClob.length()));
                    productDescriptionWcsModel.setLongDescription(longDesriptionClob.getSubString(1, (int) longDesriptionClob.length()));
                    productDescriptionWcsModel.setPublished(published);
                    productDescriptionWcsModelList.add(productDescriptionWcsModel);
                }
                return productDescriptionWcsModelList;
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
