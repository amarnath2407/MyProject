package com.salmon.test.dataload.price;


import com.google.gson.Gson;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.price.ProductPricePimModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.framework.helpers.DataLoaderHelper.createPriceFolder;
import static com.salmon.test.framework.helpers.DataLoaderHelper.getInterfaceFilename;
import static com.salmon.test.framework.helpers.DataLoaderHelper.pricesFolderName;

public class ProductPriceJsonFileGenerator {
    static final Logger LOG = LoggerFactory.getLogger(ProductPriceJsonFileGenerator.class);

    public static final String PRICES_INTERFACE_NUMBER = "050_1";
    private static final String PRICES_FILENAME = "ProductPricePIM.json";
    private static List<String> brands;

    public void createPricesJson(ProductPricePimModel pricePimModel) throws Exception {
        Gson gson = new Gson();
        String pricesJson = gson.toJson(pricePimModel).replace("gtin", "GTIN");
        System.out.println(pricesJson);
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String priceFolderName = pricesFolderName(brand);
            createPriceFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, priceFolderName, PRICES_FILENAME, PRICES_INTERFACE_NUMBER);
            try {
                fileWriter = new FileWriter(FILE_NAME, false);
                gson.toJson(pricePimModel, fileWriter);

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
}
