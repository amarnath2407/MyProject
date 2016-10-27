@dataload
Feature: This is a feature for INT_050.013_Product Attribute Values PIM Dataload.

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
    Given I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
      | catalogIdentifier | Brand               | en_GB    | Brand    | 1         | 0      |
      | catalogIdentifier | Gender              | en_GB    | Gender   | 1         | 0      |
    Given I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |

  Scenario:1-(WCSCMS-265) Process Product Attribute Values PIM file is missing gtin field
    And I create Product Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      | catalogIdentifier | Kid                      | AgeRange            | 1     | Product       | 1        | 0      | 12345678 |            |
      | catalogIdentifier | TeenAge                  | AgeRange            | 1     | ProductColour | 2        | 0      |          | 1234567890 |
      | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:2-(WCSCMS-265) Process Product Attribute Values PIM file is missing catalogIdentifier field
    And I create Product Attribute Values PIM file
      | gtin         | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | Kid                      | AgeRange            | 1     | Product       | 1        | 0      | 12345678 |            |
      |              | TeenAge                  | AgeRange            | 1     | ProductColour | 2        | 0      |          | 1234567890 |
      | 123456789001 | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:3-(WCSCMS-265) Process Product Attribute Values PIM file is missing attributeValueIdentifier field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | AgeRange            | 1     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | AgeRange            | 1     | ProductColour | 2        | 0      |          | 1234567890 |
      | 123456789001 | catalogIdentifier | AgeRange            | 1     | SKU           | 3        | 0      |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:4-(WCSCMS-265) Process Product Attribute Values PIM file is missing attributeIdentifier field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | 1     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | 1     | ProductColour | 2        | 0      |          | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | 1     | SKU           | 3        | 0      |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:5-(WCSCMS-265) Process Product Attribute Values PIM file is missing usage field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | ProductColour | 2        | 0      |          | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | SKU           | 3        | 0      |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:6-(WCSCMS-265) Process Product Attribute Values PIM file is missing productType field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 1     | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 1     | 2        | 0      |          | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | 3        | 0      |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:7-(WCSCMS-265) Process Product Attribute Values PIM file is missing sequence field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 1     | Product       | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 1     | ProductColour | 0      |          | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 0      |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:8-(WCSCMS-265) Process Product Attribute Values PIM file is missing delete field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 1     | Product       | 1        | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 1     | ProductColour | 2        |          | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        |          |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:9-(WCSCMS-265) Process Product Attribute Values PIM file is missing modelId field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 1     | Product       | 1        |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 1     | ProductColour | 2        | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        |            |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:10-(WCSCMS-265) Process Product Attribute Values PIM file is missing variantId field
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 1     | Product       | 1        | 0      | 12345678 |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 1     | ProductColour | 2        | 0      |          |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      |          |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:11-(WCSCMS-264)Process Product Attribute Values PIM file with an extra field
    Given I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType | sequence | delete | modelId  | variantId  | ExtraField |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU         | 3        | 0      | 12345678 | 1234567890 | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder


#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:12-(WCSCMS-265)Process Product Attribute Values PIM schema validation tests
#    And I create Product Attribute Values PIM file
#      | gtin   | catalogIdentifier   | attributeValueIdentifier   | attributeIdentifier   | usage   | productType   | sequence   | delete   | modelId   | variantId   |
#      | <gtin> | <catalogIdentifier> | <attributeValueIdentifier> | <attributeIdentifier> | <usage> | <productType> | <sequence> | <delete> | <modelId> | <variantId> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify ProductAttributeValuePIM files into failure folder
#    Examples:
#      | gtin | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType | sequence | delete | modelId  | variantId |
#      |      |                   | Kid                      | AgeRange            | 1     | Product     | 1        | 0      | 12345678 |           |
#      |      | catalogIdentifier |                          | MicroColor          | 1     | Product     | 1        | 0      | 12345678 |           |
#      |      | catalogIdentifier | Kid                      |                     | 1     | Product     | 1        | 0      | 12345678 |           |
#      |      | catalogIdentifier | Kid                      | AgeRange            |       | Product     | 1        | 0      | 12345678 |           |
#      |      | catalogIdentifier | Kid                      | AgeRange            | 1     |             | 1        | 0      | 12345678 |           |
#      |      | catalogIdentifier | Kid                      | AgeRange            | 1     | Product     |          | 0      | 12345678 |           |
#      |      | catalogIdentifier | Kid                      | AgeRange            | 1     | Product     | 1        |        | 12345678 |           |

  Scenario:13-(WCSCMS-264)Delete Product Attribute Values data and check into database
    Given I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |
    And I process the products PIM files from Talend to WCS
    When I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 1      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 1      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 1      | 12345678 | 1234567890 |
    And I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
      | catalogIdentifier | Brand               | en_GB    | Brand    | 1         | 0      |
      | catalogIdentifier | Gender              | en_GB    | Gender   | 1         | 0      |
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    And I process the products PIM files from Talend to WCS
    Then data is deleted into database table for Product Attribute Values
      | partNumber   | parentStoreIdentifier | attributeValueIdentifier |
      | 12345678     | parentStoreIdentifier | Kid                      |
      | 1234567890   | parentStoreIdentifier | TeenAge                  |
      | 123456789001 | parentStoreIdentifier | Junior                   |

  Scenario:14-(WCSCMS-264)Create and Process Product Attribute Values PIM file with comma delimiter
    Given I create Product Attribute Values PIM file with comma delimiter
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder

  Scenario:15-(WCSCMS-264)Create and Process Product Attribute Values PIM file with wrong header row order
    Given I create Product Attribute Values PIM file with wrong header row order
      | sequence | usage | attributeValueIdentifier | gtin         | modelId  | variantId  | attributeIdentifier | catalogIdentifier | productType   | delete |
      | 1        | 2     | Kid                      |              | 12345678 |            | AgeRange            | catalogIdentifier | Product       | 0      |
      | 2        | 2     | TeenAge                  |              | 12345678 | 1234567890 | AgeRange            | catalogIdentifier | ProductColour | 0      |
      | 3        | 1     | Junior                   | 123456789001 | 12345678 | 1234567890 | AgeRange            | catalogIdentifier | SKU           | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductAttributeValuePIM files into failure folder
