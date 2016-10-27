package com.salmon.test.step_definitions.dataload.price;


import com.salmon.test.dataload.price.ProductPriceJsonFileGenerator;
import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.models.dataload.pim.price.Prices;
import com.salmon.test.models.dataload.pim.price.ProductPricePimModel;
import com.salmon.test.models.dataload.wcs.price.ProductPriceWcsModel;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.getBrands;
//import static com.salmon.test.dataload.price.ProductPriceCsvFileGenerator.generateProductPriceCsvFile;
//import static com.salmon.test.dataload.price.ProductPriceCsvFileGenerator.generateProductPriceCsvFileWithDiffFieldSequence;
//import static com.salmon.test.dataload.price.ProductPriceCsvFileGenerator.generateProductPriceCsvFileWithoutHeader;
import static com.salmon.test.dataload.price.ProductPriceWcsFileChecker.checkTalendProductpriceFileFormat;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductPriceSteps {
    static final Logger LOG = LoggerFactory.getLogger(ProductPriceSteps.class);
    private List<Prices> pricesModels;
    private List<ProductPricePimModel> productPricePimModels;

    public ProductPriceSteps(ProductPriceJsonFileGenerator priceJsonFileGenerator) {
        this.priceJsonFileGenerator = priceJsonFileGenerator;
    }

    private ProductPriceJsonFileGenerator priceJsonFileGenerator;

    @Given("^A new PIM PRODUCT PRICE file is uploaded into a location for ETL system$")
    public void aNewPIMPRODUCTPRICEFileIsUploadedIntoALocationForETLSystem(List<ProductPricePimModel> productPricePimModels) throws Throwable {
//        generateProductPriceCsvFile(productPricePimModels, "|");
    }

    @And("^ETL system should have transformed all PIM files into their relevant WCS files$")
    public void etlSystemShouldHaveTransformedAllPIMFilesIntoTheirRelevantWCSFiles(List<ProductPriceWcsModel> productPriceWcsModels) throws Throwable {
        checkTalendProductpriceFileFormat(productPriceWcsModels);
    }

    @And("^WCS system should have all product price data available in relevant data tables$")
    public void wcsSystemShouldHaveAllProductPriceDataAvailableInRelevantDataTables(List<ProductPriceWcsModel> productPriceWcsModels) throws Throwable {
        List<String> brands = getBrands();
        for (String brand : brands) {
            List<HashMap> actOfferDatabaseResult, actOfferPriceDatabaseResult, actXOfferPriceDatabaseResult;

            for (ProductPriceWcsModel productPriceWcsModel : productPriceWcsModels) {
                actOfferDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_PRICE_OFFER.getQuery().replace("|partNumber|", productPriceWcsModel.getPartNumber()));
                actOfferPriceDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_PRICE_OFFERPRICE.getQuery().replace("|partNumber|", productPriceWcsModel.getPartNumber()));
                actXOfferPriceDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_PRICE_XOFFERPRICE.getQuery().replace("|partNumber|", productPriceWcsModel.getPartNumber()));

                assertThat(actOfferDatabaseResult.get(0).get("STARTDATE").toString().equals(productPriceWcsModel.getStartDate()));
                assertThat(actOfferDatabaseResult.get(0).get("ENDDATE").toString().equals(productPriceWcsModel.getEndDate()));
                assertThat(actOfferDatabaseResult.get(0).get("PRECEDENCE").toString().equals(productPriceWcsModel.getPrecedence().toString()));
                assertThat(actOfferDatabaseResult.get(0).get("IDENTIFIER").toString()).isEqualTo(productPriceWcsModel.getIdentifier().toString());

                assertThat(actOfferPriceDatabaseResult.get(0).get("CURRENCY").toString().equals(productPriceWcsModel.getCurrencyCode()));
                assertThat(actOfferPriceDatabaseResult.get(0).get("PRICE").toString()).isEqualTo(productPriceWcsModel.getPrice().toString());


                assertThat(actXOfferPriceDatabaseResult.get(0).get("CURRENCY").toString().equals(productPriceWcsModel.getCurrencyCode().toString()));
                Object markDownCode = actXOfferPriceDatabaseResult.get(0).get("MARKDOWNCDE");
                assertThat(null == markDownCode ? StringUtils.EMPTY : markDownCode.toString()).isEqualTo(productPriceWcsModel.getMarkdownCode().toString());
                assertThat(actXOfferPriceDatabaseResult.get(0).get("MARKDOWNPCT").toString()).isEqualTo(productPriceWcsModel.getMarkdownPercentage().toString());
            }
        }
    }

    @And("^WCS system should NOT have all product price data available in relevant data tables$")
    public void wcsSystemShouldNOTHaveAllProductPriceDataAvailableInRelevantDataTables(List<ProductPriceWcsModel> productPriceWcsModels) throws Throwable {
        List<String> brands = getBrands();
        for (String brand : brands) {
            List<HashMap> actOfferDatabaseResult, actOfferPriceDatabaseResult, actXOfferPriceDatabaseResult;

            for (ProductPriceWcsModel productPriceWcsModel : productPriceWcsModels) {
                actOfferDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_PRICE_OFFER.getQuery().replace("|partNumber|", productPriceWcsModel.getPartNumber()));
                actOfferPriceDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_PRICE_OFFERPRICE.getQuery().replace("|partNumber|", productPriceWcsModel.getPartNumber()));
                actXOfferPriceDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_PRICE_XOFFERPRICE.getQuery().replace("|partNumber|", productPriceWcsModel.getPartNumber()));

                assertThat(actOfferDatabaseResult.size()).isEqualTo(0);
                assertThat(actOfferPriceDatabaseResult.size()).isEqualTo(0);
                assertThat(actXOfferPriceDatabaseResult.size()).isEqualTo(0);

            }
        }
    }


    @Given("^A new PIM PRODUCT PRICE file is created with comma delimiter and uploaded$")
    public void aNewPIMPRODUCTPRICEFileIsCreatedWithCommaDelimiterAndUploaded(List<ProductPricePimModel> productPricePimModels) throws Throwable {
//        generateProductPriceCsvFile(productPricePimModels, ",");
    }

    @Given("^A new PIM PRODUCT PRICE file with no header is created and uploaded for processing$")
    public void aNewPIMPRODUCTPRICEFileWithNoHeaderIsCreatedAndUploadedForProcessing(List<ProductPricePimModel> productPricePimModels) throws Throwable {
        //       generateProductPriceCsvFileWithoutHeader(productPricePimModels, "|");
    }

    @Given("^A new PIM PRODUCT PRICE file is created with different field sequence and uploaded for processing$")
    public void aNewPIMPRODUCTPRICEFileIsCreatedWithDifferentFieldSequenceAndUploadedForProcessing(List<ProductPricePimModel> productPricePimModels) throws Throwable {
        //     generateProductPriceCsvFileWithDiffFieldSequence(productPricePimModels);
    }

    @Given("^provided list of prices for product$")
    public void providedListOfPricesForProduct(List<Prices> pricesList) throws Throwable {
        this.pricesModels = pricesList;
    }


    @And("^I create Prices Json file$")
    public void iCreatePricesJsonFile(List<ProductPricePimModel> productPricePimModelList) throws Throwable {
        productPricePimModels = productPricePimModelList;
        productPricePimModels.get(0).setPrices(this.pricesModels);
        priceJsonFileGenerator.createPricesJson(productPricePimModels.get(0));
    }
}
