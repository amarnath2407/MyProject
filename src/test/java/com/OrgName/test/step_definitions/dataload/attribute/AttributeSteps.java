package com.salmon.test.step_definitions.dataload.attribute;

import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.attribute.AttributePimModel;
import com.salmon.test.models.dataload.pim.attribute.AttributeValuesPimModel;
import com.salmon.test.models.dataload.pim.attribute.ProductAttributeValuesPimModel;
import com.salmon.test.models.dataload.pim.product.ProductPimModel;
import com.salmon.test.models.dataload.wcs.attribute.AttributeValuesWcsModel;
import com.salmon.test.models.dataload.wcs.attribute.AttributeWcsModel;
import com.salmon.test.models.dataload.wcs.attribute.ProductAttributeValuesWcsModel;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.salmon.test.dataload.attribute.AttributeCsvFileGenerator.*;
import static com.salmon.test.dataload.attribute.AttributeWcsFileChecker.checkTalendAttributeFileFormat;
import static com.salmon.test.dataload.attribute.AttributeWcsFileChecker.checkTalendAttributeValuesFileFormat;
import static com.salmon.test.dataload.attribute.AttributeWcsFileChecker.checkTalendProductAttributeValuesFileFormat;
import static org.assertj.core.api.Assertions.assertThat;

public class AttributeSteps {

    private AttributePimModel attributePimModel;
    private AttributeValuesPimModel attributeValuesPimModels;
    private ProductAttributeValuesPimModel productAttributeValuesPimModels;
    private AttributeWcsModel attributeWcsModels;
    private AttributeValuesWcsModel attributeValuesWcsModels;
    private ProductAttributeValuesWcsModel productAttributeValuesWcsModels;
    private String profileId = UrlBuilder.readValueFromConfig("profile.id");

    public AttributeSteps(AttributePimModel attributePimModel, AttributeValuesPimModel attributeValuesPimModels, ProductAttributeValuesPimModel productAttributeValuesPimModels, AttributeWcsModel attributeWcsModels, AttributeValuesWcsModel attributeValuesWcsModels, ProductAttributeValuesWcsModel productAttributeValuesWcsModels) {
        this.attributePimModel = attributePimModel;
        this.attributeValuesPimModels = attributeValuesPimModels;
        this.productAttributeValuesPimModels = productAttributeValuesPimModels;
        this.attributeWcsModels = attributeWcsModels;
        this.attributeValuesWcsModels = attributeValuesWcsModels;
        this.productAttributeValuesWcsModels = productAttributeValuesWcsModels;
    }

    @Given("^I create Attribute PIM file$")
    public void iCreateAttributePIMFile(List<AttributePimModel> attributePimModelList) throws Throwable {
        generateAttributeCsvFile(attributePimModelList);
    }

    @Given("^I create Attribute Values PIM file$")
    public void iCreateAttributeValuesPIMFile(List<AttributeValuesPimModel> attributeValuesPimModels) throws Throwable {
        generateAttributeValuesCsvFile(attributeValuesPimModels);
    }

    @And("^I create Product Attribute Values PIM file$")
    public void iCreateProductAttributeValuesPIMFile(List<ProductAttributeValuesPimModel> productAttributeValuesPimModels) throws Throwable {
        generateProductAttributeValuesCsvFile(productAttributeValuesPimModels);
    }

    @Then("^verify WCS Attribute file has the correct format$")
    public void verifyWCSAttributeFileHasTheCorrectFormat(List<AttributeWcsModel> attributeWcsModels) throws Throwable {
        checkTalendAttributeFileFormat(attributeWcsModels);
    }

    @And("^data is inserted into database table for Attribute$")
    public void dataIsInsertedIntoDatabaseTableForAttribute(List<AttributeWcsModel> attributeWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actAttributeDatabaseResult;

            for (AttributeWcsModel attributeWcsModel : attributeWcsModels) {
                String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand +"."+ attributeWcsModel.getParentStoreIdentifier());
                actAttributeDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_ATTRIBUTE.getQuery().replace("|categoryIdentifier|", parentStoreIdentifier).replace("|attributeIdentifier|", attributeWcsModel.getAttributeIdentifier()).replace("|languageId|", attributeWcsModel.getLanguageId()));
                assertThat(actAttributeDatabaseResult.size()).isGreaterThan(0);

                for (HashMap attributeDatabaseResult : actAttributeDatabaseResult) {
                    assertThat(attributeDatabaseResult.get("PARENTSTOREIDENTIFIER").toString()).isEqualTo(parentStoreIdentifier);
                    assertThat(attributeDatabaseResult.get("ATTRIBUTEIDENTIFIER").toString()).isEqualTo(attributeWcsModel.getAttributeIdentifier());
                    assertThat(attributeDatabaseResult.get("LANGUAGE_ID").toString()).isEqualTo(attributeWcsModel.getLanguageId());
                    assertThat(attributeDatabaseResult.get("NAME").toString()).isEqualTo(attributeWcsModel.getName());
                    assertThat(attributeDatabaseResult.get("ATTRUSAGE").toString()).isEqualTo(attributeWcsModel.getAttrUsage());
                }
            }
        }
    }

    @And("^verify WCS Attribute Values file has the correct format$")
    public void verifyWCSAttributeValuesFileHasTheCorrectFormat(List<AttributeValuesWcsModel> attributeValuesWcsModels) throws Throwable {
        checkTalendAttributeValuesFileFormat(attributeValuesWcsModels);
    }

    @And("^data is inserted into database table for Attribute Values$")
    public void dataIsInsertedIntoDatabaseTableForAttributeValues(List<AttributeValuesWcsModel> attributeValuesWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actAttributeValuesDatabaseResult;
            for (AttributeValuesWcsModel attributeValuesWcsModel : attributeValuesWcsModels) {
                String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand +"."+ attributeValuesWcsModel.getParentStoreIdentifier());
                actAttributeValuesDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_ATTRIBUTE_VALUES.getQuery().replace("|categoryIdentifier|", parentStoreIdentifier).replace("|attributeValueIdentifier|", attributeValuesWcsModel.getAttributeValueIdentifier()).replace("|languageId|", attributeValuesWcsModel.getLanguageId()));
                assertThat(actAttributeValuesDatabaseResult.size()).isGreaterThan(0);

                for (HashMap attributeValuesDatabaseResult : actAttributeValuesDatabaseResult) {
                    assertThat(attributeValuesDatabaseResult.get("PARENTSTOREIDENTIFIER").toString()).isEqualTo(parentStoreIdentifier);
                    assertThat(attributeValuesDatabaseResult.get("ATTRIBUTEVALUEIDENTIFIER").toString()).isEqualTo(attributeValuesWcsModel.getAttributeValueIdentifier());
                    assertThat(attributeValuesDatabaseResult.get("ATTRIBUTEIDENTIFIER").toString()).isEqualTo(attributeValuesWcsModel.getAttributeIdentifier());
                    assertThat(attributeValuesDatabaseResult.get("FIELD1").toString()).isEqualTo(attributeValuesWcsModel.getField1());
                    assertThat(attributeValuesDatabaseResult.get("LANGUAGE_ID").toString()).isEqualTo(attributeValuesWcsModel.getLanguageId());
                    assertThat(attributeValuesDatabaseResult.get("VALUE").toString()).isEqualTo(attributeValuesWcsModel.getValue());
                    assertThat(attributeValuesDatabaseResult.get("SEQUENCE").toString()).isEqualTo(attributeValuesWcsModel.getSequence());
                    assertThat(attributeValuesDatabaseResult.get("VALUSAGE").toString()).isEqualTo(attributeValuesWcsModel.getValUsage());
                }
            }
        }
    }

    @And("^verify WCS Product Attribute Values file has the correct format$")
    public void verifyWCSProductAttributeValuesFileHasTheCorrectFormat(List<ProductAttributeValuesWcsModel> productAttributeValuesWcsModels) throws Throwable {
        checkTalendProductAttributeValuesFileFormat(productAttributeValuesWcsModels);
    }

    @And("^data is inserted into database table for Product Attribute Values$")
    public void dataIsInsertedIntoDatabaseTableForProductAttributeValues(List<ProductAttributeValuesWcsModel> productAttributeValuesWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actProductAttributeValuesDatabaseResult;
            for (ProductAttributeValuesWcsModel productAttributeValuesWcsModel : productAttributeValuesWcsModels) {
                String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand +"."+ productAttributeValuesWcsModel.getParentStoreIdentifier());
                actProductAttributeValuesDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_ATTRIBUTE_VALUES.getQuery().replace("|categoryIdentifier|", parentStoreIdentifier).replace("|partNumber|", productAttributeValuesWcsModel.getPartNumber()).replace("|attributeValueIdentifier|", productAttributeValuesWcsModel.getAttributeValueIdentifier()));
                assertThat(actProductAttributeValuesDatabaseResult.size()).isGreaterThan(0);

                for (HashMap productAttributeValuesDatabaseResult : actProductAttributeValuesDatabaseResult) {
                    assertThat(productAttributeValuesDatabaseResult.get("PARTNUMBER").toString()).isEqualTo(productAttributeValuesWcsModel.getPartNumber());
                    assertThat(productAttributeValuesDatabaseResult.get("PARENTSTOREIDENTIFIER").toString()).isEqualTo(parentStoreIdentifier);
                    assertThat(productAttributeValuesDatabaseResult.get("ATTRIBUTEVALUEIDENTIFIER").toString()).isEqualTo(productAttributeValuesWcsModel.getAttributeValueIdentifier());
                    assertThat(productAttributeValuesDatabaseResult.get("ATTRIBUTEIDENTIFIER").toString()).isEqualTo(productAttributeValuesWcsModel.getAttributeIdentifier());
                    assertThat(productAttributeValuesDatabaseResult.get("USAGE").toString()).isEqualTo(productAttributeValuesWcsModel.getUsage());
                    assertThat(productAttributeValuesDatabaseResult.get("SEQUENCE").toString()).isEqualTo(productAttributeValuesWcsModel.getSequence());
                }
            }
        }
    }

    @Given("^I create Attribute PIM file with comma delimiter$")
    public void iCreateAttributePIMFileWithCommaDelimiter(List<AttributePimModel> attributePimModels) throws Throwable {
        generateAttributeCsvFileWithDelimiter(attributePimModels, ",");
    }

    @Given("^I create Attribute PIM file with wrong header row order$")
    public void iCreateAttributePIMFileWithWrongHeaderRowOrder(List<AttributePimModel> attributePimModels) throws Throwable {
        generateAttributeCsvFileWithDelimiter(attributePimModels, "|");
    }

    @Then("^data is deleted into database table for Attribute$")
    public void dataIsDeletedIntoDatabaseTableForAttribute(List<AttributeWcsModel> attributeWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actAttributeDatabaseResult;
            for (AttributeWcsModel attributeWcsModel : attributeWcsModels) {
                String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand +"."+ attributeWcsModel.getParentStoreIdentifier());
                actAttributeDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_ATTRIBUTE.getQuery().replace("|categoryIdentifier|", parentStoreIdentifier).replace("|attributeIdentifier|", attributeWcsModel.getAttributeIdentifier()).replace("|languageId|", attributeWcsModel.getLanguageId()));
                assertThat(actAttributeDatabaseResult.size()).isEqualTo(0);
            }
        }
    }

    @Then("^data is deleted into database table for Attribute Values$")
    public void dataIsDeletedIntoDatabaseTableForAttributeValues(List<AttributeValuesWcsModel> attributeValuesWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actAttributeValuesDatabaseResult;
            for (AttributeValuesWcsModel attributeValuesWcsModel : attributeValuesWcsModels) {
                String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand +"."+ attributeValuesWcsModel.getParentStoreIdentifier());
                actAttributeValuesDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_ATTRIBUTE_VALUES.getQuery().replace("|categoryIdentifier|", parentStoreIdentifier).replace("|attributeValueIdentifier|", attributeValuesWcsModel.getAttributeValueIdentifier()).replace("|languageId|", attributeValuesWcsModel.getLanguageId()));
                assertThat(actAttributeValuesDatabaseResult.size()).isEqualTo(0);
            }
        }
    }

    @Then("^data is deleted into database table for Product Attribute Values$")
    public void dataIsDeletedIntoDatabaseTableForProductAttributeValues(List<ProductAttributeValuesWcsModel> productAttributeValuesWcsModels) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            List<HashMap> actProductAttributeValuesDatabaseResult;
            for (ProductAttributeValuesWcsModel productAttributeValuesWcsModel : productAttributeValuesWcsModels) {
                String parentStoreIdentifier = UrlBuilder.readValueFromConfig(brand +"."+ productAttributeValuesWcsModel.getParentStoreIdentifier());
                actProductAttributeValuesDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_PRODUCT_ATTRIBUTE_VALUES.getQuery().replace("|categoryIdentifier|", parentStoreIdentifier).replace("|partNumber|", productAttributeValuesWcsModel.getPartNumber()).replace("|attributeValueIdentifier|", productAttributeValuesWcsModel.getAttributeValueIdentifier()));
                assertThat(actProductAttributeValuesDatabaseResult.size()).isEqualTo(0);
            }
        }
    }

    @Given("^I create Attribute Values PIM file with comma delimiter$")
    public void iCreateAttributeValuesPIMFileWithCommaDelimiter(List<AttributeValuesPimModel> attributeValuesPimModels) throws Throwable {
        generateAttributeValuesCsvFileWithDelimiter(attributeValuesPimModels, ",");
    }

    @Given("^I create Attribute Values PIM file with wrong header row order$")
    public void iCreateAttributeValuesPIMFileWithWrongHeaderRowOrder(List<AttributeValuesPimModel> attributeValuesPimModels) throws Throwable {
        generateAttributeValuesCsvFileWithDelimiter(attributeValuesPimModels, "|");
    }

    @Given("^I create Product Attribute Values PIM file with comma delimiter$")
    public void iCreateProductAttributeValuesPIMFileWithCommaDelimiter(List<ProductAttributeValuesPimModel> productAttributeValuesPimModels) throws Throwable {
        generateProductAttributeValuesCsvFileWithDelimiter(productAttributeValuesPimModels, ",");
    }

    @Given("^I create Product Attribute Values PIM file with wrong header row order$")
    public void iCreateProductAttributeValuesPIMFileWithWrongHeaderRowOrder(List<ProductAttributeValuesPimModel> productAttributeValuesPimModels) throws Throwable {
        generateProductAttributeValuesCsvFileWithDelimiter(productAttributeValuesPimModels, "|");
    }
}
