package com.salmon.test.step_definitions.dataload.product;


import com.salmon.test.dataload.product.ProductDescriptionSQL;
import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.product.ProductCategoryPimModel;
import com.salmon.test.models.dataload.pim.product.ProductDescriptionPimModel;
import com.salmon.test.models.dataload.pim.product.ProductPimModel;
import com.salmon.test.models.dataload.wcs.product.ProductCategoryWcsModel;
import com.salmon.test.models.dataload.wcs.product.ProductDescriptionWcsModel;
import com.salmon.test.models.dataload.wcs.product.ProductWcsModel;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.salmon.test.dataload.product.ProductCsvFileGenerator.*;
import static com.salmon.test.dataload.product.ProductWcsFileChecker.checkTalendProducCategoryTransformtionFile;
import static com.salmon.test.dataload.product.ProductWcsFileChecker.checkTalendProductDescriptionFileFormat;
import static com.salmon.test.dataload.product.ProductWcsFileChecker.checkTalendProductFileFormat;
import static com.salmon.test.step_definitions.dataload.category.CategorySteps.getCurrentDBDateTime;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSteps {

    private ProductPimModel productPimModel;
    private ProductDescriptionPimModel productDescriptionPimModel;
    private String profileId = UrlBuilder.readValueFromConfig("profile.id");
    private ProductDescriptionSQL productDescriptionSQL;
    private static String currentDBDateTime;

    public ProductSteps(ProductPimModel productPimModel, ProductDescriptionPimModel productDescriptionPimModel, ProductDescriptionSQL productDescriptionSQL) {
        this.productPimModel = productPimModel;
        this.productDescriptionPimModel = productDescriptionPimModel;
        this.productDescriptionSQL = productDescriptionSQL;
    }

    @Given("^I create Product PIM file$")
    public void iCreateProductPIMFile(List<ProductPimModel> productPimModelList) throws Throwable {
        generateProductCsvFile(productPimModelList);
    }

    @Given("^I create Product Description PIM file$")
    public void iCreateProductDescriptionPIMFile(List<ProductDescriptionPimModel> productDescriptionPimModels) throws Throwable {
        generateProductDescriptionCsvFile(productDescriptionPimModels);
    }

    @Then("^Talend performs correct transformation$")
    public void talendPerformsCorrectTransformation(List<ProductDescriptionWcsModel> productDescriptionWcsModels) throws Throwable {
        checkTalendProductDescriptionFileFormat(productDescriptionWcsModels);
    }

    @And("^I create Product Category PIM file$")
    public void iCreateProductCategoryPIMFile(List<ProductCategoryPimModel> productCategoryPimModels) throws Throwable {
        generateProductCategoryCsvFile(productCategoryPimModels);
    }

    @Then("^verify WCS Product file has the correct format:$")
    public void verifyWCSProductFileHasTheCorrectFormat(List<ProductWcsModel> productWcsModels) throws Throwable {
        checkTalendProductFileFormat(productWcsModels);
    }

    @And("^data is inserted into CATENTRY table:$")
    public void dataIsInsertedIntoCATENTRYTable(List<ProductWcsModel> productWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String manufacturer = UrlBuilder.readValueFromConfig(brand + ".manufacturer");
            List<HashMap> actProductDatabaseResult;
            for (ProductWcsModel productWcsModel : productWcsModels) {
                actProductDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_CATENTRY.getQuery().replace("|partnumber|", productWcsModel.getPartNumber()).replace("|mfname|", manufacturer));
                assertThat(actProductDatabaseResult.size()).isGreaterThan(0);

                for (HashMap productDatabaseResult : actProductDatabaseResult) {
                    assertThat(productDatabaseResult.get("PARTNUMBER").toString()).isEqualTo(productWcsModel.getPartNumber());
                    assertThat(productDatabaseResult.get("MFPARTNUMBER").toString()).isEqualTo(productWcsModel.getManufacturerPartNumber());
                    assertThat(productDatabaseResult.get("CATENTTYPE_ID").toString()).isEqualTo(productWcsModel.getCatEntTypeId());
                    assertThat(productDatabaseResult.get("MFNAME").toString()).isEqualTo(manufacturer);
                    assertThat(productDatabaseResult.get("FIELD1").toString()).isEqualTo(productWcsModel.getField1());
                }
            }
        }
    }

    @And("^data is inserted into CATENTREL table:$")
    public void dataIsInsertedIntoCATENTRELTable(List<ProductWcsModel> productWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String manufacturer = UrlBuilder.readValueFromConfig(brand + ".manufacturer");
            List<HashMap> actProductCatEntRelDatabaseResult;
            for (ProductWcsModel productWcsModel : productWcsModels) {
                actProductCatEntRelDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATENTREL_PRODUCT.getQuery().replace("|catEntryParentId|", productWcsModel.getCatEntryParentId()).replace("|catEntryChildId|", productWcsModel.getCatEntryChildId()).replace("|catRelTypeId|", productWcsModel.getCatRelTypeId()).replace("|mfname|", manufacturer));
                assertThat(actProductCatEntRelDatabaseResult.size()).isGreaterThan(0);

                for (HashMap productDatabaseResult : actProductCatEntRelDatabaseResult) {
                    assertThat(productDatabaseResult.get("CATRELTYPE_ID").toString()).isEqualTo(productWcsModel.getCatRelTypeId());
                }
            }
        }
    }

    @Then("^data is deleted into CATENTRY table:$")
    public void dataIsDeletedIntoCATENTRYTable(List<ProductWcsModel> productWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String manufacturer = UrlBuilder.readValueFromConfig(brand + ".manufacturer");
            List<HashMap> actProductDatabaseResult;
            for (ProductWcsModel productWcsModel : productWcsModels) {
                actProductDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_CATENTRY.getQuery().replace("|partnumber|", productWcsModel.getPartNumber()));
                assertThat(actProductDatabaseResult.size()).isEqualTo(0);
            }
        }
    }

    @And("^The product description data is deleted and recreated into database successfully$")
    public void theProductDescriptionDataIsDeletedAndRecreatedIntoDatabaseSuccessfully(List<ProductDescriptionWcsModel> productDescriptionWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<ProductDescriptionWcsModel> actProductDescriptionDatabaseResult;
            for (ProductDescriptionWcsModel productDescriptionWcsModel : productDescriptionWcsModels) {
                List<Map<String, Object>> language = DatabaseHelper.executeQuery(DatabaseQueries.GET_LANGUAGE_ID_BY_LOCALE_NAME.getQuery().replace("|LOCALE_NAME|", productDescriptionWcsModel.getLanguageId()));
              //  String langId = language.get(0).get("languageId").toString();
                actProductDescriptionDatabaseResult = productDescriptionSQL.getProductDescriptionDetails(DatabaseQueries.SELECT_PRODUCT_DESCRIPTION_PRODUCT.getQuery().replace("|partnumber|", productDescriptionWcsModel.getPartNumber()).replace("|languageId|", productDescriptionWcsModel.getLanguageId()));
                if (productDescriptionWcsModel.getDelete().equals("1")) {
                    assertThat(actProductDescriptionDatabaseResult.size()).isEqualTo(0);
                } else {
                    for (ProductDescriptionWcsModel productDescriptionDatabaseResult : actProductDescriptionDatabaseResult) {
                        assertThat(productDescriptionDatabaseResult.getLanguageId()).isEqualTo(productDescriptionWcsModel.getLanguageId());
                        assertThat(productDescriptionDatabaseResult.getPartNumber()).isEqualTo(productDescriptionWcsModel.getPartNumber());
                        assertThat(productDescriptionDatabaseResult.getExtendedName()).isEqualTo(productDescriptionWcsModel.getExtendedName());
                        assertThat(productDescriptionDatabaseResult.getEditorialDescription()).isEqualTo(productDescriptionWcsModel.getEditorialDescription());
                        assertThat(productDescriptionDatabaseResult.getDetailsAndCare()).isEqualTo(productDescriptionWcsModel.getDetailsAndCare());
                        assertThat(productDescriptionDatabaseResult.getSizeAndFit()).isEqualTo(productDescriptionWcsModel.getSizeAndFit());
                        assertThat(productDescriptionDatabaseResult.getLongDescription()).isEqualTo(productDescriptionWcsModel.getLongDescription());
                        assertThat(productDescriptionDatabaseResult.getPublished()).isEqualTo(productDescriptionWcsModel.getPublished());
                    }
                }
            }
        }
    }

    @And("^data is deleted into CATENTREL table:$")
    public void dataIsDeletedIntoCATENTRELTable(List<ProductWcsModel> productWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String manufacturer = UrlBuilder.readValueFromConfig(brand + ".manufacturer");
            List<HashMap> actProductCatEntRelDatabaseResult;
            for (ProductWcsModel productWcsModel : productWcsModels) {
                actProductCatEntRelDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATENTREL_PRODUCT.getQuery().replace("|catEntryParentId|", productWcsModel.getCatEntryParentId()).replace("|catEntryChildId|", productWcsModel.getCatEntryChildId()).replace("|catRelTypeId|", productWcsModel.getCatRelTypeId()).replace("|mfname|", manufacturer));
                assertThat(actProductCatEntRelDatabaseResult.size()).isEqualTo(0);
            }
        }
    }

    @Given("^I create Product PIM file with comma delimiter$")
    public void iCreateProductPIMFileWithCommaDelimiter(List<ProductPimModel> productPimModelList) throws Throwable {
        generateProductCsvFileWithDelimiter(productPimModelList, ",");
    }

    @Given("^I create Product PIM file with header row wrong order$")
    public void iCreateProductPIMFileWithHeaderRowWrongOrder(List<ProductPimModel> productPimModelList) throws Throwable {
        generateProductCsvFileWithIncorrectHeaderOrder(productPimModelList, "|");

    }

    @And("^The product description data is loaded into database successfully$")
    public void theProductDescriptionDataIsLoadedIntoDatabaseSuccessfully(List<ProductDescriptionWcsModel> productDescriptionWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<ProductDescriptionWcsModel> actProductDescriptionDatabaseResult;
            for (ProductDescriptionWcsModel productDescriptionWcsModel : productDescriptionWcsModels) {
                //List<Map<String, Object>> language = DatabaseHelper.executeQuery(DatabaseQueries.GET_LANGUAGE_ID_BY_LOCALE_NAME.getQuery().replace("|LOCALE_NAME|", productDescriptionWcsModel.getLanguageId()));
                //String langId = language.get(0).get("languageId").toString();
                //System.out.println("Query : "+DatabaseQueries.SELECT_PRODUCT_DESCRIPTION_PRODUCT_LOCAL.getQuery().replace("|partnumber|", productDescriptionWcsModel.getPartNumber()).replace("|languageId|", langId));
                actProductDescriptionDatabaseResult = productDescriptionSQL.getProductDescriptionDetails(DatabaseQueries.SELECT_PRODUCT_DESCRIPTION_PRODUCT.getQuery().replace("|partnumber|", productDescriptionWcsModel.getPartNumber()).replace("|languageId|", productDescriptionWcsModel.getLanguageId()));
                for (ProductDescriptionWcsModel productDescriptionDatabaseResult : actProductDescriptionDatabaseResult) {
                    assertThat(productDescriptionDatabaseResult.getLanguageId()).isEqualTo(productDescriptionWcsModel.getLanguageId());
                    assertThat(productDescriptionDatabaseResult.getPartNumber()).isEqualTo(productDescriptionWcsModel.getPartNumber());
                    assertThat(productDescriptionDatabaseResult.getExtendedName()).isEqualTo(productDescriptionWcsModel.getExtendedName());
                    assertThat(productDescriptionDatabaseResult.getEditorialDescription()).isEqualTo(productDescriptionWcsModel.getEditorialDescription());
                    assertThat(productDescriptionDatabaseResult.getDetailsAndCare()).isEqualTo(productDescriptionWcsModel.getDetailsAndCare());
                    assertThat(productDescriptionDatabaseResult.getSizeAndFit()).isEqualTo(productDescriptionWcsModel.getSizeAndFit());
                    assertThat(productDescriptionDatabaseResult.getLongDescription()).isEqualTo(productDescriptionWcsModel.getLongDescription());
                    assertThat(productDescriptionDatabaseResult.getPublished()).isEqualTo(productDescriptionWcsModel.getPublished());
                }
            }
        }

    }

    @Then("^Talend performs correct transformation for Product Category$")
    public void talendPerformsCorrectTransformationForProductCategory(List<ProductCategoryWcsModel> productCategoryWcsModels) throws Throwable {
        checkTalendProducCategoryTransformtionFile(productCategoryWcsModels);
    }

    @And("^The product description data is deleted into database successfully$")
    public void theProductDescriptionDataIsDeletedIntoDatabaseSuccessfully(List<ProductDescriptionWcsModel> productDescriptionWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<ProductDescriptionWcsModel> actProductDescriptionDatabaseResult;
            for (ProductDescriptionWcsModel productDescriptionWcsModel : productDescriptionWcsModels) {
                List<Map<String, Object>> language = DatabaseHelper.executeQuery(DatabaseQueries.GET_LANGUAGE_ID_BY_LOCALE_NAME.getQuery().replace("|LOCALE_NAME|", productDescriptionWcsModel.getLanguageId()));
                //String langId = language.get(0).get("languageId").toString();
                actProductDescriptionDatabaseResult = productDescriptionSQL.getProductDescriptionDetails(DatabaseQueries.SELECT_PRODUCT_DESCRIPTION_PRODUCT.getQuery().replace("|partnumber|", productDescriptionWcsModel.getPartNumber()).replace("|languageId|", productDescriptionWcsModel.getLanguageId()));
                assertThat(actProductDescriptionDatabaseResult.size()).isEqualTo(0);
            }
        }

    }

    @And("^The product category data is loaded into CATGPENREL table successfully$")
    public void theProductCategoryDataIsLoadedIntoCATGPENRELTableSuccessfully(List<ProductCategoryWcsModel> productCategoryWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actProductCatEntryDatabaseResult;
            for (ProductCategoryWcsModel categoryWcsModel : productCategoryWcsModels) {
                String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand + "." + categoryWcsModel.getParentStoreIdentifier());
                actProductCatEntryDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_CATEGORY.getQuery().replace("|partnumber|", categoryWcsModel.getPartNumber()).replace("|categoryIdentifier|", categoryWcsModel.getParentGroupIdentifier()));
                assertThat(actProductCatEntryDatabaseResult.size()).isGreaterThan(0);
                for (HashMap productDatabaseResult : actProductCatEntryDatabaseResult) {
                    assertThat(productDatabaseResult.get("PARTNUMBER").toString()).isEqualTo(categoryWcsModel.getPartNumber());
                    assertThat(productDatabaseResult.get("CATEGORY_IDENTIFIER").toString()).isEqualTo(categoryWcsModel.getParentGroupIdentifier());
                    assertThat(productDatabaseResult.get("CATALOG_IDENTIFIER").toString()).isEqualTo(parentStoreIdentifier);
                    assertThat(productDatabaseResult.get("SEQUENCE").toString()).isEqualTo(categoryWcsModel.getSequence());
                }
            }
        }
    }

    @And("^The product category data is Deleted from CATGPENREL table successfully$")
    public void theProductCategoryDataIsDeletedFromCATGPENRELTableSuccessfully(List<ProductCategoryWcsModel> productCategoryWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actProductCatEntryDatabaseResult;
            for (ProductCategoryWcsModel categoryWcsModel : productCategoryWcsModels) {
                // String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand + "." + categoryWcsModel.getParentStoreIdentifier());
                actProductCatEntryDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_CATEGORY.getQuery().replace("|partnumber|", categoryWcsModel.getPartNumber()).replace("|categoryIdentifier|", categoryWcsModel.getParentGroupIdentifier()));
                assertThat(actProductCatEntryDatabaseResult.size()).isEqualTo(0);
            }
        }
    }

    @Given("^I create Product Description PIM file with comma delimiter$")
    public void iCreateProductDescriptionPIMFileWithCommaDelimiter(List<ProductDescriptionPimModel> productDescriptionPimModels) throws Throwable {
        generateProductDescriptionCsvFileWithDelimiter(productDescriptionPimModels, ",");
    }

    @Given("^I create Product Description PIM file with header row wrong order$")
    public void iCreateProductDescriptionPIMFileWithHeaderRowWrongOrder(List<ProductDescriptionPimModel> productDescriptionPimModels) throws Throwable {
        generateProductDescriptionCsvFileWithDelimiter(productDescriptionPimModels, "|");
    }


    @Then("^Verify product data and count in catentry and catentrel$")
    public void verifyProductDataAndCountInCatentryAndCatentrel() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        currentDBDateTime = getCurrentDBDateTime();
        for (String brand : brands) {
            String manufacturer = UrlBuilder.readValueFromConfig(brand + ".manufacturer");
            Integer count = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".product.count"));
            Integer countrel = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".productrel.count"));
            String[] expectedProductData = UrlBuilder.readValueFromConfig(brand + ".st2.product").replace("manufacturer", manufacturer).split(",");
            List<HashMap> actProductDatabaseResult, productDatabaseResult, actProductCatEntRelDatabaseResult, productCatEntRelDatabaseResult,storecentDatabaseResult;
            productCatEntRelDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_PRODUCT_CATENTREL.getQuery().replace("|sysTime|", currentDBDateTime));
            actProductDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_PRODUCT_CATENTRY.getQuery().replace("|sysTime|", currentDBDateTime));
            storecentDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_PRODUCT_STORECENT.getQuery().replace("|sysTime|", currentDBDateTime));
            productDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_CATENTRY.getQuery().replace("|partnumber|", expectedProductData[0]).replace("|mfname|", manufacturer));
            actProductCatEntRelDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATENTREL_PRODUCT.getQuery().replace("|catEntryParentId|", expectedProductData[5]).replace("|catEntryChildId|", expectedProductData[6]).replace("|catRelTypeId|", expectedProductData[7]).replace("|mfname|", manufacturer));

            assertThat(actProductDatabaseResult.size()).isEqualTo(count);
            assertThat(storecentDatabaseResult.size()).isEqualTo(count);
            assertThat(productCatEntRelDatabaseResult.size()).isEqualTo(countrel);

            assertThat(productDatabaseResult.get(0).get("PARTNUMBER").toString()).isEqualTo(expectedProductData[0]);
            assertThat(productDatabaseResult.get(0).get("MFPARTNUMBER").toString()).isEqualTo(expectedProductData[1]);
            assertThat(productDatabaseResult.get(0).get("CATENTTYPE_ID").toString()).isEqualTo(expectedProductData[2]);
            assertThat(productDatabaseResult.get(0).get("MFNAME").toString()).isEqualTo(expectedProductData[3]);
            assertThat(productDatabaseResult.get(0).get("FIELD1").toString()).isEqualTo(expectedProductData[4]);

            assertThat(actProductCatEntRelDatabaseResult.get(0).get("CATRELTYPE_ID").toString()).isEqualTo(expectedProductData[7]);
        }
    }

    @And("^Verify product description data and count in catentdesc and xcatentdesc$")
    public void verifyProductDescriptionDataAndCountInCatentdescAndXcatentdesc() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            Integer count = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".productdescrition.count"));
            Integer xcount = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".productxdescrition.count"));
            String[] expectedProductDescriptionData = UrlBuilder.readValueFromConfig(brand + ".st2.productdescrition").replace("|"," ").split("\\^");
            List<ProductDescriptionWcsModel> productDescriptionDatabaseResult;
            List<HashMap> actProductDescriptionDatabaseResult, actProductXDescriptionDatabaseResult;

            actProductDescriptionDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_PRODUCT_DESCRIPTION.getQuery().replace("|sysTime|", currentDBDateTime));
            actProductXDescriptionDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_PRODUCT_XDESCRIPTION.getQuery().replace("|sysTime|", currentDBDateTime));
            productDescriptionDatabaseResult = productDescriptionSQL.getProductDescriptionDetails(DatabaseQueries.SELECT_PRODUCT_DESCRIPTION_PRODUCT.getQuery().replace("|partnumber|", expectedProductDescriptionData[1]).replace("|languageId|", expectedProductDescriptionData[0]));

            assertThat(actProductDescriptionDatabaseResult.size()).isEqualTo(count);
            assertThat(actProductXDescriptionDatabaseResult.size()).isEqualTo(xcount);
            assertThat(productDescriptionDatabaseResult.get(0).getLanguageId()).isEqualTo(expectedProductDescriptionData[0]);
            assertThat(productDescriptionDatabaseResult.get(0).getPartNumber()).isEqualTo(expectedProductDescriptionData[1]);
            assertThat(productDescriptionDatabaseResult.get(0).getExtendedName()).isEqualTo(expectedProductDescriptionData[2]);
            assertThat(productDescriptionDatabaseResult.get(0).getEditorialDescription()).isEqualTo(expectedProductDescriptionData[3]);
            assertThat(productDescriptionDatabaseResult.get(0).getDetailsAndCare()).isEqualTo(expectedProductDescriptionData[4]);
            assertThat(productDescriptionDatabaseResult.get(0).getSizeAndFit()).isEqualTo(expectedProductDescriptionData[5]);
            assertThat(productDescriptionDatabaseResult.get(0).getLongDescription()).isEqualTo(expectedProductDescriptionData[6]);
            assertThat(productDescriptionDatabaseResult.get(0).getPublished()).isEqualTo(expectedProductDescriptionData[7]);
        }
    }

    @And("^Verify product category data and count in catgpenrel$")
    public void verifyProductCategoryDataAndCountInCatgpenrel() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));

        for (String brand : brands) {
            Integer count = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".productcategory.count"));
            String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier");
            String[] expectedProductCategoryData = UrlBuilder.readValueFromConfig(brand + ".st2.productcategory").replace("parentStoreIdentifier", parentStoreIdentifier).split(",");
            List<HashMap> actProductCatEntryDatabaseResult, productCatEntryDatabaseResult;

            actProductCatEntryDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_PRODUCT_CATEGORY.getQuery().replace("|sysTime|", currentDBDateTime));
            productCatEntryDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_CATEGORY.getQuery().replace("|partnumber|", expectedProductCategoryData[0]).replace("|categoryIdentifier|", expectedProductCategoryData[1]));

           assertThat(actProductCatEntryDatabaseResult.size()).isEqualTo(count);

            assertThat(productCatEntryDatabaseResult.get(0).get("PARTNUMBER").toString()).isEqualTo(expectedProductCategoryData[0]);
            assertThat(productCatEntryDatabaseResult.get(0).get("CATEGORY_IDENTIFIER").toString()).isEqualTo(expectedProductCategoryData[1]);
            assertThat(productCatEntryDatabaseResult.get(0).get("CATALOG_IDENTIFIER").toString()).isEqualTo(expectedProductCategoryData[2]);
            assertThat(productCatEntryDatabaseResult.get(0).get("SEQUENCE").toString()).isEqualTo(expectedProductCategoryData[3]);
        }
    }


}
