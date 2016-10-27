package com.salmon.test.step_definitions.api.megamenu;

import com.jayway.restassured.response.Response;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.UrlBuilder;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import java.util.Arrays;
import java.util.List;

import static com.salmon.test.services.megamenu.MegaMenuAPI.getMegaMenu;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Developer on 11/10/2016.
 */
public class MegaMenuApiSteps {
    private static List<String> brands, megaMenuParams;

    @Given("^I have brand and path parameters$")
    public void iHaveBrandAndPathParameters() throws Throwable {
        brands = Arrays.asList(UrlBuilder.readValueFromConfig("cm.brands").split(","));
    }

    @Then("^I get mega menu request and verify the response$")
    public void iGetMegaMenuRequestAndVerifyTheResponse() throws Throwable {
        Response response = null;

        for (String brand : brands) {
            megaMenuParams = Arrays.asList(UrlBuilder.readValueFromConfig(brand + ".megamenu").split(","));
            for (String megaMenuParam : megaMenuParams) {
                response = getMegaMenu(megaMenuParam);
                assertThat(response.statusCode()).isEqualTo(TestConstants.STATUS_CODE_GET_OK);
            }
        }
    }
}
