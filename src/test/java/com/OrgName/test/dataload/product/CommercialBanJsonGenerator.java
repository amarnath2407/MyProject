package com.salmon.test.dataload.product;


import com.google.gson.Gson;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.product.CommercialBanningPimModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.framework.helpers.DataLoaderHelper.*;
import static com.salmon.test.framework.helpers.DataloadHook.getTimeStamp;


public class CommercialBanJsonGenerator {

    public static final String COMMERCIAL_BAN_INTERFACE_NUMBER = "423.01";
    private static final String COMMERCIAL_BANNING_FILENAME = "CommercialBanningPIM.json";
    private static List<String> brands;

    public void createCommercialBanningJson(CommercialBanningPimModel cbp) throws Exception {
        Gson gson = new Gson();
//        String json = gson.toJson(cbp);
//        System.out.println(json.replace("gTIN", "GTIN"));
        FileWriter fileWriter = null;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String banningFolderName = commercialBanningFolderName(brand);
            createCommercialBanningFolder(brand);

            String FILE_NAME = getInterfaceFilename(brand, banningFolderName, COMMERCIAL_BANNING_FILENAME, COMMERCIAL_BAN_INTERFACE_NUMBER);
            try {
                fileWriter = new FileWriter(FILE_NAME, false);
                gson.toJson(cbp, fileWriter);

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
