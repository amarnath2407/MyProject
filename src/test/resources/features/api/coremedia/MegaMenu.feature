@cmapi
Feature: Mega Menu API tests

  Scenario: Get the mega menu by brand country language and country group
    Given I have brand and path parameters
    Then I get mega menu request and verify the response