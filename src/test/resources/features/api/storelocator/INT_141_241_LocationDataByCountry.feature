@regression
Feature:( WCSCMS-241/INT_141 ) This is to test the store locator Location data by Country level

  @smoke
  Scenario: ( WCSCMS-241/INT_141 ) As a Customer I should be able to get all countries with cities for any specific Store
    Given I have the store identifiers "store.identifiers"
    Then I call the service Location Data by Country-level and compare the country details returned for the specific store should be verified with the database

  Scenario: ( WCSCMS-241/INT_141 ) As a Customer I should be able to get all countries with cities for any specific Store with query parameters locale and langId
    Given I have the store identifiers "store.identifiers" with query parameters locale "stloc.locales" and langId "stloc.languages.id"
    Then I call the service Location Data by Country-level with query parameters locale and langId and compare the country details returned for the specific store should be verified with the database

  Scenario: ( WCSCMS-241/INT_141 ) As a Customer I should be able to get all countries with cities for any specific Store which has different attributes
    Given I have the store identifiers "store.identifiers" with different attributes "stloc.country.attributes" and attribute values "stloc.country.attributes.values" and locale "stloc.locales"
    Then I call the service Location Data by Country-level and compare the country details returned for the specific store with the attributes should be verified

  Scenario: ( WCSCMS-241/INT_141 ) As a Customer I should be able to get all cities in a country for a specific Store
    Given  I have the store identifiers "store.identifiers" with "country.id"
    Then I call the service Location Data by Country-level with country code and compare the country details returned for the specific store withing the country should be verified with the database

  Scenario: ( WCSCMS-241/INT_141 ) As a Customer I should be able to get all countries with cities for any specific Store within the country which has different attributes
    Given I have the store identifiers "store.identifiers" with "country.id" different attributes "stloc.country.attributes" attribute values "stloc.country.attributes.values" and locales "stloc.locales"
    Then I call the service Location Data by Country-level with country code and compare the country details returned for the specific store withing the country with the attributes should be verified
