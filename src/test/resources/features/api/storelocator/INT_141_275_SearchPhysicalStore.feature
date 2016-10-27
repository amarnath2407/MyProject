@regression
Feature: This is to search the physical stores for a brand

  Scenario: ( WCSCMS-282/INT_141 ) As a Customer I should be able to search for physical stores with store identifier
    Given I have the store identifiers "store.identifiers" to search for physical store
    Then I call the service Physical Store Data - Search and compare the store details will be returned for the stores for brand should be verified

  Scenario: ( WCSCMS-282/INT_141 ) As a Customer I should be able to search for physical stores with store identifier with query parameters locale and langId
    Given I have the store identifiers "store.identifiers" with query params locale  "stloc.locales" and langId "stloc.languages.id" to search for physical store
    Then I call the service Physical Store Data - Search with query params locale and langId and compare the store details will be returned for the stores for brand should be verified

  Scenario: ( WCSCMS-275/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameters locale and Region code
    Given I have the store identifiers "store.identifiers" with queryParams locale "stloc.locales",regionCode "region.code"  to search for physical store
    Then I call the service Physical Store Data - Search api and compare the store details returned in ascending order by name after sending different query params should be verified

  Scenario: ( WCSCMS-275/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameter Country code
    Given I have the store identifiers "store.identifiers" with queryParams countryCode "country.id"  to search for physical store
    Then I call the service Physical Store Data - Search api with country code and compare the store details returned in ascending order by name after sending different query params should be verified

  Scenario: ( WCSCMS-231/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code, Country code and city
    Given I have the store identifiers "store.identifiers" with queryParams Region Code "region.code", Country Code "country.id" and City Name "stloc.city.names" to search for physical store
    Then I call the service Physical Store Data - Search api with region,country code and city name and compare the store details returned in ascending order by name after sending different query params should be verified

  Scenario: ( WCSCMS-286/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code and attributes
    Given I have the store identifiers "store.identifiers" with queryParams Region Code "region.code", attributes "stloc.search.attributes" and attribute values "stloc.search.attributes.values" to search for physical store
    Then I call the service Physical Store Data - Search api with region,attributes and compare the store details returned in ascending order by name after sending different query params should be verified

    @smoke
  Scenario: ( WCSCMS-232/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code and attributes
    Given I have the store identifiers "store.identifiers" with queryParams attributes "stloc.search.attributes" and attribute values "stloc.search.attributes.values" to search for physical store
    Then I call the service Physical Store Data - Search api with attributes and compare the store details returned in ascending order by name after sending different query params should be verified

  Scenario: ( WCSCMS-232/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameters country code, city name and attributes
    Given I have the store identifiers "store.identifiers" with queryParams Country Code "country.id" and City Name "stloc.city.names" , attributes "stloc.search.attributes" and attribute values "stloc.search.attributes.values" to search for physical store
    Then I call the service Physical Store Data - Search api with country code, city and attributes and compare the store details returned in ascending order by name after sending different query params should be verified

    @smoke
  Scenario Outline: ( WCSCMS-275/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameters Region code ,limit and include inactive
    Given I have the store identifiers "<storeIdentifiers>" with queryParams regionCode "<regionCode>", limit "<limit>" and includeInactive "<includeInactive>" and  to search for physical store
    Then I call the service Physical Store Data - Search api with region code , limit and include inactive attributes and compare the store details returned in ascending order by name after sending different query params should be verified
    Examples:
      | storeIdentifiers  | regionCode  | limit               | includeInactive |
      | store.identifiers | region.code | stloc.limitStores1  | true            |
      | store.identifiers | region.code | stloc.limitStores2  | false           |

  Scenario Outline: ( WCSCMS-275/INT_141 )As a Customer I should be able to search for physical stores with store identifier and different query parameters offset ,limit and include inactive
    Given I have the store identifiers "<storeIdentifiers>" with queryParams Offset "<offset>", limit "<limit>" and includeInactive "<includeInactive>" and  to search for physical store
    Then I call the service Physical Store Data - Search api with offset , limit and include inactive attributes and compare the store details returned in ascending order by name after sending different query params should be verified
    Examples:
      | storeIdentifiers  | offset              | limit              | includeInactive |
      | store.identifiers | stloc.offsetStores1 | stloc.limitStores1 | true            |
      | store.identifiers | stloc.offsetStores2 | stloc.limitStores2 | false           |