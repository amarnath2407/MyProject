package com.salmon.test.framework.helpers;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.salmon.test.dataload.category.CategoryCsvFileGenerator;
import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.utils.SSHClient;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.salmon.test.framework.helpers.DataloadHook.getTimeStamp;

public class DataLoaderHelper {

    private static final String CSV_JSON_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "PIM" + File.separator;
    private static final String WCS_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;
    private static SSHClient sshClient = null;
    private static String profileId, currentDBDateTime;
    private static String folderName;
    private static String fileName, filePathInArchiveFolder;
    private static String WCS_FILE_NAME;
    private static String filePathInFailureFolder;

    public DataLoaderHelper(SSHClient sshClient) {
        this.sshClient = sshClient;
    }

    public static String getInterfaceFilename(String brand, String folderName, String csvFileName, String interfaceNumber) throws IOException {
        return getFilePath() + File.separator + folderName + File.separator + "" + brand + "_" + getTimeStamp() + "_" + interfaceNumber + "-" + csvFileName;
    }

    public static String getFilePath() throws IOException {
        return new File(".").getCanonicalPath() + CSV_JSON_FILE_PATH;
    }

    public static String productsFolderName(String brand) {
        return brand + "_" + getTimeStamp() + "_050-Products";
    }

    public static String pricesFolderName(String brand) {
        return brand + "_" + getTimeStamp() + "_051-PricePIM";
    }

    public static String commercialBanningFolderName(String brand) {
        return brand + "_" + getTimeStamp() + "_423_Commercial_BanningPIM";
    }

    public static void createPriceFolder(String perBrand) throws IOException {
        String folderName = getFilePath() + pricesFolderName(perBrand);
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static void createCommercialBanningFolder(String perBrand) throws IOException {
        String folderName = getFilePath() + commercialBanningFolderName(perBrand);
        File folder = new File(folderName);
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static String getFolderPath(String brand, String interfaceName) throws IOException {
        return new File(".").getCanonicalPath() + CSV_JSON_FILE_PATH + File.separator + interfacefolderName(brand, interfaceName);
    }

    public static String interfacefolderName(String brand, String interfaceName) {
        String interfaceFolderName = "";
        if (interfaceName.contains("products")) {
            interfaceFolderName = productsFolderName(brand);
        } else if (interfaceName.contains("prices")) {
            interfaceFolderName = pricesFolderName(brand);
        } else if (interfaceName.contains("banning")) {
            interfaceFolderName = commercialBanningFolderName(brand);
        }
        return interfaceFolderName;
    }

//
//    public static void processPimFilesIntoTalend(String stage) throws SQLException, IOException, InterruptedException, JSchException, SftpException {
//        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
//        String dataloadInLocation;
//        profileId = UrlBuilder.readValueFromConfig("profile.id");
//        File srcDir;
//        List<HashMap> currentDBSysTime;
//        for (String brand : brands) {
//            currentDBSysTime = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_SYSDATE.getQuery());
//            currentDBDateTime = currentDBSysTime.get(0).get("SYSDATE").toString();
//            if (profileId.equalsIgnoreCase("dev")) {
//
//                if ("stage2".equalsIgnoreCase(stage.trim())) {
//                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + brand + File.separator + "in";
//                    srcDir = new File(CategoryCsvFileGenerator.getStageTwoFilePath(brand));
//                } else {
//                    srcDir = new File(getFolderPath(brand, stage));
//                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + brand + File.separator + "in" + File.separator + interfolderName(brand, stage);
//                }
//                File destDir = new File(dataloadInLocation);
//                FileUtils.copyDirectory(srcDir, destDir);
//                Process p = Runtime.getRuntime().exec("cmd /c start /wait " + UrlBuilder.readValueFromConfig("interface.location") + brand);
//                System.out.println("\nWaiting for batch file ...");
//               /* do {
//                } while (p.isAlive());*/
//                p.waitFor();
//                System.out.println("Batch file done.");
//            } else {
//                if ("stage2".equalsIgnoreCase(stage.trim())) {
//                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + ("int" + brand.toLowerCase()) + "/datain/" + getStageTwoFolderName(brand);
//                    srcDir = new File(getStageTwoFilePath(brand));
//                } else {
//                    srcDir = new File(getFilePath(brand));
//                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + ("int" + brand.toLowerCase()) + "/datain/" + folderName(brand);
//                }
//
//                //Transfer the files with intdiesel user
//                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.getSystestUser().toString(), UrlBuilder.getSystestPassword().toString());
//                sshClient.executeCommand("mkdir  " + dataloadInLocation);
//                sshClient.copyFileToUnix(srcDir + "/*.csv", dataloadInLocation);
//                sshClient.disconnect();
//
//                // Executing the interface script with autotest1 user
//                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.readValueFromConfig("systest.sudo.user"), UrlBuilder.getSystestPassword().toString());
//                sshClient.executeCommand(UrlBuilder.readValueFromConfig("interface.location") + brand);
//                sshClient.disconnect();
//            }
//
//        }
//        new DataloadHook().deleteProcessedPimFiles();
//    }

    public static BufferedReader readWcsMappingFile(String brand, String csvFileName, String interfaceName) throws Throwable {
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        String FILE_NAME;
        if (profileId.equalsIgnoreCase("dev")) {
            FILE_NAME = getFileFromArchiveFolder(brand, csvFileName, interfaceName);
            return new BufferedReader(new FileReader(FILE_NAME));
        } else {
            FILE_NAME = getFileFromArchiveFolder(brand, csvFileName, interfaceName);
            copyWCSFilesFromArchiveToLocal();
            File wcsFolder = new File(new File(".").getCanonicalPath() + WCS_FILE_PATH + interfacefolderName(brand, interfaceName));
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
            WCS_FILE_NAME = getWcsFolderLocalPath(brand, interfaceName) + File.separator + fileName;
            return new BufferedReader(new FileReader(WCS_FILE_NAME));
        }
    }

    private static String getFileFromArchiveFolder(String brand, String csvFileName, String interfaceName) throws IOException {
        File folder;
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        folderName = interfacefolderName(brand, interfaceName);
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
            filePathInArchiveFolder = folder.getPath() + File.separator + fileName;
        } else {
            filePathInArchiveFolder = UrlBuilder.readValueFromConfig("dataload.sftp.location") + brand + "/archive/" + folderName + "/tmp/*.csv";
        }

        return filePathInArchiveFolder;
    }

    private static void copyWCSFilesFromArchiveToLocal() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        for (String brand : brands) {
            if (!profileId.equalsIgnoreCase("dev")) {
                //Transfer the files from unix to windows
                sshClient = new SSHClient();
                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.readValueFromConfig("systest.sudo.user"), UrlBuilder.getSystestPassword().toString());
                System.out.print(filePathInArchiveFolder);

                sshClient.copyFileToWindows(filePathInArchiveFolder, CategoryCsvFileGenerator.getWcsFilePath(brand));
                sshClient.disconnect();
            }
        }
    }



    public static void copyPimFilesFromUnixFailureFolderToLocal() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        for (String brand : brands) {
            if (!profileId.equalsIgnoreCase("dev")) {
                //Transfer the files from unix to windows
                sshClient = new SSHClient();
                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.readValueFromConfig("systest.sudo.user"), UrlBuilder.getSystestPassword().toString());
                sshClient.copyFileToWindows(filePathInFailureFolder, CategoryCsvFileGenerator.getWcsFilePath(brand));

                // sshClient.connect("52.208.80.100", "mkadiyala", "pa55word");
                //sshClient.copyFileToUnix("/home/mkadiyala/DieselCAS/DieselCASCategory.csv",  "C:\\YNAP\\RegressionTest\\src\\test\\resources\\dataloadcsvfiles\\WCS\\");
                sshClient.disconnect();

            }
        }
    }


    public static String getWcsFolderLocalPath(String brand, String interfaceName) throws IOException {
        return new File(".").getCanonicalPath() + WCS_FILE_PATH + interfacefolderName(brand, interfaceName);
    }

    public static String getFileFromFailureFolder(String brand, String csvFileName, String interfaceName) throws Exception {
        File folder;
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        try {
            folderName = interfacefolderName(brand, interfaceName);
            if (profileId.equalsIgnoreCase("dev")) {
                folder = new File(UrlBuilder.readValueFromConfig("dataload.location") + brand + File.separator + "failure" + File.separator + folderName);
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
                filePathInFailureFolder = folder.getPath() + File.separator + fileName;
            } else {
                // folder = new File(UrlBuilder.readValueFromConfig("dataload.location") + ("int" + brand.toLowerCase()) + "/datain/" + "failure/" + folderName);
                filePathInFailureFolder = UrlBuilder.readValueFromConfig("dataload.sftp.location") + brand + "/failure/" + interfacefolderName(brand, interfaceName) + "/*.csv";
            }

            return filePathInFailureFolder;
        } catch (NullPointerException e) {
            throw new Exception("File were not moved to Failure folder");
        }
    }



}

