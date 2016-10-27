@dataload
Feature: Load Category data csv file from Talend

  Scenario:1-(WCSCMS-73)Process Category data from PIM to WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3704               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |
    When I process the products PIM files from Talend to WCS
    Then verify WCS "products" Category file has the correct format:
      | groupIdentifier | parentStoreIdentifier | catalogIdentifier | topGroup | sequence | field1 | field2 | delete |
      | 3704            | parentStoreIdentifier | catalogIdentifier | False    | 439      |        |        | 0      |
      | 7050            | parentStoreIdentifier | catalogIdentifier | False    | 842      |        |        | 0      |
#    Then verify WCS Category file has the correct format:
#      | groupIdentifier | parentStoreIdentifier | catalogIdentifier | topGroup | sequence | field1 | field2 | delete |
#      | 3704            | parentStoreIdentifier | catalogIdentifier | False    | 439      |        |        | 0      |
#      | 7050            | parentStoreIdentifier | catalogIdentifier | False    | 842      |        |        | 0      |
    And verify data is inserted into CATGROUP table:
      | categoryIdentifier |
      | 3704               |
      | 7050               |

  Scenario:2-(WCSCMS-253)Process Category PIM file missing a CatalogIdentifier field
    Given I create Category PIM file
      | categoryIdentifier | topGroup | sequence | delete |
      | 3674               | False    | 409      | 0      |
      | 5787               | False    | 489      | 0      |
    When I process the products PIM files from Talend to WCS
#    Then Verify CategoryPIM files into failure folder
    Then Verify CategoryPIM files into products failure folder


  Scenario:3-(WCSCMS-253)Process Category PIM file missing a CategoryIdentifier field
    Given I create Category PIM file
      | catalogIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | False    | 409      | 0      |
      | catalogIdentifier | False    | 410      | 0      |
      | catalogIdentifier | False    | 414      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder

  Scenario:4-(WCSCMS-253)Process Category PIM file missing a TopGroup field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | sequence | delete |
      | catalogIdentifier | 3674               | 409      | 0      |
      | catalogIdentifier | 9605               | 410      | 0      |
      | catalogIdentifier | 7026               | 414      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder

  Scenario:5-(WCSCMS-253)Process Category PIM file missing a Sequence field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | delete |
      | catalogIdentifier | 3674               | False    | 0      |
      | catalogIdentifier | 9605               | False    | 0      |
      | catalogIdentifier | 7026               | False    | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder

  Scenario:6-(WCSCMS-253)Process Category PIM file missing a Delete field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence |
      | catalogIdentifier | 3674               | False    | 409      |
      | catalogIdentifier | 9605               | False    | 410      |
      | catalogIdentifier | 5787               | False    | 489      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder

  Scenario:7-(WCSCMS-253)Process Category PIM file with extra field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete | extraField |
      | catalogIdentifier | 3674               | False    | 409      | 0      | AutoTest   |
      | catalogIdentifier | 9605               | False    | 410      | 0      | AutoTest   |
      | catalogIdentifier | 7026               | False    | 414      | 0      | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder

  Scenario:8-(WCSCMS-253)Process Delete Category data from WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3704               | False    | 439      | 1      |
      | catalogIdentifier | 7050               | False    | 842      | 1      |
    When I process the products PIM files from Talend to WCS
    And verify data is deleted from CATGROUP table:
      | categoryIdentifier |
      | 3704               |
      | 7050               |


#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:9-(WCSCMS-253)Schema failure
#    Given I create Category PIM file
#      | catalogIdentifier   | categoryIdentifier   | topGroup   | sequence   | delete   |
#      | <catalogIdentifier> | <categoryIdentifier> | <topGroup> | <sequence> | <delete> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify CategoryPIM files into failure folder
#
#    Examples:
#      | catalogIdentifier        | categoryIdentifier | topGroup | sequence | delete |
#      | InvalidcatalogIdentifier | 3704               | False    | 439      | 0      |
#      | catalogIdentifier        | abcd               | False    | 439      | 0      |
#      | catalogIdentifier        | 3704               | FALSEE   | 439      | 0      |
#      | catalogIdentifier        | 3704               | False    | abcd     | 0      |
#      | catalogIdentifier        | 3704               | False    | 439      | 2      |
#
#
#  Scenario Outline:10-(WCSCMS-253)Mandatory validation failure
#    Given I create Category PIM file
#      | catalogIdentifier   | categoryIdentifier   | topGroup   | sequence   | delete   |
#      | <catalogIdentifier> | <categoryIdentifier> | <topGroup> | <sequence> | <delete> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify CategoryPIM files into failure folder
#
#    Examples:
#      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
#      |                   | 3704               | False    | 439      | 0      |
#      | catalogIdentifier |                    | False    | 439      | 0      |
#      | catalogIdentifier | 3704               |          | 439      | 0      |
#      | catalogIdentifier | 3704               | False    |          | 0      |
#      | catalogIdentifier | 3704               | False    | 439      |        |


  Scenario:11-(WCSCMS-253)Process comma-separated Category data
    Given I create Category PIM file with comma-separation
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3704               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder

  Scenario:12-(WCSCMS-253)Process Category data without header in csv file
    Given I create Category PIM file without header
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3704               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder

  Scenario:13-(WCSCMS-253)Process Category data when fields are in different sequence in csv file
    Given I create Category PIM file with different field sequence
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3704               | False    | 439      | 0      |
      | catalogIdentifier | 7050               | False    | 842      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
