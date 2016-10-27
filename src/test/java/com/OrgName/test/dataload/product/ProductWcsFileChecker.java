package com.salmon.test.dataload.product;

import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.wcs.product.CommercialBanningWcsModel;
import com.salmon.test.models.dataload.wcs.product.ProductCategoryWcsModel;
import com.salmon.test.models.dataload.wcs.product.ProductDescriptionWcsModel;
import com.salmon.test.models.dataload.wcs.product.ProductWcsModel;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryWcsFileChecker.*;
import static com.salmon.test.framework.helpers.DataLoaderHelper.readWcsMappingFile;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductWcsFileChecker {
    private static final String WCS_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;

    private static final String WCS_PRODUCT_HEADER = "ParentStoreIdentifier|PartNumber|ParentPartNumber|ParentColourPartNumber|Type|Sequence|ManufacturerPartNumber|Manufacturer|Buyable|StartDate|EndDate|Field1|Field2|Field3|Field4|Delete";
    private static final String WCS_PRODUCT_DESCRIPTION_HEADER = "LanguageID|PartNumber|Name|ExtendedName|EditorialDescription|DetailsAndCare|ShortDescription|SizeAndFit|LongDescription|AuxDescription1|AuxDescription2|Published|Delete|ParentStoreIdentifier";
    private static final String WCS_PRODUCT_CATEGORY_HEADER = "PartNumber|PartnumberParentStoreIdentifier|Sequence|Delete|ParentGroupIdentifier|ParentStoreIdentifier";
    private static final String WCS_PRODUCT_BANNING_HEADER = "PartNumber|ParentStoreIdentifier|Sequence|Delete|ParentGroupIdentifier|ParentStoreIdentifier";


    private static final String PIPE_DELIMITER = "|";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String PRODUCT_WCS_INTERFACE_NUMBER = "001.06";
    private static final String PRODUCT_DESCRIPTION_WCS_INTERFACE_NUMBER = "001.07";
    private static final String PRODUCT_FILENAME = "Product";
    private static final String PRODUCT_DESCRIPTION_FILENAME = "ProductDescription";
    private static final String PRODUCT_CATEGORY_FILENAME = "ProductCategory";
    private static final String COMM_BANNING_FILENAME = "CommercialBanning";

    private static StringBuilder productRowBuilder;
    private static StringBuilder productDescriptionRowBuilder;
    private static StringBuilder productCategoryRowBuilder;
    private static StringBuilder productBanningRowBuilder;
    private static List<String> brands;
    private static String profileId;

    public static void checkTalendProductFileFormat(List<ProductWcsModel> productWcsModelList) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, PRODUCT_FILENAME);
            assertThat(reader.readLine().contains(WCS_PRODUCT_HEADER)).isTrue();
            for (ProductWcsModel productWcsModel : productWcsModelList) {
                productRowBuilder = new StringBuilder();
                buildProductRow(productRowBuilder, productWcsModel, brand);
                assertThat(reader.readLine().contains(productRowBuilder)).isTrue();
            }
            reader.close();
        }
    }

    public static void checkTalendProductDescriptionFileFormat(List<ProductDescriptionWcsModel> productDescriptionWcsModelList) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, PRODUCT_DESCRIPTION_FILENAME);
            assertThat(reader.readLine().contains(WCS_PRODUCT_DESCRIPTION_HEADER)).isTrue();
            for (ProductDescriptionWcsModel productDescriptionWcsModel : productDescriptionWcsModelList) {
                productDescriptionRowBuilder = new StringBuilder();
                buildProductDescriptionRow(productDescriptionRowBuilder, productDescriptionWcsModel, brand);
                assertThat(reader.readLine().contains(productDescriptionRowBuilder)).isTrue();
            }
            reader.close();
        }
    }

    public static void checkTalendProducCategoryTransformtionFile(List<ProductCategoryWcsModel> productCategoryWcsModelList) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, PRODUCT_CATEGORY_FILENAME);
            assertThat(reader.readLine().contains(WCS_PRODUCT_CATEGORY_HEADER)).isTrue();
            for (ProductCategoryWcsModel productCategoryWcsModel : productCategoryWcsModelList) {
                productCategoryRowBuilder = new StringBuilder();
                buildProductCategoryRow(productCategoryRowBuilder, productCategoryWcsModel, brand);
                assertThat(reader.readLine().contains(productCategoryRowBuilder)).isTrue();
            }
            reader.close();
        }
    }


    public static void verifyWcsProductBanningMappingFile(List<CommercialBanningWcsModel> banningWcsModelsList, String interfaceName) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsMappingFile(brand, COMM_BANNING_FILENAME, interfaceName);
            assertThat(reader.readLine().contains(WCS_PRODUCT_BANNING_HEADER)).isTrue();
            for (CommercialBanningWcsModel banningWcsModel : banningWcsModelsList) {
                productBanningRowBuilder = new StringBuilder();
                buildProductBanningRow(productBanningRowBuilder, banningWcsModel, brand);
                assertThat(reader.readLine().contains(productBanningRowBuilder)).isTrue();
            }
            reader.close();
        }
    }


    private static void buildProductBanningRow(StringBuilder productBanningRowBuilder, CommercialBanningWcsModel banningWcsModel, String brand) {
        if (!banningWcsModel.getPartNumber().equalsIgnoreCase("")) {
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(banningWcsModel.getPartNumber());
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(PIPE_DELIMITER);
        }
        if (!banningWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier"));
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(PIPE_DELIMITER);
        }
        if (!banningWcsModel.getAttributeValueIdentifier().equalsIgnoreCase("")) {
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(banningWcsModel.getAttributeValueIdentifier());
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(PIPE_DELIMITER);
        }

        if (!banningWcsModel.getAttributeIdentifier().equalsIgnoreCase("")) {
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(banningWcsModel.getAttributeIdentifier());
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(PIPE_DELIMITER);
        }

        if (!banningWcsModel.getUsage().equalsIgnoreCase("")) {
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(banningWcsModel.getUsage());
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(PIPE_DELIMITER);
        }

        if (!banningWcsModel.getSequence().equalsIgnoreCase("")) {
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(banningWcsModel.getSequence());
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(PIPE_DELIMITER);
        }

        if (!banningWcsModel.getDelete().equalsIgnoreCase("")) {
            productBanningRowBuilder.append(DOUBLE_QUOTES);
            productBanningRowBuilder.append(banningWcsModel.getDelete());
            productBanningRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }


    private static void buildProductCategoryRow(StringBuilder productCategoryRowBuilder, ProductCategoryWcsModel productCategoryWcsModel, String brand) {
        if (!productCategoryWcsModel.getPartNumber().equalsIgnoreCase("")) {
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(productCategoryWcsModel.getPartNumber());
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productCategoryWcsModel.getPartNumberParentStoreIdentifier().equalsIgnoreCase("")) {
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier"));
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productCategoryWcsModel.getSequence().equalsIgnoreCase("")) {
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(productCategoryWcsModel.getSequence());
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(PIPE_DELIMITER);
        }

        if (!productCategoryWcsModel.getDelete().equalsIgnoreCase("")) {
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(productCategoryWcsModel.getDelete());
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(PIPE_DELIMITER);
        }

        if (!productCategoryWcsModel.getParentGroupIdentifier().equalsIgnoreCase("")) {
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(productCategoryWcsModel.getParentGroupIdentifier());
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(PIPE_DELIMITER);
        }

        if (!productCategoryWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
            productCategoryRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier"));
            productCategoryRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }

    private static void buildProductRow(StringBuilder productRowBuilder, ProductWcsModel productWcsModel, String brand) {

        if (!productWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier"));
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getPartNumber().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getPartNumber());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }

        if (!productWcsModel.getParentPartNumber().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getParentPartNumber());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        } else {
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getParentColourPartNumber().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getParentColourPartNumber());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        } else {
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getType().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getType());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getSequence().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getSequence());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getManufacturerPartNumber().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getManufacturerPartNumber());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }

        if (!productWcsModel.getManufacturer().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".manufacturer"));
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getBuyable().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getBuyable());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getStartDate().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getStartDate());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        } else {
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getEndDate().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getEndDate());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        } else {
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getField1().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getField1());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getField2().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getField2());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        } else {
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getField3().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getField3());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        } else {
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getField4().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getField4());
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(PIPE_DELIMITER);
        } else {
            productRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productWcsModel.getDelete().equalsIgnoreCase("")) {
            productRowBuilder.append(DOUBLE_QUOTES);
            productRowBuilder.append(productWcsModel.getDelete());
            productRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }

    private static void buildProductDescriptionRow(StringBuilder productDescriptionRowBuilder, ProductDescriptionWcsModel productDescriptionWcsModel, String brand) {

        if (!productDescriptionWcsModel.getLanguageId().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getLanguageId());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getPartNumber().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getPartNumber());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }

        if (!productDescriptionWcsModel.getName().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getName());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        } else {
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getExtendedName().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getExtendedName());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        } else {
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getEditorialDescription().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getEditorialDescription());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getDetailsAndCare().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getDetailsAndCare());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getShortDescription().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getShortDescription());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        } else {
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }

        if (!productDescriptionWcsModel.getSizeAndFit().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getSizeAndFit());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getLongDescription().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getLongDescription());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getAuxDescription1().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getAuxDescription1());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        } else {
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getAuxDescription2().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getAuxDescription2());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        } else {
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getPublished().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getPublished());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getDelete().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(productDescriptionWcsModel.getDelete());
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productDescriptionWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            productDescriptionRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier"));
            productDescriptionRowBuilder.append(DOUBLE_QUOTES);
            //productDescriptionRowBuilder.append(PIPE_DELIMITER);
        } else {
            // nothing to do
        }
    }

}
