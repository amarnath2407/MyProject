@regression
Feature: (WCSCMS-229/INT 141) This is to search the physical stores with latitude and Longitude for a brand

  Scenario: (WCSCMS-233/INT 141) As a customer I should be able to search for physical stores with store identifier latitude and longitude
    Given I have the store id "store.identifiers" to search for physical store with latitude and longitude
    Then I call the service Physical Store Data by latitude and longitude and compare the details against the database

  Scenario: (WCSCMS-233/INT 141) As a customer I should be able to search for physical stores with store identifier latitude and longitude with query params locale and langId
    Given I have the store id "store.identifiers" to search for physical store with latitude and longitude and query parameters locale "stloc.locales" and langId "stloc.languages.id"
    Then I call the service Physical Store Data by latitude and longitude with query parameters locale and langId and compare the results against the database

    @smoke
  Scenario Outline: (WCSCMS-233/INT 141) As a customer I should be able to search for physical stores with store identifier latitude,longitude and query params locale,radius and radiusUom
    Given I have the store id "store.identifiers" to search for physical store with latitude and longitude and locale "stloc.locales"
    Then I call the service Physical Store Data by latitude and longitude with query params radius "<radius>" and radiusUom "<radiusUom>" then compare the details against the database
    Examples:
    | radius         | radiusUom        |
    | stloc.radius1  | stloc.radiusUom1 |
    | stloc.radius2  | stloc.radiusUom2 |

  Scenario Outline: (WCSCMS-233/INT 141) As a customer I should be able to search for physical stores with store identifier latitude and longitude and query params radius,radiusUom and attributes
    Given I have the store id "store.identifiers" to search for physical store with latitude and longitude
    Then I call the service Physical Store Data by latitude and longitude with query params radius "<radius>",radiusUom "<radiusUom>" and attributes then compare the details against the database
  Examples:
  | radius         | radiusUom        |
  | stloc.radius1  | stloc.radiusUom1 |
  | stloc.radius2  | stloc.radiusUom2 |

  Scenario Outline: (WCSCMS-233/INT 141) As a customer I should be able to search for physical stores with store identifier latitude and longitude and query params radius,radiusUom,limit and includeactive
    Given I have the store id "store.identifiers" to search for physical store with latitude and longitude
    Then I call the service Physical Store Data by latitude and longitude with query params radius "<radius>",radiusUom "<radiusUom>","<limit>","<offset>" and "<includeInactive>" and attributes then compare the details against the database
    Examples:
      | radius         | radiusUom        | limit              | offset              | includeInactive |
      | stloc.radius1  | stloc.radiusUom1 | stloc.limitStores1 | stloc.offsetStores1 | true            |
      | stloc.radius2  | stloc.radiusUom2 | stloc.limitStores2 | stloc.offsetStores2 | false           |