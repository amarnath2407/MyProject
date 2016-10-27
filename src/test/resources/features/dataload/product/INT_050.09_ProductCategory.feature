@dataload
Feature: This is a feature for INT_050.09_Product Category PIM Dataload.

  Background:
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | Product       | manufacturer | 1        | 13579246             | MF-13579246 |            |               |              |                 | 0      | 13579246 |            |
      | catalogIdentifier | ProductColour | manufacturer | 1        |                      |             |            | MF-1357924690 |              |                 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469001 | MF-135792469001 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469002 | MF-135792469002 | 0      | 13579246 | 1357924690 |
    And I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 9605               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |

  Scenario:1-(WCSCMS-65) Successful Product Category dataload for a 'Product' for a given category
    And I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId |
      |      | catalogIdentifier | 9        | 0      | 7050                  | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation for Product Category
      | partNumber | partNumberParentStoreIdentifier | sequence | delete | parentGroupIdentifier | parentStoreIdentifier |
      | 12345678   | catalogIdentifier               | 9        | 0      | 7050                  | catalogIdentifier     |
    And The product category data is loaded into CATGPENREL table successfully
      | partNumber | parentGroupIdentifier | parentStoreIdentifier | sequence |
      | 12345678   | 7050                  | catalogIdentifier     | 9        |

  Scenario:2-(WCSCMS-65) Successful Product Category dataload for a 'ProductColour' for a given category
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId | variantId  |
      |      | catalogIdentifier | 9        | 0      | 7050                  | ProductColour |         | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation for Product Category
      | partNumber | partNumberParentStoreIdentifier | sequence | delete | parentGroupIdentifier | parentStoreIdentifier |
      | 1234567890 | catalogIdentifier               | 9        | 0      | 7050                  | catalogIdentifier     |
    And The product category data is loaded into CATGPENREL table successfully
      | partNumber | parentGroupIdentifier | parentStoreIdentifier | sequence |
      | 1234567890 | 7050                  | catalogIdentifier     | 9        |

  Scenario:3-(WCSCMS-65) Successful Product Category dataload for a 'SKU' for a given category
    Given I create Product Category PIM file
      | gtin         | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId | variantId |
      | 123456789001 | catalogIdentifier | 9        | 0      | 7050                  | SKU         |         |           |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation for Product Category
      | partNumber   | partNumberParentStoreIdentifier | sequence | delete | parentGroupIdentifier | parentStoreIdentifier |
      | 123456789001 | catalogIdentifier               | 9        | 0      | 7050                  | catalogIdentifier     |
    And The product category data is loaded into CATGPENREL table successfully
      | partNumber   | parentGroupIdentifier | parentStoreIdentifier | sequence |
      | 123456789001 | 7050                  | catalogIdentifier     | 9        |

  Scenario:4-(WCSCMS-65) Successful Product Category dataload to remove a 'Product' from a given category
    And I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId |
      |      | catalogIdentifier | 9        | 0      | 7050                  | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | Product       | manufacturer | 1        | 13579246             | MF-13579246 |            |               |              |                 | 0      | 13579246 |            |
      | catalogIdentifier | ProductColour | manufacturer | 1        |                      |             |            | MF-1357924690 |              |                 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469001 | MF-135792469001 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469002 | MF-135792469002 | 0      | 13579246 | 1357924690 |
    And I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 9605               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |
    And I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId |
      |      | catalogIdentifier | 9        | 1      | 7050                  | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then The product category data is Deleted from CATGPENREL table successfully
      | partNumber | parentGroupIdentifier |
      | 12345678   | 7050                  |

  Scenario:5-(WCSCMS-65) Successful Product Category dataload to remove a 'ProductColour' from a given category
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId | variantId  |
      |      | catalogIdentifier | 9        | 0      | 7050                  | ProductColour |         | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | Product       | manufacturer | 1        | 13579246             | MF-13579246 |            |               |              |                 | 0      | 13579246 |            |
      | catalogIdentifier | ProductColour | manufacturer | 1        |                      |             |            | MF-1357924690 |              |                 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469001 | MF-135792469001 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469002 | MF-135792469002 | 0      | 13579246 | 1357924690 |
    And I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 9605               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId | variantId  |
      |      | catalogIdentifier | 9        | 1      | 7050                  | ProductColour |         | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then The product category data is Deleted from CATGPENREL table successfully
      | partNumber | parentGroupIdentifier |
      | 1234567890 | 7050                  |

  Scenario:6-(WCSCMS-65) Successful Product Category dataload to remove a 'SKU' from a given category
    Given I create Product Category PIM file
      | gtin         | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId | variantId |
      | 123456789001 | catalogIdentifier | 9        | 0      | 7050                  | SKU         |         |           |
    When I process the products PIM files from Talend to WCS
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | Product       | manufacturer | 1        | 13579246             | MF-13579246 |            |               |              |                 | 0      | 13579246 |            |
      | catalogIdentifier | ProductColour | manufacturer | 1        |                      |             |            | MF-1357924690 |              |                 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469001 | MF-135792469001 | 0      | 13579246 | 1357924690 |
      | catalogIdentifier | SKU           | manufacturer | 1        |                      |             |            |               | 135792469002 | MF-135792469002 | 0      | 13579246 | 1357924690 |
    And I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 9605               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |
    Given I create Product Category PIM file
      | gtin         | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId | variantId |
      | 123456789001 | catalogIdentifier | 9        | 1      | 7050                  | SKU         |         |           |
    When I process the products PIM files from Talend to WCS
    Then The product category data is Deleted from CATGPENREL table successfully
      | partNumber   | parentGroupIdentifier |
      | 123456789001 | 7050                  |

  Scenario:7-(WCSCMS-65) Successful Product Category dataload for a full Product set (Product, ProductColour & SKU) for a given category
    Given I create Product Category PIM file
      | gtin         | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId  | variantId  |
      |              | catalogIdentifier | 15       | 0      | 7050                  | Product       | 13579246 |            |
      |              | catalogIdentifier | 16       | 0      | 7050                  | ProductColour |          | 1357924690 |
      | 135792469001 | catalogIdentifier | 17       | 0      | 7050                  | SKU           |          |            |
      | 135792469002 | catalogIdentifier | 18       | 0      | 7050                  | SKU           |          |            |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation for Product Category
      | partNumber   | partNumberParentStoreIdentifier | sequence | delete | parentGroupIdentifier | parentStoreIdentifier |
      | 13579246     | catalogIdentifier               | 15       | 0      | 7050                  | catalogIdentifier     |
      | 1357924690   | catalogIdentifier               | 16       | 0      | 7050                  | catalogIdentifier     |
      | 135792469001 | catalogIdentifier               | 17       | 0      | 7050                  | catalogIdentifier     |
      | 135792469002 | catalogIdentifier               | 18       | 0      | 7050                  | catalogIdentifier     |
    And The product category data is loaded into CATGPENREL table successfully
      | partNumber   | parentGroupIdentifier | parentStoreIdentifier | sequence |
      | 13579246     | 7050                  | catalogIdentifier     | 15       |
      | 1357924690   | 7050                  | catalogIdentifier     | 16       |
      | 135792469001 | 7050                  | catalogIdentifier     | 17       |
      | 135792469002 | 7050                  | catalogIdentifier     | 18       |

  Scenario:8-(WCSCMS-65) Combination of removing a product set (Product,ProductColour & SKU) from one category and adding to a new category
    Given I create Product Category PIM file
      | gtin         | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId  | variantId  |
      |              | catalogIdentifier | 15       | 1      | 7050                  | Product       | 13579246 |            |
      |              | catalogIdentifier | 16       | 1      | 7050                  | ProductColour |          | 1357924690 |
      | 135792469001 | catalogIdentifier | 17       | 1      | 7050                  | SKU           |          |            |
      | 135792469002 | catalogIdentifier | 18       | 1      | 7050                  | SKU           |          |            |
      |              | catalogIdentifier | 28       | 0      | 9605                  | Product       | 13579246 |            |
      |              | catalogIdentifier | 29       | 0      | 9605                  | ProductColour |          | 1357924690 |
      | 135792469001 | catalogIdentifier | 30       | 0      | 9605                  | SKU           |          |            |
      | 135792469002 | catalogIdentifier | 31       | 0      | 9605                  | SKU           |          |            |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation for Product Category
      | partNumber   | partNumberParentStoreIdentifier | sequence | delete | parentGroupIdentifier | parentStoreIdentifier |
      | 13579246     | catalogIdentifier               | 15       | 1      | 7050                  | catalogIdentifier     |
      | 1357924690   | catalogIdentifier               | 16       | 1      | 7050                  | catalogIdentifier     |
      | 135792469001 | catalogIdentifier               | 17       | 1      | 7050                  | catalogIdentifier     |
      | 135792469002 | catalogIdentifier               | 18       | 1      | 7050                  | catalogIdentifier     |
      | 13579246     | catalogIdentifier               | 28       | 0      | 9605                  | catalogIdentifier     |
      | 1357924690   | catalogIdentifier               | 29       | 0      | 9605                  | catalogIdentifier     |
      | 135792469001 | catalogIdentifier               | 30       | 0      | 9605                  | catalogIdentifier     |
      | 135792469002 | catalogIdentifier               | 31       | 0      | 9605                  | catalogIdentifier     |
    And The product category data is loaded into CATGPENREL table successfully
      | partNumber   | parentGroupIdentifier | parentStoreIdentifier | sequence |
      | 13579246     | 9605                  | catalogIdentifier     | 28       |
      | 1357924690   | 9605                  | catalogIdentifier     | 29       |
      | 135792469001 | 9605                  | catalogIdentifier     | 30       |
      | 135792469002 | 9605                  | catalogIdentifier     | 31       |
    Then The product category data is Deleted from CATGPENREL table successfully
      | partNumber   | parentGroupIdentifier |
      | 13579246     | 7050                  |
      | 1357924690   | 7050                  |
      | 135792469001 | 7050                  |
      | 135792469002 | 7050                  |

#  Scenario:9-(WCSCMS-65 - BR1) delete a product from a deleted category
#    Given I create Product Category PIM file
#      | gtin         | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId  | variantId  |
#      |              | catalogIdentifier | 15       | 1      | 9999                  | Product       | 13579246 |            |
#      |              | catalogIdentifier | 16       | 1      | 9999                  | ProductColour |          | 1357924690 |
#      | 135792469001 | catalogIdentifier | 17       | 1      | 9999                  | SKU           |          |            |
#    When I process the products PIM files from Talend to WCS
#    Then Verify ProductCategoryPIM files into failure folder
#
#  Scenario:10-(WCSCMS-65 - BR2-1) Category does not exist
#    Given I create Product Category PIM file
#      | gtin         | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId  | variantId  |
#      |              | catalogIdentifier | 15       | 0      | 9999                  | Product       | 13579246 |            |
#      |              | catalogIdentifier | 16       | 0      | 9999                  | ProductColour |          | 1357924690 |
#      | 135792469001 | catalogIdentifier | 17       | 0      | 9999                  | SKU           |          |            |
#    When I process the products PIM files from Talend to WCS
#    Then Verify ProductCategoryPIM files into failure folder

  Scenario:11-(WCSCMS-65 - BR2-2) Product does not exist
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId |
      |      | catalogIdentifier | 15       | 0      | 7050                  | Product     | 99999999 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:12-(WCSCMS-258) Successful failure when mandatory fields missing values (catalogIdentifier, sequence, delete, parentGroupIdentifier, productType)
#    Given I create Product Category PIM file
#      | gtin   | catalogIdentifier   | sequence   | delete   | parentGroupIdentifier   | productType   | modelId   | variantId   |
#      | <gtin> | <catalogIdentifier> | <sequence> | <delete> | <parentGroupIdentifier> | <productType> | <modelId> | <variantId> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify ProductCategoryPIM files into failure folder
#    Examples:
#      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId |
#      |      |                   | 15       | 0      | 7050                  | Product     | 99999999 |           |
#      |      | catalogIdentifier |          | 0      | 7050                  | Product     | 99999999 |           |
#      |      | catalogIdentifier | 15       |        | 7050                  | Product     | 99999999 |           |
#      |      | catalogIdentifier | 15       | 0      |                       | Product     | 99999999 |           |
#      |      | catalogIdentifier | 15       | 0      | 7050                  |             | 99999999 |           |
#
#  Scenario Outline:13-(WCSCMS-258) Successful failure when mandatory fields missing values (GTIN when Type=SKU, VARIANTID when Type=ProductColour, MODELID when Type=Product)
#    Given I create Product Category PIM file
#      | gtin   | catalogIdentifier   | sequence   | delete   | parentGroupIdentifier   | productType   | modelId   | variantId   |
#      | <gtin> | <catalogIdentifier> | <sequence> | <delete> | <parentGroupIdentifier> | <productType> | <modelId> | <variantId> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify ProductCategoryPIM files into failure folder
#    Examples:
#      | gtin     | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType   | modelId  | variantId |
#      | 99999999 | catalogIdentifier | 15       | 0      | 7050                  | Product       |          | 99999999  |
#      | 99999999 | catalogIdentifier | 15       | 0      | 7050                  | ProductColour | 99999999 |           |
#      |          | catalogIdentifier | 15       | 0      | 7050                  | SKU           | 99999999 | 99999999  |

  Scenario:14-(WCSCMS-258) Process Product Category PIM file missing a GTIN field
    Given I create Product Category PIM file
      | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId |
      | catalogIdentifier | 1        | 0      | 9605                  | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:15-(WCSCMS-258) Process Product Category PIM file missing a catalogIdentifier field
    Given I create Product Category PIM file
      | gtin | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId |
      |      | 1        | 0      | 9605                  | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:16-(WCSCMS-258) Process Product Category PIM file missing a Sequence field
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | delete | parentGroupIdentifier | productType | modelId  | variantId |
      |      | catalogIdentifier | 0      | 9605                  | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:17-(WCSCMS-258) Process Product Category PIM file missing a Delete field
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | parentGroupIdentifier | productType | modelId  | variantId |
      |      | catalogIdentifier | 1        | 9605                  | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:18-(WCSCMS-258) Process Product Category PIM file missing a ParentGroupIdentifier field
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | productType | modelId  | variantId |
      |      | catalogIdentifier | 1        | 0      | Product     | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:19-(WCSCMS-258) Process Product Category PIM file missing a ProductType field
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | modelId  | variantId |
      |      | catalogIdentifier | 1        | 0      | 9605                  | 12345678 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:20-(WCSCMS-258) Process Product Category PIM file missing a MODELID field
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | variantId |
      |      | catalogIdentifier | 1        | 0      | 9605                  | Product     |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:21-(WCSCMS-258) Process Product Category PIM file missing a VARIANTID field
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  |
      |      | catalogIdentifier | 1        | 0      | 9605                  | Product     | 12345678 |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder

  Scenario:22-(WCSCMS-258) Process Product Category PIM file With an extra field
    Given I create Product Category PIM file
      | gtin | catalogIdentifier | sequence | delete | parentGroupIdentifier | productType | modelId  | variantId | extraField |
      |      | catalogIdentifier | 1        | 0      | 9605                  | Product     | 12345678 |           | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductCategoryPIM files into failure folder
