package com.salmon.test.sql;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.models.database.AddressModel;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class AddressDB extends DatabaseHelper {
    private static final Logger LOG = LoggerFactory.getLogger(AddressDB.class);

    public List<AddressModel> getAddressResults(String sqlQuery) {
        ResultSetHandler<List<AddressModel>>addressBean = new BeanListHandler<AddressModel>(AddressModel.class);
        try {
            return getQueryRunner().query(setUpConnection(), sqlQuery, addressBean);
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }
}