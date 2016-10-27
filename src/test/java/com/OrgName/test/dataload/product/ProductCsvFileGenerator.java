package com.salmon.test.dataload.product;

import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.category.CategoryDescriptionPimModel;
import com.salmon.test.models.dataload.pim.category.CategoryPimModel;
import com.salmon.test.models.dataload.pim.product.ProductCategoryPimModel;
import com.salmon.test.models.dataload.pim.product.ProductDescriptionPimModel;
import com.salmon.test.models.dataload.pim.product.ProductPimModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.*;
import static com.salmon.test.framework.helpers.DataloadHook.getCurrentDateAndTime;
import static com.salmon.test.framework.helpers.DataloadHook.getTimeStamp;

public class ProductCsvFileGenerator {

    private static final String CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "PIM" + File.separator;
    private static final String PRODUCT_HEADER_WITH_COMMA_DELIMITER = "\"CatalogIdentifier\",\"ProductType\",\"Manufacturer\",\"Sequence\",\"ModelFabriccBrandCode\",\"YooxCode8\",\"NAPPID\",\"YooxCode10\",\"GTIN\",\"EAN\",\"Delete\",\"MODELID\",\"VARIANTID\"";
    private static final String PRODUCT_INCORRECT_HEADER_ORDER = "\"ModelFabriccBrandCode\"|\"YooxCode8\"|\"NAPPID\"|\"YooxCode10\"|\"GTIN\"|\"EAN\"|\"Delete\"|\"MODELID\"|\"VARIANTID\"|\"CatalogIdentifier\"|\"ProductType\"|\"Manufacturer\"|\"Sequence\"";
    private static final String PRODUCT_DESCRIPTION_HEADER = "\"GTIN\"|\"CatalogIdentifier\"|\"Type\"|\"Language\"|\"ExtendedName\"|\"EditorialDescription\"|\"DetailsAndCare\"|\"SizeAndFit\"|\"LongDescription\"|\"Delete\"|\"MODELID\"|\"VARIANTID\"";
    private static final String PRODUCT_DESCRIPTION_HEADER_WITH_COMMA_DELIMITER = "\"GTIN\",\"CatalogIdentifier\",\"Type\",\"Language\",\"ExtendedName\",\"EditorialDescription\",\"DetailsAndCare\",\"SizeAndFit\",\"LongDescription\",\"Delete\",\"MODELID\",\"VARIANTID\"";
    private static final String PRODUCT_DESCRIPTION_INCORRECT_HEADER_ORDER = "\"ExtendedName\"|\"SizeAndFit\"|\"GTIN\"|\"CatalogIdentifier\"|\"Type\"|\"Delete\"|\"MODELID\"|\"VARIANTID\"|\"DetailsAndCare\"|\"Language\"|\"EditorialDescription\"|\"LongDescription\"";
    private static final String PRODUCT_CATEGORY_HEADER = "\"ModelFabricBrandCode(C8)\"|\"BrandColourCode(C10)\"|\"GTIN(C12)\"|\"CatalogIdentifier\"|\"Sequence\"|\"Delete\"|\"ParentGroupIdentifier\"|\"ProductType\"";
    private static final String PIPE_DELIMITER = "|";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String NEW_LINE_SEPARATOR = "\n";
    public static final String PRODUCT_INTERFACE_NUMBER = "050.06";
    public static final String PRODUCT_DESCRIPTION_INTERFACE_NUMBER = "050.07";
    public static final String PRODUCT_CATEGORY_INTERFACE_NUMBER = "050.09";
    private static final String SEQUENCE = "\"Sequence\"";
    private static final String DELETE = "\"Delete\"";
    private static final String EXTRA_FIELD = "\"ExtraField\"";
    private static final String CATALOG_IDENTIFIER = "\"CatalogIdentifier\"";
    private static final String PRODUCT_TYPE = "\"ProductType\"";
    private static final String MANUFACTURER = "\"Manufacturer\"";
    private static final String MODEL_FABRICC_BRANDCODE = "\"ModelFabricBrandCode\"";
    private static final String YOOXCODE8 = "\"YooxCode8\"";
    private static final String NAPPID = "\"NAPPID\"";
    private static final String YOOXCODE10 = "\"YooxCode10\"";
    private static final String GTIN = "\"GTIN\"";
    private static final String EAN = "\"EAN\"";
    private static final String MODELID = "\"MODELID\"";
    private static final String VARIANTID = "\"VARIANTID\"";
    private static final String TYPE = "\"Type\"";
    private static final String LANGUAGE = "\"Language\"";
    private static final String EXTENDED_NAME = "\"ExtendedName\"";
    private static final String EDITORIAL_DESCRIPTION = "\"Editorial_Description\"";
    private static final String DETAILS_AND_CARE = "\"Details_And_Care\"";
    private static final String SIZE_AND_FIT = "\"Size_And_Fit\"";
    private static final String LONG_DESCRIPTION = "\"LongDescription\"";
    private static final String PARENT_GROUP_IDENTIFIER = "\"ParentGroupIdentifier\"";
    private static final String PRODUCT_FILENAME = "ProductPIM.csv";
    private static final String PRODUCT_DESCRIPTION_FILENAME = "ProductDescriptionPIM.csv";
    private static final String PRODUCT_CATEGORY_FILENAME = "ProductCategoryPIM.csv";
    private static List<String> brands;
    private static String DOT = ".";
    private static int i = 0;

    private static String generateProductCategoryCsvFileHeader(ProductCategoryPimModel productCategoryPimModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((productCategoryPimModel.getGtin() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productCategoryPimModel.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productCategoryPimModel.getSequence() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productCategoryPimModel.getDelete() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productCategoryPimModel.getParentGroupIdentifier() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productCategoryPimModel.getProductType() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if (productCategoryPimModel.getModelId() == null) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if (productCategoryPimModel.getVariantId() == null) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
        } else if (!(productCategoryPimModel.getExtraField() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        }
        return headerStrBuffer.toString();
    }


    private static String generateProductDescriptionCsvFileHeader(ProductDescriptionPimModel productDescriptionPimModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((productDescriptionPimModel.getGtin() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getType() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getLanguage() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getExtendedName() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getEditorialDescription() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getDetailsAndCare() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getSizeAndFit() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getLongDescription() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productDescriptionPimModel.getDelete() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if (productDescriptionPimModel.getModelId() == null) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if (productDescriptionPimModel.getVariantId() == null) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
        } else if (!(productDescriptionPimModel.getExtraField() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTENDED_NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EDITORIAL_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DETAILS_AND_CARE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SIZE_AND_FIT);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LONG_DESCRIPTION);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        }
        return headerStrBuffer.toString();
    }


    private static String generateProductCsvFileHeader(ProductPimModel productPimModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((productPimModel.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getProductType() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getManufacturer() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getSequence() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
            headerStrBuffer.append(PIPE_DELIMITER);
        } else if ((productPimModel.getModelFabricBrandCode() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getYooxCode8() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getNappID() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getYooxCode10() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getGtin() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getEan() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getDelete() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getModelId() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        } else if ((productPimModel.getVariantId() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
        } else if (!(productPimModel.getExtraField() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MANUFACTURER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_FABRICC_BRANDCODE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE8);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAPPID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(YOOXCODE10);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EAN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODELID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANTID);
        }
        return headerStrBuffer.toString();
    }


    public static void generateProductCsvFile(List<ProductPimModel> productPimModelList) throws IOException {
        FileWriter fileWriter = null;
        //checkFileExists(getFilePath());
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));

        //setTimeStamp(getCurrentDateAndTime());

        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, PRODUCT_FILENAME);
            try {
                fileWriter = writeProductDataIntoFile(brand, productPimModelList, fileWriter, FILE_NAME);

            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }

    }


    public static void generateProductCsvFileWithDelimiter(List<ProductPimModel> productPimModelList, String delimiter) throws IOException {
        FileWriter fileWriter = null;
        //checkFileExists(getFilePath());
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        //setTimeStamp(getCurrentDateAndTime());

        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, PRODUCT_FILENAME);
            try {
                fileWriter = writeProductDataIntoFileWithDelimiter(brand, productPimModelList, fileWriter, FILE_NAME, delimiter);

            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }


    public static void generateProductCsvFileWithIncorrectHeaderOrder(List<ProductPimModel> productPimModelList, String delimiter) throws IOException {
        FileWriter fileWriter = null;
        //checkFileExists(getFilePath());
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        //setTimeStamp(getCurrentDateAndTime());

        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, PRODUCT_FILENAME);
            try {
                fileWriter = writeProductDataIntoFileWithIncorrectHeaderOrder(brand, productPimModelList, fileWriter, FILE_NAME, delimiter);

            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

    public static void generateProductDescriptionCsvFileWithDelimiter(List<ProductDescriptionPimModel> productDescriptionPimModels, String delimiter) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, PRODUCT_DESCRIPTION_FILENAME);
            try {
                if (delimiter == ",") {
                    fileWriter = writeProductDescriptionDataIntoFileWithDelimiter(brand, productDescriptionPimModels, fileWriter, FILE_NAME, delimiter);
                } else if (delimiter == "|") {
                    fileWriter = writeProductDescriptionDataIntoFileWithIncorrectHeaderOrder(brand, productDescriptionPimModels, fileWriter, FILE_NAME, delimiter);
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }

    public static void generateProductDescriptionCsvFile(List<ProductDescriptionPimModel> productDescriptionPimModelList) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        for (String brand : brands) {
            //String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getFilePath() + File.separator + folderName(brand) + File.separator + "" + brand + "_" + getTimeStamp() + "_" + PRODUCT_DESCRIPTION_INTERFACE_NUMBER + "-" + PRODUCT_DESCRIPTION_FILENAME;
            try {
                fileWriter = writeProductDescriptionDataIntoFile(brand, productDescriptionPimModelList, fileWriter, FILE_NAME);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }


    public static void generateProductCategoryCsvFile(List<ProductCategoryPimModel> productCategoryPimModelList) throws IOException {
        FileWriter fileWriter = null;

        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, PRODUCT_CATEGORY_FILENAME);
          //  String FILE_NAME = getFilePath() + File.separator + folderName(brand) + File.separator + "" + brand + "_" + getTimeStamp() + "_" + PRODUCT_CATEGORY_INTERFACE_NUMBER + "-" + PRODUCT_CATEGORY_FILENAME;
            try {
                fileWriter = writeProductCategoryDataIntoFile(brand, productCategoryPimModelList, fileWriter, FILE_NAME);
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            } finally {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (IOException e) {
                    System.out.println(e.getStackTrace());
                }
            }
        }
    }


    private static String getFilePath() throws IOException {
        return new File(".").getCanonicalPath() + CSV_FILE_PATH;
    }


    //Write product data into csv file with comma delimiter
    private static FileWriter writeProductDataIntoFileWithDelimiter(String brand, List<ProductPimModel> productPimModelList, FileWriter writer, String FILE_NAME, String commaDelimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(PRODUCT_HEADER_WITH_COMMA_DELIMITER);
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductPimModel product : productPimModelList) {

            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + product.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, commaDelimiter);

            enterFieldValue(brand, writer, String.valueOf(product.getProductType()), commaDelimiter);

            String manufacturer = UrlBuilder.readValueFromConfig(brand + DOT + product.getManufacturer());
            enterFieldValue(brand, writer, manufacturer, commaDelimiter);

            enterFieldValue(brand, writer, String.valueOf(product.getSequence()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(product.getModelFabricBrandCode()), commaDelimiter);
            if (product.getNappID() != null) {
                if (product.getNappID().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }
            if (product.getYooxCode10() != null) {
                if (product.getYooxCode10().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }
            if (product.getGtin() != null) {
                if (product.getGtin().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }
            if (product.getEan() != null) {
                if (product.getEan().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }

            enterFieldValue(brand, writer, String.valueOf(product.getDelete()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(product.getModelId()), commaDelimiter);

            if (product.getVariantId() != null) {
                if (product.getVariantId().equalsIgnoreCase("")) {
                    writer.append(NEW_LINE_SEPARATOR);
                }
            }
        }
        return writer;
    }

    //Write product description data into csv file with comma delimiter
    private static FileWriter writeProductDescriptionDataIntoFileWithDelimiter(String brand, List<ProductDescriptionPimModel> productDescriptionPimModels, FileWriter writer, String FILE_NAME, String commaDelimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(PRODUCT_DESCRIPTION_HEADER_WITH_COMMA_DELIMITER);
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductDescriptionPimModel productDescription : productDescriptionPimModels) {
            if (productDescription.getGtin() != null) {
                if (productDescription.getGtin().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + productDescription.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getType()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getLanguage()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getExtendedName()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getEditorialDescription()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getDetailsAndCare()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getSizeAndFit()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getLongDescription()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getDelete()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getModelId()), commaDelimiter);
            if (productDescription.getVariantId() != null) {
                if (productDescription.getVariantId().equalsIgnoreCase("")) {
                    writer.append(NEW_LINE_SEPARATOR);
                }
            }
        }
        return writer;
    }

    //Write product description data into csv file with Incorrect header order
    private static FileWriter writeProductDescriptionDataIntoFileWithIncorrectHeaderOrder(String brand, List<ProductDescriptionPimModel> productDescriptionPimModels, FileWriter writer, String FILE_NAME, String delimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(PRODUCT_DESCRIPTION_INCORRECT_HEADER_ORDER);
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductDescriptionPimModel productDescription : productDescriptionPimModels) {
            enterFieldValue(brand, writer, String.valueOf(productDescription.getExtendedName()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getSizeAndFit()), delimiter);
            if (productDescription.getGtin() != null) {
                if (productDescription.getGtin().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + productDescription.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, delimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getType()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getDelete()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getModelId()), delimiter);
            if (productDescription.getVariantId() != null) {
                if (productDescription.getVariantId().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            enterFieldValue(brand, writer, String.valueOf(productDescription.getDetailsAndCare()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getLanguage()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getEditorialDescription()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productDescription.getLongDescription()), NEW_LINE_SEPARATOR);
        }
        return writer;
    }

    //Write product data into csv file with comma delimiter
    private static FileWriter writeProductDataIntoFileWithIncorrectHeaderOrder(String brand, List<ProductPimModel> productPimModelList, FileWriter writer, String FILE_NAME, String delimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(PRODUCT_INCORRECT_HEADER_ORDER);
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductPimModel product : productPimModelList) {

            enterFieldValue(brand, writer, String.valueOf(product.getModelFabricBrandCode()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(product.getYooxCode8()), delimiter);
            if (product.getNappID() != null) {
                if (product.getNappID().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            if (product.getYooxCode10() != null) {
                if (product.getYooxCode10().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            if (product.getGtin() != null) {
                if (product.getGtin().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            if (product.getEan() != null) {
                if (product.getEan().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }

            enterFieldValue(brand, writer, String.valueOf(product.getDelete()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(product.getModelId()), delimiter);

            if (product.getVariantId() != null) {
                if (product.getVariantId().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }

            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + product.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, delimiter);

            enterFieldValue(brand, writer, String.valueOf(product.getProductType()), delimiter);

            String manufacturer = UrlBuilder.readValueFromConfig(brand + DOT + product.getManufacturer());
            enterFieldValue(brand, writer, manufacturer, delimiter);

            enterFieldValue(brand, writer, String.valueOf(product.getSequence()), delimiter);
            writer.append(NEW_LINE_SEPARATOR);
        }
        return writer;
    }

    private static void enterFieldValue(String brand, FileWriter writer, String fieldVaue, String delimiter) throws IOException {
        writer.append(DOUBLE_QUOTES);
        writer.append(fieldVaue);
        writer.append(DOUBLE_QUOTES);
        writer.append(delimiter);
    }


    //Write product data into csv file

    private static FileWriter writeProductDataIntoFile(String brand, List<ProductPimModel> productPimModelList, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateProductCsvFileHeader(productPimModelList.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductPimModel product : productPimModelList) {
            if (product.getCatalogIdentifier() != null) {
                if (product.getCatalogIdentifier().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(UrlBuilder.readValueFromConfig(brand + DOT + product.getCatalogIdentifier()));
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getProductType() != null) {
                if (product.getProductType().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(String.valueOf(product.getProductType()));
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getManufacturer() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + product.getManufacturer()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (product.getSequence() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(String.valueOf(product.getSequence()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }

            if (product.getModelFabricBrandCode() != null) {
                if (product.getModelFabricBrandCode().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(product.getModelFabricBrandCode());
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getYooxCode8() != null) {
                if (product.getYooxCode8().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(product.getYooxCode8());
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getNappID() != null) {
                if (product.getNappID().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(product.getNappID());
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getYooxCode10() != null) {
                if (product.getYooxCode10().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(product.getYooxCode10());
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getGtin() != null) {
                if (product.getGtin().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(product.getGtin());
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getEan() != null) {
                if (product.getEan().equalsIgnoreCase("")) {
                    writer.append(PIPE_DELIMITER);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(product.getEan());
                    writer.append(DOUBLE_QUOTES);
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (product.getDelete() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(String.valueOf(product.getDelete()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (product.getModelId() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(String.valueOf(product.getModelId()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (product.getVariantId() != null) {
                if (product.getVariantId().equalsIgnoreCase("")) {
                    writer.append(NEW_LINE_SEPARATOR);
                } else {
                    writer.append(DOUBLE_QUOTES);
                    writer.append(String.valueOf(product.getVariantId()));
                    writer.append(DOUBLE_QUOTES);
                    writer.append(NEW_LINE_SEPARATOR);
                }
            }
            if (!(product.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(product.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }
        }
        return writer;
    }

    //Write product description data into csv file
    private static FileWriter writeProductDescriptionDataIntoFile(String brand, List<ProductDescriptionPimModel> productDescriptionPimModelList, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateProductDescriptionCsvFileHeader(productDescriptionPimModelList.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductDescriptionPimModel productDescriptionPimModel : productDescriptionPimModelList) {
            if (productDescriptionPimModel.getGtin() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getGtin()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getGtin());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getGtin())) {
                writer.append(PIPE_DELIMITER);
            }
            if (productDescriptionPimModel.getCatalogIdentifier() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getCatalogIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + productDescriptionPimModel.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getCatalogIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getType() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getType()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getType());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getType())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getLanguage() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getLanguage()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getLanguage());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getLanguage())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getExtendedName() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getExtendedName()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getExtendedName());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getExtendedName())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getEditorialDescription() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getEditorialDescription()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getEditorialDescription());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getEditorialDescription())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getDetailsAndCare() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getDetailsAndCare()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getDetailsAndCare());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getDetailsAndCare())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getSizeAndFit() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getSizeAndFit()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getSizeAndFit());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getSizeAndFit())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getLongDescription() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getLongDescription()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getLongDescription());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getLongDescription())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getDelete() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getDelete()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getDelete());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getDelete())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getModelId() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getModelId()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getModelId());
                writer.append(DOUBLE_QUOTES);
                if (productDescriptionPimModel.getVariantId() != null) {
                    writer.append(PIPE_DELIMITER);
                }
            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getModelId())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productDescriptionPimModel.getVariantId() != null && !("".equalsIgnoreCase(productDescriptionPimModel.getVariantId()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getVariantId());
                writer.append(DOUBLE_QUOTES);
                if (productDescriptionPimModel.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }

            } else if ("".equalsIgnoreCase(productDescriptionPimModel.getModelId())) {
                writer.append(PIPE_DELIMITER);
            } else {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(productDescriptionPimModel.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(productDescriptionPimModel.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }

        }
        return writer;
    }


    //Write product Category data into csv file
    private static FileWriter writeProductCategoryDataIntoFile(String brand, List<ProductCategoryPimModel> productCategoryPimModels, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateProductCategoryCsvFileHeader(productCategoryPimModels.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductCategoryPimModel productCategoryPimModel : productCategoryPimModels) {

            if (productCategoryPimModel.getGtin() != null && !("".equalsIgnoreCase(productCategoryPimModel.getGtin()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getGtin());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productCategoryPimModel.getGtin())) {
                writer.append(PIPE_DELIMITER);
            }
            if (productCategoryPimModel.getCatalogIdentifier() != null && !("".equalsIgnoreCase(productCategoryPimModel.getCatalogIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + productCategoryPimModel.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productCategoryPimModel.getCatalogIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productCategoryPimModel.getSequence() != null && !("".equalsIgnoreCase(productCategoryPimModel.getSequence()))) {

                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getSequence());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productCategoryPimModel.getSequence())) {
                writer.append(PIPE_DELIMITER);
            }


            if (productCategoryPimModel.getDelete() != null && !("".equalsIgnoreCase(productCategoryPimModel.getDelete()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getDelete());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productCategoryPimModel.getDelete())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productCategoryPimModel.getParentGroupIdentifier() != null && !("".equalsIgnoreCase(productCategoryPimModel.getParentGroupIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getParentGroupIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productCategoryPimModel.getParentGroupIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productCategoryPimModel.getProductType() != null && !("".equalsIgnoreCase(productCategoryPimModel.getProductType()))) {

                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getProductType());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productCategoryPimModel.getProductType())) {
                writer.append(PIPE_DELIMITER);
            }
            if (productCategoryPimModel.getModelId() != null && !("".equalsIgnoreCase(productCategoryPimModel.getModelId()))) {

                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getModelId());
                writer.append(DOUBLE_QUOTES);
                if (productCategoryPimModel.getVariantId() != null) {
                    writer.append(PIPE_DELIMITER);
                }
            } else if ("".equalsIgnoreCase(productCategoryPimModel.getModelId())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productCategoryPimModel.getVariantId() != null && !("".equalsIgnoreCase(productCategoryPimModel.getVariantId()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getVariantId());
                writer.append(DOUBLE_QUOTES);

                if (productCategoryPimModel.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }

            } else if ("".equalsIgnoreCase(productCategoryPimModel.getVariantId())) {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(productCategoryPimModel.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(productCategoryPimModel.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }
        }
        return writer;
    }
}
