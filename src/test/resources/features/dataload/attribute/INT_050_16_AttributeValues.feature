@dataload
Feature: This is a feature for INT_050.016_Attribute Values PIM Dataload.

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
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |

  Scenario:1-(WCSCMS-264)Process Attribute Values PIM file missing catalogIdentifier field
    Given I create Attribute Values PIM file
      | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:2-(WCSCMS-264)Process Attribute Values PIM file missing attributeValueIdentifier field
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | AgeRange            | en_GB    | Junior  | 3        | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:3-(WCSCMS-264)Process Attribute Values PIM file missing attributeIdentifier field
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | en_GB    | Junior  | 3        | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:4-(WCSCMS-264)Process Attribute Values PIM file missing language field
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | Junior  | 3        | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:5-(WCSCMS-264)Process Attribute Values PIM file missing Value field
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | 3        | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:6-(WCSCMS-264)Process Attribute Values PIM file missing sequence field
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:7-(WCSCMS-264)Process Attribute Values PIM file missing Delete field
    And I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:8-(WCSCMS-264)Process Attribute Values PIM file with an extra field
    Given I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value  | sequence | delete | ExtraField |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior | 2        | 0      | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:9-(WCSCMS-264)Process Attribute Values PIM schema validation tests
#    Given I create Attribute Values PIM file
#      | catalogIdentifier   | attributeValueIdentifier   | attributeIdentifier   | language   | value   | sequence   | delete   |
#      | <catalogIdentifier> | <attributeValueIdentifier> | <attributeIdentifier> | <language> | <value> | <sequence> | <delete> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify AttributeValuesPIM files into failure folder
#    Examples:
#      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
#      |                   | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
#      | catalogIdentifier |                          | AgeRange            | en_GB    | TeenAge | 2        | 0      |
#      | catalogIdentifier | Junior                   |                     | en_GB    | Junior  | 3        | 0      |
#      | catalogIdentifier | Kid                      | AgeRange            |          | Kid     | 1        | 0      |
#      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    |         | 2        | 0      |
#      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  |          | 0      |
#      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 2        |        |
#

  Scenario:10-(WCSCMS-264)Delete Attribute Values data and check into database
    Given I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    And I process the products PIM files from Talend to WCS
    When I create Attribute Values PIM file
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 1      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 1      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 1      |
    And I create Attribute PIM file
      | catalogIdentifier | attributeIdentifier | language | name     | attrUsage | delete |
      | catalogIdentifier | AgeRange            | en_GB    | AgeRange | 1         | 0      |
      | catalogIdentifier | Brand               | en_GB    | Brand    | 1         | 0      |
      | catalogIdentifier | Gender              | en_GB    | Gender   | 1         | 0      |
    And I create Product Attribute Values PIM file
      | gtin         | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | usage | productType   | sequence | delete | modelId  | variantId  |
      |              | catalogIdentifier | Kid                      | AgeRange            | 2     | Product       | 1        | 0      | 12345678 |            |
      |              | catalogIdentifier | TeenAge                  | AgeRange            | 2     | ProductColour | 2        | 0      | 12345678 | 1234567890 |
      | 123456789001 | catalogIdentifier | Junior                   | AgeRange            | 1     | SKU           | 3        | 0      | 12345678 | 1234567890 |
    And I process the products PIM files from Talend to WCS
    Then data is deleted into database table for Attribute Values
      | parentStoreIdentifier | attributeValueIdentifier | languageId |
      | parentStoreIdentifier | Kid                      | 44         |
      | parentStoreIdentifier | TeenAge                  | 44         |
      | parentStoreIdentifier | Junior                   | 44         |

  Scenario:11-(WCSCMS-264)Create and Process Attribute Values PIM file with comma delimiter
    Given I create Attribute Values PIM file with comma delimiter
      | catalogIdentifier | attributeValueIdentifier | attributeIdentifier | language | value   | sequence | delete |
      | catalogIdentifier | Kid                      | AgeRange            | en_GB    | Kid     | 1        | 0      |
      | catalogIdentifier | TeenAge                  | AgeRange            | en_GB    | TeenAge | 2        | 0      |
      | catalogIdentifier | Junior                   | AgeRange            | en_GB    | Junior  | 3        | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder

  Scenario:12-(WCSCMS-264)Create and Process Attribute Values PIM file with wrong header row order
    Given I create Attribute Values PIM file with wrong header row order
      | language | attributeIdentifier | sequence | delete | catalogIdentifier | attributeValueIdentifier | value   |
      | en_GB    | AgeRange            | 1        | 0      | catalogIdentifier | Kid                      | Kid     |
      | en_GB    | AgeRange            | 2        | 0      | catalogIdentifier | TeenAge                  | TeenAge |
      | en_GB    | AgeRange            | 3        | 0      | catalogIdentifier | Junior                   | Junior  |
    When I process the products PIM files from Talend to WCS
    Then Verify AttributeValuesPIM files into failure folder


