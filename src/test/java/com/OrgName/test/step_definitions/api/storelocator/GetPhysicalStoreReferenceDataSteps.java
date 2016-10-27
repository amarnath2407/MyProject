package com.salmon.test.step_definitions.api.storelocator;

import com.jayway.restassured.response.Response;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.Props;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.api.errors.ErrorType;
import com.salmon.test.models.api.loginidentity.LoginPayload;
import com.salmon.test.models.api.storelocator.*;
import com.salmon.test.services.loginidentity.login;
import com.salmon.test.services.storelocator.StoreLocatorAPI;
import com.salmon.test.sql.store.StoreDAO;
import com.salmon.test.step_definitions.api.loginhelper.LoginHelper;
import com.salmon.test.step_definitions.api.utility.BaseStepDefinition;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.salmon.test.enums.DatabaseQueries.*;
import static com.salmon.test.framework.helpers.ApiHelper.StoreIdentifierToStoreId;
import static com.salmon.test.framework.helpers.UrlBuilder.readValueFromConfig;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by veeranank on 22/08/2016.
 */

public class GetPhysicalStoreReferenceDataSteps extends BaseStepDefinition {

    private LoginPayload testLoginPayload;

    private static final Logger log = LoggerFactory.getLogger(GetPhysicalStoreReferenceDataSteps.class);
    private Map<String, String> queryParams = new HashMap<String, String>();
    private StoreLocatorAPI storeLocatorAPI;
    private StoreDAO storeDAO;
    private String stlocattr_Field;
    private AttributeData attributeData;
    List<String> storeIdentifierList;
    List<String> languageList;
    List<String> localeList;
    private List<String> userTypeList;
    private ContactsTypes contactsType;
    private List<HashMap> databaseResult;


    public GetPhysicalStoreReferenceDataSteps(StoreDAO storeDAO, StoreLocatorAPI storeLocatorAPI) {
        super(storeDAO);
        this.storeDAO = storeDAO;
        this.storeLocatorAPI = storeLocatorAPI;
    }

     @Given("^I have the store identifiers for store\"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersForStore(String storeIdentifierKey) throws Throwable {
        storeIdentifierList = Arrays.asList(readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^I call the attribute service and compare the attributes returned for the specific store against the database$")
    public void i_call_the_attribute_service_and_compare_the_attributes_returned_for_the_specific_store_against_the_database() throws Throwable {
        for (String storeIdentifier : storeIdentifierList) {
            log.info("****** Comparing data for Store Identifier attributes: " + storeIdentifier + " *****************");
            attributeData = storeLocatorAPI.attributeListByStore(storeIdentifier, queryParams);
            String catalogAssetId = storeDAO.getStoreCatalogAssetId(storeIdentifier);
            Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
            stlocattr_Field = Props.getMessage("stloc.attribute.field1");
            List<Map<String, Object>> expectedAttributeData = storeDAO.getStoreAttributesWithLanguage(catalogAssetId, stlocattr_Field, language.getLanguageId());
            if (expectedAttributeData.size() > 0) {
                List<Map<String, Object>> expectedAttributesCount = storeDAO.getStoreAttributesWithLanguageCount(catalogAssetId, stlocattr_Field, language.getLanguageId());
                int attributesCount = Integer.parseInt(expectedAttributesCount.get(0).get("ATTRIBUTESCOUNT").toString());
                assertThat(attributesCount).as("Attributes count is wrong").isEqualTo(attributeData.getData().size());
            } else {
                assertThat(attributeData.getData().size()).as("Attributes count should be 0").isEqualTo(0);
            }
            comparingAttributesReturnedForTheSpecificStoreAgainstTheDatabase(expectedAttributeData, storeIdentifier);
        }
    }

    @Given("^I have the store identifiers for store\"([^\"]*)\" and language\"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersForStoreAndLanguage(String storeIdentifierKey, String languageKey) throws Throwable {
        storeIdentifierList = Arrays.asList(readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
    }

    @Then("^I call the attribute service by language ID and compare the attributes returned for the specific store against the database$")
    public void iCallTheAttributeServiceByLanguageIDAndCompareTheAttributesReturnedForTheSpecificStoreAgainstTheDatabase() throws Throwable {
        for (String storeIdentifier : storeIdentifierList) {
            String catalogAssetId = storeDAO.getStoreCatalogAssetId(storeIdentifier);
            queryParams = new HashMap<String, String>();
            for (String languageId : languageList) {
                log.info("****** Comparing data for Store Identifier attributes : " + storeIdentifier + " with language : " + languageId + "  *****************");
                if (storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), languageId) == 0) {
                    log.info("*****Language ID : " + languageId + "not available for store");
                    Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    log.info("*****Replacing language ID : " + languageId + "with default language ID" + language.getLanguageId());
                    languageId = language.getLanguageId();
                }
                queryParams.put("langId", languageId);
                attributeData = storeLocatorAPI.attributeListByStore(storeIdentifier, queryParams);
                stlocattr_Field = Props.getMessage("stloc.attribute.field1");
                List<Map<String, Object>> expectedAttributeData = storeDAO.getStoreAttributesWithLanguage(catalogAssetId, stlocattr_Field, languageId);
                if (expectedAttributeData.size() > 0) {
                    List<Map<String, Object>> expectedAttributesCount = storeDAO.getStoreAttributesWithLanguageCount(catalogAssetId, stlocattr_Field, languageId);
                    int attributesCount = Integer.parseInt(expectedAttributesCount.get(0).get("ATTRIBUTESCOUNT").toString());
                    assertThat(attributesCount).as("Attributes count is wrong").isEqualTo(attributeData.getData().size());
                } else {
                    assertThat(attributeData.getData().size()).as("Attributes count should be 0").isEqualTo(0);
                }
                comparingAttributesReturnedForTheSpecificStoreAgainstTheDatabase(expectedAttributeData, storeIdentifier);
            }
        }
    }

    @Given("^I have the store identifiers for store\"([^\"]*)\" and locale\"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersForStoreAndLocale(String storeIdentifierKey, String locale) throws Throwable {
        storeIdentifierList = Arrays.asList(readValueFromConfig(storeIdentifierKey).split(","));
        localeList = Arrays.asList(Props.getMessage(locale).split(","));
        log.info("****** localeList" + localeList);
    }

    @Then("^I call the attribute service by locale and compare the attributes returned for the specific store against the database$")
    public void iCallTheAttributeServiceByLocaleAndCompareTheAttributesReturnedForTheSpecificStoreAgainstTheDatabase() throws Throwable {
        for (String storeIdentifier : storeIdentifierList) {
            String catalogAssetId = storeDAO.getStoreCatalogAssetId(storeIdentifier);
            queryParams = new HashMap<String, String>();
            for (String localeName : localeList) {
                log.info("****** Comparing data for Store Identifier attributes : " + storeIdentifier + " with locale : " + localeName + "  *****************");
                Integer languageId = storeDAO.getLanguageIdByLocaleName(localeName);
                if (languageId == 0 || storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), languageId.toString()) == 0) {
                    Language language = storeDAO.getDefaultLanguageForStore(storeIds.get(storeIdentifier));
                    languageId = Integer.parseInt(language.getLanguageId());
                }
                queryParams.put("locale", localeName);
                attributeData = storeLocatorAPI.attributeListByStore(storeIdentifier, queryParams);
                stlocattr_Field = Props.getMessage("stloc.attribute.field1");
                List<Map<String, Object>> expectedAttributeData = storeDAO.getStoreAttributesWithLanguage(catalogAssetId, stlocattr_Field, languageId.toString());
                if (expectedAttributeData.size() > 0) {
                    List<Map<String, Object>> expectedAttributesCount = storeDAO.getStoreAttributesWithLanguageCount(catalogAssetId, stlocattr_Field, languageId.toString());
                    int attributesCount = Integer.parseInt(expectedAttributesCount.get(0).get("ATTRIBUTESCOUNT").toString());
                    assertThat(attributesCount).as("Attributes count is wrong").isEqualTo(attributeData.getData().size());
                } else {
                    assertThat(attributeData.getData().size()).as("Attributes count should be 0").isEqualTo(0);
                }
                comparingAttributesReturnedForTheSpecificStoreAgainstTheDatabase(expectedAttributeData, storeIdentifier);
            }
        }
    }

    public void comparingAttributesReturnedForTheSpecificStoreAgainstTheDatabase(List<Map<String, Object>> expectedAttributeData, String storeIdentifier) {
        String expAttributeIdentifier, expAttributeType, expAttributeName, expAtrributeValIdentifier, expAttributeValue;
        Attribute attribute;
        AttributeValues attributeVal;
        Integer matches=0;
        for (int attributeCount = 0; attributeCount < expectedAttributeData.size(); attributeCount++) {
            List<Attribute> actualAttributes = attributeData.getData();
            Map<String, Object> expectedAttributeMap = expectedAttributeData.get(attributeCount);
            expAttributeIdentifier = expectedAttributeMap.get("IDENTIFIER").toString();
            expAttributeName = expectedAttributeMap.get("NAME").toString();
            expAtrributeValIdentifier = expectedAttributeMap.get("VIDENTIFIER").toString();
            expAttributeValue = expectedAttributeMap.get("VALUE").toString();
            Boolean isAttributeExpected = false;
            Boolean isAttributeValExpected = false;
            for (int actualAttributesCount = 0; actualAttributesCount < actualAttributes.size(); actualAttributesCount++) {
                attribute = actualAttributes.get(actualAttributesCount);
                 if (expAttributeIdentifier.equals(attribute.getIdentifier())) {
                     assertThat(expAttributeName).isEqualTo(attribute.getName());
                     assertThat(expAtrributeValIdentifier).isEqualTo(attribute.getValues().get(0).getIdentifier());
                     assertThat(expAttributeValue).isEqualTo(attribute.getValues().get(0).getValue());
                    matches++;
                }
                log.info("****** matches = "+matches);

            }
        }
        assertThat(matches.equals(expectedAttributeData.size())).as("Expected result set size  " + expectedAttributeData.size() + " is not equal to actual result set size of " + matches + "").isTrue();
    }

    // get list of store identifiers for testing
    @Given("^I have store identifiers \"([^\"]*)\"$")
    public void iHaveStoreIdentifiers(String storeIdentifierKey) throws Throwable {
        storeIdentifierList = Arrays.asList(readValueFromConfig(storeIdentifierKey).split(","));
    }

    @Then("^When I am logged in as a \"([^\"]*)\" and call the GetContactTypes service without parameters for the current store I receive an error$")
    public void whenIAmLoggedstoreIdentifiersInAsAAndCallTheGetContactTypesServiceWithoutParametersForTheCurrentStoreIReceiveAnError(String userType) throws Throwable {
        ContactsTypes contactsTypes;
        for (String storeIdentifier : storeIdentifierList) {
            LoginHelper loginHelper = new LoginHelper();
            // login as the specified user
            Response response = loginHelper.login(userType, storeIdentifier);
            // call getContactTypes as this site user
            contactsTypes = storeLocatorAPI.getContactTypes(storeIdentifier, "https", null);
            // Assert that the correct error message is returned
            ErrorType errorType = contactsTypes.getErrors().get(0);
            assertThat(errorType.getErrorCode()).contains("CWXFR0268E");
            assertThat(errorType.getErrorKey()).contains("NOT_AUTHORIZED_FOR_QUERY");
        }
    }


    @Then("^when I attempt to execute the GetContactTypes service under http as admin user I receive an error$")
    public void whenIAttemptToExecuteTheGetContactTypesServiceUnderHttpAsAdminUserIReceiveAnError() throws Throwable {
        for (String storeIdentifier : storeIdentifierList) {
            Assert.assertTrue(validHttpError("getcontacttypes",storeIdentifier));
        }
    }


    @Then("^when I call GetContactTypes as admin user with no parameters I receive all contact types in the default language$")
    public void whenICallGetContactTypesWithNoParametersIReceiveAllContactTypesInTheDefaultLanguage() throws Throwable {
        getContacts("contacttypes",null,null);
    }

    @Then("^when I call GetContactTypes as admin user with a locale parameter I receive the contact types in the associated language for that locale$")
    public void whenICallGetContactTypesAsAdminUserWithAValidLocaleParameterIReceiveTheContactTypesInTheAssociatedLanguageForThatLocale() throws Throwable {
        getContacts("contacttypes",null,localeList);
    }

    @Then("^when I call GetContactTypes as admin user with a language parameter I receive the contact types in the associated language for that language$")
    public void whenICallGetContactTypesAsAdminUserWithALanguageParameterIReceiveTheContactTypesInTheAssociatedLanguageForThatLanguage() throws Throwable {
        getContacts("contacttypes",languageList,null);
    }

    @Given("^I have the store identifiers for store\"([^\"]*)\" and language\"([^\"]*)\" and locale \"([^\"]*)\"$")
    public void iHaveTheStoreIdentifiersForStoreAndLanguageAndLocale(String storeIdentifierKey, String languageKey, String locale) throws Throwable {
        storeIdentifierList = Arrays.asList(readValueFromConfig(storeIdentifierKey).split(","));
        languageList = Arrays.asList(Props.getMessage(languageKey).split(","));
        localeList = Arrays.asList(Props.getMessage(locale).split(","));
    }

    @Then("^when I call GetContactTypes as admin user with locale and language parameters then the language parameter takes priority$")
    public void whenICallGetContactTypesAsAdminUserWithLocaleAndLanguageParametersThenTheLanguageParameterTakesPriority() throws Throwable {
        getContacts("contacttypes",languageList,localeList);
    }

    private boolean validHttpError( String serviceName, String storeIdentifier ) {
        ContactsTypes contactsTypes;
        ContactsNames contactsNames;
        ErrorType errorType;
        // construct admin login payload
        LoginPayload adminLoginPayload = login.existingUserLoginData(UrlBuilder.readValueFromConfig("wcsadmin.username"), UrlBuilder.readValueFromConfig("wcsadmin.password"));
        // login as admin user wcsadmin
        Response response = login.loginUser(storeIdentifier, adminLoginPayload);
        if ( serviceName.equals("getcontacttypes")) {
            // call getContactTypes under http
            contactsTypes = storeLocatorAPI.getContactTypes(storeIdentifier, "http", null);
            errorType = contactsTypes.getErrors().get(0);
        } else {
            // call getContactTypes under http
            contactsNames = storeLocatorAPI.getContactNames(storeIdentifier, "http", null);
            errorType = contactsNames.getErrors().get(0);
        }
      return  errorType.getErrorCode().contains("CWXFR0268E") && errorType.getErrorKey().contains("NOT_AUTHORIZED_FOR_QUERY");
    }

    private List<Map<String, Object>> expectedDataForGetContactTypes( String storeIdentifier, String languageId, String locale) {
        Integer localeLanguageId;
        int supported = 0;
        List<Map<String, Object>> expectedData = null;
        StringBuilder sqlQuery;
        sqlQuery = new StringBuilder();
        // if we have a language parameter this takes priority over locale
        if (languageId == null && locale == null) {
            // if no parameters supplied use store default language
            sqlQuery.append(GET_CONTACT_TYPE_CODE_NAME.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeIdentifier)));
        } else {
            // if language id is provided
            if (languageId != null) {
                // check to see if it is supported by the store
                try {
                    supported = storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), languageId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // if the language is supported query by this language
                if (supported != 0) {
                    sqlQuery.append(GET_CONTACT_TYPE_CODE_NAME_BY_LANGUAGE.getQuery().replace("|LANGUAGE_ID|", languageId));
                    // unsupported language so use store default language
                } else {
                    sqlQuery.append(GET_CONTACT_TYPE_CODE_NAME.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeIdentifier)));
                }

            } else {
                // no language supplied so locale must be present
                try {
                    localeLanguageId = storeDAO.getLanguageIdByLocaleName(locale);
                    supported = storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), localeLanguageId.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (supported != 0) {
                    // if supported locale language use locale query
                    sqlQuery.append(GET_CONTACT_TYPE_CODE_NAME_BY_LOCALE.getQuery().replace("|LOCALENAME|", locale));
                } else {
                    // use query with store default language
                    sqlQuery.append(GET_CONTACT_TYPE_CODE_NAME.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeIdentifier)));
                }
            }
        }
        // perform query
        try {

            expectedData = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Exception while getting data from database: " + e);
            assertThat(false).as("Exception while getting data from database: " + e).isTrue();
        }
        log.trace("expectedDataForGetContactTypes: query = "+sqlQuery);
        return expectedData;
    }

    private List<Map<String, Object>> expectedDataForGetContactNames( String storeIdentifier, String languageId, String locale) {
        Integer localeLanguageId;
        int supported = 0;
        List<Map<String, Object>> expectedData = null;
        StringBuilder sqlQuery;
        sqlQuery = new StringBuilder();
        // if we have a language parameter this takes priority over locale
        if (languageId == null && locale == null) {
            // if no parameters supplied use store default language
            sqlQuery.append(GET_CONTACT_NAME_ID_NAME.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeIdentifier)));
        } else {
            // if language id is provided
            if (languageId != null) {
                // check to see if it is supported by the store
                try {
                    supported = storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), languageId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // if the language is supported query by this language
                if (supported != 0) {
                    sqlQuery.append(GET_CONTACT_NAME_ID_NAME_BY_LANGUAGE.getQuery().replace("|LANGUAGE_ID|", languageId));
                    // unsupported language so use store default language
                } else {
                    sqlQuery.append(GET_CONTACT_NAME_ID_NAME.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeIdentifier)));
                }

            } else {
                // no language supplied so locale must be present
                try {
                    localeLanguageId = storeDAO.getLanguageIdByLocaleName(locale);
                    supported = storeDAO.checkLanguageSupportedForStore(StoreIdentifierToStoreId(storeIdentifier), localeLanguageId.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (supported != 0) {
                    // if supported locale language use locale query
                    sqlQuery.append(GET_CONTACT_NAME_ID_NAME_BY_LOCALE.getQuery().replace("|LOCALENAME|", locale));
                } else {
                    // use query with store default language
                    sqlQuery.append(GET_CONTACT_NAME_ID_NAME.getQuery().replace("|STORE_ID|", StoreIdentifierToStoreId(storeIdentifier)));
                }
            }
        }
        // perform query
        try {

            expectedData = DatabaseHelper.executeDatabaseQuery(sqlQuery.toString());
        } catch (Exception e) {
            log.error("Exception while getting data from database: " + e);
            assertThat(false).as("Exception while getting data from database: " + e).isTrue();
        }
        return expectedData;
    }


    @Then("^When I am logged in as a \"([^\"]*)\" and call the GetContactNames service without parameters for the current store I receive an error$")
    public void whenIAmLoggedInAsAAndCallTheGetContactNamesServiceWithoutParametersForTheCurrentStoreIReceiveAnError(String userType) throws Throwable {
        ContactsNames contactsNames;

        for (String storeIdentifier : storeIdentifierList) {
            LoginHelper loginHelper = new LoginHelper();
            // login as the specified user
            Response response = loginHelper.login(userType, storeIdentifier);
            // call getContactTypes as this site user
            contactsNames = storeLocatorAPI.getContactNamesForNegative(storeIdentifier, "https", null);
            // Assert that the correct error message is returned
            ErrorType errorType = contactsNames.getErrors().get(0);
            assertThat(errorType.getErrorCode()).contains("CWXFR0268E");
            assertThat(errorType.getErrorKey()).contains("NOT_AUTHORIZED_FOR_QUERY");
        }

    }

    @Then("^when I attempt to execute the GetContactNames service under http as admin user I receive an error$")
    public void whenIAttemptToExecuteTheGetContactNamesServiceUnderHttpAsAdminUserIReceiveAnError() throws Throwable {
        for (String storeIdentifier : storeIdentifierList) {
            Assert.assertTrue(validHttpError("getcontactnames",storeIdentifier));
        }
    }

    @Then("^when I call GetContactNames as admin user with no parameters I receive all contact names in the default language$")
    public void whenICallGetContactNamesAsAdminUserWithNoParametersIReceiveAllContactNamesInTheDefaultLanguage() throws Throwable {
        getContacts("contactnames",null,null);
    }

    @Then("^when I call GetContactNames as admin user with a locale parameter I receive the contact names in the associated language for that locale$")
    public void whenICallGetContactNamesAsAdminUserWithALocaleParameterIReceiveTheContactNamesInTheAssociatedLanguageForThatLocale() throws Throwable {
        getContacts("contactnames",null,localeList);
    }

    @Then("^when I call GetContactNames as admin user with a language parameter I receive the contact names in the associated language for that language$")
    public void whenICallGetContactNamesAsAdminUserWithALanguageParameterIReceiveTheContactNamesInTheAssociatedLanguageForThatLanguage() throws Throwable {
        getContacts("contactnames",languageList,null);
    }

    @Then("^when I call GetContactames as admin user with locale and language parameters then the language parameter takes priority$")
    public void whenICallGetContactamesAsAdminUserWithLocaleAndLanguageParametersThenTheLanguageParameterTakesPriority() throws Throwable {
        getContacts("contactnames",languageList,localeList);

    }



     public void getContacts(String serviceName, List<String> langList, List<String> locList ) throws Throwable {
        List<Map<String, Object>> expectedData = null;
        String storeId;
        ContactsNames contactsNames = null;
        ContactsTypes contactsTypes = null;
        LoginPayload adminLoginPayload = login.existingUserLoginData(UrlBuilder.readValueFromConfig("wcsadmin.username"), UrlBuilder.readValueFromConfig("wcsadmin.password"));
        Integer matches;
        for (String storeIdentifier : storeIdentifierList) {
            // get store id
            storeId=StoreIdentifierToStoreId(storeIdentifier);
            // login as admin
            Response response = login.loginUser(storeIdentifier, adminLoginPayload);

            // initialise parameter
            queryParams = new HashMap<>();
            // if language and locale present loop over both setting parameter
            if  ( !(langList==null)  && !(locList==null)  ) {
                for (String thisLocale: locList) {
                    for (String thisLanguage: langList) {
                        queryParams.put("langId", thisLanguage);
                        queryParams.put("locale", thisLocale);
                        // call the service
                        log.info(">>> lang and loc <<<");
                        log.info(">>> values:"+thisLanguage+" "+thisLocale+"<<<");
                        if (serviceName.equals("contacttypes")) {
                            contactsTypes = storeLocatorAPI.getContactTypes(storeIdentifier, "https", queryParams);
                            expectedData = expectedDataForGetContactTypes(storeIdentifier,thisLanguage,thisLocale);
                            assertThat(checkExpectedResults(serviceName,null,contactsTypes,expectedData));
                        } else {
                            contactsNames = storeLocatorAPI.getContactNames(storeIdentifier, "https", queryParams);
                            expectedData = expectedDataForGetContactNames(storeIdentifier,thisLanguage,thisLocale);
                            assertThat(checkExpectedResults(serviceName,contactsNames,null,expectedData));
                        }
                    }
                }

            } else {
                // if only language present
                if( !(langList==null) ) {
                    // loop over language only
                    for (String thisLanguage: langList) {
                        queryParams.put("langId", thisLanguage);
                        // call service
                        log.info(">>> lang <<<");
                        log.info(">>> values:"+thisLanguage+"<<<");
                        if (serviceName.equals("contacttypes")) {
                            contactsTypes = storeLocatorAPI.getContactTypes(storeIdentifier, "https", queryParams);
                            expectedData = expectedDataForGetContactTypes(storeIdentifier,thisLanguage,null);
                            assertThat(checkExpectedResults(serviceName,null,contactsTypes,expectedData));
                        } else {
                            contactsNames = storeLocatorAPI.getContactNames(storeIdentifier, "https", queryParams);
                            expectedData = expectedDataForGetContactNames(storeIdentifier,thisLanguage,null);
                            assertThat(checkExpectedResults(serviceName,contactsNames,null,expectedData));
                        }
                    }
                } else {
                    // if only locale present
                    if( !(locList==null) ) {
                        // loop over locale only
                        for (String thisLocale: locList) {
                            queryParams.put("locale", thisLocale);
                            // call service
                            log.info(">>> loc <<<");
                            log.info(">>> values:"+thisLocale+"<<<");
                            if (serviceName.equals("contacttypes")) {
                                contactsTypes = storeLocatorAPI.getContactTypes(storeIdentifier, "https", queryParams);
                                expectedData = expectedDataForGetContactTypes(storeIdentifier,null,thisLocale);
                                assertThat(checkExpectedResults(serviceName,null,contactsTypes,expectedData));
                            } else {
                                contactsNames = storeLocatorAPI.getContactNames(storeIdentifier, "https", queryParams);
                                expectedData = expectedDataForGetContactNames(storeIdentifier,null,thisLocale);
                                assertThat(checkExpectedResults(serviceName,contactsNames,null,expectedData));
                            }
                        }
                    } else {
                        // there are no parameters
                        // call service
                        log.info(">>> no parameters <<<");
                        if (serviceName.equals("contacttypes")) {
                            contactsTypes = storeLocatorAPI.getContactTypes(storeIdentifier, "https", queryParams);
                            expectedData = expectedDataForGetContactTypes(storeIdentifier,null,null);
                            assertThat(checkExpectedResults(serviceName,null,contactsTypes,expectedData));
                        } else {
                            contactsNames = storeLocatorAPI.getContactNames(storeIdentifier, "https", queryParams);
                            expectedData = expectedDataForGetContactNames(storeIdentifier,null,null);
                            assertThat(checkExpectedResults(serviceName,contactsNames,null,expectedData));
                        }
                }
            }
}

        }
    }

    /* common expected result checker for contacttypes and contactnames */
    private boolean checkExpectedResults(String serviceName, ContactsNames contactsNames,
                                         ContactsTypes contactsTypes, List<Map<String, Object>> expectedData) {

        // reconcile service response with database query results
        Integer matches = 0;
        Integer responseSize, thisRow, thisElement;
        if ( serviceName == "contacttypes" ) {
            responseSize = contactsTypes.getData().size();
        }  else {
            responseSize = contactsNames.getData().size();
        }
        for (thisRow = 0; thisRow < expectedData.size(); thisRow++) {
            for (thisElement = 0; thisElement < responseSize; thisElement++) {
                // if both code and name match on service response and query result increment the matches count
                if ( serviceName == "contacttypes" ) {
                    if (expectedData.get(thisRow).get("NAME").equals(contactsTypes.getData().get(thisElement).getName()) &&
                            expectedData.get(thisRow).get("CONTACT_CODE").toString().equals(String.valueOf(contactsTypes.getData().get(thisElement).getCode()))) {
                        matches++;
                    }
                } else  {
                    if (expectedData.get(thisRow).get("NAME").equals(contactsNames.getData().get(thisElement).getName()) &&
                            expectedData.get(thisRow).get("CONTACT_NAME_ID").toString().equals(String.valueOf(contactsNames.getData().get(thisElement).getId()))) {
                        matches++;
                    }
                }
                log.info("matches = " + matches);
            }
        }
        // assert that correct number of rows/response elements match
        assertThat(expectedData.size()).isEqualTo(matches);
        // assert that number of db rows = size of service response
        if (serviceName.equals("contacttypes")) {
            return (expectedData.size() == contactsTypes.getData().size());
        } else {
            return (expectedData.size() == contactsNames.getData().size());
        }

    }
}









