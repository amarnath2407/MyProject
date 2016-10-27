package com.salmon.test.dataload.attribute;

import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.wcs.attribute.AttributeValuesWcsModel;
import com.salmon.test.models.dataload.wcs.attribute.AttributeWcsModel;
import com.salmon.test.models.dataload.wcs.attribute.ProductAttributeValuesWcsModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryWcsFileChecker.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by veeranank on 08/09/2016.
 */
public class AttributeWcsFileChecker {
    private static final String WCS_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;
    private static final String WCS_ATTRIBUTE_HEADER = "ParentStoreIdentifier|AttributeIdentifier|Field1|Field2|Field3|LanguageID|Name|Description|Description2|AttributeValueField1|NoteInfo|AttrUsage|Delete|AttributeType";
    private static final String WCS_ATTRIBUTE_VALUES_HEADER = "ParentStoreIdentifier|AttributeValueIdentifier|AttributeIdentifier|Field1|Field2|Field3|LanguageID|Value|Sequence|Image1|Image2|AttributeValueField1|AttributeValueField2|AttributeValueField3|ValUsage|Delete";
    private static final String WCS_PRODUCT_ATTRIBUTE_VALUES_HEADER = "PartNumber|ParentStoreIdentifier|AttributeValueIdentifier|AttributeIdentifier|Usage|Sequence|Delete";
    private static final String PIPE_DELIMITER = "|";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String ATTRIBUTE_WCS_INTERFACE_NUMBER = "001.15";
    private static final String ATTRIBUTE_VALUES_WCS_INTERFACE_NUMBER = "001.16";
    private static final String PRODUCT_ATTRIBUTE_VALUES_WCS_INTERFACE_NUMBER = "001.13";
    private static final String ATTRIBUTE_FILENAME = "Attribute.csv";
    private static final String ATTRIBUTE_VALUES_FILENAME = "-AttributeValues.csv";
    private static final String PRODUCT_ATTRIBUTE_VALUES_FILENAME = "ProductAttributeValues.csv";
    private static StringBuilder attributeRowBuilder;
    private static StringBuilder attributeValuesRowBuilder;
    private static StringBuilder productAttributeValuesRowBuilder;
    private static List<String> brands;
    private static String profileId;

    public static void checkTalendAttributeFileFormat(List<AttributeWcsModel> attributeWcsModelList) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, ATTRIBUTE_FILENAME);
            assertThat(reader.readLine().contains(WCS_ATTRIBUTE_HEADER)).isTrue();
            for (AttributeWcsModel attributeWcsModel : attributeWcsModelList) {
                attributeRowBuilder = new StringBuilder();
                buildAttributeRow(attributeRowBuilder, attributeWcsModel, brand);
                assertThat(reader.readLine().contains(attributeRowBuilder)).isTrue();
            }
            reader.close();
        }
    }

    public static void checkTalendAttributeValuesFileFormat(List<AttributeValuesWcsModel> attributeValuesWcsModelList) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, ATTRIBUTE_VALUES_FILENAME);
            assertThat(reader.readLine().contains(WCS_ATTRIBUTE_VALUES_HEADER)).isTrue();
            for (AttributeValuesWcsModel attributeValuesWcsModel : attributeValuesWcsModelList) {
                attributeValuesRowBuilder = new StringBuilder();
                buildAttributeValuesRow(attributeValuesRowBuilder, attributeValuesWcsModel, brand);
                assertThat(reader.readLine().contains(attributeValuesRowBuilder)).isTrue();
            }
            reader.close();
        }
    }

    public static void checkTalendProductAttributeValuesFileFormat(List<ProductAttributeValuesWcsModel> productAttributeValuesWcsModelList) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, PRODUCT_ATTRIBUTE_VALUES_FILENAME);
            assertThat(reader.readLine().contains(WCS_PRODUCT_ATTRIBUTE_VALUES_HEADER)).isTrue();
            for (ProductAttributeValuesWcsModel productAttributeValuesWcsModel : productAttributeValuesWcsModelList) {
                productAttributeValuesRowBuilder = new StringBuilder();
                buildProductAttributeValuesRow(productAttributeValuesRowBuilder, productAttributeValuesWcsModel, brand);
                assertThat(reader.readLine().contains(productAttributeValuesRowBuilder)).isTrue();
            }
            reader.close();
        }
    }

    private static void buildAttributeRow(StringBuilder attributeRowBuilder, AttributeWcsModel attributeWcsModel, String brand) {

        if (!attributeWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier"));
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getAttributeIdentifier().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getAttributeIdentifier());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getField1().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getField1());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getField2().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getField2());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getField3().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getField3());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getLanguageId().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getLanguageId());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getName().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getName());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getDescription().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getDescription());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getDescription2().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getDescription2());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getAttributeValueField1().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getAttributeValueField1());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getNoteInfo().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getNoteInfo());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getAttrUsage().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getAttrUsage());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getDelete().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getDelete());
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeWcsModel.getDelete().equalsIgnoreCase("")) {
            attributeRowBuilder.append(DOUBLE_QUOTES);
            attributeRowBuilder.append(attributeWcsModel.getAttributeType());
            attributeRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }

    private static void buildAttributeValuesRow(StringBuilder attributeValuesRowBuilder, AttributeValuesWcsModel attributeValuesWcsModel, String brand) {

        if (!attributeValuesWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier"));
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getAttributeValueIdentifier().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getAttributeValueIdentifier());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getAttributeIdentifier().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getAttributeIdentifier());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getField1().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getField1());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getField2().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getField2());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getField3().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getField3());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getLanguageId().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getLanguageId());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getValue().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getValue());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getSequence().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getSequence());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getImage1().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getImage1());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getImage2().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getImage2());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getAttributeValueField1().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getAttributeValueField1());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getAttributeValueField2().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getAttributeValueField2());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getAttributeValueField3().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getAttributeValueField3());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        } else {
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getValUsage().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getValUsage());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!attributeValuesWcsModel.getDelete().equalsIgnoreCase("")) {
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
            attributeValuesRowBuilder.append(attributeValuesWcsModel.getDelete());
            attributeValuesRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }

    private static void buildProductAttributeValuesRow(StringBuilder productAttributeValuesRowBuilder, ProductAttributeValuesWcsModel productAttributeValuesWcsModel, String brand) {

        if (!productAttributeValuesWcsModel.getPartNumber().equalsIgnoreCase("")) {
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(productAttributeValuesWcsModel.getPartNumber());
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productAttributeValuesWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier"));
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productAttributeValuesWcsModel.getAttributeValueIdentifier().equalsIgnoreCase("")) {
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(productAttributeValuesWcsModel.getAttributeValueIdentifier());
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productAttributeValuesWcsModel.getAttributeIdentifier().equalsIgnoreCase("")) {
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(productAttributeValuesWcsModel.getAttributeIdentifier());
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productAttributeValuesWcsModel.getUsage().equalsIgnoreCase("")) {
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(productAttributeValuesWcsModel.getUsage());
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productAttributeValuesWcsModel.getSequence().equalsIgnoreCase("")) {
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(productAttributeValuesWcsModel.getSequence());
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productAttributeValuesWcsModel.getDelete().equalsIgnoreCase("")) {
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
            productAttributeValuesRowBuilder.append(productAttributeValuesWcsModel.getDelete());
            productAttributeValuesRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }
}
