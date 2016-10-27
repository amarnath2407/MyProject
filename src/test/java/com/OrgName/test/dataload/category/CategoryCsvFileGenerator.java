package com.salmon.test.dataload.category;


import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.dataload.pim.category.CategoryDescriptionPimModel;
import com.salmon.test.models.dataload.pim.category.CategoryPimModel;
import com.salmon.test.models.dataload.pim.category.CategoryRelationshipPimModel;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.attribute.AttributeCsvFileGenerator.ATTRIBUTE_INTERFACE_NUMBER;
import static com.salmon.test.dataload.attribute.AttributeCsvFileGenerator.ATTRIBUTE_VALUES_INTERFACE_NUMBER;
import static com.salmon.test.dataload.attribute.AttributeCsvFileGenerator.PRODUCT_ATTRIBUTE_VALUES_INTERFACE_NUMBER;
//import static com.salmon.test.dataload.price.ProductPriceCsvFileGenerator.PRICE_INTERFACE_NUMBER;
import static com.salmon.test.dataload.product.ProductCsvFileGenerator.PRODUCT_CATEGORY_INTERFACE_NUMBER;
import static com.salmon.test.dataload.product.ProductCsvFileGenerator.PRODUCT_DESCRIPTION_INTERFACE_NUMBER;
import static com.salmon.test.dataload.product.ProductCsvFileGenerator.PRODUCT_INTERFACE_NUMBER;
import static com.salmon.test.framework.helpers.DataloadHook.getTimeStamp;


public class CategoryCsvFileGenerator {

    static final Logger LOG = LoggerFactory.getLogger(CategoryCsvFileGenerator.class);

    private static final String CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "PIM" + File.separator;
    private static final String STAGE_TWO_CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "StageTwo" + File.separator;
    private static final String WCS_CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;
    private static final String PIPE_DELIMITER = "|";
    private static final String COMMA = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String CATEGORY_INTERFACE_NUMBER = "050.02";
    private static final String CATEGORY_DESC_INTERFACE_NUMBER = "050.03";
    private static final String CATEGORY_REL_INTERFACE_NUMBER = "050.04";
    private static final String CATALOG_IDENTIFIER = "\"CatalogIdentifier\"";
    private static final String CATEGORY_IDENTIFIER = "\"CategoryIdentifier\"";
    private static final String TOP_GROUP = "\"TopGroup\"";
    private static final String SEQUENCE = "\"Sequence\"";
    private static final String DELETE = "\"Delete\"";
    private static final String EXTRA_FIELD = "\"ExtraField\"";
    private static final String GROUP_IDENTIFIER = "\"GroupIdentifier\"";
    private static final String LANGUAGE = "\"Language\"";
    private static final String NAME = "\"Name\"";
    private static final String PARENT_GROUP_IDENTIFIER = "\"ParentGroupIdentifier\"";
    private static String timeStamp;
    private static final String CATEGORY_FILENAME = "CategoryPIM.csv";
    private static final String CATEGORY_DESC_FILENAME = "CategoryDescriptionPIM.csv";
    private static final String CATEGORY_REL_FILENAME = "CategoryRelationshipPIM.csv";
    private static List<String> brands;
    private static String DOT = ".";
    private static String profileId;
    private static int i = 0;

    private static String generateCategoryRelationshipCsvFileHeader(CategoryRelationshipPimModel categoryRelationshipPimModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((categoryRelationshipPimModel.getGroupIdentifier() == null)) {
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryRelationshipPimModel.getParentGroupIdentifier() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryRelationshipPimModel.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryRelationshipPimModel.getSequence() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryRelationshipPimModel.getDelete() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
        } else if (!(categoryRelationshipPimModel.getExtraField() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(PARENT_GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        }
        return headerStrBuffer.toString();
    }

    private static String generateCategoryDescriptionCsvFileHeader(CategoryDescriptionPimModel categoryDescriptionModel) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((categoryDescriptionModel.getGroupIdentifier() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryDescriptionModel.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryDescriptionModel.getLanguage() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryDescriptionModel.getName() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categoryDescriptionModel.getDelete() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
        } else if (!(categoryDescriptionModel.getExtraField() == null)) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(GROUP_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(LANGUAGE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(NAME);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        }
        return headerStrBuffer.toString();
    }

    private static String generateCategoryCsvFileHeader(CategoryPimModel categories) {
        StringBuffer headerStrBuffer = new StringBuffer();
        if ((categories.getCatalogIdentifier() == null)) {
            headerStrBuffer.append(CATEGORY_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TOP_GROUP);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categories.getCategoryIdentifier() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TOP_GROUP);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categories.getTopGroup() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATEGORY_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categories.getSequence() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATEGORY_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TOP_GROUP);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        } else if ((categories.getDelete() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATEGORY_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TOP_GROUP);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
        } else if (!(categories.getExtraField() == null)) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATEGORY_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TOP_GROUP);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(EXTRA_FIELD);
        } else if (headerStrBuffer.toString().equalsIgnoreCase("")) {
            headerStrBuffer.append(CATALOG_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(CATEGORY_IDENTIFIER);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(TOP_GROUP);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(SEQUENCE);
            headerStrBuffer.append(PIPE_DELIMITER);
            headerStrBuffer.append(DELETE);
        }
        return headerStrBuffer.toString();
    }

    public static void generateCategoryCsvFile(List<CategoryPimModel> categoriesList) throws IOException {
        FileWriter fileWriter = null;

        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));

//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;

        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, CATEGORY_FILENAME);
            try {
                fileWriter = writeCategoryDataIntoFile(brand, categoriesList, fileWriter, FILE_NAME);

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

    public static void generateCategoryCsvFileWithDiffFieldSequence(List<CategoryPimModel> categoriesList) throws IOException {
        FileWriter fileWriter = null;
        //checkFileExists(getFilePath()); // name change to deleteFileIfExists()
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        //setTimeStamp(getCurrentDateAndTime());

        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, CATEGORY_FILENAME);
            try {
                fileWriter = writeCategoryDataIntoFileWithDiffFieldSequence(brand, categoriesList, fileWriter, FILE_NAME);

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

    public static void generateCategoryCsvFileWithoutHeader(List<CategoryPimModel> categoriesList) throws IOException {
        FileWriter fileWriter = null;
        //checkFileExists(getFilePath()); // name change to deleteFileIfExists()
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        //setTimeStamp(getCurrentDateAndTime());

        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, CATEGORY_FILENAME);
            try {
                fileWriter = writeCategoryDataIntoFileWithoutHeader(brand, categoriesList, fileWriter, FILE_NAME);

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

    public static void generateCategoryCsvFileWithComma(List<CategoryPimModel> categoriesList) throws IOException {
        FileWriter fileWriter = null;
        //checkFileExists(getFilePath()); // name change to deleteFileIfExists()
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        //setTimeStamp(getCurrentDateAndTime());

        for (String brand : brands) {
            String folderName = folderName(brand);
            createBrandFolder(brand);
            String FILE_NAME = getInterfaceFilename(brand, folderName, CATEGORY_FILENAME);
            try {
                fileWriter = writeCategoryDataIntoFileWithComma(brand, categoriesList, fileWriter, FILE_NAME);

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

    public static List<String> getBrands() {
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        return brands;
    }

    public static String getInterfaceFilename(String brand, String folderName, String csvFileName) throws IOException {
        String fileName = "";
        switch (csvFileName) {
            case "CategoryPIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + CATEGORY_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "CategoryDescriptionPIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + CATEGORY_DESC_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "CategoryRelationshipPIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + CATEGORY_REL_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "ProductPIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + PRODUCT_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "ProductDescriptionPIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + PRODUCT_DESCRIPTION_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "ProductCategoryPIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + PRODUCT_CATEGORY_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "AttributePIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + ATTRIBUTE_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "AttributeValuesPIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + ATTRIBUTE_VALUES_INTERFACE_NUMBER + "-" + csvFileName;
                break;
            case "ProductAttributeValuePIM.csv":
                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + PRODUCT_ATTRIBUTE_VALUES_INTERFACE_NUMBER + "-" + csvFileName;
                break;
//            case "ProductPricePIM.csv":
//                fileName = getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + PRICE_INTERFACE_NUMBER + "-" + csvFileName;
//                break;
        }

        return fileName;
    }

    public static String folderName(String brand) {
        return brand + "_" + getTimeStamp() + "_050-Products";
    }

    public static String getStageTwoFolderName(String brand) throws IOException {
        File folder = new File(new File(".").getCanonicalPath() +STAGE_TWO_CSV_FILE_PATH + brand);

        File[] listOfFiles = folder.listFiles();
        String folderName = "";
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isDirectory()) {
                if (listOfFiles[i].getName().contains(brand)) {
                    folderName = listOfFiles[i].getName();
                    break;
                }
            }
        }
        return folderName;
    }

    public static void createBrandFolder(String brand) throws IOException {
        String folderName = getFilePath() + brand + "_" + getTimeStamp() + "_050-Products";
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        //new File(getFilePath() + brand + "_" + getTimeStamp() + "_050-Products").mkdir();
    }


    public static void generateCategoryDescriptionCsvFile(List<CategoryDescriptionPimModel> categoryDescriptionModelList) throws IOException {
        FileWriter fileWriter = null;

        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        for (String brand : brands) {

            String FILE_NAME = getFilePath() + File.separator + folderName(brand) + File.separator + "" + brand + "_" + getTimeStamp() + "_" + CATEGORY_DESC_INTERFACE_NUMBER + "-" + CATEGORY_DESC_FILENAME;

            try {
                //checkFileExists(FILE_NAME);
                fileWriter = writeCategoryDescriptionDataIntoFile(brand, categoryDescriptionModelList, fileWriter, FILE_NAME);

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


    public static void generateCategoryRelationshipCsvFile(List<CategoryRelationshipPimModel> relationshipPimModels) throws IOException {
        FileWriter fileWriter = null;

        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        if (i > 0) {
//            setTimeStamp(getCurrentDateAndTime());
//        }
//        i++;
        for (String brand : brands) {

            String FILE_NAME = getFilePath() + File.separator + folderName(brand) + File.separator + "" + brand + "_" + getTimeStamp() + "_" + CATEGORY_REL_INTERFACE_NUMBER + "-" + CATEGORY_REL_FILENAME;
            try {
                fileWriter = writeCategoryRelationshipDataIntoFile(brand, relationshipPimModels, fileWriter, FILE_NAME);

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


    //Write category Relationship data into csv file
    private static FileWriter writeCategoryRelationshipDataIntoFile(String brand, List<CategoryRelationshipPimModel> relationshipPimModels, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateCategoryRelationshipCsvFileHeader(relationshipPimModels.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (CategoryRelationshipPimModel categoryRelationship : relationshipPimModels) {
            if (categoryRelationship.getGroupIdentifier() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryRelationship.getGroupIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (categoryRelationship.getParentGroupIdentifier() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryRelationship.getParentGroupIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (categoryRelationship.getCatalogIdentifier() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + categoryRelationship.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (categoryRelationship.getSequence() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryRelationship.getSequence());
                writer.append(DOUBLE_QUOTES);
                if (categoryRelationship.getDelete() != null) {
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (categoryRelationship.getDelete() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryRelationship.getDelete());
                writer.append(DOUBLE_QUOTES);
                if (categoryRelationship.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }
            } else {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(categoryRelationship.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryRelationship.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }
        }
        return writer;
    }


    //Write category description data into csv file
    private static FileWriter writeCategoryDescriptionDataIntoFile(String brand, List<CategoryDescriptionPimModel> categoryDescriptionModelList, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateCategoryDescriptionCsvFileHeader(categoryDescriptionModelList.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (CategoryDescriptionPimModel categoryDescription : categoryDescriptionModelList) {
            if (categoryDescription.getGroupIdentifier() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(String.valueOf(categoryDescription.getGroupIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (categoryDescription.getCatalogIdentifier() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + categoryDescription.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (categoryDescription.getLanguage() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryDescription.getLanguage());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            }
            if (categoryDescription.getName() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryDescription.getName());
                writer.append(DOUBLE_QUOTES);
                if (categoryDescription.getDelete() != null) {
                    writer.append(PIPE_DELIMITER);
                }
            }
            if (categoryDescription.getDelete() != null) {
                writer.append(DOUBLE_QUOTES);
                writer.append(String.valueOf(categoryDescription.getDelete()));
                writer.append(DOUBLE_QUOTES);
                if (categoryDescription.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }
            } else {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(categoryDescription.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(categoryDescription.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }

        }
        return writer;
    }

    public static String getFilePath() throws IOException {
        return new File(".").getCanonicalPath() + CSV_FILE_PATH;
    }

    public static String getFilePath(String brand) throws IOException {
        return new File(".").getCanonicalPath() + CSV_FILE_PATH + File.separator + folderName(brand);
    }

    public static String getStageTwoFilePath(String brand) throws IOException {
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        String path;
        if (profileId.equalsIgnoreCase("dev")) {
            path = new File(".").getCanonicalPath() + STAGE_TWO_CSV_FILE_PATH + File.separator + brand;
        } else {
            path = new File(".").getCanonicalPath() + STAGE_TWO_CSV_FILE_PATH + File.separator + brand + File.separator + getStageTwoFolderName(brand);
        }
        return path;
    }


    public static String getWcsFilePath(String brand) throws IOException {
        String folderName = new File(".").getCanonicalPath() + WCS_CSV_FILE_PATH + folderName(brand);
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
        return new File(".").getCanonicalPath() + WCS_CSV_FILE_PATH + folderName(brand) + File.separator;
    }

    private static FileWriter writeCategoryDataIntoFileWithoutHeader(String brand, List<CategoryPimModel> categoriesList, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        for (CategoryPimModel categories : categoriesList) {
            writer.append(DOUBLE_QUOTES);
            writer.append(UrlBuilder.readValueFromConfig(brand + DOT + categories.getCatalogIdentifier()));
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getCategoryIdentifier());
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getTopGroup());
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getSequence());
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getDelete());
            writer.append(DOUBLE_QUOTES);
            writer.append(NEW_LINE_SEPARATOR);
        }
        return writer;

    }

    private static FileWriter writeCategoryDataIntoFileWithComma(String brand, List<CategoryPimModel> categoriesList, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateCategoryCsvFileHeader(categoriesList.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (CategoryPimModel categories : categoriesList) {

            writer.append(DOUBLE_QUOTES);
            writer.append(UrlBuilder.readValueFromConfig(brand + DOT + categories.getCatalogIdentifier()));
            writer.append(DOUBLE_QUOTES);
            writer.append(COMMA);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getCategoryIdentifier());
            writer.append(DOUBLE_QUOTES);
            writer.append(COMMA);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getTopGroup());
            writer.append(DOUBLE_QUOTES);
            writer.append(COMMA);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getSequence());
            writer.append(DOUBLE_QUOTES);
            writer.append(COMMA);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getDelete());
            writer.append(DOUBLE_QUOTES);
            writer.append(NEW_LINE_SEPARATOR);
        }
        return writer;

    }

    private static FileWriter writeCategoryDataIntoFileWithDiffFieldSequence(String brand, List<CategoryPimModel> categoriesList, FileWriter writer, String FILE_NAME) throws IOException {
        writer = new FileWriter(FILE_NAME, false);
        String header = CATALOG_IDENTIFIER + PIPE_DELIMITER + TOP_GROUP + PIPE_DELIMITER + SEQUENCE + PIPE_DELIMITER + DELETE + PIPE_DELIMITER + CATEGORY_IDENTIFIER;
        writer.append(header);
        writer.append(NEW_LINE_SEPARATOR);
        for (CategoryPimModel categories : categoriesList) {
            writer.append(DOUBLE_QUOTES);
            writer.append(UrlBuilder.readValueFromConfig(brand + DOT + categories.getCatalogIdentifier()));
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getTopGroup());
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getSequence());
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getDelete());
            writer.append(DOUBLE_QUOTES);
            writer.append(PIPE_DELIMITER);
            writer.append(DOUBLE_QUOTES);
            writer.append(categories.getCategoryIdentifier());
            writer.append(DOUBLE_QUOTES);
            writer.append(NEW_LINE_SEPARATOR);
        }
        return writer;

    }

    //Write category data into csv file
    private static FileWriter writeCategoryDataIntoFile(String brand, List<CategoryPimModel> categoriesList, FileWriter writer, String FILE_NAME) throws IOException {

        writer = new FileWriter(FILE_NAME, false);
        writer.append(generateCategoryCsvFileHeader(categoriesList.get(0)));
        writer.append(NEW_LINE_SEPARATOR);
        for (CategoryPimModel categories : categoriesList) {
            if (categories.getCatalogIdentifier() != null && !("".equalsIgnoreCase(categories.getCatalogIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(UrlBuilder.readValueFromConfig(brand + DOT + categories.getCatalogIdentifier()));
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(categories.getCatalogIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }
            if (categories.getCategoryIdentifier() != null && !("".equalsIgnoreCase(categories.getCategoryIdentifier()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categories.getCategoryIdentifier());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(categories.getCategoryIdentifier())) {
                writer.append(PIPE_DELIMITER);
            }
            if (categories.getTopGroup() != null && !("".equalsIgnoreCase(categories.getTopGroup()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categories.getTopGroup());
                writer.append(DOUBLE_QUOTES);
                writer.append(PIPE_DELIMITER);
            } else if ("".equalsIgnoreCase(categories.getTopGroup())) {
                writer.append(PIPE_DELIMITER);
            }
            if (categories.getSequence() != null && !("".equalsIgnoreCase(categories.getSequence()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categories.getSequence());
                writer.append(DOUBLE_QUOTES);
                if (categories.getDelete() != null) {
                    writer.append(PIPE_DELIMITER);
                }
            } else if ("".equalsIgnoreCase(categories.getSequence())) {
                writer.append(PIPE_DELIMITER);
            }
            if (categories.getDelete() != null && !("".equalsIgnoreCase(categories.getDelete()))) {
                writer.append(DOUBLE_QUOTES);
                writer.append(categories.getDelete());
                writer.append(DOUBLE_QUOTES);
                if (categories.getExtraField() == null) {
                    writer.append(NEW_LINE_SEPARATOR);
                }

            } else if ("".equalsIgnoreCase(categories.getSequence())) {
                writer.append(PIPE_DELIMITER);
            } else {
                writer.append(NEW_LINE_SEPARATOR);
            }

            if (!(categories.getExtraField() == null)) {
                writer.append(PIPE_DELIMITER);
                writer.append(DOUBLE_QUOTES);
                writer.append(categories.getExtraField());
                writer.append(DOUBLE_QUOTES);
                writer.append(NEW_LINE_SEPARATOR);
            }

        }
        return writer;
    }


    //Delete existing file
//    public static void checkFileExists(String FILE_NAME) {
//        File categoryFile = new File(FILE_NAME);
//        if (categoryFile.exists()) {
//            categoryFile.delete();
//        }
//    }

//    public static String getTimeStamp() {
//        return timeStamp;
//    }
//
//    public static void setTimeStamp(String dateTime) {
//        timeStamp = dateTime;
//    }
//
//    public static String getCurrentDateAndTime() {
//        return new DateTime().toString("yyyy-MM-dd-hhmmss");
//    }

    //Delete existing file
    public static void checkFileExists(String filePath) throws IOException {
        File file = new File(filePath);
        String[] dirList = file.list();
        for (String dir : dirList) {
            File folder = new File(filePath + "" + dir);
            File[] filesInFolder = folder.listFiles();
            for (File f : filesInFolder) {
                if (f.isFile() && f.exists()) {
                    f.delete();
                    System.out.println("successfully deleted");
                    LOG.info("successfully deleted the file");
                } else {
                    System.out.println("cant delete a file due to open or error");
                    LOG.info("cant delete a file due to open or error");
                }
            }
            folder.delete();
            LOG.info("Folder deleted");
        }

//        File[] files = file.listFiles();
//        for (File f : files) {
//            if (f.isFile() && f.exists()) {
//                f.delete();
//                System.out.println("successfully deleted");
//            } else {
//                System.out.println("cant delete a file due to open or error");
//            }
//        }

    }
}
