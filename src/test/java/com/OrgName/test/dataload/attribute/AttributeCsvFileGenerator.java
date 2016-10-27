package com.salmon.test.dataload.attribute;


import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.attribute.AttributePimModel;
import com.salmon.test.models.dataload.pim.attribute.AttributeValuesPimModel;
import com.salmon.test.models.dataload.pim.attribute.ProductAttributeValuesPimModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.*;



public class AttributeCsvFileGenerator {

    public static final String ATTRIBUTE_INTERFACE_NUMBER = "050.15";
    public static final String ATTRIBUTE_VALUES_INTERFACE_NUMBER = "050.16";
    public static final String PRODUCT_ATTRIBUTE_VALUES_INTERFACE_NUMBER = "050.13";
    private static final String CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "PIM" + File.separator;
    private static final String ATTRIBUTE_HEADER = "\"CatalogIdentifier\"|\"AttributeIdentifier\"|\"Language\"|\"Name\"|\"AttrUsage\"|\"Delete\"";
    private static final String ATTRIBUTE_HEADER_WITH_COMMA_DELIMITER = "\"CatalogIdentifier\",\"AttributeIdentifier\",\"Language\",\"Name\",\"AttrUsage\",\"Delete\"";
    private static final String ATTRIBUTE_INCORRECT_HEADER_ORDER = "\"AttrUsage\"|\"Language\"|\"Delete\"|\"CatalogIdentifier\"|\"AttributeIdentifier\"|\"Name\"";
    private static final String ATTRIBUTE_VALUES_HEADER = "\"CatalogIdentifier\"|\"AttributeValueIdentifier\"|\"AttributeIdentifier\"|\"Language\"|\"Value\"|\"Sequence\"|\"Delete\"";
    private static final String ATTRIBUTE_VALUES_HEADER_WITH_COMMA_DELIMITER = "\"CatalogIdentifier\",\"AttributeValueIdentifier\",\"AttributeIdentifier\",\"Language\",\"Value\",\"Sequence\",\"Delete\"";
    private static final String ATTRIBUTE_VALUES_INCORRECT_HEADER_ORDER = "\"Language\"|\"AttributeIdentifier\"|\"Sequence\"|\"Delete\"|\"CatalogIdentifier\"|\"AttributeValueIdentifier\"|\"Value\"";
    private static final String PRODUCT_ATTRIBUTE_VALUES_HEADER = "\"GTIN\"|\"CatalogIdentifier\"|\"AttributeValueIdentifier\"|\"AttributeIdentifier\"|\"Usage\"|\"ProductType\"|\"Sequence\"|\"Delete\"|\"MODELID\"|\"VARIANTID\"";
    private static final String PRODUCT_ATTRIBUTE_VALUES_HEADER_WITH_COMMA_DELIMITER = "\"GTIN\",\"CatalogIdentifier\",\"AttributeValueIdentifier\",\"AttributeIdentifier\",\"Usage\",\"ProductType\",\"Sequence\",\"Delete\",\"MODELID\",\"VARIANTID\"";
    private static final String PRODUCT_ATTRIBUTE_VALUES_INCORRECT_HEADER_ORDER = "\"Sequence\"|\"Usage\"|\"AttributeValueIdentifier\"|\"GTIN\"|\"MODELID\"|\"VARIANTID\"|\"AttributeIdentifier\"|\"CatalogIdentifier\"|\"ProductType\"|\"Delete\"";
    private static final String PIPE_DELIMITER = "|";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String CATALOG_IDENTIFIER = "\"CatalogIdentifier\"";
    private static final String ATTRIBUTE_IDENTIFIER = "\"AttributeIdentifier\"";
    private static final String ATTRIBUTEVALUE_IDENTIFIER = "\"AttributeValueIdentifier\"";
    private static final String LANGUAGE = "\"Language\"";
    private static final String VALUE = "\"Value\"";
    private static final String SEQUENCE = "\"Sequence\"";
    private static final String NAME = "\"Name\"";
    private static final String ATTR_USAGE = "\"AttrUsage\"";
    private static final String DELETE = "\"Delete\"";
    private static final String EXTRA_FIELD = "\"ExtraField\"";
    private static final String USAGE = "\"Usage\"";
    private static final String PRODUCT_TYPE = "\"ProductType\"";
    private static final String MODEL_ID = "\"MODELID\"";
    private static final String VARIANT_ID = "\"VARIANTID\"";
    private static final String GTIN = "\"GTIN\"";
    private static final String ATTRIBUTE_FILENAME = "AttributePIM.csv";
    private static final String ATTRIBUTE_VALUES_FILENAME = "AttributeValuesPIM.csv";
    private static final String PRODUCT_ATTRIBUTE_VALUES_FILENAME = "ProductAttributeValuePIM.csv";
    private static List<String> brands;
    private static String DOT = ".";
    private static int i = 0;

    public static void generateAttributeCsvFile(List<AttributePimModel> attributePimModelList) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, ATTRIBUTE_FILENAME);
            try {
                fileWriter = writeAttributeDataIntoFile(brand, attributePimModelList, fileWriter, FILE_NAME);

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

    public static void generateAttributeValuesCsvFile(List<AttributeValuesPimModel> attributeValuesPimModels) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, ATTRIBUTE_VALUES_FILENAME);
            try {
                fileWriter = writeAttributeValuesDataIntoFile(brand, attributeValuesPimModels, fileWriter, FILE_NAME);

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

    public static void generateProductAttributeValuesCsvFile(List<ProductAttributeValuesPimModel> productAttributeValuesPimModels) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, PRODUCT_ATTRIBUTE_VALUES_FILENAME);
            try {
                fileWriter = writeProductAttributeValuesDataIntoFile(brand, productAttributeValuesPimModels, fileWriter, FILE_NAME);

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

    //Write Attribute data into csv file
    private static FileWriter writeAttributeDataIntoFile(String brand, List<AttributePimModel> attributePimModels, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateAttributeCsvFileHeader(attributePimModels.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (AttributePimModel attributePimModel : attributePimModels) {
            if (attributePimModel.getCatalogIdentifier() != null && !("".equalsIgnoreCase(attributePimModel.getCatalogIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + attributePimModel.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributePimModel.getCatalogIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributePimModel.getAttributeIdentifier() != null && !("".equalsIgnoreCase(attributePimModel.getAttributeIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributePimModel.getAttributeIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributePimModel.getAttributeIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributePimModel.getLanguage() != null && !("".equalsIgnoreCase(attributePimModel.getLanguage()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributePimModel.getLanguage());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributePimModel.getLanguage())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributePimModel.getName() != null && !("".equalsIgnoreCase(attributePimModel.getName()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributePimModel.getName());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributePimModel.getName())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributePimModel.getAttrUsage() != null && !("".equalsIgnoreCase(attributePimModel.getAttrUsage()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributePimModel.getAttrUsage());
                writer.append(DOUBLE_QUOTES);
                if (attributePimModel.getDelete() != null) {
                    writer.append(PIPE_DELIMITER);
                }
            } else if ("".equalsIgnoreCase(attributePimModel.getAttrUsage())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributePimModel.getDelete() != null && !("".equalsIgnoreCase(attributePimModel.getDelete()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributePimModel.getDelete());
                writer.append(DOUBLE_QUOTES);
                if (attributePimModel.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }

            } else if ("".equalsIgnoreCase(attributePimModel.getAttrUsage())) {
                writer.append(PIPE_DELIMITER);
            } else {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(attributePimModel.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(attributePimModel.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }

        }
        return writer;
    }


    //Write Attribute Value data into csv file
    private static FileWriter writeAttributeValuesDataIntoFile(String brand, List<AttributeValuesPimModel> attributeValuesPimModels, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateAttributeValuesCsvFileHeader(attributeValuesPimModels.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (AttributeValuesPimModel attributeValuesPimModel : attributeValuesPimModels) {
            if (attributeValuesPimModel.getCatalogIdentifier() != null && !("".equalsIgnoreCase(attributeValuesPimModel.getCatalogIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + attributeValuesPimModel.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributeValuesPimModel.getCatalogIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributeValuesPimModel.getAttributeValueIdentifier() != null && !("".equalsIgnoreCase(attributeValuesPimModel.getAttributeValueIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributeValuesPimModel.getAttributeValueIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributeValuesPimModel.getAttributeValueIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }


            if (attributeValuesPimModel.getAttributeIdentifier() != null && !("".equalsIgnoreCase(attributeValuesPimModel.getAttributeIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributeValuesPimModel.getAttributeIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributeValuesPimModel.getAttributeIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }


            if (attributeValuesPimModel.getLanguage() != null && !("".equalsIgnoreCase(attributeValuesPimModel.getLanguage()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributeValuesPimModel.getLanguage());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributeValuesPimModel.getLanguage())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributeValuesPimModel.getValue() != null && !("".equalsIgnoreCase(attributeValuesPimModel.getValue()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributeValuesPimModel.getValue());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(attributeValuesPimModel.getValue())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributeValuesPimModel.getSequence() != null && !("".equalsIgnoreCase(attributeValuesPimModel.getSequence()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributeValuesPimModel.getSequence());
                writer.append(DOUBLE_QUOTES);
                if (attributeValuesPimModel.getDelete() != null) {
                    writer.append(PIPE_DELIMITER);
                }

            } else if ("".equalsIgnoreCase(attributeValuesPimModel.getSequence())) {
                writer.append(PIPE_DELIMITER);
            }

            if (attributeValuesPimModel.getDelete() != null && !("".equalsIgnoreCase(attributeValuesPimModel.getDelete()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(attributeValuesPimModel.getDelete());
                writer.append(DOUBLE_QUOTES);
                if (attributeValuesPimModel.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }

            } else if ("".equalsIgnoreCase(attributeValuesPimModel.getSequence())) {
                writer.append(PIPE_DELIMITER);
            } else {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(attributeValuesPimModel.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(attributeValuesPimModel.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }

        }
        return writer;
    }

    //Write Produt Attribute values data into csv file
    private static FileWriter writeProductAttributeValuesDataIntoFile(String brand, List<ProductAttributeValuesPimModel> productAttributeValuesPimModels, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateProductAttributeValuesCsvFileHeader(productAttributeValuesPimModels.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductAttributeValuesPimModel productAttributeValuesPimModel : productAttributeValuesPimModels) {

            if (productAttributeValuesPimModel.getGtin() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getGtin()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getGtin());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getGtin())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productAttributeValuesPimModel.getCatalogIdentifier() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getCatalogIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + productAttributeValuesPimModel.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getCatalogIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }


            if (productAttributeValuesPimModel.getAttributeValueIdentifier() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getAttributeValueIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getAttributeValueIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getAttributeValueIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }


            if (productAttributeValuesPimModel.getAttributeIdentifier() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getAttributeIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getAttributeIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getAttributeIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productAttributeValuesPimModel.getUsage() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getUsage()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getUsage());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getUsage())) {
                writer.append(PIPE_DELIMITER);
            }


            if (productAttributeValuesPimModel.getProductType() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getProductType()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getProductType());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);

            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getProductType())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productAttributeValuesPimModel.getSequence() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getSequence()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getSequence());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getSequence())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productAttributeValuesPimModel.getDelete() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getDelete()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getDelete());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getDelete())) {
                writer.append(PIPE_DELIMITER);
            }


            if (productAttributeValuesPimModel.getModelId() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getModelId()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getModelId());
                writer.append(DOUBLE_QUOTES);

                if (productAttributeValuesPimModel.getVariantId() != null) {
                    writer.append(PIPE_DELIMITER);
                }

            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getModelId())) {
                writer.append(PIPE_DELIMITER);
            }

            if (productAttributeValuesPimModel.getVariantId() != null && !("".equalsIgnoreCase(productAttributeValuesPimModel.getVariantId()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getVariantId());
                writer.append(DOUBLE_QUOTES);
                if (productAttributeValuesPimModel.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }

            } else if ("".equalsIgnoreCase(productAttributeValuesPimModel.getVariantId())) {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(productAttributeValuesPimModel.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(productAttributeValuesPimModel.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }

        }
        return writer;
    }

    private static String getFilePath() throws IOException {
        return new File(".").getCanonicalPath() + CSV_FILE_PATH;
    }

    private static String generateAttributeCsvFileHeader(AttributePimModel attributePimModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((attributePimModel.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTR_USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributePimModel.getAttributeIdentifier() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTR_USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributePimModel.getLanguage() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTR_USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributePimModel.getName() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTR_USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributePimModel.getAttrUsage() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributePimModel.getDelete() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTR_USAGE);
        } else if (!(attributePimModel.getExtraField() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTR_USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTR_USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        }
        return headerStrBuffer.toString();
    }

    private static String generateAttributeValuesCsvFileHeader(AttributeValuesPimModel attributeValuesPimModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((attributeValuesPimModel.getCatalogIdentifier()) == null) {
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributeValuesPimModel.getAttributeValueIdentifier() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributeValuesPimModel.getAttributeIdentifier() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributeValuesPimModel.getLanguage() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributeValuesPimModel.getValue() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((attributeValuesPimModel.getSequence() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if (attributeValuesPimModel.getDelete() == null) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
        } else if (!(attributeValuesPimModel.getExtraField() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VALUE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        }
        return headerStrBuffer.toString();
    }

    private static String generateProductAttributeValuesCsvFileHeader(ProductAttributeValuesPimModel productAttributeValuesPimModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((productAttributeValuesPimModel.getGtin()) == null) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);

        } else if ((productAttributeValuesPimModel.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getAttributeValueIdentifier() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getAttributeIdentifier() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getUsage() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getProductType() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getSequence() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getDelete() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getModelId() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        } else if ((productAttributeValuesPimModel.getVariantId() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
        } else if (!(productAttributeValuesPimModel.getExtraField() == null)) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(GTIN);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTEVALUE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(ATTRIBUTE_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(USAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PRODUCT_TYPE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(MODEL_ID);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(VARIANT_ID);
        }
        return headerStrBuffer.toString();
    }

    public static void generateAttributeCsvFileWithDelimiter(List<AttributePimModel> attributePimModels, String delimiter) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, ATTRIBUTE_FILENAME);
            try {
                if (delimiter == ",") {
                    fileWriter = writeAttributeDataIntoFileWithDelimiter(brand, attributePimModels, fileWriter, FILE_NAME, delimiter);
                } else if (delimiter == "|") {
                    fileWriter = writeAttributeDataIntoFileWithIncorrectHeaderOrder(brand, attributePimModels, fileWriter, FILE_NAME, delimiter);
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

    public static void generateAttributeValuesCsvFileWithDelimiter(List<AttributeValuesPimModel> attributeValuesPimModels, String delimiter) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, ATTRIBUTE_VALUES_FILENAME);
            try {
                if (delimiter == ",") {
                    fileWriter = writeAttributeValuesDataIntoFileWithDelimiter(brand, attributeValuesPimModels, fileWriter, FILE_NAME, delimiter);
                } else if (delimiter == "|") {
                    fileWriter = writeAttributeValuesDataIntoFileWithIncorrectHeaderOrder(brand, attributeValuesPimModels, fileWriter, FILE_NAME, delimiter);
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

    public static void generateProductAttributeValuesCsvFileWithDelimiter(List<ProductAttributeValuesPimModel> productAttributeValuesPimModels, String delimiter) throws IOException {
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, PRODUCT_ATTRIBUTE_VALUES_FILENAME);
            try {
                if (delimiter == ",") {
                    fileWriter = writeProductAttributeValuesDataIntoFileWithDelimiter(brand, productAttributeValuesPimModels, fileWriter, FILE_NAME, delimiter);
                } else if (delimiter == "|") {
                    fileWriter = writeProductAttributeValuesDataIntoFileWithIncorrectHeaderOrder(brand, productAttributeValuesPimModels, fileWriter, FILE_NAME, delimiter);
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

    private static void enterFieldValue(String brand, FileWriter writer, String fieldVaue, String delimiter) throws IOException {
        writer.append(DOUBLE_QUOTES);
        writer.append(fieldVaue);
        writer.append(DOUBLE_QUOTES);
        writer.append(delimiter);
    }

    //Write Attribute data into csv file with comma delimiter
    private static FileWriter writeAttributeDataIntoFileWithDelimiter(String brand, List<AttributePimModel> attributePimModels, FileWriter writer, String FILE_NAME, String commaDelimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(ATTRIBUTE_HEADER_WITH_COMMA_DELIMITER);
        writer.append(NEW_LINE_SEPARATOR);
        for (AttributePimModel attribute : attributePimModels) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + attribute.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attribute.getAttributeIdentifier()), commaDelimiter);
            if (attribute.getLanguage() != null) {
                if (attribute.getLanguage().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }
            enterFieldValue(brand, writer, String.valueOf(attribute.getName()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attribute.getAttrUsage()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attribute.getDelete()), NEW_LINE_SEPARATOR);
        }
        return writer;
    }

    //Write Attribute Values data into csv file with comma delimiter
    private static FileWriter writeAttributeValuesDataIntoFileWithDelimiter(String brand, List<AttributeValuesPimModel> attributeValuesPimModels, FileWriter writer, String FILE_NAME, String commaDelimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(ATTRIBUTE_VALUES_HEADER_WITH_COMMA_DELIMITER);
        writer.append(NEW_LINE_SEPARATOR);
        for (AttributeValuesPimModel attributeValues : attributeValuesPimModels) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + attributeValues.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getAttributeValueIdentifier()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getAttributeIdentifier()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getLanguage()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getValue()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getSequence()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getDelete()), NEW_LINE_SEPARATOR);
        }
        return writer;
    }

    //Write Product Attribute Values data into csv file with comma delimiter
    private static FileWriter writeProductAttributeValuesDataIntoFileWithDelimiter(String brand, List<ProductAttributeValuesPimModel> productAttributeValuesPimModels, FileWriter writer, String FILE_NAME, String commaDelimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(PRODUCT_ATTRIBUTE_VALUES_HEADER_WITH_COMMA_DELIMITER);
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductAttributeValuesPimModel productAttributeValues : productAttributeValuesPimModels) {
            if (productAttributeValues.getGtin() != null) {
                if (productAttributeValues.getGtin().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + productAttributeValues.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getAttributeValueIdentifier()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getAttributeIdentifier()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getUsage()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getProductType()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getSequence()), commaDelimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getDelete()), commaDelimiter);
            if (productAttributeValues.getModelId() != null) {
                if (productAttributeValues.getModelId().equalsIgnoreCase("")) {
                    writer.append(commaDelimiter);
                }
            }
            if (productAttributeValues.getVariantId() != null) {
                if (productAttributeValues.getVariantId().equalsIgnoreCase("")) {
                    writer.append(NEW_LINE_SEPARATOR);
                }
            }
        }
        return writer;
    }

    //Write Attribute data into csv file with Incorrect header order
    private static FileWriter writeAttributeDataIntoFileWithIncorrectHeaderOrder(String brand, List<AttributePimModel> attributePimModels, FileWriter writer, String FILE_NAME, String delimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(ATTRIBUTE_INCORRECT_HEADER_ORDER);
        writer.append(NEW_LINE_SEPARATOR);
        for (AttributePimModel attribute : attributePimModels) {
            enterFieldValue(brand, writer, String.valueOf(attribute.getAttrUsage()), delimiter);
            if (attribute.getLanguage() != null) {
                if (attribute.getLanguage().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            enterFieldValue(brand, writer, String.valueOf(attribute.getDelete()), delimiter);
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + attribute.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, delimiter);
            enterFieldValue(brand, writer, String.valueOf(attribute.getName()), NEW_LINE_SEPARATOR);
        }
        return writer;
    }

    //Write Attribute Values data into csv file with Incorrect header order
    private static FileWriter writeAttributeValuesDataIntoFileWithIncorrectHeaderOrder(String brand, List<AttributeValuesPimModel> attributeValuesPimModels, FileWriter writer, String FILE_NAME, String delimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(ATTRIBUTE_VALUES_INCORRECT_HEADER_ORDER);
        writer.append(NEW_LINE_SEPARATOR);
        for (AttributeValuesPimModel attributeValues : attributeValuesPimModels) {
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getLanguage()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getAttributeIdentifier()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getSequence()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getDelete()), delimiter);
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + attributeValues.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, delimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getAttributeValueIdentifier()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(attributeValues.getValue()), NEW_LINE_SEPARATOR);
        }
        return writer;
    }

    //Write Product Attribute Values data into csv file with Incorrect header order
    private static FileWriter writeProductAttributeValuesDataIntoFileWithIncorrectHeaderOrder(String brand, List<ProductAttributeValuesPimModel> productAttributeValuesPimModels, FileWriter writer, String FILE_NAME, String delimiter) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(PRODUCT_ATTRIBUTE_VALUES_INCORRECT_HEADER_ORDER);
        writer.append(NEW_LINE_SEPARATOR);
        for (ProductAttributeValuesPimModel productAttributeValues : productAttributeValuesPimModels) {
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getSequence()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getUsage()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getAttributeValueIdentifier()), delimiter);
            if (productAttributeValues.getGtin() != null) {
                if (productAttributeValues.getGtin().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            if (productAttributeValues.getModelId() != null) {
                if (productAttributeValues.getModelId().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            if (productAttributeValues.getVariantId() != null) {
                if (productAttributeValues.getVariantId().equalsIgnoreCase("")) {
                    writer.append(delimiter);
                }
            }
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getAttributeIdentifier()), delimiter);
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + DOT + productAttributeValues.getCatalogIdentifier());
            enterFieldValue(brand, writer, catalogIdentifier, delimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getProductType()), delimiter);
            enterFieldValue(brand, writer, String.valueOf(productAttributeValues.getDelete()), NEW_LINE_SEPARATOR);
        }
        return writer;
    }


}
