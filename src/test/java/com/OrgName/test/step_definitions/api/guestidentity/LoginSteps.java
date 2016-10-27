package com.salmon.test.step_definitions.api.guestidentity;

import com.jayway.restassured.response.Response;
import com.salmon.test.framework.helpers.DatabaseHelper;
import com.salmon.test.framework.helpers.Props;
import com.salmon.test.services.guestidentity.login;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {
    private Response response;
    @Getter private String userId;
    private String dbRegisterType;

    @When("^I login as a guest User$")
    public void iLoginAsAGuest() throws Throwable {
       // Response response = login.guestIdentity("Aurora-UK");
        assertThat(response.body().jsonPath().get("WCToken") != null );
        userId = response.body().jsonPath().get("userId");
        assertThat(userId != null);
    }

    @When("^I perform query \"([^\"]*)\" for this user$")
    public void theUserExistsInTheUsersTable(String usersQuery) throws Throwable {
        List<HashMap> results = DatabaseHelper.executeQuery(Props.getMessage(usersQuery).replace("|users_id|", userId));
        dbRegisterType= results.get(0).get("REGISTERTYPE").toString();
    }

    @Then("^I exist with a USERS.REGISTERTYPE = G$")
    public void iExistWithRegisterTypeG() {
        assertThat(dbRegisterType.contentEquals("G"));
        System.out.println("\n %%%% Service dbRegisterType = "+dbRegisterType+" %%%%");
    }

}