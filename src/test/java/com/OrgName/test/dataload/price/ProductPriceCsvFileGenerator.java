package com.salmon.test.dataload.price;


import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.price.ProductPricePimModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.*;
import static com.salmon.test.framework.helpers.DataloadHook.getCurrentDateAndTime;
import static com.salmon.test.framework.helpers.DataloadHook.setTimeStamp;

public class ProductPriceCsvFileGenerator {

    static final Logger LOG = LoggerFactory.getLogger(ProductPriceCsvFileGenerator.class);
//
//    private static final String CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "PIM" + File.separator;
//    private static final String STAGE_TWO_CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "StageTwo" + File.separator;
//    private static final String WCS_CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;
//    private static String PIPE_DELIMITER = "|";
//    private static final String COMMA = ",";
//    private static final String NEW_LINE_SEPARATOR = "\n";
//    private static final String DOUBLE_QUOTES = "\"";
//    public static final String PRICE_INTERFACE_NUMBER = "051.1";
//
//    private static final String CATALOG_IDENTIFIER = "\"CatalogIdentifier\"";
//    private static final String CURRENCY_CODE = "\"CurrencyCode\"";
//    private static final String END_DATE = "\"EndDate\"";
//    private static final String GTIN = "\"GTIN\"";
//    private static final String MODELID = "\"MODELID\"";
//    private static final String EXTRA_FIELD = "\"ExtraField\"";
//    private static final String MARKDOWN_PERCENTAGE = "\"MarkdownPercentage\"";
//    private static final String MARKDOWN_CODE = "\"MarkdownCode\"";
//    private static final String PRICE = "\"Price\"";
//    private static final String PRICE_TYPE = "\"PriceType\"";
//    private static final String START_DATE = "\"StartDate\"";
//    private static String timeStamp;
//    private static final String PRICE_FILENAME = "ProductPricePIM.csv";
//    private static List<String> brands;
//    private static String DOT = ".";
//    private static String profileId;
//
//
//    private static String generateProductPriceCsvFileHeader(ProductPricePimModel productPricePimModel) {
//        StringBuffer headerStrBuffer = new StringBuffer();
//        if ((productPricePimModel.getCatalogIdentifier() == null)) {
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getGtin() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getPrice() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getCurrencyCode() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getPriceType() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getStartDate() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getEndDate() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getMarkdownCode() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getMarkdownPercentage() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        } else if ((productPricePimModel.getModelId() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//        } else if (!(productPricePimModel.getExtraField() == null)) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(EXTRA_FIELD);
//        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
//            headerStrBuffer.append(CATALOG_IDENTIFIER);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(GTIN);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(CURRENCY_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(PRICE_TYPE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(START_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(END_DATE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_CODE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MARKDOWN_PERCENTAGE);
//            headerStrBuffer.append(PIPE_DELIMITER);
//            headerStrBuffer.append(MODELID);
//        }
//        return headerStrBuffer.toString();
//    }
//
//    //Write Product price data into csv file
//    private static FileWriter writeProductPriceDataIntoFile(String brand, List<ProductPricePimModel> productPricePimModels, FileWriter writer, String FILE_NAME, String delimiter) throws IOException {
//        PIPE_DELIMITER = delimiter;
//        writer = new FileWriter(FILE_NAME, false);
//
//        String header = null;
//        if (0 == productPricePimModels.size()) {
//            header = CATALOG_IDENTIFIER + PIPE_DELIMITER + GTIN + PIPE_DELIMITER + CATALOG_IDENTIFIER + PIPE_DELIMITER + PRICE + PIPE_DELIMITER + CURRENCY_CODE + PIPE_DELIMITER + PRICE_TYPE + PIPE_DELIMITER +
//                    START_DATE + PIPE_DELIMITER + END_DATE + PIPE_DELIMITER + MARKDOWN_CODE + PIPE_DELIMITER + MARKDOWN_PERCENTAGE + PIPE_DELIMITER + MODELID;
//        } else {
//            header = generateProductPriceCsvFileHeader(productPricePimModels.get(0));
//        }
//        writer.append(header);
//        writer.append(NEW_LINE_SEPARATOR);
//        for (ProductPricePimModel productPrice : productPricePimModels) {
//            if (productPrice.getCatalogIdentifier() != null && !("".equalsIgnoreCase(productPrice.getCatalogIdentifier()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + productPrice.getCatalogIdentifier()));
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getCatalogIdentifier())) {
//                writer.append(PIPE_DELIMITER);
//            }
//            if (productPrice.getGtin() != null && !("".equalsIgnoreCase(productPrice.getGtin()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getGtin());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getGtin())) {
//                writer.append(PIPE_DELIMITER);
//            }
//            if (productPrice.getPrice() != null && !("".equalsIgnoreCase(productPrice.getPrice()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getPrice());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getPrice())) {
//                writer.append(PIPE_DELIMITER);
//            }
//
//            if (productPrice.getCurrencyCode() != null && !("".equalsIgnoreCase(productPrice.getCurrencyCode()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getCurrencyCode());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getCurrencyCode())) {
//                writer.append(PIPE_DELIMITER);
//            }
//
//            if (productPrice.getPriceType() != null && !("".equalsIgnoreCase(productPrice.getPriceType()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getPriceType());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getPriceType())) {
//                writer.append(PIPE_DELIMITER);
//            }
//
//            if (productPrice.getStartDate() != null && !("".equalsIgnoreCase(productPrice.getStartDate()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getStartDate());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getStartDate())) {
//                writer.append(PIPE_DELIMITER);
//            }
//
//            if (productPrice.getEndDate() != null && !("".equalsIgnoreCase(productPrice.getEndDate()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getEndDate());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getEndDate())) {
//                writer.append(PIPE_DELIMITER);
//            }
//
//            if (productPrice.getMarkdownCode() != null && !("".equalsIgnoreCase(productPrice.getMarkdownCode()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getMarkdownCode());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(PIPE_DELIMITER);
//            } else if ("".equalsIgnoreCase(productPrice.getMarkdownCode())) {
//                writer.append(PIPE_DELIMITER);
//            }
//
//            if (productPrice.getMarkdownPercentage() != null && !("".equalsIgnoreCase(productPrice.getMarkdownPercentage()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getMarkdownPercentage());
//                writer.append(DOUBLE_QUOTES);
//                if (productPrice.getModelId() != null) {
//                    writer.append(PIPE_DELIMITER);
//                }
//            } else if ("".equalsIgnoreCase(productPrice.getMarkdownPercentage())) {
//                writer.append(PIPE_DELIMITER);
//            }
//            if (productPrice.getModelId() != null && !("".equalsIgnoreCase(productPrice.getModelId()))) {
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getModelId());
//                writer.append(DOUBLE_QUOTES);
//                if (productPrice.getExtraField() == null) {
//                    writer.append(NEW_LINE_SEPARATOR);
//                }
//
//            } else if ("".equalsIgnoreCase(productPrice.getMarkdownPercentage())) {
//                writer.append(PIPE_DELIMITER);
//            } else {
//                writer.append(NEW_LINE_SEPARATOR);
//            }
//
//            if (!(productPrice.getExtraField() == null)) {
//                writer.append(PIPE_DELIMITER);
//                writer.append(DOUBLE_QUOTES);
//                writer.append(productPrice.getExtraField());
//                writer.append(DOUBLE_QUOTES);
//                writer.append(NEW_LINE_SEPARATOR);
//            }
//
//        }
//        return writer;
//    }
//
//
//    //Write Product price data into csv file
//
//    private static FileWriter writeProductPriceDataIntoFileWithoutHeader(String brand, List<ProductPricePimModel> productPricePimModels, FileWriter writer, String FILE_NAME, String delimiter) throws IOException {
//        PIPE_DELIMITER = delimiter;
//        writer = new FileWriter(FILE_NAME, false);
//        for (ProductPricePimModel productPrice : productPricePimModels) {
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(UrlBuilder.readValueFromConfig(brand + DOT + productPrice.getCatalogIdentifier()));
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getGtin());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getPrice());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getCurrencyCode());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getPriceType());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getStartDate());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getEndDate());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getMarkdownCode());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getMarkdownPercentage());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getModelId());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(NEW_LINE_SEPARATOR);
//        }
//        return writer;
//    }
//
//    public static void generateProductPriceCsvFile(List<ProductPricePimModel> productPricePimModels, String delimeter) throws IOException {
//        FileWriter fileWriter = null;
//        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        setTimeStamp(getCurrentDateAndTime());
//        for (String brand : brands) {
//            String folderName = folderName(brand);
//            createBrandFolder(brand);
//            String FILE_NAME = getInterfaceFilename(brand, folderName, PRICE_FILENAME);
//            try {
//                fileWriter = writeProductPriceDataIntoFile(brand, productPricePimModels, fileWriter, FILE_NAME, delimeter);
//
//            } catch (Exception e) {
//                System.out.println(e.getStackTrace());
//            } finally {
//                try {
//                    fileWriter.flush();
//                    fileWriter.close();
//                } catch (IOException e) {
//                    System.out.println(e.getStackTrace());
//                }
//            }
//        }
//    }
//
//    public static void generateProductPriceCsvFileWithDiffFieldSequence(List<ProductPricePimModel> productPricePimModels) throws IOException {
//        FileWriter fileWriter = null;
//        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//
//        for (String brand : brands) {
//            String folderName = folderName(brand);
//            createBrandFolder(brand);
//            String FILE_NAME = getInterfaceFilename(brand, folderName, PRICE_FILENAME);
//            try {
//                fileWriter = writeProductPriceDataIntoFileWithDiffFieldSequence(brand, productPricePimModels, fileWriter, FILE_NAME);
//
//            } catch (Exception e) {
//                System.out.println(e.getStackTrace());
//            } finally {
//                try {
//                    fileWriter.flush();
//                    fileWriter.close();
//                } catch (IOException e) {
//                    System.out.println(e.getStackTrace());
//                }
//            }
//        }
//
//    }
//
//    public static void generateProductPriceCsvFileWithoutHeader(List<ProductPricePimModel> productPricePimModels, String delimiter) throws IOException {
//        FileWriter fileWriter = null;
//        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//
//        for (String brand : brands) {
//            String folderName = folderName(brand);
//            createBrandFolder(brand);
//            String FILE_NAME = getInterfaceFilename(brand, folderName, PRICE_FILENAME);
//            try {
//                fileWriter = writeProductPriceDataIntoFileWithoutHeader(brand, productPricePimModels, fileWriter, FILE_NAME, delimiter);
//
//            } catch (Exception e) {
//                System.out.println(e.getStackTrace());
//            } finally {
//                try {
//                    fileWriter.flush();
//                    fileWriter.close();
//                } catch (IOException e) {
//                    System.out.println(e.getStackTrace());
//                }
//            }
//        }
//
//    }
//
//
//    public static List<String> getBrands() {
//        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        return brands;
//    }
//
//
//    private static FileWriter writeProductPriceDataIntoFileWithDiffFieldSequence(String brand, List<ProductPricePimModel> productPricePimModels, FileWriter writer, String FILE_NAME) throws IOException {
//        writer = new FileWriter(FILE_NAME, false);
//        String header = GTIN + PIPE_DELIMITER + CATALOG_IDENTIFIER + PIPE_DELIMITER + PRICE + PIPE_DELIMITER + CURRENCY_CODE + PIPE_DELIMITER + PRICE_TYPE + PIPE_DELIMITER +
//                START_DATE + PIPE_DELIMITER + END_DATE + PIPE_DELIMITER + MARKDOWN_CODE + PIPE_DELIMITER + MARKDOWN_PERCENTAGE + PIPE_DELIMITER + MODELID;
//        writer.append(header);
//        writer.append(NEW_LINE_SEPARATOR);
//        for (ProductPricePimModel productPrice : productPricePimModels) {
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getGtin());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(UrlBuilder.readValueFromConfig(brand + DOT + productPrice.getCatalogIdentifier()));
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getPrice());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getCurrencyCode());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getPriceType());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getStartDate());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getEndDate());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getMarkdownCode());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getMarkdownPercentage());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(PIPE_DELIMITER);
//
//            writer.append(DOUBLE_QUOTES);
//            writer.append(productPrice.getModelId());
//            writer.append(DOUBLE_QUOTES);
//            writer.append(NEW_LINE_SEPARATOR);
//        }
//        return writer;
//
//    }
//

}
