package com.salmon.test.step_definitions.api.productview;

import com.salmon.test.framework.helpers.Props;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.api.searchandbrowse.CatalogEntryView;
import com.salmon.test.models.api.searchandbrowse.ProductsByCategoryData;
import com.salmon.test.models.api.storelocator.Language;
import com.salmon.test.services.searchandbrowse.SearchAndBrowseAPI;
import com.salmon.test.sql.products.ProductDAO;
import com.salmon.test.sql.store.StoreDAO;
import com.salmon.test.step_definitions.api.utility.BaseStepDefinition;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.salmon.test.framework.helpers.ApiHelper.StoreIdentifierToStoreId;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Developer on 14/10/2016.
 */
public class ProductNameStepDefs extends BaseStepDefinition{

    private Logger log = LoggerFactory.getLogger(this.getClass().getCanonicalName());
    private List<String> storeIdentifierList;
    private StoreDAO storeDAO;
    private SearchAndBrowseAPI searchAndBrowseAPI;
    private ProductDAO productDAO;
    Map<String,String> queryParams = null;
    private List<String> languageIdList;

    public ProductNameStepDefs(StoreDAO storeDAO,SearchAndBrowseAPI searchAndBrowseAPI,ProductDAO productDAO){
        super(storeDAO);
        this.storeDAO = storeDAO;
        this.searchAndBrowseAPI = searchAndBrowseAPI;
        this.productDAO = productDAO;
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with categories to check the catalogEntryView details$")
    public void iHaveTheStoreIdentifiersWithCategoriesToCheckTheCatalogEntryViewDetails(String storeIdentifierKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^I call the Get products by category API with categoryId and verify the response with the database$")
    public void iCallTheGetProductsByCategoryAPIWithCategoryIdAndVerifyTheResponseWithTheDatabase() throws Throwable {
        ProductsByCategoryData productsByCategoryData = null;
        ProductsByCategoryData expectedProductsByCategoryData = null;
        List<Map<String,Object>> categoryList = null;
        String catalogId = "";
        Language language = null;
        String offset = Props.getMessage("categories.offset");
        String noOfRows = Props.getMessage("categories.noofrows");
        for(String storeIdentifier:storeIdentifierList){
            language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            catalogId = catalogIds.get(storeCASIds.get(storeIdentifier));
            categoryList = productDAO.getCategoriesForBrand(catalogId,offset,noOfRows);
            for(Map categoryMap:categoryList){
                String categoryId = categoryMap.get("CATGROUP_ID_PARENT").toString();
                productsByCategoryData = searchAndBrowseAPI.findProductsByCategory(storeIdentifier,categoryId,queryParams);
                expectedProductsByCategoryData = searchAndBrowseAPI.findProductsByCategoryFromDatabase(catalogId,categoryId,language.getLanguageId());
                assertThat(expectedProductsByCategoryData.getRecordSetCount()).as("").isEqualTo(productsByCategoryData.getRecordSetCount());
                compareProductsData(productsByCategoryData,expectedProductsByCategoryData);
            }

        }
    }

    private void compareProductsData(ProductsByCategoryData productsByCategoryData,ProductsByCategoryData expProductsByCategoryData){
        int i=0;
        for(CatalogEntryView expCatalogEntryView:expProductsByCategoryData.getCatalogEntryView()){
            CatalogEntryView actCatalogEntryView =  productsByCategoryData.getCatalogEntryView().get(i);
            assertThat(expCatalogEntryView.getExtendedName()).as("Product Extended Name is not correct for partNumber: "+expCatalogEntryView.getPartNumber()+"").isEqualTo(actCatalogEntryView.getExtendedName());
            assertThat(expCatalogEntryView.getPartNumber()).as("Product PartNumber is not correct").isEqualTo(actCatalogEntryView.getPartNumber());
            assertThat(expCatalogEntryView.getMfPartNumber()).as("Product MfPartNumber is not correct").isEqualTo(actCatalogEntryView.getMfPartNumber());
            assertThat(expCatalogEntryView.getField4()).as("Product Field4 is not correct for partNumber : "+expCatalogEntryView.getPartNumber()+"").isEqualTo(actCatalogEntryView.getField4());
            i++;
        }
    }

    @Given("^I have the store identifiers \"([^\"]*)\" with categories and langId \"([^\"]*)\" to check the catalogEntryView details$")
    public void iHaveTheStoreIdentifiersWithCategoriesAndLangIdToCheckTheCatalogEntryViewDetails(String storeIdentifierKey, String languageIdKey) throws Throwable {
        storeIdentifierList =  Arrays.asList(UrlBuilder.readValueFromConfig(storeIdentifierKey).split(","));
        languageIdList = Arrays.asList(Props.getMessage(languageIdKey).split(","));
    }

    @Then("^I call the Get products by category API with categoryId and langId then verify the response with the database$")
    public void iCallTheGetProductsByCategoryAPIWithCategoryIdAndLangIdThenVerifyTheResponseWithTheDatabase() throws Throwable {
        ProductsByCategoryData productsByCategoryData = null;
        ProductsByCategoryData expectedProductsByCategoryData = null;
        List<Map<String,Object>> categoryList = null;
        String catalogId = "";
        String offset = Props.getMessage("categories.offset");
        String noOfRows = Props.getMessage("categories.noofrows");
        for(String storeIdentifier:storeIdentifierList){
            for(String expLangId:languageIdList) {
                queryParams = new HashMap<String,String>();
                String languageId = null;
                if (storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), expLangId) == 0) {
                    log.info("language Id inside");
                    Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    languageId = language.getLanguageId();
                } else {
                    languageId = expLangId;
                }
                queryParams.put("langId",languageId);
                catalogId = catalogIds.get(storeCASIds.get(storeIdentifier));
                categoryList = productDAO.getCategoriesForBrand(catalogId, offset, noOfRows);
                for (Map categoryMap : categoryList) {
                    String categoryId = categoryMap.get("CATGROUP_ID_PARENT").toString();
                    productsByCategoryData = searchAndBrowseAPI.findProductsByCategory(storeIdentifier, categoryId, queryParams);
                    expectedProductsByCategoryData = searchAndBrowseAPI.findProductsByCategoryFromDatabase(catalogId, categoryId, languageId);
                    assertThat(expectedProductsByCategoryData.getRecordSetCount()).as("").isEqualTo(productsByCategoryData.getRecordSetCount());
                    compareProductsData(productsByCategoryData, expectedProductsByCategoryData);
                }
            }

        }
    }
}
