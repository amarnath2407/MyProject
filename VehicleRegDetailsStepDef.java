import com.williamhill.VehicleRegDetailsPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

/**
 * 19/05/2017
 *
 * @author 44016257 - Amarnath Ettukullapati
 */
public class VehicleRegDetailsStepDef {

    VehicleRegDetailsPage vehicleRegDetailsPage;

    String regNumberPlate;


    public VehicleRegDetailsStepDef(VehicleRegDetailsPage vehicleRegDetailsPage) {
        this.vehicleRegDetailsPage = vehicleRegDetailsPage;
    }



    @Given("^user on gov-vehicle-information main page$")
    public void userOnGovVehicleInformationMainPage() throws Throwable {
        vehicleRegDetailsPage.navigateToGovDVLAPage();
    }

    @And("^clicks on \"([^\"]*)\" button$")
    public void clicksOnButton(String button) throws Throwable {
        vehicleRegDetailsPage.clickOnButton(button);
    }

    @Then("^system should navigates to enter vehicle reg number page$")
    public void systemShouldNavigatesToEnterVehicleRegNumberPage() throws Throwable {
       Assert.assertTrue(vehicleRegDetailsPage.verifyPageHeading());
    }

    @When("^user input the \"([^\"]*)\" and clicks \"([^\"]*)\" button$")
    public void userInputTheAndClicksButton(String regNumber, String button) throws Throwable {
        regNumberPlate = regNumber;
        vehicleRegDetailsPage.inputRegistrationNumber(regNumberPlate);
        vehicleRegDetailsPage.clickOnButton(button);
    }

    @Then("^verify that system displayed correct registration number$")
    public void verifyThatSystemDisplayedCorrectRegistrationNumber() throws Throwable {
       Assert.assertTrue("\n verifying Reg number ", vehicleRegDetailsPage.verifyNumberPlateConfirmation(regNumberPlate));
    }

    @And("^select \"([^\"]*)\" and click on \"([^\"]*)\" button$")
    public void selectAndClickOnButton(String radio, String button) throws Throwable {
        vehicleRegDetailsPage.clickRadioButton();
        vehicleRegDetailsPage.clickOnButton(button);
    }


}
