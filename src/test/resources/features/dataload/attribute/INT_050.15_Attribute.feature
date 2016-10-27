@dataload
Feature: Load Attribute INT_050.15, Attribute Values INT_050.16, Product Attribute Values INT_050.13 data PIM files from Talend, Insert and Delete data into WCS

  Background:
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |

  Scenario:1-(WCSCMS-64)Process Attribute PIM file missing a catalogIdentifier field
    Given I create Attribute PIM file
      | attributeIdentifier | language | name     | attrUsage | delete |
      | AgeRange            | en_GB    | AgeRange | 1         | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:2-(WCSCMS-64)Process Attribute PIM file missing a attributeIdentifier field
    Given I create Attribute PIM file
      | catalogIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | en_GB    | AgeRange | 1         | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:3-(WCSCMS-64)Process Attribute PIM file missing a language field
    Given I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | AgeRange | 1         | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:4-(WCSCMS-64)Process Attribute PIM file missing a name field
    Given I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | 1         | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:5-(WCSCMS-64)Process Attribute PIM file missing a attrUsage field
    Given I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:6-(WCSCMS-64)Process Attribute PIM file missing a delete field
    Given I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:7-(WCSCMS-64)Process Attribute PIM file with an extra field
    Given I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete | ExtraField |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:8-(WCSCMS-64)Process Attribute PIM schema validation tests
#    Given I create Attribute PIM file
#      | catalogIdentifier   | attributeIdentifier   | language   | name   | attrUsage   | delete   |
#      | <catalogIdentifier> | <attributeIdentifier> | <language> | <name> | <attrUsage> | <delete> |
#    When I process the PIM files from Talend to WCS
#    Then Verify AttributePIM files into failure folder
#    And Verify ProductPIM files into failure folder
#    And Verify AttributeValuesPIM files into failure folder
#    And Verify ProductAttributeValuePIM files into failure folder
#    Examples:
#      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
#      |                   | AgeRange            | en_GB    | AgeRange | 1         | 0      |
#      | catalogIdentifier |                     | en_GB    | AgeRange | 1         | 0      |
#      | catalogIdentifier | AgeRange            | en_GB    |          | 1         | 0      |
#      | catalogIdentifier | AgeRange            | en_GB    | AgeRange |           | 0      |
#      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         |        |

  Scenario:9-(WCSCMS-64)Create and Process Attribute PIM file with comma delimiter
    Given I create Attribute PIM file with comma delimiter
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:10-(WCSCMS-64)Create and Process Attribute PIM file with wrong header row order
    Given I create Attribute PIM file with wrong header row order
      | attrUsage | language | delete | catalogIdentifier | attributeIdentifier | name     |
      | 1         | en_GB    | 0      | catalogIdentifier | AgeRange            | AgeRange |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:11-(WCSCMS-64)Delete Attribute data and check into database
    Given I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
    And I process the PIM files from Talend to WCS
    When I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 1      |
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    And I process the products PIM files from Talend to WCS
    Then data is deleted into database table for Attribute
      | parentStoreIdentifier | attributeIdentifier | languageId |
      | parentStoreIdentifier | AgeRange            | 44         |



