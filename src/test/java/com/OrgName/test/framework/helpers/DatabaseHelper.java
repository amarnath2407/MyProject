package com.salmon.test.framework.helpers;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DatabaseHelper {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseHelper.class);
    private static final String jdbcUrl;
    private static final String jdbcDriver;
    private static final String jdbcUser;
    private static final String jdbcPwd;
    private static final String jdbcSchema;
    private static final String RUN_CONFIG_PROPERTIES = "/environment.properties";
    private static Connection conn = null;
    private static QueryRunner run;


    static {
        Props.loadRunConfigProps(RUN_CONFIG_PROPERTIES);

        jdbcUrl = Props.getProp("jdbcUrl");
        jdbcDriver = Props.getProp("jdbcDriver");
        jdbcUser = Props.getProp("jdbcUser");
        jdbcPwd = Props.getProp("jdbcPwd");
        jdbcSchema = Props.getProp("jdbcSchema");

    }

    protected static Connection setUpConnection() {

        try {
            DbUtils.loadDriver(jdbcDriver);
            conn =  DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPwd);
            conn.createStatement().execute("ALTER SESSION SET CURRENT_SCHEMA = "+jdbcSchema+"");

        } catch (SQLException se) {
            LOG.info(se.getMessage());
        }
        return conn;
    }

    /**
     * Executes the sql Query and returns the results in list format
     *
     * @param sqlQuery Specify sql query in String format
     */
    public static List executeQuery(String sqlQuery) throws SQLException {
        try {
            conn = setUpConnection();
            return getQueryRunner().query(conn, sqlQuery, new MapListHandler());
        } catch (SQLException e) {
            LOG.error("Exception",e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        return null;
    }

    public static List<Map<String,Object>> executeDatabaseQuery(String sqlQuery) throws SQLException {
        List<Map<String,Object>> resultMap = null;
        try {
            conn = setUpConnection();
            resultMap = getQueryRunner().query(conn, sqlQuery, new MapListHandler());
            return resultMap;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }


    public static <T> List<T> executeQueryToObject(Class<T> c, String query) {
        conn = setUpConnection();
        ResultSetHandler<List<T>> h = new BeanListHandler<T>(c);
        try {
            return getQueryRunner().query(
                    conn, query, h);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> executeParamQueryToObject(Class<T> c, String query, String... params) {
        conn = setUpConnection();
        ResultSetHandler<List<T>> h = new BeanListHandler<T>(c);
        try {
            return getQueryRunner().query(
                    conn, query, h, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static QueryRunner getQueryRunner() {
        return new QueryRunner();
    }
}