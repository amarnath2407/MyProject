package com.williamhill;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * 19/05/2017
 *
 * @author 44016257 - Amarnath Ettukullapati
 */
public class VehicleRegDetailsPage {

     private  WebDriver driver = null;

    private By startNowButton = By.cssSelector("a[href=\"https://www.vehicleenquiry.service.gov.uk\"]");
    private By regNumberInput = By.id("Vrm");
    private By continueButton=By.cssSelector("button[name=\"Continue\"]");
    private By regNumberInputPageHeading = By.cssSelector(".heading-large");
    private By regNumberConfirmation =By.cssSelector(".reg-mark");
    private By yesRadioBtn = By.cssSelector("#Correct_True");

    VehicleRegDetailsPage(){
        driver = new FirefoxDriver();
    }

    public void navigateToGovDVLAPage(){
        driver.get("https://www.gov.uk/get-vehicle-information-from-dvla");
    }

    public void clickOnButton(String button){
        if(button.equalsIgnoreCase("Start now")){
            driver.findElement(startNowButton).click();
        }else if(button.equalsIgnoreCase("Continue")){
            driver.findElement(continueButton).click();
        }
    }

    public boolean verifyPageHeading(){
        String headText ="Enter the registration number of the vehicle" ;
     return  driver.findElement(regNumberInputPageHeading).getText().contains(headText);
    }

    public void inputRegistrationNumber(String regNumber){
        driver.findElement(regNumberInput).sendKeys(regNumber);
    }

    public boolean verifyNumberPlateConfirmation(String regNumberPlate){
       return driver.findElement(regNumberConfirmation).getText().equalsIgnoreCase(regNumberPlate);
    }

    public void clickRadioButton() {
        driver.findElement(yesRadioBtn).click();
    }
}
