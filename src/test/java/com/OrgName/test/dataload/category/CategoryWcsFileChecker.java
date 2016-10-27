package com.salmon.test.dataload.category;

import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.framework.helpers.utils.SSHClient;
import com.salmon.test.models.dataload.wcs.category.CategoryDescriptionWcsModel;
import com.salmon.test.models.dataload.wcs.category.CategoryRelationshipWcsModel;
import com.salmon.test.models.dataload.wcs.category.CategoryWcsModel;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.folderName;
//import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.getInterfaceFilename;
import static com.salmon.test.framework.helpers.DataLoaderHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryWcsFileChecker {

    private static final String WCS_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;
    private static final String WCS_CATEGORY_HEADER = "GroupIdentifier|ParentStoreIdentifier|CatalogIdentifier|TopGroup|Sequence|Field1|Field2|Delete";
    private static final String WCS_CATEGORY_DESC_HEADER = "GroupIdentifier|CatalogIdentifier|LanguageID|Name|ShortDescription|LongDescription|Published|Delete";
    private static final String WCS_CATEGORY_RELATIONSHIP_HEADER = "GroupIdentifier|ParentGroupIdentifier|ParentStoreIdentifier|CatalogIdentifier|Sequence|Delete";
    private static final String PIPE_DELIMITER = "|";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final String DOUBLE_QUOTES = "\"";
    private static final String CATEGORY_WCS_INTERFACE_NUMBER = "001.02";
    private static final String CATEGORY_DESC_WCS_INTERFACE_NUMBER = "001.03";
    private static final String CATEGORY_REL_WCS_INTERFACE_NUMBER = "001.04";
    private static final String CATEGORY_FILENAME = "Category.csv";
    private static final String CATEGORY_DESC_FILENAME = "CategoryDescription.csv";
    private static final String CATEGORY_REL_FILENAME = "CategoryRelationship.csv";
    private static String WCS_FILE_NAME;

    private static String failureFIleName;
    private static final String CATEGORY_INTERFACE_NUMBER = "050.02";
    private static StringBuilder categoryRowBuilder, categoryDescRowBuilder, categoryRelationshipRowBuilder;
    private static String profileId;
    private static String folderName;
    private static List<String> brands;
    private static String fullFilePath;
    private static String failureFolderPath;
    private static SSHClient sshClient = null;
    private static String wcsFileName;
    private static String fileName;

    public static void copyWCSFilesFromArchiveToLocal() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        for (String brand : brands) {
            if (profileId.equalsIgnoreCase("dev")) {

            } else {
                //Transfer the files from unix to windows
                sshClient = new SSHClient();
                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.readValueFromConfig("systest.sudo.user"), UrlBuilder.getSystestPassword().toString());
                System.out.print(fullFilePath);

                sshClient.copyFileToWindows(fullFilePath, CategoryCsvFileGenerator.getWcsFilePath(brand));

                // sshClient.connect("52.208.80.100", "mkadiyala", "pa55word");
                //sshClient.copyFileToUnix("/home/mkadiyala/DieselCAS/DieselCASCategory.csv",  "C:\\YNAP\\RegressionTest\\src\\test\\resources\\dataloadcsvfiles\\WCS\\");
                sshClient.disconnect();

            }
        }
    }


    public static void copyPIMFilesFromFailureToLocal() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        for (String brand : brands) {
            if (profileId.equalsIgnoreCase("dev")) {

            } else {
                //Transfer the files from unix to windows
                sshClient = new SSHClient();
                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.readValueFromConfig("systest.sudo.user"), UrlBuilder.getSystestPassword().toString());
                sshClient.copyFileToWindows(failureFolderPath, CategoryCsvFileGenerator.getWcsFilePath(brand));

                // sshClient.connect("52.208.80.100", "mkadiyala", "pa55word");
                //sshClient.copyFileToUnix("/home/mkadiyala/DieselCAS/DieselCASCategory.csv",  "C:\\YNAP\\RegressionTest\\src\\test\\resources\\dataloadcsvfiles\\WCS\\");
                sshClient.disconnect();

            }
        }
    }


    public static void checkTalendCategoryFileFormat(List<CategoryWcsModel> categoryWcsModels, String interfaceName) throws Throwable {
//        BufferedReader reader;
//        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        for (String brand : brands) {
//            reader = readWcsFile(brand, CATEGORY_FILENAME);
//            assertThat(reader.readLine().contains(WCS_CATEGORY_HEADER)).isTrue();
//            for (CategoryWcsModel categoryWcsModel : categoryWcsModels) {
//                categoryRowBuilder = new StringBuilder();
//                buildCategoryRow(categoryRowBuilder, categoryWcsModel, brand);
//                assertThat(reader.readLine().contains(categoryRowBuilder)).isTrue();
//
//            }
//            reader.close();
//        }
        BufferedReader reader;
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsMappingFile(brand, CATEGORY_FILENAME, interfaceName);
            assertThat(reader.readLine().contains(WCS_CATEGORY_HEADER)).isTrue();
            for (CategoryWcsModel categoryWcsModel : categoryWcsModels) {
                categoryRowBuilder = new StringBuilder();
                buildCategoryRow(categoryRowBuilder, categoryWcsModel, brand);
                assertThat(reader.readLine().contains(categoryRowBuilder)).isTrue();

            }
            reader.close();
        }
    }

    public static void checkWcsCategoryDescriptionFileFormat(List<CategoryDescriptionWcsModel> categoryDescriptionWcsModels) throws Throwable {
        BufferedReader reader;
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, CATEGORY_DESC_FILENAME);
            assertThat(reader.readLine().contains(WCS_CATEGORY_DESC_HEADER)).isTrue();
            for (CategoryDescriptionWcsModel categoryDescriptionWcsModel : categoryDescriptionWcsModels) {
                categoryDescRowBuilder = new StringBuilder();
                buildCategoryDescriptionRow(categoryDescRowBuilder, categoryDescriptionWcsModel, brand);
                assertThat(reader.readLine().contains(categoryDescRowBuilder)).isTrue();
            }
            reader.close();
        }
    }


    public static void checkWcsCategoryRelationshipFileFormat(List<CategoryRelationshipWcsModel> categoryRelationshipWcsModels) throws Throwable {
        BufferedReader reader;
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            reader = readWcsFile(brand, CATEGORY_REL_FILENAME);
            assertThat(reader.readLine().contains(WCS_CATEGORY_RELATIONSHIP_HEADER)).isTrue();
            for (CategoryRelationshipWcsModel relationshipWcsModel : categoryRelationshipWcsModels) {
                categoryRelationshipRowBuilder = new StringBuilder();
                buildCategoryRelationshipRow(categoryRelationshipRowBuilder, relationshipWcsModel, brand);
                assertThat(reader.readLine().contains(categoryRelationshipRowBuilder)).isTrue();
            }
            reader.close();
        }
    }

    private static void buildCategoryRelationshipRow(StringBuilder categoryRelationshipRowBuilder, CategoryRelationshipWcsModel relationshipWcsModel, String brand) {
        if (!relationshipWcsModel.getGroupIdentifier().equalsIgnoreCase("")) {
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(relationshipWcsModel.getGroupIdentifier());
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        } else {
            //nothing to do
        }
        if (!relationshipWcsModel.getParentGroupIdentifier().equalsIgnoreCase("")) {
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(relationshipWcsModel.getParentGroupIdentifier());
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        } else {
            if (!relationshipWcsModel.getGroupIdentifier().equalsIgnoreCase("")) {

                categoryRowBuilder.append(PIPE_DELIMITER);
            }
        }
        if (!relationshipWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier"));
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        }
        if (!relationshipWcsModel.getCatalogIdentifier().equalsIgnoreCase("")) {
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier"));
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        }
        if (!relationshipWcsModel.getSequence().equalsIgnoreCase("")) {
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(relationshipWcsModel.getSequence());
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRelationshipRowBuilder.append(PIPE_DELIMITER);
        }
        if (!relationshipWcsModel.getSequence().equalsIgnoreCase("")) {
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
            categoryRelationshipRowBuilder.append(relationshipWcsModel.getDelete());
            categoryRelationshipRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }

    }


    private static void buildCategoryDescriptionRow(StringBuilder categoryDescRowBuilder, CategoryDescriptionWcsModel categoryDescriptionWcsModel, String brand) {
        if (!categoryDescriptionWcsModel.getGroupIdentifier().equalsIgnoreCase("")) {
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(categoryDescriptionWcsModel.getGroupIdentifier());
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        } else {
            //nothing to do
        }
        if (!categoryDescriptionWcsModel.getCatalogIdentifier().equalsIgnoreCase("")) {
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier"));
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        } else {
            if (!categoryDescriptionWcsModel.getGroupIdentifier().equalsIgnoreCase("")) {

                categoryRowBuilder.append(PIPE_DELIMITER);
            }

        }
        if (!categoryDescriptionWcsModel.getLanguageId().equalsIgnoreCase("")) {
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(categoryDescriptionWcsModel.getLanguageId());
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryDescriptionWcsModel.getName().equalsIgnoreCase("")) {
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(categoryDescriptionWcsModel.getName());
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryDescriptionWcsModel.getShortDescription().equalsIgnoreCase("")) {
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(categoryDescriptionWcsModel.getShortDescription());
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryDescriptionWcsModel.getLongDescription().equalsIgnoreCase("")) {
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(categoryDescriptionWcsModel.getLongDescription());
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryDescriptionWcsModel.getPublished().equalsIgnoreCase("")) {
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(categoryDescriptionWcsModel.getPublished());
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryDescRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryDescriptionWcsModel.getPublished().equalsIgnoreCase("")) {

            categoryDescRowBuilder.append(DOUBLE_QUOTES);
            categoryDescRowBuilder.append(categoryDescriptionWcsModel.getDelete());
            categoryDescRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }


    public static BufferedReader readWcsFile(String brand, String csvFileName) throws Throwable {
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        String FILE_NAME;
        if (profileId.equalsIgnoreCase("dev")) {
            FILE_NAME = getFilePath(brand, csvFileName);
            return new BufferedReader(new FileReader(FILE_NAME));
        } else {
            FILE_NAME = getFilePath(brand, csvFileName);
            copyWCSFilesFromArchiveToLocal();
            File wcsFolder = new File(new File(".").getCanonicalPath() + WCS_FILE_PATH + folderName(brand));
            File[] listOfFiles = wcsFolder.listFiles();
            fileName = "";
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if (listOfFiles[i].getName().contains(csvFileName)) {
                        fileName = listOfFiles[i].getName();
                        break;
                    }
                }
            }
            WCS_FILE_NAME = getWcsLocalPath(brand) + File.separator + fileName;
            return new BufferedReader(new FileReader(WCS_FILE_NAME));
        }

    }


    private static String getWcsLocalPath(String brand) throws IOException {
        return new File(".").getCanonicalPath() + WCS_FILE_PATH + folderName(brand);
    }

    private static void buildCategoryRow(StringBuilder categoryRowBuilder, CategoryWcsModel categoryWcsModel, String brand) {

        if (!categoryWcsModel.getGroupIdentifier().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(categoryWcsModel.getGroupIdentifier());
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(PIPE_DELIMITER);
        } else {
            // Nothing to do
        }
        if (!categoryWcsModel.getParentStoreIdentifier().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".parentStoreIdentifier"));
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(PIPE_DELIMITER);
        } else {
            if (!categoryWcsModel.getGroupIdentifier().equalsIgnoreCase("")) {

                categoryRowBuilder.append(PIPE_DELIMITER);
            }
        }


        if (!categoryWcsModel.getCatalogIdentifier().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            //categoryRowBuilder.append(categoryWcsModel.getCatalogIdentifier());
            categoryRowBuilder.append(UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier"));
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryWcsModel.getTopGroup().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(categoryWcsModel.getTopGroup());
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryWcsModel.getSequence().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(categoryWcsModel.getSequence());
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryWcsModel.getField1().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(categoryWcsModel.getField1());
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryWcsModel.getField2().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(categoryWcsModel.getField2());
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(PIPE_DELIMITER);
        } else {
            categoryRowBuilder.append(PIPE_DELIMITER);
        }
        if (!categoryWcsModel.getDelete().equalsIgnoreCase("")) {
            categoryRowBuilder.append(DOUBLE_QUOTES);
            categoryRowBuilder.append(categoryWcsModel.getDelete());
            categoryRowBuilder.append(DOUBLE_QUOTES);
        } else {
            // nothing to do
        }
    }


    public static void checkInvalidFilesMovedToFailureFolder(String categoryType, String interfaceName) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        String csvFileName = categoryType + ".csv";
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        if (profileId.equalsIgnoreCase("dev")) {
            for (String brand : brands) {

                String actualFailureFolder = new File(getFileFromFailureFolder(brand, csvFileName, interfaceName)).getName();

                String priceFolderName = productsFolderName(brand);
                String expectedFailureFolder = new File(getInterfaceFilename(brand, priceFolderName, csvFileName, CATEGORY_INTERFACE_NUMBER)).getName();

                assertThat(actualFailureFolder).isEqualTo(expectedFailureFolder);

                //assertThat(getFileName(getFailureFilePath(brand, csvFileName))).isEqualTo(getFileName(getInterfaceFilename(brand, folderName, csvFileName)));
            }
        } else {

            for (String brand : brands) {

//                getFileFromFailureFolder(brand, csvFileName,interfaceName);
//                getFailureFilePath(brand, csvFileName);
                String priceFolderName = productsFolderName(brand);
                copyPimFilesFromUnixFailureFolderToLocal();

                File wcsFolderInLocal = new File(getWcsFolderLocalPath(brand, interfaceName));
                File[] listOfFiles = wcsFolderInLocal.listFiles();
                fileName = "";
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        if (listOfFiles[i].getName().contains(csvFileName)) {
                            fileName = listOfFiles[i].getName();
                            break;
                        }
                    }
                }
                String actFailureFileName = getWcsFolderLocalPath(brand, interfaceName) + File.separator + fileName;

                String expFailureFileName = getInterfaceFilename(brand, priceFolderName, csvFileName, CATEGORY_INTERFACE_NUMBER);
                assertThat(actFailureFileName).isEqualTo(expFailureFileName);
                //assertThat(getFileName(WCS_FILE_NAME)).isEqualTo(getFileName(getInterfaceFilename(brand, folderName, csvFileName)));
            }
        }
    }


    public static void checkInvalidFilesMovedToFailureFolder(String categoryType) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        String csvFileName = categoryType + ".csv";
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        if (profileId.equalsIgnoreCase("dev")) {
            for (String brand : brands) {
                assertThat(getFileName(getFailureFilePath(brand, csvFileName))).isEqualTo(getFileName(getInterfaceFilename(brand, folderName, csvFileName, CATEGORY_INTERFACE_NUMBER)));
            }
        } else {

            for (String brand : brands) {
                getFailureFilePath(brand, csvFileName);
                copyPIMFilesFromFailureToLocal();
                File wcsFolder = new File(new File(".").getCanonicalPath() + WCS_FILE_PATH + folderName(brand));
                File[] listOfFiles = wcsFolder.listFiles();
                fileName = "";
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        if (listOfFiles[i].getName().contains(csvFileName)) {
                            fileName = listOfFiles[i].getName();
                            break;
                        }
                    }
                }
                WCS_FILE_NAME = getWcsLocalPath(brand) + File.separator + fileName;
                assertThat(getFileName(WCS_FILE_NAME)).isEqualTo(getFileName(getInterfaceFilename(brand, folderName, csvFileName,CATEGORY_INTERFACE_NUMBER)));
            }
        }
    }


    public static String getFilePath() throws IOException {
        return new File(".").getCanonicalPath() + WCS_FILE_PATH;
    }

    public static String getFilePath(String brand, String csvFileName) throws IOException {
        File folder;
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        folderName = folderName(brand);
        if (profileId.equalsIgnoreCase("dev")) {
            folder = new File(UrlBuilder.readValueFromConfig("dataload.location") + brand + File.separator + "archive" + File.separator + folderName + File.separator + "tmp" + File.separator);

            File[] listOfFiles = folder.listFiles();
            fileName = "";
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if (listOfFiles[i].getName().contains(csvFileName)) {
                        fileName = listOfFiles[i].getName();
                        break;
                    }
                }
            }
            fullFilePath = folder.getPath() + File.separator + fileName;
        } else {
            fullFilePath = UrlBuilder.readValueFromConfig("dataload.sftp.location") + brand + "/archive/" + folderName + "/tmp/*.csv";
        }

        return fullFilePath;
    }

    private static String getFailureFilePath(String brand, String csvFileName) throws Exception {
        File folder;
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        try {
            folderName = folderName(brand);
            if (profileId.equalsIgnoreCase("dev")) {
                folder = new File(UrlBuilder.readValueFromConfig("dataload.location") + brand + File.separator + "failure" + File.separator + folderName(brand));
                String fileName = "";
                File[] listOfFiles = folder.listFiles();
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        if (listOfFiles[i].getName().contains(csvFileName)) {
                            fileName = listOfFiles[i].getName();
                            break;
                        }
                    }
                }
                failureFolderPath = folder.getPath() + File.separator + fileName;
            } else {
                // folder = new File(UrlBuilder.readValueFromConfig("dataload.location") + ("int" + brand.toLowerCase()) + "/datain/" + "failure/" + folderName);
                failureFolderPath = UrlBuilder.readValueFromConfig("dataload.sftp.location") + brand + "/failure/" + folderName(brand) + "/*.csv";
            }

            return failureFolderPath;
        } catch (NullPointerException e) {
            throw new Exception("File were not moved to Failure folder");
        }
    }

    private static String getFileName(String filePath) {
        File folder = new File(filePath);
        return folder.getName();
    }

    public static void checkWcsFileURFFormat() throws FileNotFoundException {
        File csvFile = new File(WCS_FILE_NAME);
        InputStreamReader streamReader = new InputStreamReader(new FileInputStream(csvFile));
        assertThat(streamReader.getEncoding()).isEqualTo("UTF8");
    }

    public static String getWcsFileName() {
        return fileName;
    }


}


