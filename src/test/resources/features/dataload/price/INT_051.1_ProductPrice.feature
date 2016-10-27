@dataload
Feature: Load SKU prices and check price roll-up to Product and ProductColour


  Scenario:1(WCSCMS-551)-Process product prices from PIM to WCS for a single SKU and check the roll-up
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin          | brandChannel | Country | Currency | Timestamp                     |
      | 0412345678901 | Moncler      | GB      | GBP      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Then Prices are correctly loaded to the database for the SKU
    And prices are correctly rolled up to the Product and ProductColour

  Scenario:2(WCSCMS-551)-Process product prices from PIM to WCS for a 2 SKU's with the same price and check the roll-up
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345611             | MF-12345611 |            |               |              |                 | 0      | 12345611 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567811 | MF-1234567811 |              |                 | 0      | 12345611 | 1234567811 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789002 | MF-123456789002 | 0      | 12345611 | 1234567811 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789003 | MF-123456789003 | 0      | 12345611 | 1234567811 |
    When I process the products PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin          | brandChannel | Country | Currency | Timestamp                     |
      | 0412345678902 | Moncler      | FR      | EUR      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin          | brandChannel | Country | Currency | Timestamp                     |
      | 0412345678903 | Moncler      | FR      | EUR      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Then Prices are correctly loaded to the database for the SKU
    And prices are correctly rolled up to the Product and ProductColour

  Scenario:3(WCSCMS-551)-Process product prices from PIM to WCS for a 2 SKU's with different prices and check the roll-up
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345612             | MF-12345612 |            |               |              |                 | 0      | 12345612 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567812 | MF-1234567812 |              |                 | 0      | 12345612 | 1234567812 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789004 | MF-123456789004 | 0      | 12345612 | 1234567812 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789005 | MF-123456789005 | 0      | 12345612 | 1234567812 |
    When I process the products PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin          | brandChannel | Country | Currency | Timestamp                     |
      | 0412345678904 | Moncler      | US      | USD      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 90        | 90        | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin          | brandChannel | Country | Currency | Timestamp                     |
      | 0412345678905 | Moncler      | US      | USD      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Then Prices are correctly loaded to the database for the SKU
    And prices are correctly rolled up to the Product and ProductColour


  Scenario:4(WCSCMS-551)-Process product prices for a 2 ProductColour's and 2 SKU's with the same prices and check the roll-up
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345613             | MF-12345613 |            |               |              |                 | 0      | 12345613 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567813 | MF-1234567813 |              |                 | 0      | 12345613 | 1234567813 |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567814 | MF-1234567814 |              |                 | 0      | 12345613 | 1234567814 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789006 | MF-123456789006 | 0      | 12345613 | 1234567813 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789007 | MF-123456789007 | 0      | 12345613 | 1234567814 |
    When I process the products PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin          | brandChannel | Country | Currency | Timestamp                     |
      | 0412345678906 | Moncler      | IT      | EUR      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin          | brandChannel | Country | Currency | Timestamp                     |
      | 0412345678907 | Moncler      | IT      | EUR      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Then Prices are correctly loaded to the database for the SKU
    And prices are correctly rolled up to the Product and ProductColour

  Scenario:5(WCSCMS-551)-Process product prices for a 2 ProductColour's and 2 SKU's with different prices and check the roll-up
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345614             | MF-12345614 |            |               |              |                 | 0      | 12345614 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567815 | MF-1234567815 |              |                 | 0      | 12345614 | 1234567815 |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567816 | MF-1234567816 |              |                 | 0      | 12345614 | 1234567816 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789008 | MF-123456789008 | 0      | 12345614 | 1234567815 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789009 | MF-123456789009 | 0      | 12345614 | 1234567816 |
    When I process the products PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin         | brandChannel | Country | Currency | Timestamp                     |
      | 123456789008 | Moncler      | CN      | CNY      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 90        | 90        | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin         | brandChannel | Country | Currency | Timestamp                     |
      | 123456789009 | Moncler      | CN      | CNY      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Then Prices are correctly loaded to the database for the SKU
    And prices are correctly rolled up to the Product and ProductColour

  Scenario:6(WCSCMS-551)-Process product prices for 1 country with different currencies
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345615             | MF-12345615 |            |               |              |                 | 0      | 12345615 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567817 | MF-1234567817 |              |                 | 0      | 12345615 | 1234567817 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789010 | MF-123456789010 | 0      | 12345615 | 1234567817 |
    When I process the products PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin         | brandChannel | Country | Currency | Timestamp                     |
      | 123456789010 | Moncler      | GB      | EUR      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Given provided list of prices for product
      | fullPrice | salePrice | validFrom                     | validTo | markdownCode | markdownPercentage |
      | 100       | 100       | 2016-07-30T00:00:00.000+02:00 |         |              |                    |
    And I create Prices Json file
      | gtin         | brandChannel | Country | Currency | Timestamp                     |
      | 123456789010 | Moncler      | GB      | GBP      | 2016-06-28T10:15:58.567+02:00 |
    When I process the prices PIM files from Talend to WCS
    Then Prices are correctly loaded to the database for the SKU
    And prices are correctly rolled up to the Product and ProductColour
