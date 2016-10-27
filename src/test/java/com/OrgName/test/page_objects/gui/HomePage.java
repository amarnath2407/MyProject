package com.salmon.test.page_objects.gui;

import com.salmon.test.framework.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Page object class which defines all the elements for a specific page.
 * Every  Class which contains page objects should extend "PageObject" class
 * This gives access to the webdriver object and utility methods
 * USE CSS,ID,NAME,CLASSNAME selectors instead of xpath
 */
public class HomePage extends PageObject {

    private By headerSignInLink = By.cssSelector("#headerSignInLink a");
    private By headerLogoutLink = By.id("headerLogout");
    private By toolsElement = By.xpath(".//div[contains(@modulename,'BannerArea::_$0_$1')]");
    private By storeLocatorMenuItem = By.xpath("//div[@widgetid='dijit__WidgetBase_110']");
    private By storeLocatorTab = By.xpath("//div[contains(@modulename,'ToolTab::_$0_$1_$1')]");
    private By closeButtonFirstTab = By.xpath("//div[contains(@modulename,'ToolTab::_$0_$1_$3')]");
    private By storeDropdown = By.cssSelector("#customtool #storeSel");
    private By storeDropdownList = By.xpath("//div[@modulename = 'cmc/foundation/ListItem']");
    private By menuList = By.xpath("//div[@modulename = 'cmc/foundation/Tree::_$0_$1_$3']");
    private By storeListRow = By.xpath("//div[@modulename = 'cmc/foundation/GridRow']");
    private By storeListColumn = By.xpath("//div[@modulename = 'cmc/foundation/GridTextViewer']");

    public void clickSignInLink() {
        waitForExpectedElement(headerSignInLink).click();
    }

    public void clickSignOutLink() {
        waitForExpectedElement(headerLogoutLink).click();
    }

    public WebElement getToolsElement(){
        return waitForExpectedElement(toolsElement);
    }

    public WebElement getStoreLocatorMenuItem(){
        return waitForExpectedElement(storeLocatorMenuItem);
    }

    public WebElement getStoreLocatorTab(){
       return waitForExpectedElement(storeLocatorTab,30);
    }

    public WebElement getPhysicalStoresDropDown(){

        return waitForExpectedElement(storeDropdown);
    }

    public WebElement getCloseButtonFirstTab(){

        return waitForExpectedElement(closeButtonFirstTab);
    }

    public List<WebElement> getPhysicalStoresDropDownList(){

        return getWebDriver().findElements(storeDropdownList);
    }

    public void selectBrand(String brandName){
        List<WebElement> storesList = this.getPhysicalStoresDropDownList();
        for(WebElement storeElement:storesList){
            if(storeElement.getText().equals(brandName)){
                storeElement.click();
                break;
            }
        }
    }

    public List<WebElement> getMenuList(){
        return getWebDriver().findElements(menuList);
    }

    public List<WebElement> getStoreRowList(){
        return getWebDriver().findElements(storeListRow);
    }

    public List<WebElement> getStoreColumnList(){
        return getWebDriver().findElements(storeListColumn);
    }

    public void storeLocatorTabClicked(){
        this.getToolsElement().click();
        this.getStoreLocatorMenuItem().click();
    }

}
