@gui
Feature: (WCSCMS-322) As a Business user, I should be able to select the admin function I wish to work on
  in order to manage my store data

  Background:I should be able to land on Home page with admin user details
    Given I am on Login Page
    When I enter userName as "wcsadmin" , password as "wcsadmin.password" and click on Login
    Then Home page should be displayed

  Scenario:(WCSCMS-322) As a Business user, I should have the Store Locator tab created in Management center
    Given I have the Store Locator Menu Item in the Management Center Tools list
    When I click on Store Locator menu item
    Then Store Locator tab should be opened with a drop down to select brand
    #And

  Scenario Outline:(WCSCMS-322) As a Business user, I should be able to check the list of stores for particular brand
    Given I have selected a brand "<brandName>" with brand id "<storeId>"
    When I click on Stores link
    Then all physical stores related to the selected brand should be displayed in a list view
    And compare the list of stores data with the api returned data
    Examples:
      | brandName | storeId   |
      | Aurora_UK | store1.id |