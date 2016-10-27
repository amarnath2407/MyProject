package com.salmon.test.page_objects.gui;

import com.salmon.test.framework.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.salmon.test.framework.helpers.UrlBuilder.readValueFromConfig;

/**
 * Created by akepativ on 04/08/2016.
 */
public class LoginPage extends PageObject {


    private By userNameText = By.xpath("//input[@type='text']");
    private By passwordText = By.xpath("//input[@type='password']");
    private By logInButton = By.xpath("//div[text()='Log in']");

    public void enterLoginPageUrl(){
        getWebDriver().get(readValueFromConfig("management.center.url"));
    }

    public WebElement getUserNameElement(){
        return waitForExpectedElement(userNameText);
    }
    public WebElement getPasswordElement(){
        return getWebDriver().findElement(passwordText);
    }
    public WebElement getLoginButtonElement(){
        return getWebDriver().findElement(logInButton);
    }
}
