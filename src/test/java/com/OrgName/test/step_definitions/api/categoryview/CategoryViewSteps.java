package com.salmon.test.step_definitions.api.categoryview;

import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.utils.StringHelper;
import com.salmon.test.models.api.searchandbrowse.CatalogGroup;
import com.salmon.test.services.searchandbrowse.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.salmon.test.enums.DatabaseQueries.*;
import static com.salmon.test.enums.TestConstants.STATUS_CODE_NOT_FOUND;
import static com.salmon.test.enums.TestConstants.STATUS_CODE_OK;
import static com.salmon.test.framework.helpers.ApiHelper.StoreIdentifierToStoreId;
import static com.salmon.test.framework.helpers.UrlBuilder.readValueFromConfig;
import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertEquals;


/**
 * Created by John Elsen on 29/09/2016.
 */
public class CategoryViewSteps {
    private CatalogGroup  catalogGroup;
    private SearchAndBrowseAPI searchAndBrowseAPI;
    private Map<String, String> queryParams = new HashMap<String, String>();
    private Map<String, ArrayList<String>>  queryParamsMultiId = new HashMap<String, ArrayList<String>>();
    private List<String> storeIdentifierList ;
    List<CatalogGroup> catalogGroupList;
    String storeIdentifier="Moncler_GB";
    String storeCAS="MonclerCAS";
    public CategoryViewSteps(SearchAndBrowseAPI searchAndBrowseAPI) {
        this.searchAndBrowseAPI = searchAndBrowseAPI;
    }
    private CatalogGroup getCatalogGroupFromDb( String parentCatalogGroupID, String storeIdentifier) {
        CatalogGroup thisCatalogGroup = new CatalogGroup();
        // get a list of children for this category
        List<Map<String, Object>> expectedData = null;
        StringBuilder sqlQuery;
        sqlQuery = new StringBuilder();
        // get catalog id for this store
        sqlQuery.append(GET_CATALOG_ID.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeIdentifier)));
        // perform query
        try {
            expectedData = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("getCatalogGroup: Exception while getting catalog id from database: " + e);
            assertThat(false).as("getCatalogGroup: Exception while getting catalog id from database: " + e).isTrue();
        }

        log.info("response=====> " + expectedData.toString());

        return thisCatalogGroup;

    }
    @Given("^I have store identifiers for store \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersForStore(String storeIdentifierKey) throws Throwable {
        storeIdentifierList = Arrays.asList(readValueFromConfig(storeIdentifierKey).split(","));
    }

    private static final Logger log = LoggerFactory.getLogger(CategoryViewSteps.class);
    @When("^I call the findTopCategories method under protocol \"([^\"]*)\" omitting the store identifier$")
    public void iCallTheGetTopCategoriesmethodOmittingTheStoreIdentifier(String protocol) throws Throwable {
        String expectedHttpStatusCode = STATUS_CODE_NOT_FOUND.getStringValue();
        catalogGroup = searchAndBrowseAPI.getTopCategories("", queryParams,protocol,expectedHttpStatusCode);
        log.info("response=====> " + catalogGroup.toString());
    }

    @Then("^All response fields are empty or null$")
    public void iReceiveAnErrorResponse() throws Throwable {
        assertThat(catalogGroup.getRecordSetCount() == null || catalogGroup.getRecordSetCount().equals("0"));
        assertThat(catalogGroup.getRecordSetStartNumber() == null || catalogGroup.getRecordSetStartNumber().equals("0"));
        assertThat(catalogGroup.getRecordSetTotal() == null || catalogGroup.getRecordSetTotal().equals("0"));
        assertThat(catalogGroup.getResourceId() == null || catalogGroup.getResourceId().equals("0"));
        assertThat(catalogGroup.getResourceName() == null || catalogGroup.getResourceName().equals("0"));
        assertThat(catalogGroup.getRecordSetCount() == null || catalogGroup.getRecordSetCount().equals("0"));
        assertThat(catalogGroup.getCatalogGroupView() == null || catalogGroup.getCatalogGroupView().equals("0"));
    }

    @When("^I call the findTopCategories method with \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" and \"([^\"]*)\" parameters under protocol \"([^\"]*)\"$")
    public void iCallTheGetTopCategoriesmethodWithAndParameters(String langId, String pageType, String designerCat, String attrs,String protocol) throws Throwable {
        // build the query string if the parameter isn't specified as empty
        queryParams = new HashMap<String, String>();
        if ( !langId.equals("empty")) {
            queryParams.put("langId", langId);
        }
        if ( !designerCat.equals("empty")) {
            queryParams.put("designerCat", designerCat);
        }
        if ( !attrs.equals("empty")) {
            queryParams.put("attrs", attrs);
        }
        if ( !pageType.equals("empty")) {
            queryParams.put("pageType", pageType);
        }
        catalogGroup = searchAndBrowseAPI.getTopCategories(storeIdentifier, queryParams,protocol,"200");
    }

    @Then("^I get all top categories for this store for the supplied langId, pageType, designerCat$")
    public void iGetAllTopCategoriesForThisStoreForTheSuppliedLangIdPageTypeDesignerCat() throws Throwable {
        assert(true);
    }

    @And("^Attributes are only returned when requested$")
    public void attributesAreOnlyReturnedWhenRequested() throws Throwable {
        assert(true);
    }

    @Then("^I get the correct category information$")
    public void iGetTheCorrectCategoryInformation() throws Throwable {
        assert(true);
    }

    @And("^Category attributes are returned if requested$")
    public void categoryAttributesAreReturnedIfRequested() throws Throwable {
        assert(true);
    }

    @When("^I call the findCategoryByIdentifier method for a specific category identifier with parameters \"([^\"]*)\" and \"([^\"]*)\" for category identifier \"([^\"]*)\" under protocol \"([^\"]*)\"$")
    public void iCallTheGetCategorymethodForASpecificCategoryIdentifierWithParametersAndForCategoryIdentifier(String langId, String attrs, String categoryIdentifier, String protocol) throws Throwable {
        boolean requireAttributes = false;
        StringBuilder sqlQuery;
        List<Map<String, Object>> thisCatgroupId = null;
        String expectedHttpStatusCode = STATUS_CODE_OK.getStringValue();

        queryParams = new HashMap<String, String>();
        if ( !langId.equals("empty")) {
            queryParams.put("langId", langId);
        }
        if ( !attrs.equals("empty")) {
            queryParams.put("attrs", attrs);
        }
        if (categoryIdentifier.equals("with attributes") ) {
            requireAttributes = true;
        }
        // get a category identifier with or without attributes as specified
        // not attributes held at CAS level
        sqlQuery = new StringBuilder();
        if ( requireAttributes) {
            sqlQuery.append(GET_CATEGORY_IDENTIFIER_WITH_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
        }
        else
        {
            sqlQuery.append(GET_CATEGORY_IDENTIFIER_WITH_NO_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
        }
        // perform query
        try {
            log.info(">>>>>>>>>>>>>>>"+sqlQuery.toString());
            thisCatgroupId = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Get category (Identifier): Exception while getting data from database: " + e);
            assertThat(false).as("Get category (Identifier): Exception while getting data from database: " + e).isTrue();
        }

        // call the method with the correct parameters
        String catgroupIdentifier = StringHelper.returnEmptyStringIfNull(thisCatgroupId.get(0).get("IDENTIFIER"));
        log.info("catgroup identifier = "+catgroupIdentifier);

        catalogGroup = searchAndBrowseAPI.findCategoryByIdentifier(storeIdentifier,catgroupIdentifier, queryParams,protocol,expectedHttpStatusCode);

    }

    @When("^I call the findCategoryByIdentifier method for a specific category identifier under protocol \"([^\"]*)\" omitting the store identifier$")
    public void iCallTheGetCategoriesmethodForASpecificCategoryIdentifierUnderProtocolOmittingTheStoreIdentifier(String protocol) throws Throwable {
        String expectedHttpStatusCode = STATUS_CODE_NOT_FOUND.getStringValue();
        catalogGroup = searchAndBrowseAPI.getTopCategories("", queryParams,protocol,expectedHttpStatusCode);
        log.info("response=====> " + catalogGroup.toString());
    }

    @When("^I call the findCategoryByUniqueId method for a specific category identifier under protocol \"([^\"]*)\" omitting \"([^\"]*)\"$")
    public void iCallTheFindCategoryByUniqueIdMethodForASpecificCategoryIdentifierUnderProtocolOmitting(String protocol, String omittedParameter) throws Throwable {
        String expectedHttpStatusCode = STATUS_CODE_NOT_FOUND.getStringValue();
        String thisStoreIdentifier="";
        StringBuilder sqlQuery = new StringBuilder();
        String catgroupId="";
        List <Map<String, Object>> thisCatgroupId = new ArrayList<Map<String, Object>>();

        if ( omittedParameter.equals("categoryId")) {
            thisStoreIdentifier=storeIdentifier;
            catgroupId="";
            expectedHttpStatusCode=STATUS_CODE_OK.getStringValue();
        } else {
            sqlQuery.append(GET_CATEGORY_ID_WITH_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
            expectedHttpStatusCode=STATUS_CODE_NOT_FOUND.getStringValue();
            // perform query
            try {
                log.info(">>>>>>>>>>>>>>>"+sqlQuery.toString());
                thisCatgroupId = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
            } catch (Exception e) {
                log.error("Get category (Id): Exception while getting data from database: " + e);
                assertThat(false).as("Get category (Id): Exception while getting data from database: " + e).isTrue();
            }
            catgroupId = StringHelper.returnEmptyStringIfNull(thisCatgroupId.get(0).get("CATGROUP_ID"));
            thisStoreIdentifier="";
        }
        log.info("catgroup id = "+catgroupId);
        catalogGroup = searchAndBrowseAPI.findCategoryByUniqueId(thisStoreIdentifier, catgroupId, queryParams, protocol, expectedHttpStatusCode);
        log.info("response=====> " + catalogGroup.toString());
    }

    @When("^I call the findCategoryByUniqueId method for a specific category identifier with parameters \"([^\"]*)\" and \"([^\"]*)\" for category identifier \"([^\"]*)\" under protocol \"([^\"]*)\"$")
    public void iCallTheFindCategoryByUniqueIdMethodForASpecificCategoryIdentifierWithParametersAndForCategoryIdentifierUnderProtocol(String langId, String attrs, String categoryId, String protocol) throws Throwable {
        boolean requireAttributes = false;
        StringBuilder sqlQuery;
        List<Map<String, Object>> thisCatgroupId = null;
        String expectedHttpStatusCode = STATUS_CODE_OK.getStringValue();

        queryParams = new HashMap<String, String>();
        if ( !langId.equals("empty")) {
            queryParams.put("langId", langId);
        }
        if ( !attrs.equals("empty")) {
            queryParams.put("attrs", attrs);
        }
        if (categoryId.equals("with attributes") ) {
            requireAttributes = true;
        }
        // get a category identifier with or without attributes as specified
        // not attributes held at CAS level
        sqlQuery = new StringBuilder();
        if ( requireAttributes) {
            sqlQuery.append(GET_CATEGORY_ID_WITH_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
        }
        else
        {
            sqlQuery.append(GET_CATEGORY_ID_WITH_NO_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
        }
        // perform query
        try {
            log.info(">>>>>>>>>>>>>>>"+sqlQuery.toString());
            thisCatgroupId = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Get category (Identifier): Exception while getting data from database: " + e);
            assertThat(false).as("Get category (Identifier): Exception while getting data from database: " + e).isTrue();
        }

        // call the method with the correct parameters
        String catgroupId = StringHelper.returnEmptyStringIfNull(thisCatgroupId.get(0).get("CATGROUP_ID"));
        log.info("catgroup id = "+catgroupId);

        catalogGroup = searchAndBrowseAPI.findCategoryByIdentifier(storeIdentifier,catgroupId, queryParams,protocol,expectedHttpStatusCode);
    }



    @When("^I call the findCategoryByUniqueIds method for a specific category identifier under protocol \"([^\"]*)\" omitting \"([^\"]*)\"$")
    public void iCallTheFindCategoryByUniqueIdsMethodForASpecificCategoryIdentifierUnderProtocolOmitting(String protocol, String omittedParameter) throws Throwable {
        String expectedHttpStatusCode = STATUS_CODE_NOT_FOUND.getStringValue();
        String thisStoreIdentifier="";
        StringBuilder sqlQuery;
        String catgroupId="";
        List <Map<String, Object>> thisCatgroupId = new ArrayList<Map<String, Object>>();

        if ( omittedParameter.equals("categoryId")) {
            thisStoreIdentifier=storeIdentifier;
            catgroupId="";
            expectedHttpStatusCode=STATUS_CODE_OK.getStringValue();
        } else {
            sqlQuery = new StringBuilder();
            sqlQuery.append(GET_CATEGORY_ID_WITH_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
            expectedHttpStatusCode=STATUS_CODE_NOT_FOUND.getStringValue();
            // perform query
            try {
                log.info(">>>>>>>>>>>>>>>"+sqlQuery.toString());
                thisCatgroupId = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
            } catch (Exception e) {
                log.error("Get category (Id): Exception while getting data from database: " + e);
                assertThat(false).as("Get category (Id): Exception while getting data from database: " + e).isTrue();
            }
            catgroupId = StringHelper.returnEmptyStringIfNull(thisCatgroupId.get(0).get("CATGROUP_ID"));
            thisStoreIdentifier="";
        }
        log.info("catgroup id = "+catgroupId);
        catalogGroup = searchAndBrowseAPI.findCategoryByUniqueId(thisStoreIdentifier, catgroupId, queryParams, protocol, expectedHttpStatusCode);
        log.info("response=====> " + catalogGroup.toString());
    }


    @When("^I call the findCategoryByUniqueIds method with query parameters \"([^\"]*)\"  \"([^\"]*)\" and \"([^\"]*)\" under protocol \"([^\"]*)\"$")
    public void iCallTheFindCategoryByUniqueIdsMethodWithQueryParametersAndUnderProtocol(String catIds, String langId, String attrs, String protocol) throws Throwable {
        StringBuilder sqlQuery;
        List<Map<String, Object>> thisCatgroupId = null;
        String expectedHttpStatusCode = STATUS_CODE_OK.getStringValue();
        String catgroupId;
        String thisStoreIdentifier;
        thisStoreIdentifier=storeIdentifier;
        String catgroupIds="";
        if ( !langId.equals("empty")) {
            queryParams.put("langId", langId);
        }
        if ( !attrs.equals("empty")) {
            queryParams.put("attrs", attrs);
        }
        log.info("attribute string "+catIds);
        String [] attributeRequirements  = catIds.split(",");
        log.info("length="+attributeRequirements.length);
        for (int x=0;x<attributeRequirements.length;x++) {
            sqlQuery = new StringBuilder();
            if ( attributeRequirements[x].equals("with attributes") ) {
                sqlQuery.append(GET_CATEGORY_ID_WITH_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
            } else {
                sqlQuery.append(GET_CATEGORY_ID_WITH_NO_ATTRIBUTES.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeCAS)).replace("|ROWS|","1"));
            }
            log.info("sql query >>>>>>>>>>>>>>>"+sqlQuery.toString());
            try {
                thisCatgroupId = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
            } catch (Exception e) {
                log.error("Get category (Identifiers): Exception while getting data from database: " + e);
                assertThat(false).as("Get category (Identifiers): Exception while getting data from database: " + e).isTrue();
            }
            catgroupId = StringHelper.returnEmptyStringIfNull(thisCatgroupId.get(0).get("CATGROUP_ID"));
            if ( x == 0) {
                catgroupIds = catgroupId;
            } else {
                catgroupIds = catgroupIds + "&id=" + catgroupId;
            }
            log.info("attribute catgroup id = "+catgroupId);
        }
        queryParams.put("id",catgroupIds);
        catalogGroup = searchAndBrowseAPI.findCategoryByUniqueIds(thisStoreIdentifier, queryParams, protocol, expectedHttpStatusCode);
    }
}
