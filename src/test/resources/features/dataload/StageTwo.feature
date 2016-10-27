@stage2
Feature: Process Stage Two PIM files from WCS

  Scenario:1-Process all Category and product data from PIM to WCS
    When I process the Stage2 PIM files from Talend to WCS
    Then Verify category data and count in catgroup
    And Verify category description data and count in catgrpdesc
    And Verify category relationship data and count in catgrprel
    And Verify product data and count in catentry and catentrel
    And Verify product description data and count in catentdesc and xcatentdesc
    And Verify product category data and count in catgpenrel



