@dataload
Feature: Load Product data PIM files from Talend, Insert and Delete data into WCS

  Scenario:1-(WCSCMS-261)Process Product PIM file missing a CatalogIdentifier field
    Given I create Product PIM file
      | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:2-(WCSCMS-261)Process Product PIM file missing a productType field
    Given I create Product PIM file
      | catalogIdentifier | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:3-(WCSCMS-261)Process Product PIM file missing a manufacturer field
    Given I create Product PIM file
      | catalogIdentifier | productType | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:4-(WCSCMS-261)Process Product PIM file missing a sequence field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:5-(WCSCMS-261)Process Product PIM file missing a modelFabricBrandCode field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:6-(WCSCMS-261)Process Product PIM file missing a yooxCode8 field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:7-(WCSCMS-261)Process Product PIM file missing a NAPPID field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:8-(WCSCMS-261)Process Product PIM file missing a YooxCode10 field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:9-(WCSCMS-261)Process Product PIM file missing a GTIN field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:10-(WCSCMS-261)Process Product PIM file missing a EAN field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |      | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:11-(WCSCMS-261)Process Product PIM file missing a Delete field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:12-(WCSCMS-261)Process Product PIM file missing a MODELID field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | variantId |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 0      |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:13-(WCSCMS-261)Process Product PIM file missing a VARIANTID field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:14-(WCSCMS-261)Process Product PIM file With an extra field
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId | ExtraField |
      | catalogIdentifier | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:15-(WCSCMS-261)Product schema validation tests
#    Given I create Product PIM file
#      | catalogIdentifier   | productType   | manufacturer   | sequence   | modelFabricBrandCode   | yooxCode8   | nappID   | yooxCode10   | gtin   | ean   | delete   | modelId   | variantId   |
#      | <catalogIdentifier> | <productType> | <manufacturer> | <sequence> | <modelFabricBrandCode> | <yooxCode8> | <nappID> | <yooxCode10> | <gtin> | <ean> | <delete> | <modelId> | <variantId> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify ProductPIM files into failure folder
#    Examples:
#      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
#      |                   | Product     | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
#      | catalogIdentifier |             | manufacturer | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
#      | catalogIdentifier | Product     |              | l        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
#      | catalogIdentifier | Product     | manufacturer |          | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
#      | catalogIdentifier | Product     | manufacturer | 1        |                      | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
#      | catalogIdentifier | Product     | manufacturer | 1        | 12345678             | MF-12345678 |        |            |      |     |        | 12345678 |           |
#      | catalogIdentifier |             | manufacturer | 1        | 12345678             | MF-12345678 |        |            |      |     | 0      |          |           |

  Scenario:16-(WCSCMS-261)Create and Process Product PIM file with comma delimiter
    Given I create Product PIM file with comma delimiter
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 1        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:17-(WCSCMS-261)Create and Process Product PIM file with header row wrong order
    Given I create Product PIM file with header row wrong order
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID | yooxCode10 | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 1        | 12345678             | MF-12345678 |        |            |      |     | 0      | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:18-(WCSCMS-67)Product data inserted and Check into Database
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then verify WCS Product file has the correct format:
      | parentStoreIdentifier | partNumber   | parentPartNumber | parentColourPartNumber | type        | sequence | manufacturerPartNumber | manufacturer | buyable | startDate | endDate | field1 | field2 | field3 | field4     | delete |
      | parentStoreIdentifier | 12345678     |                  |                        | ProductBean | 1        | MF-12345678            | manufacturer | 1       |           |         | 0      |        |        | 12345678   | 0      |
      | parentStoreIdentifier | 1234567890   | 12345678         |                        | ProductBean | 2        | MF-1234567890          | manufacturer | 1       |           |         | 1      |        |        | 1234567890 | 0      |
      | parentStoreIdentifier | 123456789001 | 12345678         | 1234567890             | ItemBean    | 3        | MF-123456789001        | manufacturer | 1       |           |         | 2      |        |        |            | 0      |
    And data is inserted into CATENTRY table:
      | partNumber   | manufacturerPartNumber | catEntTypeId | manufacturer | field1 |
      | 12345678     | MF-12345678            | ProductBean  | manufacturer | 0      |
      | 1234567890   | MF-1234567890          | ProductBean  | manufacturer | 1      |
      | 123456789001 | MF-123456789001        | ItemBean     | manufacturer | 2      |
    And data is inserted into CATENTREL table:
      | catEntryParentId | catEntryChildId | catRelTypeId          |
      | 12345678         | 1234567890      | PRODUCT_PRODUCTCOLOUR |
      | 1234567890       | 123456789001    | PRODUCTCOLOUR_ITEM    |
      | 12345678         | 123456789001    | PRODUCT_ITEM          |

   Scenario:19-(WCSCMS-67)Delete Product data and Check into Database
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 1      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 1      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 1      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then data is deleted into CATENTRY table:
      | partNumber   |
      | 12345678     |
      | 1234567890   |
      | 123456789001 |
    And data is deleted into CATENTREL table:
      | catEntryParentId | catEntryChildId | catRelTypeId          |
      | 12345678         | 1234567890      | PRODUCT_PRODUCTCOLOUR |
      | 1234567890       | 123456789001    | PRODUCTCOLOUR_ITEM    |
      | 12345678         | 123456789001    | PRODUCT_ITEM          |

  Scenario:20-(WCSCMS-67)Process Multi color and Multi SKU Product
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID    | yooxCode10  | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | Product       | manufacturer | 16       | 11001400             | MF11001400 |           |             |               |                 | 0      | 11001400 |           |
      | catalogIdentifier | ProductColour | manufacturer | 6407     |                      |            | 110014003 | MF110014003 |               |                 | 0      | 11001400 | 110014003 |
      | catalogIdentifier | ProductColour | manufacturer | 6408     |                      |            | 110014002 | MF110014002 |               |                 | 0      | 11001400 | 110014002 |
      | catalogIdentifier | SKU           | manufacturer | 17       |                      |            |           |             | 0400298690134 | MF0400298690134 | 0      | 11001400 | 110014003 |
      | catalogIdentifier | SKU           | manufacturer | 18       |                      |            |           |             | 0400406162218 | MF0400406162218 | 0      | 11001400 | 110014002 |
    When I process the products PIM files from Talend to WCS
    Then data is inserted into CATENTRY table:
      | partNumber    | manufacturerPartNumber | catEntTypeId | manufacturer | field1 |
      | 11001400      | MF11001400             | ProductBean  | manufacturer | 0      |
      | 110014003     | MF110014003            | ProductBean  | manufacturer | 1      |
      | 110014002     | MF110014002            | ProductBean  | manufacturer | 1      |
      | 0400298690134 | MF0400298690134        | ItemBean     | manufacturer | 2      |
      | 0400406162218 | MF0400406162218        | ItemBean     | manufacturer | 2      |
    And data is inserted into CATENTREL table:
      | catEntryParentId | catEntryChildId | catRelTypeId          |
      | 11001400         | 110014003       | PRODUCT_PRODUCTCOLOUR |
      | 11001400         | 110014002       | PRODUCT_PRODUCTCOLOUR |
      | 110014003        | 0400298690134   | PRODUCTCOLOUR_ITEM    |
      | 110014002        | 0400406162218   | PRODUCTCOLOUR_ITEM    |
      | 11001400         | 0400406162218   | PRODUCT_ITEM          |
      | 11001400         | 0400298690134   | PRODUCT_ITEM          |

  Scenario:21-(WCSCMS-261)BR: Parent MODELID is not included in the first Where an item SKU is a child
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID | yooxCode10 | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 16       | 11001400             | MF11001400 |        |            |               |                 | 0      | 11001400 |           |
      | catalogIdentifier | SKU         | manufacturer | 17       |                      |            |        |            | 0400298690134 | MF0400298690134 | 0      | 11001400 | 110014003 |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID | yooxCode10 | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 16       | 11001400             | MF11001400 |        |            |               |                 | 1      | 11001400 |           |
      | catalogIdentifier | SKU         | manufacturer | 17       |                      |            |        |            | 0400298690134 | MF0400298690134 | 1      | 11001400 | 110014003 |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID | yooxCode10 | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | SKU         | manufacturer | 17       |                      |            |        |            | 0400298690134 | MF0400298690134 | 0      | 11001400 | 110014003 |
      | catalogIdentifier | Product     | manufacturer | 16       | 11001400             | MF11001400 |        |            |               |                 | 0      | 11001400 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:22-(WCSCMS-261)BR: Load an item SKU without product
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID | yooxCode10 | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 16       | 11001400             | MF11001400 |        |            |               |                 | 1      | 11001400 |           |
      | catalogIdentifier | SKU         | manufacturer | 17       |                      |            |        |            | 0400298690134 | MF0400298690134 | 1      | 11001400 | 110014003 |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8 | nappID | yooxCode10 | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | SKU         | manufacturer | 17       |                      |           |        |            | 0400298690134 | MF0400298690134 | 0      | 11001400 | 110014003 |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder

  Scenario:23-(WCSCMS-261)BR: Load Product Colour without product
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID    | yooxCode10  | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | Product       | manufacturer | 16       | 11001400             | MF11001400 |           |             |      |     | 1      | 11001400 |           |
      | catalogIdentifier | ProductColour | manufacturer | 6407     |                      |            | 110014003 | MF110014003 |      |     | 1      | 11001400 | 110014003 |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8 | nappID    | yooxCode10  | gtin | ean | delete | modelId  | variantId |
      | catalogIdentifier | ProductColour | manufacturer | 6407     |                      |           | 110014003 | MF110014003 |      |     | 0      | 11001400 | 110014003 |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
