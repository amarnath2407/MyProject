package com.salmon.test.step_definitions.dataload.category;


import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.salmon.test.dataload.category.CategoryCsvFileGenerator;
import com.salmon.test.enums.DatabaseQueries;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.DataloadHook;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.framework.helpers.utils.SSHClient;
import com.salmon.test.models.dataload.pim.category.CategoryDescriptionPimModel;
import com.salmon.test.models.dataload.pim.category.CategoryPimModel;
import com.salmon.test.models.dataload.pim.category.CategoryRelationshipPimModel;
import com.salmon.test.models.dataload.wcs.category.CategoryDescriptionWcsModel;
import com.salmon.test.models.dataload.wcs.category.CategoryRelationshipWcsModel;
import com.salmon.test.models.dataload.wcs.category.CategoryWcsModel;


import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.salmon.test.dataload.category.CategoryCsvFileGenerator.*;
import static com.salmon.test.dataload.category.CategoryWcsFileChecker.*;
import static com.salmon.test.framework.helpers.DataLoaderHelper.getFolderPath;
import static com.salmon.test.framework.helpers.DataLoaderHelper.interfacefolderName;
import static org.assertj.core.api.Assertions.assertThat;


public class CategorySteps {

    static final Logger LOG = LoggerFactory.getLogger(CategorySteps.class);

    private SSHClient sshClient = null;
    private static String profileId, currentDBDateTime;


    public CategorySteps(SSHClient sshClient) {
        this.sshClient = sshClient;
    }

    @When("^I process the(.*)PIM files from Talend to WCS$")
    public void iProcessThePIMFilesFromTalendToWCS(String stage) throws Throwable {
        processPimFilesIntoTalend(stage);

    }

    private void processPimFilesIntoTalend(String stage) throws SQLException, IOException, InterruptedException, JSchException, SftpException {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        String dataloadInLocation;
        profileId = UrlBuilder.readValueFromConfig("profile.id");
        File srcDir;
        List<HashMap> currentDBSysTime;
        for (String brand : brands) {
            currentDBSysTime = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_SYSDATE.getQuery());
            currentDBDateTime = currentDBSysTime.get(0).get("SYSDATE").toString();
            if (profileId.equalsIgnoreCase("dev")) {

                if ("stage2".equalsIgnoreCase(stage.trim())) {
                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + brand + File.separator + "in";
                    srcDir = new File(CategoryCsvFileGenerator.getStageTwoFilePath(brand));
                } else {
                    srcDir = new File(getFolderPath(brand, stage));
                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + brand + File.separator + "in" + File.separator + interfacefolderName(brand, stage);
                }
                File destDir = new File(dataloadInLocation);
                FileUtils.copyDirectory(srcDir, destDir);
                Process p = Runtime.getRuntime().exec("cmd /c start /wait " + UrlBuilder.readValueFromConfig("interface.location") + brand);
                System.out.println("\nWaiting for batch file ...");
               /* do {
                } while (p.isAlive());*/
                p.waitFor();
                System.out.println("Batch file done.");
            } else {
                if ("stage2".equalsIgnoreCase(stage.trim())) {
                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + ("int" + brand.toLowerCase()) + "/datain/" + getStageTwoFolderName(brand);
                    srcDir = new File(getStageTwoFilePath(brand));
                } else {
                    srcDir = new File(getFilePath(brand));
                    dataloadInLocation = UrlBuilder.readValueFromConfig("dataload.location") + ("int" + brand.toLowerCase()) + "/datain/" + folderName(brand);
                }

                //Transfer the files with intdiesel user
                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.getSystestUser().toString(), UrlBuilder.getSystestPassword().toString());
                sshClient.executeCommand("mkdir  " + dataloadInLocation);
                sshClient.copyFileToUnix(srcDir + "/*.csv", dataloadInLocation);
                sshClient.disconnect();

                // Executing the interface script with autotest1 user
                sshClient.connect(UrlBuilder.getSystestIP().toString(), UrlBuilder.readValueFromConfig("systest.sudo.user"), UrlBuilder.getSystestPassword().toString());
                sshClient.executeCommand(UrlBuilder.readValueFromConfig("interface.location") + brand);
                sshClient.disconnect();
            }

        }
        new DataloadHook().deleteProcessedPimFiles();
    }

    @Then("^data is inserted into CATGRPDESC table:$")
    public void dataIsInsertedIntoCATGRPDESCTable(List<CategoryDescriptionPimModel> categoryDescriptionModelList) throws Throwable {
        List<HashMap> actCatDescSqlResult;
        List<String> brands = getBrands();
        for (String brand : brands) {
            for (CategoryDescriptionPimModel descriptionModel : categoryDescriptionModelList) {
                actCatDescSqlResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATGRPDESC.getQuery().replace("|descName|", descriptionModel.getName()));
                assertThat(actCatDescSqlResult.get(0).get("NAME").equals(descriptionModel.getName())).isTrue();
            }
        }
    }

    @Then("^category data is inserted correctly into table:$")
    public void categoryDataIsInsertedCorrectlyIntoTable(List<CategoryPimModel> categoryPimModelList) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier");
            List<HashMap> categoryDatabaseResult;
            for (CategoryPimModel categoryPimModel : categoryPimModelList) {
                categoryDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY.getQuery().replace("|categoryIdentifier|", categoryPimModel.getCategoryIdentifier()));

                if (!categoryPimModel.getSequence().equalsIgnoreCase("")) {
                    for (HashMap actCategoryDatabaseResult : categoryDatabaseResult) {
                        assertThat(actCategoryDatabaseResult.get("CATALOG_IDENTIFIER").toString().contains(catalogIdentifier)).isTrue();
                        assertThat(actCategoryDatabaseResult.get("CATEGORY_IDENTIFIER").toString().contains(categoryPimModel.getCategoryIdentifier())).isTrue();
                        assertThat(actCategoryDatabaseResult.get("SEQUENCE").toString().contains(categoryPimModel.getSequence())).isTrue();
                    }
                } else {
                    assertThat(categoryDatabaseResult.size()).isEqualTo(0);
                }
            }
        }
    }

    @And("^category Description data is inserted correctly into table:$")
    public void categoryDescriptionDataIsInsertedCorrectlyIntoTable(List<CategoryDescriptionPimModel> categoryDescriptionModelList) throws Throwable {
        List<HashMap> actCategoryDescriptionDatabaseResult;
        for (CategoryDescriptionPimModel descriptionModel : categoryDescriptionModelList) {
            actCategoryDescriptionDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY_DESCRIPTION.getQuery().replace("|groupIdentifier|", descriptionModel.getGroupIdentifier()));
            assertThat(actCategoryDescriptionDatabaseResult.size()).isEqualTo(1);
            for (HashMap categoryDescriptionDatabaseResult : actCategoryDescriptionDatabaseResult) {
                assertThat(categoryDescriptionDatabaseResult.get("GROUP_IDENTIFIER").toString().contains(descriptionModel.getGroupIdentifier())).isTrue();
                assertThat(categoryDescriptionDatabaseResult.get("NAME").toString().contains(descriptionModel.getName())).isTrue();
                assertThat(categoryDescriptionDatabaseResult.get("LANGUAGE").toString().contains(descriptionModel.getLanguage())).isTrue();
            }
        }
    }

    @And("^category Relationship data is inserted correctly into table:$")
    public void categoryRelationshipDataIsInsertedCorrectlyIntoTable(List<CategoryRelationshipPimModel> categoryRelationshipPimModelList) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier");
            List<HashMap> actCategoryRelationshipDatabaseResult;
            for (CategoryRelationshipPimModel relationshipPimModel : categoryRelationshipPimModelList) {
                actCategoryRelationshipDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY_RELATIONSHIP.getQuery().replace("|groupIdentifier|", relationshipPimModel.getGroupIdentifier()).replace("|parentGroupIdentifier|", relationshipPimModel.getParentGroupIdentifier()));

                assertThat(actCategoryRelationshipDatabaseResult.size()).isEqualTo(1);
                for (HashMap categoryRelationshipDatabaseResult : actCategoryRelationshipDatabaseResult) {
                    assertThat(categoryRelationshipDatabaseResult.get("CATALOG_IDENTIFIER").toString().contains(catalogIdentifier)).isTrue();
                    assertThat(categoryRelationshipDatabaseResult.get("PARENT_GROUP_IDENTIFIER").toString().contains(relationshipPimModel.getParentGroupIdentifier())).isTrue();
                    assertThat(categoryRelationshipDatabaseResult.get("GROUP_IDENTIFIER").toString().contains(relationshipPimModel.getGroupIdentifier())).isTrue();
                    assertThat(categoryRelationshipDatabaseResult.get("SEQUENCE").toString().contains(relationshipPimModel.getSequence())).isTrue();
                }
            }
        }
    }

    @Given("^I create Category PIM file$")
    public void iCreateCategoryPIMFile(List<CategoryPimModel> categoryPimModels) throws Throwable {
        generateCategoryCsvFile(categoryPimModels);
    }

    @Given("^I create Category PIM file with comma-separation$")
    public void iCreateCategoryPIMFileWithCommaSeparation(List<CategoryPimModel> categoryPimModels) throws Throwable {
        generateCategoryCsvFileWithComma(categoryPimModels);
    }

    @Given("^I create Category PIM file without header$")
    public void iCreateCategoryPIMFileWithoutHeader(List<CategoryPimModel> categoryPimModels) throws Throwable {
        generateCategoryCsvFileWithoutHeader(categoryPimModels);
    }

    @Given("^I create Category PIM file with different field sequence$")
    public void iCreateCategoryPIMFileWithDifferentFieldSequence(List<CategoryPimModel> categoryPimModels) throws Throwable {
        generateCategoryCsvFileWithDiffFieldSequence(categoryPimModels);
    }

    @Then("^verify WCS \"([^\"]*)\" Category file has the correct format:$")
    public void verifyWCSCategoryFileHasTheCorrectFormat(String interfaceName, List<CategoryWcsModel> categoryWcsModels) throws Throwable {
        checkTalendCategoryFileFormat(categoryWcsModels, interfaceName);
    }

//    @Then("^verify WCS(.*)Category file has the correct format:$")
//    public void verifyWCSCategoryFileHasTheCorrectFormat(List<CategoryWcsModel> categoryWcsModels, String interfaceName) throws Throwable {
//        checkTalendCategoryFileFormat(categoryWcsModels, interfaceName);
//    }

    @And("^verify data is inserted into CATGROUP table:$")
    public void verifyDataIsInsertedIntoCATGROUPTable(List<CategoryPimModel> categoryPimModels) throws Throwable {
        List<HashMap> actCatgroupSqlResult;
        List<String> brands = getBrands();
        for (String brand : brands) {
            for (CategoryPimModel categoryPimModel : categoryPimModels) {
                actCatgroupSqlResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATGROUP.getQuery().replace("|categoryIdentifier|", categoryPimModel.getCategoryIdentifier()));
                for (int brandCount = 0; brandCount < brands.size(); brandCount++) {
                    try {
                        assertThat(actCatgroupSqlResult.get(brandCount).get("identifier").equals(categoryPimModel.getCategoryIdentifier())).isTrue();
                    } catch (IndexOutOfBoundsException e) {
                        LOG.info("Category Data is not inserted for all Brands");
                        throw new Exception("Category Data is not inserted for all Brands " + e.getMessage());
                    }
                }
            }
        }
    }

    @And("^I create Category Description file$")
    public void iCreateCategoryDescriptionFile(List<CategoryDescriptionPimModel> categoryDescriptionModelList) throws Throwable {
        generateCategoryDescriptionCsvFile(categoryDescriptionModelList);
    }


    @And("^I create Category Relationship PIM file$")
    public void iCreateCategoryRelationshipPIMFile(List<CategoryRelationshipPimModel> categoryRelationshipPimModelList) throws Throwable {
        generateCategoryRelationshipCsvFile(categoryRelationshipPimModelList);
    }

    @Then("^verify WCS Category Description file has the correct format:$")
    public void verifyWCSCategoryDescriptionFileHasTheCorrectFormat(List<CategoryDescriptionWcsModel> categoryDescriptionWcsModels) throws Throwable {
        checkWcsCategoryDescriptionFileFormat(categoryDescriptionWcsModels);
    }


    @Then("^verify WCS Category Relationship file has the correct format:$")
    public void verifyWCSCategoryRelationshipFileHasTheCorrectFormat(List<CategoryRelationshipWcsModel> categoryRelationshipWcsModels) throws Throwable {
        checkWcsCategoryRelationshipFileFormat(categoryRelationshipWcsModels);
    }

    @Then("^Verify (.*) files into (.*) failure folder$")
    public void verifyCategoryPIMFilesIntoFailureFolder(String categoryType, String interfaceName) throws Throwable {
        checkInvalidFilesMovedToFailureFolder(categoryType, interfaceName);
    }


    @Then("^Verify (.*) files into failure folder$")
    public void verifyInterfaceMovesFilesToFailureFolder(String categoryType) throws Throwable {
        checkInvalidFilesMovedToFailureFolder(categoryType);
    }

    @And("^verify data is deleted from CATGROUP table:$")
    public void verifyDataIsDeletedFromCATGROUPTable(List<CategoryPimModel> categoryPimModels) throws Throwable {
        List<HashMap> actCatgroupSqlResult;
        for (CategoryPimModel categoryPimModel : categoryPimModels) {
            actCatgroupSqlResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATGROUP.getQuery().replace("|categoryIdentifier|", categoryPimModel.getCategoryIdentifier()));
            if (categoryPimModel.getCategoryIdentifier().equals("3674")) {
                assertThat(actCatgroupSqlResult.size()).isEqualTo(0);
            }

        }
    }

    @And("^Verify WCS file UTF encoding format$")
    public void verifyWCSFileUTFEncodingFormat() throws Throwable {
        checkWcsFileURFFormat();
    }

    @And("^Verify category Description data is deleted correctly from table$")
    public void verifyCategoryDescriptionDataIsDeletedCorrectlyFromTable(List<CategoryDescriptionPimModel> categoryDescriptionModelList) throws Throwable {
        List<HashMap> actCategoryDescriptionDatabaseResult;
        for (CategoryDescriptionPimModel descriptionModel : categoryDescriptionModelList) {
            actCategoryDescriptionDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY_DESCRIPTION.getQuery().replace("|groupIdentifier|", descriptionModel.getGroupIdentifier()));
            assertThat(actCategoryDescriptionDatabaseResult.size()).isEqualTo(0);
        }
    }

    @And("^Verify category Relationship data is deleted correctly from table$")
    public void verifyCategoryRelationshipDataIsDeletedCorrectlyFromTable(List<CategoryRelationshipPimModel> categoryRelationshipPimModelList) throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier");
            List<HashMap> actCategoryRelationshipDatabaseResult;
            for (CategoryRelationshipPimModel relationshipPimModel : categoryRelationshipPimModelList) {
                actCategoryRelationshipDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY_RELATIONSHIP.getQuery().replace("|groupIdentifier|", relationshipPimModel.getGroupIdentifier()).replace("|parentGroupIdentifier|", relationshipPimModel.getParentGroupIdentifier()));
                assertThat(actCategoryRelationshipDatabaseResult.size()).isEqualTo(0);
            }
        }
    }

    @Then("^Verify category data and count in catgroup$")
    public void verifyCategoryDataAndCountInCatgroup() throws Throwable {
        //    currentDBDateTime = "2016-09-29 10:34:53.0";
        //   System.out.println("\nSys Date ------------>"+currentDBDateTime);
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier");
            Integer count = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".catgroup.count"));
            String[] expectedCategoryData = UrlBuilder.readValueFromConfig(brand + ".st2.category").replace("catalogIdentifier", catalogIdentifier).split(",");
            List<HashMap> actCategoryDatabaseResult, categoryDatabaseResult;

            actCategoryDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_SELECT_CATGROUP.getQuery().replace("|catalogIdentifier|", catalogIdentifier).replace("|sysTime|", currentDBDateTime));
            categoryDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY.getQuery().replace("|categoryIdentifier|", expectedCategoryData[0]));

            assertThat(actCategoryDatabaseResult.size()).isEqualTo(count);

            assertThat(categoryDatabaseResult.get(0).get("CATEGORY_IDENTIFIER").toString().contains(expectedCategoryData[0])).isTrue();
            assertThat(categoryDatabaseResult.get(0).get("CATALOG_IDENTIFIER").toString().contains(expectedCategoryData[1])).isTrue();
            assertThat(categoryDatabaseResult.get(0).get("SEQUENCE").toString().contains(expectedCategoryData[2])).isTrue();
        }

    }

    @Then("^Verify category description data and count in catgrpdesc$")
    public void verifyCategoryDescriptionDataAndCountInCatgrpdesc() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier");
            Integer count = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".catgrpdesc.count"));
            String[] expectedCategoryDescriptionData = UrlBuilder.readValueFromConfig(brand + ".st2.categoryDescription").split(",");
            List<HashMap> actCategoryDescriptionDatabaseResult, CategoryDescriptionDatabaseResult;

            actCategoryDescriptionDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_SELECT_CATGRPDESC.getQuery().replace("|catalogIdentifier|", catalogIdentifier).replace("|sysTime|", currentDBDateTime));
            CategoryDescriptionDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY_DESCRIPTION.getQuery().replace("|groupIdentifier|", expectedCategoryDescriptionData[0]));

            assertThat(actCategoryDescriptionDatabaseResult.size()).isEqualTo(count);

            assertThat(CategoryDescriptionDatabaseResult.get(0).get("GROUP_IDENTIFIER").toString().contains(expectedCategoryDescriptionData[0])).isTrue();
            assertThat(CategoryDescriptionDatabaseResult.get(0).get("LANGUAGE").toString().contains(expectedCategoryDescriptionData[1])).isTrue();
            assertThat(CategoryDescriptionDatabaseResult.get(0).get("NAME").toString().contains(expectedCategoryDescriptionData[2])).isTrue();
        }
    }

    @Then("^Verify category relationship data and count in catgrprel$")
    public void verifyCategoryRelationshipDataAndCountInCatgrprel() throws Throwable {
        List<String> brands = Arrays.asList(UrlBuilder.readValueFromConfig("brand").split(","));
        for (String brand : brands) {
            String catalogIdentifier = UrlBuilder.readValueFromConfig(brand + ".catalogIdentifier");
            Integer count = Integer.parseInt(UrlBuilder.readValueFromConfig(brand + ".catgrprel.count"));
            String[] expectedCategoryRelationshipData = UrlBuilder.readValueFromConfig(brand + ".st2.categoryRelationship").replace("catalogIdentifier", catalogIdentifier).split(",");
            List<HashMap> actCategoryRelationshipDatabaseResult, categoryRelationshipDatabaseResult;

            actCategoryRelationshipDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.ST2_SELECT_CATGRPREL.getQuery().replace("|catalogIdentifier|", catalogIdentifier).replace("|sysTime|", currentDBDateTime));
            categoryRelationshipDatabaseResult = DatabaseHelper.executeQuery(DatabaseQueries.SELECT_CATEGORY_RELATIONSHIP.getQuery().replace("|groupIdentifier|", expectedCategoryRelationshipData[0]).replace("|parentGroupIdentifier|", expectedCategoryRelationshipData[1]));

            assertThat(actCategoryRelationshipDatabaseResult.size()).isEqualTo(count);

            assertThat(categoryRelationshipDatabaseResult.get(0).get("GROUP_IDENTIFIER").toString().contains(expectedCategoryRelationshipData[0])).isTrue();
            assertThat(categoryRelationshipDatabaseResult.get(0).get("PARENT_GROUP_IDENTIFIER").toString().contains(expectedCategoryRelationshipData[1])).isTrue();
            assertThat(categoryRelationshipDatabaseResult.get(0).get("CATALOG_IDENTIFIER").toString().contains(expectedCategoryRelationshipData[2])).isTrue();
            assertThat(categoryRelationshipDatabaseResult.get(0).get("SEQUENCE").toString().contains(expectedCategoryRelationshipData[3])).isTrue();
        }
    }

    public static String getCurrentDBDateTime() {
        return currentDBDateTime;
    }



}
