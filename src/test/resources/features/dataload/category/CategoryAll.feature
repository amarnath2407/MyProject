@dataload
Feature: Insert and Delete Category PIM files from WCS

  Scenario:1-Process Category,  Category Description and Category Relationship data from PIM to WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | True     | 409      | 0      |
      | catalogIdentifier | 9605               | False    | 410      | 0      |
      | catalogIdentifier | 7026               | False    | 414      | 0      |
      | catalogIdentifier | 9539               | False    | 423      | 0      |
      | catalogIdentifier | 10464              | False    | 488      | 0      |
      | catalogIdentifier | 5787               | False    | 489      | 0      |
    And I create Category Description file
      | groupIdentifier | catalogIdentifier | language | name              | delete |
      | 3674            | catalogIdentifier | en_GB    | Clothing          | 0      |
      | 9605            | catalogIdentifier | en_GB    | Blazers           | 0      |
      | 7026            | catalogIdentifier | en_GB    | Casual Shirts     | 0      |
      | 9539            | catalogIdentifier | en_GB    | Coats and Jackets | 0      |
      | 10464           | catalogIdentifier | en_GB    | Tuxedos           | 0      |
      | 5787            | catalogIdentifier | en_GB    | Underwear         | 0      |
    And I create Category Relationship PIM file
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence | delete |
      | 9605            | 3674                  | catalogIdentifier | 2        | 0      |
      | 7026            | 3674                  | catalogIdentifier | 6        | 0      |
      | 9539            | 3674                  | catalogIdentifier | 15       | 0      |
      | 10464           | 3674                  | catalogIdentifier | 896      | 0      |
      | 5787            | 3674                  | catalogIdentifier | 897      | 0      |
    When I process the products PIM files from Talend to WCS
    Then category data is inserted correctly into table:
      | categoryIdentifier | catalogIdentifier | sequence |
      | 3674               | catalogIdentifier | 409      |
      | 7026               |                   |          |
      | 9539               |                   |          |
      | 10464              |                   |          |
      | 5787               |                   |          |
    And category Description data is inserted correctly into table:
      | groupIdentifier | language | name              |
      | 3674            | en_GB    | Clothing          |
      | 9605            | en_GB    | Blazers           |
      | 7026            | en_GB    | Casual Shirts     |
      | 9539            | en_GB    | Coats and Jackets |
      | 10464           | en_GB    | Tuxedos           |
      | 5787            | en_GB    | Underwear         |
    And category Relationship data is inserted correctly into table:
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 9605            | 3674                  | catalogIdentifier | 2        |
      | 7026            | 3674                  | catalogIdentifier | 6        |
      | 9539            | 3674                  | catalogIdentifier | 15       |
      | 10464           | 3674                  | catalogIdentifier | 896      |
      | 5787            | 3674                  | catalogIdentifier | 897      |
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | True     | 409      | 1      |
      | catalogIdentifier | 9605               | False    | 410      | 1      |
      | catalogIdentifier | 7026               | False    | 414      | 1      |
      | catalogIdentifier | 9539               | False    | 423      | 1      |
      | catalogIdentifier | 10464              | False    | 488      | 1      |
      | catalogIdentifier | 5787               | False    | 489      | 1      |
    When I process the products PIM files from Talend to WCS
    Then verify data is deleted from CATGROUP table:
      | categoryIdentifier | catalogIdentifier | sequence |
      | 3674               | catalogIdentifier | 409      |
      | 7026               |                   |          |
      | 9539               |                   |          |
      | 10464              |                   |          |
      | 5787               |                   |          |
    And Verify category Description data is deleted correctly from table
      | groupIdentifier | language | name              |
      | 3674            | en_GB    | Clothing          |
      | 9605            | en_GB    | Blazers           |
      | 7026            | en_GB    | Casual Shirts     |
      | 9539            | en_GB    | Coats and Jackets |
      | 10464           | en_GB    | Tuxedos           |
      | 5787            | en_GB    | Underwear         |
    And Verify category Relationship data is deleted correctly from table
      | groupIdentifier | parentGroupIdentifier | catalogIdentifier | sequence |
      | 9605            | 3674                  | catalogIdentifier | 2        |
      | 7026            | 3674                  | catalogIdentifier | 6        |
      | 9539            | 3674                  | catalogIdentifier | 15       |
      | 10464           | 3674                  | catalogIdentifier | 896      |
      | 5787            | 3674                  | catalogIdentifier | 897      |

