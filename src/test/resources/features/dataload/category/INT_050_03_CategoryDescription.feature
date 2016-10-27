@dataload
Feature: Load Category Description data csv file from Talend

  Scenario:1-(WCSCMS-72)Process Category Description data from PIM to WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Description file
      | groupIdentifier | catalogIdentifier | language | name          | delete |
      | 3674            | catalogIdentifier | en_GB    | Clothing      | 0      |
      | 9605            | catalogIdentifier | en_GB    | Blazers       | 0      |
      | 7026            | catalogIdentifier | en_GB    | Casual Shirts | 0      |
    When I process the products PIM files from Talend to WCS
    Then verify WCS Category Description file has the correct format:
      | groupIdentifier | catalogIdentifier | languageId | name          | shortDescription | longDescription | published | delete |
      | 3674            | catalogIdentifier | 44         | Clothing      |                  |                 | 1         | 0      |
      | 9605            | catalogIdentifier | 44         | Blazers       |                  |                 | 1         | 0      |
      | 7026            | catalogIdentifier | 44         | Casual Shirts |                  |                 | 1         | 0      |
    And data is inserted into CATGRPDESC table:
      | name          |
      | Clothing      |
      | Blazers       |
      | Casual Shirts |

  Scenario:2-(WCSCMS-250)Process Category Description PIM file missing a GroupIdentifier field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Description file
      | catalogIdentifier | language | name          | delete |
      | catalogIdentifier | en_GB    | Clothing      | 0      |
      | catalogIdentifier | en_GB    | Blazers       | 0      |
      | catalogIdentifier | en_GB    | Casual Shirts | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryDescriptionPIM files into failure folder

  Scenario:3-(WCSCMS-250)Process Category Description PIM file missing a catalogIdentifier field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Description file
      | groupIdentifier | language | name          | delete |
      | 3720            | en_GB    | Knitwear      | 0      |
      | 3704            | en_GB    | Jeans         | 0      |
      | 7050            | en_GB    | Formal Shirts | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryDescriptionPIM files into failure folder

  Scenario:4-(WCSCMS-250)Process Category Description PIM file missing a Language field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Description file
      | groupIdentifier | catalogIdentifier | name          | delete |
      | 3720            | catalogIdentifier | Knitwear      | 0      |
      | 3704            | catalogIdentifier | Jeans         | 0      |
      | 7050            | catalogIdentifier | Formal Shirts | 0      |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryDescriptionPIM files into failure folder

  Scenario:5-(WCSCMS-250)Process Category Description PIM file missing a Delete field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Description file
      | groupIdentifier | catalogIdentifier | language | name    |
      | 10464           | catalogIdentifier | en_GB    | Tuxedos |
      | 3842            | catalogIdentifier | en_GB    | Suits   |
      | 5817            | catalogIdentifier | en_GB    | Pyjamas |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryDescriptionPIM files into failure folder

  Scenario:6-(WCSCMS-250)Process Category Description PIM file with extra field
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Description file
      | groupIdentifier | catalogIdentifier | language | name     | delete | extraField |
      | 3953            | catalogIdentifier | en_GB    | T-Shirts | 0      | AutoTest   |
      | 3947            | catalogIdentifier | en_GB    | Swimwear | 0      | AutoTest   |
      | 3941            | catalogIdentifier | en_GB    | Sweats   | 0      | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify CategoryPIM files into failure folder
    And Verify CategoryDescriptionPIM files into failure folder

  Scenario:7-(WCSCMS-250)Process different language Category Description data from PIM to WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 3001               | False    | 410      | 0      |
      | catalogIdentifier | 3002               | False    | 414      | 0      |
      | catalogIdentifier | 3003               | False    | 423      | 0      |
      | catalogIdentifier | 3004               | False    | 425      | 0      |
    And I create Category Description file
      | groupIdentifier | catalogIdentifier | language | name               | delete |
      | 3001            | catalogIdentifier | zh_CN    | 開拓者                | 0      |
      | 3002            | catalogIdentifier | zh_CN    | 休閒襯衫               | 0      |
      | 3003            | catalogIdentifier | fr_FR    | Manteaux et vestes | 0      |
      | 3004            | catalogIdentifier | ja_JP    | タキシード              | 0      |
      | 3005            | catalogIdentifier | ru_RU    | Толстовка          | 0      |
    When I process the products PIM files from Talend to WCS
    Then verify WCS Category Description file has the correct format:
      | groupIdentifier | catalogIdentifier | languageId | name               | shortDescription | longDescription | published | delete |
      | 3001            | catalogIdentifier | -7         | 開拓者                |                  |                 | 1         | 0      |
      | 3002            | catalogIdentifier | -7         | 休閒襯衫               |                  |                 | 1         | 0      |
      | 3003            | catalogIdentifier | -2         | Manteaux et vestes |                  |                 | 1         | 0      |
      | 3004            | catalogIdentifier | -10        | タキシード              |                  |                 | 1         | 0      |
      | 3005            | catalogIdentifier | -20        | Толстовка          |                  |                 | 1         | 0      |
    And category Description data is inserted correctly into table:
      | groupIdentifier | language | name               |
      | 3001            | zh_CN    | 開拓者                |
      | 3002            | zh_CN    | 休閒襯衫               |
      | 3003            | fr_FR    | Manteaux et vestes |
      | 3004            | ja_JP    | タキシード              |
      | 3005            | ru_RU    | Толстовка          |

#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario Outline:8-(WCSCMS-250)Schema failure
#    Given I create Category PIM file
#      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
#      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
#      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
#    And I create Category Description file
#      | groupIdentifier   | catalogIdentifier   | language   | name   | delete   |
#      | <groupIdentifier> | <catalogIdentifier> | <language> | <name> | <delete> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify CategoryPIM files into failure folder
#    And Verify CategoryDescriptionPIM files into failure folder
#
#    Examples:
#      | groupIdentifier | catalogIdentifier        | language | name                                                                                                                                                                                                                                                           | delete |
#      | 1234            | catalogIdentifier        | en_GB    | Clothing                                                                                                                                                                                                                                                       | 0      |
#      | 3674            | InvalidcatalogIdentifier | en_GB    | Clothing                                                                                                                                                                                                                                                       | 0      |
#      | 3674            | catalogIdentifier        | en       | Clothing                                                                                                                                                                                                                                                       | 0      |
#      | 3674            | catalogIdentifier        | en_GB    | wteqteruqtyweriuqweytriuqweytrquiweyrtqwueytrquweytruweytweruytquiywetuirytweqiruywetiurywetiurytqweiurytweiurytqiwurytiquweytriquweytriuwytriuweytqriuweytiuwetiqurywetiuqryiwuewueqytiwueytqiuyqriuweytqriuweytiuweyriqqiuweyriqweuryieuwyiuyiiuyweyurweiuiu | 0      |
#      | 3674            | catalogIdentifier        | en_GB    | Clothing                                                                                                                                                                                                                                                       | a      |
#
#
#  Scenario Outline:9-(WCSCMS-250)Mandatory validation failure
#    Given I create Category PIM file
#      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
#      | catalogIdentifier | 3674               | TRUE     | 409      | 0      |
#      | catalogIdentifier | 9605               | FALSE    | 410      | 0      |
#    And I create Category Description file
#      | groupIdentifier   | catalogIdentifier   | language   | name   | delete   |
#      | <groupIdentifier> | <catalogIdentifier> | <language> | <name> | <delete> |
#    When I process the products PIM files from Talend to WCS
#    Then Verify CategoryPIM files into failure folder
#    And Verify CategoryDescriptionPIM files into failure folder
#
#    Examples:
#      | groupIdentifier | catalogIdentifier | language | name     | delete |
#      |                 | catalogIdentifier | en_GB    | Clothing | 0      |
#      | 3674            |                   | en_GB    | Clothing | 0      |
#      | 3674            | catalogIdentifier |          | Clothing | 0      |
#      | 3674            | catalogIdentifier | en_GB    |          | 0      |
#      | 3674            | catalogIdentifier | en_GB    | Clothing |        |
#

  Scenario:10-(WCSCMS-250)BR1 Category must exists before Process Category Description data from PIM to WCS
    Given I create Category PIM file
      | catalogIdentifier | categoryIdentifier | topGroup | sequence | delete |
      | catalogIdentifier | 7026               | FALSE    | 414      | 0      |
    And I create Category Description file
      | groupIdentifier | catalogIdentifier | language | name     | delete |
      | 9999            | catalogIdentifier | en_GB    | Clothing | 0      |
      | 9998            | catalogIdentifier | en_GB    | Blazers  | 0      |
    When I process the products PIM files from Talend to WCS
    And data is inserted into CATGRPDESC table:
      | name     |
      | Clothing |
      | Blazers  |
