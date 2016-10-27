package com.salmon.test.framework.helpers;


import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class DataloadHook {

    private static final Logger LOG = LoggerFactory.getLogger(DataloadHook.class);
    private static final String CSV_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "PIM" + File.separator;
    private static final String WCS_FILE_PATH = File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "dataloadcsvfiles" + File.separator + "WCS" + File.separator;
    private static String timeStamp = "";

    //Delete existing PIM files
    @Before("@dataload")
    public void deletePimFileIfExists() throws IOException {
        LOG.info("Before Scenario");
//        if (timeStamp.equalsIgnoreCase("")) {
//            setTimeStamp(getCurrentDateAndTime());
//        }

        setTimeStamp(getCurrentDateAndTime());

        File file = new File(getFilePath());

        String[] dirList = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (String dir : dirList) {
            File folder = new File(getFilePath() + "" + dir);
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
            LOG.info("PIM files deleted");
        }
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile() && f.exists()) {
                if (!f.getName().equalsIgnoreCase("Readme.txt")) {
                    f.delete();
                }
                System.out.println("successfully deleted json files");
            } else {
                System.out.println("cant delete a file due to open or error");
            }
        }
    }


    public void deleteProcessedPimFiles() throws IOException {
        LOG.info("After Talend process..........");
        File file = new File(getFilePath());
        String[] dirList = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (String dir : dirList) {
            File folder = new File(getFilePath() + "" + dir);
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
            LOG.info("PIM files deleted");
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


    @After("@dataload")
    public void deleteWcsFileIfExists() throws IOException {
        LOG.info("After Scenario");

        File file = new File(getWcsFilePath());

        String[] dirList = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });

        for (String dir : dirList) {
            File folder = new File(getWcsFilePath() + "" + dir);
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
            LOG.info("Wcs files deleted");
        }
    }

    private static String getFilePath() throws IOException {
        return new File(".").getCanonicalPath() + CSV_FILE_PATH;
    }

    private static String getWcsFilePath() throws IOException {
        return new File(".").getCanonicalPath() + WCS_FILE_PATH;
    }

    public static String getTimeStamp() {
        return timeStamp;
    }


    public static void setTimeStamp(String dateTime) {
        timeStamp = dateTime;
    }

    public static String getCurrentDateAndTime() {
        return new DateTime().toString("yyyy-MM-dd-hhmmss");
    }
}
