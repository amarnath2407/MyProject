package com.salmon.test.step_definitions.gui.storelocator;

import com.salmon.test.page_objects.gui.HomePage;
import com.salmon.test.page_objects.gui.LoginPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.salmon.test.framework.helpers.UrlBuilder.readValueFromConfig;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 04/08/2016.
 */
public class StoreManagementSteps {

    private static Logger log = LoggerFactory.getLogger(StoreManagementSteps.class);
    private LoginPage loginPage;
    private HomePage homePage;
    public StoreManagementSteps(LoginPage loginPage, HomePage homePage) {
        this.loginPage = loginPage;
        this.homePage = homePage;
    }

    @Given("^I am on Login Page$")
    public void iAmOnLoginPage() throws Throwable {

        loginPage.enterLoginPageUrl();
        assertThat(loginPage.getCurrentPageTitle().contains("Management Center"));
    }

    @When("^I enter userName as \"([^\"]*)\" , password as \"([^\"]*)\" and click on Login$")
    public void iEnterUserNameAsPasswordAsAndClickOnLogin(String userName, String password) throws Throwable {
        loginPage.getUserNameElement().clear();
        loginPage.getUserNameElement().sendKeys(userName);
        loginPage.getPasswordElement().sendKeys(readValueFromConfig(password));
        loginPage.getLoginButtonElement().click();
    }

    @Then("^Home page should be displayed$")
    public void homePageShouldBeDisplayed() throws Throwable {
        assertThat(homePage.getToolsElement().isDisplayed()).isTrue();
    }

    @Given("^I have the Store Locator Menu Item in the Management Center Tools list$")
    public void iHaveTheStoreLocatorMenuItemInTheManagementCenterToolsList() throws Throwable {
        homePage.getCloseButtonFirstTab().click();
        homePage.getToolsElement().click();
        assertThat(homePage.getStoreLocatorMenuItem().isDisplayed()).isTrue();
    }

    @When("^I click on Store Locator menu item$")
    public void iClickOnStoreLocatorMenuItem() throws Throwable {
        homePage.getStoreLocatorMenuItem().click();
    }

    @Then("^Store Locator tab should be created$")
    public void storeLocatorTabShouldBeCreated() throws Throwable {
        assertThat(homePage.getStoreLocatorTab().getText()).isEqualTo("Store Locator");
    }

    @Then("^Store Locator tab should be opened with a drop down to select brand$")
    public void storeLocatorTabShouldBeOpenedWithADropDownToSelectBrand() throws Throwable {
        assertThat(homePage.getStoreLocatorTab().getText()).isEqualTo("Store Locator");
        assertThat(homePage.getPhysicalStoresDropDown().getText()).contains("Select Store");
    }

  /*  @After
    public void tearDown(){
        log.info("tear down method");
        homePage.getWebDriver().close();
        Alert confirmAlert = homePage.getWebDriver().switchTo().alert();
        confirmAlert.accept();
        log.info("tear down method last");
    }*/

}
