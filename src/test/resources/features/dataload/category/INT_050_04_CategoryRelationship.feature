@dataload
Feature: Load Category Relationship data csv file from Talend

  Scenario:1-(WCSCMS-70)Process Category Relationship data from PIM to WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete |
      | 9605            | 3674                  | catalogIdentifier | 2        | 0      |
      | 7026            | 3674                  | catalogIdentifier | 6        | 0      |
    Then I process the products PIM files from Talend to WCS
    Then verify WCS Category Relationship file has the correct format:
      | groupIdentifier | parentGroupIdentifier | parentStoreIdentifier | catalogIdentifier | sequence | delete |
      | 9605            | 3674                  | parentStoreIdentifier | catalogIdentifier | 2        | 0      |
      | 7026            | 3674                  | parentStoreIdentifier | catalogIdentifier | 6        | 0      |
    And category data is inserted correctly into table:
      | categoryIdentifier | catalogIdentifier | sequence |
      | 3674               | catalogIdentifier | 409      |
      | 7026               |                   |          |
      | 9605               |                   |          |
    And category Relationship data is inserted correctly into table:
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 9605            | 3674                  | catalogIdentifier | 2        |
      | 7026            | 3674                  | catalogIdentifier | 6        |

  Scenario:2-(WCSCMS-260)Process Category Relationship PIM file missing a GroupIdentifier field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | parentGroupIdentifier | catalogIdentifier | sequence | delete |
      | 3674                  | catalogIdentifier | 852      | 0      |
      | 3674                  | catalogIdentifier | 847      | 0      |
      | 3674                  | catalogIdentifier | 842      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryRelationshipPIM files into failure folder

  Scenario:3-(WCSCMS-260)Process Category Relationship PIM file missing a ParentGroupIdentifier field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | catalogIdentifier | sequence | delete |
      | 9605            | catalogIdentifier | 2        | 0      |
      | 7026            | catalogIdentifier | 6        | 0      |
      | 3917            | catalogIdentifier | 889      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryRelationshipPIM files into failure folder

  Scenario:4-(WCSCMS-260)Process Category Relationship PIM file missing a CatalogIdentifier field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | sequence | delete |
      | 3720            | 3674                  | 852      | 0      |
      | 3704            | 3674                  | 847      | 0      |
      | 7050            | 3674                  | 842      | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryRelationshipPIM files into failure folder

  Scenario:5-(WCSCMS-260)Process Category Relationship PIM file missing a Sequence field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | delete |
      | 9539            | 3674                  | catalogIdentifier | 0      |
      | 3704            | 3674                  | catalogIdentifier | 0      |
      | 7050            | 3674                  | catalogIdentifier | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryRelationshipPIM files into failure folder

  Scenario:6-(WCSCMS-260)Process Category Relationship PIM file missing a Delete field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 3720            | 3674                  | catalogIdentifier | 852      |
      | 3704            | 3674                  | catalogIdentifier | 847      |
      | 7050            | 3674                  | catalogIdentifier | 842      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryRelationshipPIM files into failure folder

  Scenario:7-(WCSCMS-260)Process Category Relationship PIM file with ExtraField
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete | extraField |
      | 3720            | 3674                  | catalogIdentifier | 852      | 0      | Autotest   |
      | 3704            | 3674                  | catalogIdentifier | 847      | 0      | Autotest   |
      | 7050            | 3674                  | catalogIdentifier | 842      | 0      | Autotest   |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryRelationshipPIM files into failure folder


#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:8-(WCSCMS-260)GroupIdentifier Schema failure
#    Given I create Category PIM file
#      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
#      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
#      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
#      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
#    And I create Category Relationship PIM file
#      | groupIdentifier   | parentGroupIdentifier   | catalogIdentifier   | sequence   | delete   |
#      | <groupIdentifier> | <parentGroupIdentifier> | <catalogIdentifier> | <sequence> | <delete> |
#    Then I process the products PIM files from Talend to WCS
#    Then Verify CategoryPIM files into failure folder
#    And Verify CategoryRelationshipPIM files into failure folder
#
#    Examples:
#      | groupIdentifier | parentGroupIdentifier | catalogIdentifier        | sequence | delete |
#      | 1234            | 3674                  | catalogIdentifier        | 2        | 0      |
#      | 9605            | 1234                  | catalogIdentifier        | 2        | 0      |
#      | 9605            | 3674                  | InvalidcatalogIdentifier | 2        | 0      |
#      | 9605            | 3674                  | catalogIdentifier        | a        | 0      |
#      | 9605            | 3674                  | catalogIdentifier        | 2        | 3      |
#
#  Scenario Outline:9-(WCSCMS-260)Mandatory validation Schema failure
#    Given I create Category PIM file
#      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
#      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
#      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
#      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
#    And I create Category Relationship PIM file
#      | groupIdentifier   | parentGroupIdentifier   | catalogIdentifier   | sequence   | delete   |
#      | <groupIdentifier> | <parentGroupIdentifier> | <catalogIdentifier> | <sequence> | <delete> |
#    Then I process the products PIM files from Talend to WCS
#    Then Verify CategoryPIM files into failure folder
#    And Verify CategoryRelationshipPIM files into failure folder
#
#    Examples:
#      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete |
#      |                 | 3674                  | catalogIdentifier | 2        | 0      |
#      | 9605            | 3674                  |                   | 2        | 0      |
#      | 9605            | 3674                  | catalogIdentifier |          | 0      |
#      | 9605            | 3674                  | catalogIdentifier | 2        |        |


  Scenario:10-(WCSCMS-260)BR1-Process Category Relationship data without category from PIM to WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 4001               | FALSE    | 410      | 0      |
      | catalogIdentifier | 4002               | FALSE    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete |
      | 4001            | 5674                  | catalogIdentifier | 2        | 0      |
      | 4002            | 5674                  | catalogIdentifier | 6        | 0      |
    Then I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryRelationshipPIM files into failure folder
    And category data is inserted correctly into table:
      | categoryIdentifier | catalogIdentifier | sequence |
      | 4001               |                   |          |
      | 4002               |                   |          |

  Scenario:11 BR 2and4-(WCSCMS-260) Move category relationship
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 1001               | True     | 409      | 0      |
      | catalogIdentifier | 1002               | True     | 410      | 0      |
      | catalogIdentifier | 2001               | False    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete |
      | 2001            | 1001                  | catalogIdentifier | 2        | 0      |
    Then I process the products PIM files from Talend to WCS
    And category data is inserted correctly into table:
      | categoryIdentifier | catalogIdentifier | sequence |
      | 1001               | catalogIdentifier | 409      |
      | 1002               | catalogIdentifier | 410      |
      | 2001               |                   |          |
    And category Relationship data is inserted correctly into table:
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 2001            | 1001                  | catalogIdentifier | 2        |
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 1001               | True     | 409      | 0      |
      | catalogIdentifier | 1002               | True     | 410      | 0      |
      | catalogIdentifier | 2001               | False    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete |
      | 2001            | *                     | catalogIdentifier | 2        | 0      |
      | 2001            | 1002                  | catalogIdentifier | 2        | 0      |
    Then I process the products PIM files from Talend to WCS
    And category data is inserted correctly into table:
      | categoryIdentifier | catalogIdentifier | sequence |
      | 1001               | catalogIdentifier | 409      |
      | 1002               | catalogIdentifier | 410      |
      | 2001               |                   |          |
    And category Relationship data is inserted correctly into table:
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 2001            | 1002                  | catalogIdentifier | 2        |
    And Verify category Relationship data is deleted correctly from table
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 2001            | 1001                  | catalogIdentifier | 2        |
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 1001               | True     | 409      | 0      |
      | catalogIdentifier | 1002               | True     | 410      | 0      |
      | catalogIdentifier | 2001               | False    | 414      | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete |
      | 2001            | *                     | catalogIdentifier | 2        | 0      |
    Then I process the products PIM files from Talend to WCS
    And Verify category Relationship data is deleted correctly from table
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 2001            | 1001                  | catalogIdentifier | 2        |