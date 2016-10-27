package com.salmon.test.step_definitions.gui.storelocator;

import com.salmon.test.framework.helpers.Props;
import com.salmon.test.models.api.storelocator.StoreAPIData;
import com.salmon.test.models.api.storelocator.StoreListAPIData;
import com.salmon.test.models.api.storelocator.StoreListUIData;
import com.salmon.test.page_objects.gui.HomePage;
import com.salmon.test.services.storelocator.StoreLocatorAPI;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by akepativ on 05/08/2016.
 */
public class PhysicalStoreListSteps {

    private static Logger log = LoggerFactory.getLogger(PhysicalStoreListSteps.class);
    private HomePage homePage;
    private String storeId,brand;
    private StoreListAPIData storeListAPIData;
    private StoreLocatorAPI storeLocatorAPI;
    private List<StoreListUIData> physicalStoreData;

    public PhysicalStoreListSteps(HomePage homePage,StoreLocatorAPI storeLocatorAPI) {
        this.homePage = homePage;
        this.storeLocatorAPI = storeLocatorAPI;
    }

    @Given("^I have selected a brand \"([^\"]*)\" with brand id \"([^\"]*)\"$")
    public void iHaveSelectedABrandWithBrandId(String brandName, String storeIdkey) throws Throwable {
        homePage.storeLocatorTabClicked();
        homePage.getPhysicalStoresDropDown().click();
        homePage.selectBrand(brandName);
        brand =  brandName;
        log.info("Selected Brand Name:"+brandName);
        storeId = Props.getMessage(storeIdkey);
    }

    @When("^I click on Stores link$")
    public void iClickOnStoresLink() throws Throwable {
        homePage.getMenuList().get(2).click();
    }

    @Then("^all physical stores related to the selected brand should be displayed in a list view$")
    public void allPhysicalStoresRelatedToTheSelectedBrandShouldBeDisplayedInAListView() throws Throwable {
        physicalStoreData = getPhysicalStoreDataFromUI();
        log.info("physicalStoreData from UI: "+physicalStoreData);
    }

    @And("^compare the list of stores data with the api returned data$")
    public void compareTheListOfStoresDataWithTheApiReturnedData() throws Throwable {
        storeListAPIData = storeLocatorAPI.findStores(storeId);
        List<StoreAPIData> actualstoreDataList = storeListAPIData.getPhysicalStore();
        StoreAPIData actualStoreData;
        StoreListUIData expectedStoreData;
        boolean storeDataExists = false;
        int physicalStoreSize = physicalStoreData.size();
        assertThat(physicalStoreSize).as("Physical store count for "+brand+" is coming as wrong").isEqualTo(actualstoreDataList.size());
        if(physicalStoreSize > 10){
            physicalStoreSize = 10;
        }
        for(int expectedStoreCount=0;expectedStoreCount<physicalStoreSize;expectedStoreCount++){
            expectedStoreData = physicalStoreData.get(expectedStoreCount);
            for(int actualStoreCount=0;actualStoreCount<actualstoreDataList.size();actualStoreCount++){
                actualStoreData = actualstoreDataList.get(actualStoreCount);
                if(expectedStoreData.getStoreName().equals(actualStoreData.getStoreName())){
                    assertThat(expectedStoreData.getStoreName()).isEqualTo(actualStoreData.getStoreName());
                    assertThat(expectedStoreData.getDisplayStoreName()).isEqualTo(actualStoreData.getStoreName());
                    assertThat(expectedStoreData.getCity()).isEqualTo(actualStoreData.getCity());
                    assertThat(expectedStoreData.getStatus()).isEqualTo("1");
                    assertThat(expectedStoreData.getCountry()).isEqualTo(actualStoreData.getCountry());
                    storeDataExists = true;
                    break;
                }
            }
            assertThat(storeDataExists).as("Physical Store "+expectedStoreData.getStoreName()+" is not coming in API response").isTrue();
        }
    }

    /**
     * This is to prepare the store data retrived from Management center UI
     * @return List of StoreListUIData objects
     */
    public List<StoreListUIData> getPhysicalStoreDataFromUI(){
        List<WebElement> storeRowsList = homePage.getStoreRowList();
        List<WebElement> storeCellsList = homePage.getStoreColumnList();
        int columns = storeCellsList.size() / storeRowsList.size();
        log.info("Stores Rows Count:"+ storeRowsList.size());
        int storeCellCount =0;
        physicalStoreData = new ArrayList<StoreListUIData>();
        StoreListUIData storeListUIData;
        for(int storeRows=0;storeRows<storeRowsList.size();storeRows++){
            storeListUIData = new StoreListUIData();
            storeListUIData.setStoreName(storeCellsList.get(storeCellCount).getText());
            storeListUIData.setStatus(storeCellsList.get(storeCellCount+1).getText());
            storeListUIData.setDisplayStoreName(storeCellsList.get(storeCellCount+2).getText());
            storeListUIData.setAddress(storeCellsList.get(storeCellCount+3).getText());
            storeListUIData.setCity(storeCellsList.get(storeCellCount+4).getText());
            storeListUIData.setCountry(storeCellsList.get(storeCellCount+5).getText());
            physicalStoreData.add(storeListUIData);
            storeCellCount = storeCellCount+columns;
        }
        return physicalStoreData;
    }

}
