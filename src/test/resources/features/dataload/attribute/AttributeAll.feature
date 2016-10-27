@dataload
Feature: Load Attribute INT_050.15, Attribute Values INT_050.16, Product Attribute Values INT_050.13 data PIM files from Talend, Insert and Delete data into WCS

  Scenario:1-(WCSCMS-64)Attribute data inserted and Check into Database
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
    And I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
    When I process the products PIM files from Talend to WCS
    Then verify WCS Attribute file has the correct format
      | parentStoreIdentifier | attributeIdentifier | field1 | field2 | field3 | languageId | name     | description | description2 | attributeValueField1 | noteInfo | attrUsage | delete | attributeType |
      | parentStoreIdentifier | AgeRange            |        |        |        | 44         | AgeRange |             |              |                      |          | 1         | 0      | AllowedValues |
    And verify WCS Attribute Values file has the correct format
      | parentStoreIdentifier | attributeValueIdentifier | attributeIdentifier | field1 | field2 | field3 | languageId | value   | sequence | image1 | image2 | attributeValueField1 | attributeValueField2 | attributeValueField3 | valUsage | delete |
      | parentStoreIdentifier | Kid                      | AgeRange            | 1      |        |        | 44         | Kid     | 1        |        |        |                      |                      |                      | 1        | 0      |
      | parentStoreIdentifier | TeenAge                  | AgeRange            | 1      |        |        | 44         | TeenAge | 2        |        |        |                      |                      |                      | 1        | 0      |
      | parentStoreIdentifier | Junior                   | AgeRange            | 1      |        |        | 44         | Junior  | 3        |        |        |                      |                      |                      | 1        | 0      |
    And verify WCS Product Attribute Values file has the correct format
      | partNumber   | parentStoreIdentifier | attributeValueIdentifier | attributeIdentifier | usage | sequence | delete |
      | 12345678     | parentStoreIdentifier | Kid                      | AgeRange            | 2     | 1        | 0      |
      | 1234567890   | parentStoreIdentifier | TeenAge                  | AgeRange            | 2     | 2        | 0      |
      | 123456789001 | parentStoreIdentifier | Junior                   | AgeRange            | 1     | 3        | 0      |
    And data is inserted into database table for Attribute
      | parentStoreIdentifier | attributeIdentifier | languageId | name     | attrUsage | delete |
      | parentStoreIdentifier | AgeRange            | 44         | AgeRange | 1         | 0      |
    And data is inserted into database table for Attribute Values
      | parentStoreIdentifier | attributeValueIdentifier | attributeIdentifier | field1 | languageId | value   | sequence | valUsage | delete |
      | parentStoreIdentifier | Kid                      | AgeRange            | 1      | 44         | Kid     | 1        | 1        | 0      |
      | parentStoreIdentifier | TeenAge                  | AgeRange            | 1      | 44         | TeenAge | 2        | 1        | 0      |
      | parentStoreIdentifier | Junior                   | AgeRange            | 1      | 44         | Junior  | 3        | 1        | 0      |
    And data is inserted into database table for Product Attribute Values
      | partNumber   | parentStoreIdentifier | attributeValueIdentifier | attributeIdentifier | usage | sequence | delete |
      | 12345678     | parentStoreIdentifier | Kid                      | AgeRange            | 2     | 1        | 0      |
      | 1234567890   | parentStoreIdentifier | TeenAge                  | AgeRange            | 2     | 2        | 0      |
      | 123456789001 | parentStoreIdentifier | Junior                   | AgeRange            | 1     | 3        | 0      |

  Scenario:2(WCSCMS-64)Process Attribute Values with out Attributes
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
    And I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | ABCDEFGH            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | ABCDEFGH            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | ABCDEFGH            | en_GB    | Junior  | 3        | 0      |
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:3(WCSCMS-64)Process Product Attribute Values with out Attribute
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
    And I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | ABCDEFGH            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | ABCDEFGH            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | ABCDEFGH            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder

  Scenario:4(WCSCMS-64)Process Product Attribute Values with out Attribute Values
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8   | nappID     | yooxCode10    | gtin         | ean             | delete | modelId  | variantId  |
      | catalogIdentifier | Product       | manufacturer | 1        | 12345678             | MF-12345678 |            |               |              |                 | 0      | 12345678 |            |
      | catalogIdentifier | ProductColour | manufacturer | 2        |                      |             | 1234567890 | MF-1234567890 |              |                 | 0      | 12345678 | 1234567890 |
      | catalogIdentifier | SKU           | manufacturer | 3        |                      |             |            |               | 123456789001 | MF-123456789001 | 0      | 12345678 | 1234567890 |
    And I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | WXYZADCD                 | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | WXYZADCD                 | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | WXYZADCD                 | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributePIM files into failure folder
    And Verify ProductPIM files into failure folder
    And Verify AttributeValuesPIM files into failure folder
    And Verify ProductAttributeValuePIM files into failure folder




