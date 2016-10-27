@regression
Feature:( WCSCMS-276/INT_141 ) This is to test the store locator Location data by Region level

  @smoke
Scenario:( WCSCMS-276/INT_141 )  As a Customer I should be able to get all regions with countries and cities for any specific Store
  Given I have the store identifiers "store.identifiers"
  Then I call the service Location Data by Region-level and compare the region details returned for the specific store should be verified with the database

  Scenario:( WCSCMS-276/INT_141 )  As a Customer I should be able to get all regions with countries and cities for any specific Store with query params locale and langId
    Given I have the store identifiers "store.identifiers" with query params locale "stloc.locales" and langId "stloc.languages.id"
    Then I call the service Location Data by Region-level with query params locale and langId and compare the region details returned for the specific store should be verified with the database

Scenario:( WCSCMS-276/INT_141 )As a Customer I should be able to get all countries with cities for any specific Store which has different attributes
  Given I have the store identifiers "store.identifiers" with different attributes "stloc.country.attributes" and attribute values "stloc.country.attributes.values" and locale "stloc.locales"
  Then I call the service Location Data by Region-level with attributes and compare the region details  with countries and cities returned for the specific store should be verified with the database

Scenario:( WCSCMS-276/INT_141 ) As a Customer I should be able to get all countries with cities on a region for any specific Store
  Given I have the store identifiers "store.identifiers" with region code "region.code"
  Then I call the service Location Data by Region-level with region code and compare the country details returned for the specific store withing the region should be verified with the database

Scenario:( WCSCMS-276/INT_141 )As a Customer I should be able to get all countries with cities for any specific Store within the country which has different attributes
  Given I have the store identifiers "store.identifiers" with region Code "region.code" different attributes "stloc.country.attributes" attribute values "stloc.country.attributes.values" and locales "stloc.locales"
  Then I call the service Location Data by Region-level with region code with attributes and compare the region details  with countries and cities returned for the specific store should be verified with the database
