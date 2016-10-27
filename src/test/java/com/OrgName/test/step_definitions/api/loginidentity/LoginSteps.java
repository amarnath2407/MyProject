package com.salmon.test.step_definitions.api.loginidentity;

import com.jayway.restassured.response.Response;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.Props;
import com.salmon.test.models.api.loginidentity.LoginPayload;
import com.salmon.test.services.loginidentity.login;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.List;

import static com.salmon.test.services.loginidentity.login.personData;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step Definition implementation class for Cucumber Steps defined in Feature file
 */

public class LoginSteps {

    private String userId;
    private LoginPayload testLoginPayload;
    // query results
    private List<HashMap> usersResult;
    private List<HashMap> addresses;

    /*   test step for registered login */
    @When("^I login as a registered User$")
    public void iLoginAsARegisteredUser() throws Throwable {
        // create the data for this user
        testLoginPayload = personData();
        // call the service
        Response response = login.registerUser(testLoginPayload,"moncler_uk");
        // assert on tokens and user id
        userId = response.body().jsonPath().get("userId");
        assertThat(response.body().jsonPath().get("WCToken") != null && response.body().jsonPath().get("WCTrustedToken") != null && userId != null);
    }

    // query the users table for the supplied user id
    @And("^I perform query \"([^\"]*)\" on the users table$")
    public void IperformAQueryOnTheUsersTable(String usersQuery) throws Throwable {
        System.out.println("\n####users query = "+usersQuery);
        usersResult = DatabaseHelper.executeQuery(Props.getMessage(usersQuery).replace("|users_id|", userId));
    }

  @Then("^the user exists with the correct values$")
  public void theUserExistsWithTheCorrectValues() {

      String databaseRegisterType = usersResult.get(0).get("REGISTERTYPE").toString();
      assertThat( databaseRegisterType.equals("R"));
      String databaseDN = usersResult.get(0).get("DN").toString();
      System.out.println("\n####databaseDN = "+databaseDN);
      assertThat( databaseDN.contains(testLoginPayload.getLogonId()));
  }

    @When("^I perform query \"([^\"]*)\" on the address table$")
    public void iPerformQueryOnTheAddressTable(String addressQuery) throws Throwable {

 //       addresses =  addressDB.getAddressResults(Props.getMessage(addressQuery).replace("|member_id|", userId));
        addresses = DatabaseHelper.executeQuery(Props.getMessage(addressQuery).replace("|member_id|", userId));
            }
    
    @Then("^the users self address contains the supplied registration data$")
    public void theUsersSelfAddressContainsTheSuppliedRegistrationData() {
        // read from the address results
        System.out.println("\n####addresses firstname = "+addresses.get(0).get("FIRSTNAME").toString());
        System.out.println("\n####test person firstname = "+ testLoginPayload.getFirstName() );
        System.out.println("\n####user id = "+userId );
        String databaseFirstName = addresses.get(0).get("FIRSTNAME").toString();
        String databaseAddress1 = addresses.get(0).get("ADDRESS1").toString();
        String databaseCity = addresses.get(0).get("CITY").toString();
        String databaseCountry = addresses.get(0).get("COUNTRY").toString();
        String databaseEmail1 = addresses.get(0).get("EMAIL1").toString();
        String databasePhone1 = addresses.get(0).get("PHONE1").toString();
        String databaseAddressType = addresses.get(0).get("ADDRESSTYPE").toString();
        String databaseBestCallingTime = addresses.get(0).get("BESTCALLINGTIME").toString();
        String databaseLastName = addresses.get(0).get("LASTNAME").toString();
        String databasePersonTitle = addresses.get(0).get("PERSONTITLE").toString();
        String databaseEmail2 = addresses.get(0).get("EMAIL2").toString();
        // assert
        assertThat(databaseFirstName.equals(testLoginPayload.getFirstName()));
        assertThat(databaseAddress1.equals(testLoginPayload.getAddressLine().get(0)));
        assertThat(databaseCity.equals(testLoginPayload.getCity()));
        assertThat(databaseCountry.equals(testLoginPayload.getCountry()));
        assertThat(databaseEmail1.equals(testLoginPayload.getEmail1()));
        assertThat(databasePhone1.equals(testLoginPayload.getPhone1()));
        assertThat(databaseAddressType.equals("SB"));
        assertThat(databaseBestCallingTime.equals(testLoginPayload.getBestCallingTime()));
        assertThat(databaseLastName.equals(testLoginPayload.getLastName()));
        assertThat(databasePersonTitle.equals(testLoginPayload.getPersonTitle()));
        assertThat(databaseEmail2.equals(testLoginPayload.getEmail2()));
    }
}