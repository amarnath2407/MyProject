package com.salmon.test.dataload.price;

import com.salmon.test.dataload.category.CategoryCsvFileGenerator;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.framework.helpers.utils.SSHClient;
import com.salmon.test.models.dataload.wcs.category.CategoryDescriptionWcsModel;
import com.salmon.test.models.dataload.wcs.category.CategoryRelationshipWcsModel;
import com.salmon.test.models.dataload.wcs.category.CategoryWcsModel;
import com.salmon.test.models.dataload.wcs.price.ProductPriceWcsModel;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.folderName;
import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.getInterfaceFilename;
import static com.salmon.test.dataload.category.CategoryWcsFileChecker.copyWCSFilesFromArchiveToLocal;
import static com.salmon.test.dataload.category.CategoryWcsFileChecker.getFilePath;
import static com.salmon.test.dataload.category.CategoryWcsFileChecker.readWcsFile;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductPriceWcsFileChecker {

    private static final String WCS_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;
    private static final String WCS_PRICE_HEADER = "PartNumber|Price|CurrencyCode|PriceListName|Precedence|StartDate|EndDate|Delete|Identifier|MarkdownCode|MarkdownPercentage|CatalogIdentifier";

    private static final String PIPE_DELIMITER = "|";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String PRICE_WCS_INTERFACE_NUMBER = "002.1";
    private static final String PRICE_FILENAME = "ProductPrice.csv";


    private static StringBuilder priceRowBuilder;
    private static String profileId;
    private static String folderName;
    private static List<String> brands;
    private static String fullFilePath;
    private static SSHClient sshClient = null;
    private static String wcsFileName;
    private static String fileName;


    public static void checkTalendProductpriceFileFormat(List<ProductPriceWcsModel> productPriceWcsModels) throws Throwable {
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand,PRICE_FILENAME);
            assertThat(reader.readLine().contains(WCS_PRICE_HEADER)).isTrue();
            for (ProductPriceWcsModel productPriceWcsModel : productPriceWcsModels) {
                priceRowBuilder = new StringBuilder();
                buildProductPriceRow(priceRowBuilder, productPriceWcsModel, brand);
                assertThat(reader.readLine().contains(priceRowBuilder)).isTrue();
            }
            reader.close();
        }

    }

    private static void buildProductPriceRow(StringBuilder productPriceRowBuilder, ProductPriceWcsModel productPriceWcsModel, String brand) {

        if (!productPriceWcsModel.getPartNumber().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getPartNumber());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            // Nothing to do
        }
        if (!productPriceWcsModel.getPrice().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getPrice());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            if (!productPriceWcsModel.getPrice().equalsIgnoreCase("")) {

                productPriceRowBuilder.append(PIPE_DELIMITER);
            }
        }


        if (!productPriceWcsModel.getCurrencyCode().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getCurrencyCode());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getPriceListName().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getPriceListName());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getPrecedence().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getPrecedence());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getStartDate().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getStartDate());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getEndDate().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getEndDate());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getDelete().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getDelete());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getIdentifier().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getIdentifier());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getMarkdownCode().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getMarkdownCode());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getMarkdownPercentage().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(productPriceWcsModel.getMarkdownPercentage());
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(PIPE_DELIMITER);
        } else {
            productPriceRowBuilder.append(PIPE_DELIMITER);
        }
        if (!productPriceWcsModel.getCatalogIdentifier().equalsIgnoreCase("")) {
            productPriceRowBuilder.append(DOUBLE_QUOTES);
            productPriceRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier"));
            productPriceRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }


}


