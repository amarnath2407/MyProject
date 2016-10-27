Feature: Load BanningRestriction data csv file from Talend

  Background:
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID      | yooxCode10    | gtin          | ean             | delete | modelId  | variantId   |
      | catalogIdentifier | Product       | manufacturer | 1        | 11001390             | MF11001390 |             |               |               |                 | 0      | 11001390 |             |
      | catalogIdentifier | ProductColour | manufacturer | 6405     |                      |            | 11001390743 | MF11001390743 |               |                 | 0      | 11001390 | 11001390743 |
      | catalogIdentifier | SKU           | manufacturer | 2        |                      |            |             |               | 0400240168209 | MF0400240168209 | 0      | 11001390 | 11001390743 |
      | catalogIdentifier | Product       | manufacturer | 16       | 11001400             | MF11001400 |             |               |               |                 | 0      | 11001400 |             |
      | catalogIdentifier | ProductColour | manufacturer | 6407     |                      |            | 110014003   | MF110014003   |               |                 | 0      | 11001400 | 110014003   |
      | catalogIdentifier | SKU           | manufacturer | 17       |                      |            |             |               | 0400298690134 | MF0400298690134 | 0      | 11001400 | 110014003   |

  Scenario:1(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when Selling is set to false
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | false    |
      | AF                 | false    |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN          | brandChannel      | timestamp                         |
      | 0400240168209 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    Then verify WCS "banning" product file has the correct format:
      | partNumber    | parentStoreIdentifier | attributeValueIdentifier | attributeIdentifier  | usage | sequence | delete |
      | 0400240168209 | catalogIdentifier     | COMMERCIAL_BANNED        | catalogIdentifier_AD | 1     | 0        | 0      |
      | 0400240168209 | catalogIdentifier     | COMMERCIAL_BANNED        | catalogIdentifier_AF | 1     | 0        | 0      |
      | 0400240168209 | catalogIdentifier     | COMMERCIAL_BANNED        | catalogIdentifier_AE | 1     | 0        | 0      |
    And verify the data has been loaded to catentryattr
      | partNumber    | attributeIdentifier  | attributeValueIdentifier |
      | 0400240168209 | catalogIdentifier_AD | COMMERCIAL_BANNED        |
      | 0400240168209 | catalogIdentifier_AF | COMMERCIAL_BANNED        |
      | 0400240168209 | catalogIdentifier_AE | COMMERCIAL_BANNED        |

  Scenario:2(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when Selling is set to true
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | true     |
      | AF                 | true     |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN          | brandChannel      | timestamp                         |
      | 0400240168209 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    Then verify WCS banning product file has the correct format:
      | partNumber    | parentStoreIdentifier | attributeValueIdentifier | attributeIdentifier  | usage | sequence | delete |
      | 0400240168209 | catalogIdentifier     | COMMERCIAL_BANNED        | catalogIdentifier_AE | 1     | 0        | 0      |
    And verify the data has been loaded to catentryattr
      | partNumber    | attributeIdentifier  | attributeValueIdentifier |
      | 0400240168209 | catalogIdentifier_AE | COMMERCIAL_BANNED        |

  Scenario:3(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when updating to a new country set.
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | false    |
      | AF                 | false    |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN          | brandChannel      | timestamp                         |
      | 0400240168209 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    Then verify the data has been loaded to catentryattr
      | partNumber    | attributeIdentifier  | attributeValueIdentifier |
      | 0400240168209 | catalogIdentifier_AD | COMMERCIAL_BANNED        |
      | 0400240168209 | catalogIdentifier_AF | COMMERCIAL_BANNED        |
      | 0400240168209 | catalogIdentifier_AE | COMMERCIAL_BANNED        |
    And provided list of country for banning
      | destinationCountry | sellable |
      | GB                 | false    |
      | FR                 | false    |
      | IT                 | false    |
    And I create banning product Json file
      | gTIN          | brandChannel      | timestamp                         |
      | 0400240168209 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    And verify the data has been loaded to catentryattr
      | partNumber    | attributeIdentifier  | attributeValueIdentifier |
      | 0400240168209 | catalogIdentifier_GB | COMMERCIAL_BANNED        |
      | 0400240168209 | catalogIdentifier_FR | COMMERCIAL_BANNED        |
      | 0400240168209 | catalogIdentifier_IT | COMMERCIAL_BANNED        |

  Scenario:4(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when SKU does not exist
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | true     |
      | AF                 | true     |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN          | brandChannel      | timestamp                         |
      | 0400299999999 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
   # Then Verify CommercialBanningPIM files into banning failure folder

  Scenario:5(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when Loading against ProductColour
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | true     |
      | AF                 | true     |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN        | brandChannel      | timestamp                         |
      | 11001390743 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    #Then Verify CommercialBanningPIM files into banning failure folder

  Scenario:6(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when misssing GTIN Madatory value
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | false    |
      | AF                 | false    |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN | brandChannel      | timestamp                         |
      |      | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    #Then Verify CommercialBanningPIM files into banning failure folder

  Scenario:7(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when misssing brandChannel Madatory value
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | false    |
      | AF                 | false    |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN        | brandChannel | timestamp                         |
      | 11001390743 |              | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    #Then Verify CommercialBanningPIM files into banning failure folder

  Scenario:8(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when misssing timestamp Madatory value
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 | false    |
      | AF                 | false    |
      | AE                 | false    |
    And I create banning product Json file
      | gTIN        | brandChannel      | timestamp |
      | 11001390743 | catalogIdentifier |           |
    When I process the banning PIM files from Talend to WCS
    #Then Verify CommercialBanningPIM files into banning failure folder

  Scenario:9(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when misssing destinationCountry Madatory value
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      |                    | false    |
      |                    | false    |
      |                    | false    |
    And I create banning product Json file
      | gTIN        | brandChannel      | timestamp                         |
      | 11001390743 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    #Then Verify CommercialBanningPIM files into banning failure folder

  Scenario:10(WCSCMS-420)-Process Commercial Banning data from PIM to WCS when misssing sellable Madatory value
    When I process the products PIM files from Talend to WCS
    Given provided list of country for banning
      | destinationCountry | sellable |
      | AD                 |          |
      | AF                 |          |
      | AE                 |          |
    And I create banning product Json file
      | gTIN        | brandChannel      | timestamp                         |
      | 11001390743 | catalogIdentifier | 2016-08-18T17:22:53.0227855+02:00 |
    When I process the banning PIM files from Talend to WCS
    #Then Verify CommercialBanningPIM files into banning failure folder