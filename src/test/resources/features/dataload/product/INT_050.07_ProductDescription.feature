@dataload
Feature: This is a feature for INT_050.07_Product Description PIM Dataload.

  #This section covers scenarios for successful load of product/ productcolour/ SKU descriptions for a given brand and language

  Background:
    Given I create Product PIM file
      | catalogIdentifier | productType   | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID      | yooxCode10    | gtin          | ean             | delete | modelId  | variantId   |
      | catalogIdentifier | Product       | manufacturer | 1        | 11001390             | MF11001390 |             |               |               |                 | 0      | 11001390 |             |
      | catalogIdentifier | SKU           | manufacturer | 2        |                      |            |             |               | 0400240168209 | MF0400240168209 | 0      | 11001390 | 11001390743 |
      | catalogIdentifier | ProductColour | manufacturer | 6405     |                      |            | 11001390743 | MF11001390743 |               |                 | 0      | 11001390 | 11001390743 |
      | catalogIdentifier | Product       | manufacturer | 16       | 11001400             | MF11001400 |             |               |               |                 | 0      | 11001400 |             |
      | catalogIdentifier | SKU           | manufacturer | 17       |                      |            |             |               | 0400298690134 | MF0400298690134 | 0      | 11001400 | 110014003   |
      | catalogIdentifier | SKU           | manufacturer | 18       |                      |            |             |               | 0400406162218 | MF0400406162218 | 0      | 11001400 | 110014002   |
      | catalogIdentifier | SKU           | manufacturer | 20       |                      |            |             |               | 0400122951707 | MF0400122951707 | 0      | 11001400 | 110014003   |
      | catalogIdentifier | SKU           | manufacturer | 21       |                      |            |             |               | 0400468205076 | MF0400468205076 | 0      | 11001400 | 110014002   |
      | catalogIdentifier | SKU           | manufacturer | 22       |                      |            |             |               | 0400497037266 | MF0400497037266 | 0      | 11001400 | 110014002   |
      | catalogIdentifier | ProductColour | manufacturer | 6407     |                      |            | 110014003   | MF110014003   |               |                 | 0      | 11001400 | 110014003   |
      | catalogIdentifier | ProductColour | manufacturer | 6408     |                      |            | 110014002   | MF110014002   |               |                 | 0      | 11001400 | 110014002   |

  Scenario:1-(WCSCMS-66)Successful Product Description dataload for a 'Product' for a given language
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | sizeAndFit                                                | longDescription                                           | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation
      | languageId | partNumber | name | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | shortDescription | sizeAndFit                                                | longDescription                                           | auxDescription1 | auxDescription2 | published | delete | parentStoreIdentifier |
      | 44         | 11001390   |      | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur |                  | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur |                 |                 | 1         | 0      | parentStoreIdentifier |
    And The product description data is loaded into database successfully
      | languageId | partNumber | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | sizeAndFit                                                | longDescription                                           | published |
      | 44         | 11001390   | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | 1         |

  Scenario:2-(WCSCMS-66)Successful Product Description dataload for a 'ProductColour' for a given language
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type          | language | extendedName                          | editorialDescription                  | detailsAndCare                        | sizeAndFit                            | longDescription       | delete | modelId  | variantId   |
      |      | catalogIdentifier | ProductColour | en_GB    | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch gewebt ohne Pelz | 0      | 11001390 | 11001390743 |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation
      | languageId | partNumber  | name | extendedName                          | editorialDescription                  | detailsAndCare                        | shortDescription | sizeAndFit                            | longDescription       | auxDescription1 | auxDescription2 | published | delete | parentStoreIdentifier |
      | 44         | 11001390743 |      | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz |                  | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch gewebt ohne Pelz |                 |                 | 1         | 0      | parentStoreIdentifier |
    And The product description data is loaded into database successfully
      | languageId | partNumber  | extendedName                          | editorialDescription                  | detailsAndCare                        | sizeAndFit                            | longDescription       | published |
      | 44         | 11001390743 | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch gewebt ohne Pelz | 1         |

  Scenario:3-(WCSCMS-66)Successful Product Description dataload for a 'SKU' for a given language
    Given I create Product Description PIM file
      | gtin          | catalogIdentifier | type | language | extendedName                               | editorialDescription                  | detailsAndCare                        | sizeAndFit                            | longDescription       | delete | modelId  | variantId   |
      | 0400240168209 | catalogIdentifier | SKU  | en_GB    | Tuch Gummisohle Tuch gewebt ohne Pelz SKU1 | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch gewebt ohne Pelz | 0      | 11001390 | 11001390743 |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation
      | languageId | partNumber    | name | extendedName                               | editorialDescription                  | detailsAndCare                        | shortDescription | sizeAndFit                            | longDescription       | auxDescription1 | auxDescription2 | published | delete | parentStoreIdentifier |
      | 44         | 0400240168209 |      | Tuch Gummisohle Tuch gewebt ohne Pelz SKU1 | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz |                  | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch gewebt ohne Pelz |                 |                 | 1         | 0      | parentStoreIdentifier |
    And The product description data is loaded into database successfully
      | languageId | partNumber    | extendedName                               | editorialDescription                  | detailsAndCare                        | sizeAndFit                            | longDescription       | published |
      | 44         | 0400240168209 | Tuch Gummisohle Tuch gewebt ohne Pelz SKU1 | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch gewebt ohne Pelz | 1         |

  Scenario:4-(WCSCMS-66)Successful Product Description dataload for a language that requires special character set
    Given I create Product Description PIM file
      | gtin          | catalogIdentifier | type          | language | extendedName                         | editorialDescription                            | detailsAndCare                                                                  | sizeAndFit                                                                      | longDescription                                                                 | delete | modelId  | variantId   |
      |               | catalogIdentifier | Product       | ru_RU    | Плотная ткань Резиновая подошва      | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | 0      | 11001390 |             |
      |               | catalogIdentifier | ProductColour | ru_RU    | Плотная ткань Резиновая подошва шва  | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | 0      | 11001390 | 11001390743 |
      | 0400240168209 | catalogIdentifier | SKU           | ru_RU    | Плотная ткань Резиновая подошва шва1 | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | 0      | 11001390 | 11001390743 |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation
      | languageId | partNumber    | name | extendedName                         | editorialDescription                            | detailsAndCare                                                                  | shortDescription | sizeAndFit                                                                      | longDescription                                                                 | auxDescription1 | auxDescription2 | published | delete | parentStoreIdentifier |
      | -20        | 11001390      |      | Плотная ткань Резиновая подошва      | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки |                  | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -20        | 11001390743   |      | Плотная ткань Резиновая подошва шва  | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки |                  | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -20        | 0400240168209 |      | Плотная ткань Резиновая подошва шва1 | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки |                  | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки |                 |                 | 1         | 0      | parentStoreIdentifier |
    And The product description data is loaded into database successfully
      | languageId | partNumber    | extendedName                         | editorialDescription                            | detailsAndCare                                                                  | sizeAndFit                                                                      | longDescription                                                                 | published |
      | -20        | 11001390      | Плотная ткань Резиновая подошва      | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | 1         |
      | -20        | 11001390743   | Плотная ткань Резиновая подошва шва  | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | 1         |
      | -20        | 0400240168209 | Плотная ткань Резиновая подошва шва1 | Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | Плотная ткань Резиновая подошва Плотная ткань Тканые детали Без меховой отделки | 1         |

  Scenario:5-(WCSCMS-66)Successful Product Description dataload with data that has double quotes in a description field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName                                                 | editorialDescription                                         | detailsAndCare                                               | sizeAndFit                                                   | longDescription                                              | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | de_DE    | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | 0      | 11001400 |           |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation
      | languageId | partNumber | name | extendedName                                                 | editorialDescription                                         | detailsAndCare                                               | shortDescription | sizeAndFit                                                   | longDescription                                              | auxDescription1 | auxDescription2 | published | delete | parentStoreIdentifier |
      | -3         | 11001400   |      | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz |                  | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle ""Leder"" - ohne Pelz |                 |                 | 1         | 0      | parentStoreIdentifier |
    And The product description data is loaded into database successfully
      | languageId | partNumber | extendedName                                               | editorialDescription                                       | detailsAndCare                                             | sizeAndFit                                                 | longDescription                                            | published |
      | -3         | 11001400   | Logo Leder Schnürverschluss Gummisohle "Leder" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle "Leder" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle "Leder" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle "Leder" - ohne Pelz | Logo Leder Schnürverschluss Gummisohle "Leder" - ohne Pelz | 1         |

  Scenario:6-(WCSCMS-66)Successful combination of actions - update of Product Description record and new records
    Given I create Product Description PIM file
      | gtin          | catalogIdentifier | type          | language | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | sizeAndFit                                                | longDescription                                           | delete | modelId  | variantId |
      |               | catalogIdentifier | Product       | de_DE    | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | 0      | 11001400 |           |
      |               | catalogIdentifier | ProductColour | de_DE    | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | 0      | 11001400 | 110014003 |
      |               | catalogIdentifier | ProductColour | de_DE    | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | 0      | 11001400 | 110014002 |
      | 0400298690134 | catalogIdentifier | SKU           | de_DE    | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | 0      | 11001400 | 110014003 |
      | 0400406162218 | catalogIdentifier | SKU           | de_DE    | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | 0      | 11001400 | 110014002 |
      | 0400122951707 | catalogIdentifier | SKU           | de_DE    | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | 0      | 11001400 | 110014003 |
      | 0400468205076 | catalogIdentifier | SKU           | de_DE    | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | 0      | 11001400 | 110014002 |
      | 0400497037266 | catalogIdentifier | SKU           | de_DE    | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | 0      | 11001400 | 110014002 |
    When I process the products PIM files from Talend to WCS
    Then Talend performs correct transformation
      | languageId | partNumber    | name | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | shortDescription | sizeAndFit                                                | longDescription                                           | auxDescription1 | auxDescription2 | published | delete | parentStoreIdentifier |
      | -3         | 11001400      |      | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz |                  | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz | Logo Leder Schnrverschluss Gummisohle ""Leder"" ohne Pelz |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -3         | 110014003     |      | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                  | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -3         | 110014002     |      | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                  | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -3         | 0400298690134 |      | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                  | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -3         | 0400406162218 |      | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                  | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -3         | 0400122951707 |      | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                  | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -3         | 0400468205076 |      | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                  | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                 |                 | 1         | 0      | parentStoreIdentifier |
      | -3         | 0400497037266 |      | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                  | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz   |                 |                 | 1         | 0      | parentStoreIdentifier |
    And The product description data is loaded into database successfully
      | languageId | partNumber    | extendedName                                            | editorialDescription                                    | detailsAndCare                                          | sizeAndFit                                              | longDescription                                         | published |
      | -3         | 11001400      | Logo Leder Schnrverschluss Gummisohle "Leder" ohne Pelz | Logo Leder Schnrverschluss Gummisohle "Leder" ohne Pelz | Logo Leder Schnrverschluss Gummisohle "Leder" ohne Pelz | Logo Leder Schnrverschluss Gummisohle "Leder" ohne Pelz | Logo Leder Schnrverschluss Gummisohle "Leder" ohne Pelz | 1         |
      | -3         | 110014003     | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | 1         |
      | -3         | 110014002     | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | 1         |
      | -3         | 0400298690134 | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | 1         |
      | -3         | 0400406162218 | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | 1         |
      | -3         | 0400122951707 | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | 1         |
      | -3         | 0400468205076 | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | 1         |
      | -3         | 0400497037266 | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | Logo Leder Schnrverschluss Gummisohle Leder - ohne Pelz | 1         |

  Scenario:7-(WCSCMS-66)Successful combination of actions - Delete a Product Description record and create new records
    Given I create Product Description PIM file
      | gtin          | catalogIdentifier | type | language | extendedName                        | editorialDescription                | detailsAndCare                      | sizeAndFit                          | longDescription                     | delete | modelId  | variantId |
      | 0400406162218 | catalogIdentifier | SKU  | en_GB    | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | 0      | 11001400 | 110014002 |
    When I process the products PIM files from Talend to WCS
    And The product description data is loaded into database successfully
      | languageId | partNumber    | extendedName                        | editorialDescription                | detailsAndCare                      | sizeAndFit                          | longDescription                     | published |
      | 44         | 0400406162218 | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | 1         |
    And I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID | yooxCode10 | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 16       | 11001400             | MF11001400 |        |            |               |                 | 0      | 11001400 |           |
      | catalogIdentifier | SKU         | manufacturer | 18       |                      |            |        |            | 0400406162218 | MF0400406162218 | 0      | 11001400 | 110014002 |
    And I create Product Description PIM file
      | gtin          | catalogIdentifier | type | language | extendedName                        | editorialDescription                | detailsAndCare                      | sizeAndFit                          | longDescription                     | delete | modelId  | variantId |
      | 0400406162218 | catalogIdentifier | SKU  | en_GB    | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | Plain weave Rubber sole Plain weave | 1      | 11001400 | 110014002 |
    And I process the products PIM files from Talend to WCS
    And The product description data is deleted into database successfully
      | languageId | partNumber    |
      | 44         | 0400406162218 |
    And I create Product PIM file
      | catalogIdentifier | productType | manufacturer | sequence | modelFabricBrandCode | yooxCode8  | nappID | yooxCode10 | gtin          | ean             | delete | modelId  | variantId |
      | catalogIdentifier | Product     | manufacturer | 16       | 11001400             | MF11001400 |        |            |               |                 | 0      | 11001400 |           |
      | catalogIdentifier | SKU         | manufacturer | 18       |                      |            |        |            | 0400406162218 | MF0400406162218 | 0      | 11001400 | 110014002 |
    And I create Product Description PIM file
      | gtin          | catalogIdentifier | type | language | extendedName                                                | editorialDescription                                        | detailsAndCare                                              | sizeAndFit                                                  | longDescription                                             | delete | modelId  | variantId |
      | 0400406162218 | catalogIdentifier | SKU  | en_GB    | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | 0      | 11001400 | 110014002 |
    When I process the products PIM files from Talend to WCS
    Then The product description data is deleted and recreated into database successfully
      | languageId | partNumber    | extendedName                                                | editorialDescription                                        | detailsAndCare                                              | sizeAndFit                                                  | longDescription                                             | published | delete |
      | 44         | 0400406162218 | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | Plain weave Rubber sole Plain weave - deleted and recreated | 1         | 0      |

  Scenario:8-Process Product Description PIM file missing a GTIN field
    Given I create Product Description PIM file
      | catalogIdentifier | type    | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  | variantId |
      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:9-Process Product Description PIM file missing a CatalogIdentifier field
    Given I create Product Description PIM file
      | gtin | type    | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  | variantId |
      |      | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:10-Process Product Description PIM file missing a Type field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  | variantId |
      |      | catalogIdentifier | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:11-Process Product Description PIM file missing a Language field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:12-Process Product Description PIM file missing a ExtendedName field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:13-Process Product Description PIM file missing a EditorialDescription field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:14-Process Product Description PIM file missing a DetailsAndCare field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | editorialDescription | sizeAndFit  | longDescription | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:15-Process Product Description PIM file missing a SizeAndFit field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | editorialDescription | detailsAndCare | longDescription | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave     | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:16-Process Product Description PIM file missing a LongDescription field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:17-Process Product Description PIM file missing a Delete field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:18-Process Product Description PIM file missing a MODELID field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:19-Process Product Description PIM file missing a VARIANTID field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:20-Process Product Description PIM file With an extra field
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName | editorialDescription | detailsAndCare | sizeAndFit  | longDescription | delete | modelId  | variantId | ExtraField |
      |      | catalogIdentifier | Product | en_GB    | Plain weave  | Plain weave          | Plain weave    | Plain weave | Plain weave     | 0      | 11001390 |           | AutoTest   |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

   #This section covers failure scenarios product/ productcolour/ SKU descriptions for a given brand and language
#Commenting because of Schema validation tests are invalid #WCSCMS-392
#  Scenario:21-(WCSCMS-256) Successful failure when mandatory fields missing values (CatalogIdentifier,Type,Language,Delete)
#    Given I create Product Description PIM file
#      | gtin | catalogIdentifier | type    | language | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | sizeAndFit                                                | longDescription                                           | delete | modelId  | variantId |
#      |      |                   | Product | en_GB    | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | 0      | 11001390 |           |
#      |      | catalogIdentifier |         | en_GB    | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | 0      | 11001390 |           |
#      |      |                   | Product |          | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | 0      | 11001390 |           |
#      |      | catalogIdentifier | Product | en_GB    | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | F      | 11001390 |           |
#    When I process the products PIM files from Talend to WCS
#    Then Verify ProductPIM files into failure folder
#    And Verify ProductDescriptionPIM files into failure folder

  Scenario:22-(WCSCMS-256) Successful failure when mandatory fields missing values (GTIN,MODELID,VARIANTID when Type=SKU, MODELID,VARIANTID when Type=ProductColour)
    Given I create Product Description PIM file
      | gtin          | catalogIdentifier | type          | language | extendedName                               | editorialDescription                  | detailsAndCare                        | sizeAndFit                                            | longDescription       | delete | modelId | variantId   |
      |               | Moncler           | ProductColour | en_GB    | Tuch Gummisohle Tuch gewebt ohne Pelz      | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz Tuch Gummisohle | Tuch gewebt ohne Pelz | 0      |         | 11001390743 |
      | 0400240168209 | catalogIdentifier | SKU           | en_GB    | Tuch Gummisohle Tuch gewebt ohne Pelz SKU1 | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz | Tuch Gummisohle Tuch gewebt ohne Pelz Tuch Gummisohle | Tuch gewebt ohne Pelz | 0      |         |             |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:23-(WCSCMS-256) Successful failure when description record provided for missing product/colour/SKU
    Given I create Product Description PIM file
      | gtin | catalogIdentifier | type    | language | extendedName                | editorialDescription        | detailsAndCare              | sizeAndFit                  | longDescription             | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | This product does not exist | This product does not exist | This product does not exist | This product does not exist | This product does not exist | 0      | 11001111 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:24-(WCSCMS-256)Create and Process Product Description PIM file with comma delimiter
    Given I create Product Description PIM file with comma delimiter
      | gtin | catalogIdentifier | type    | language | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | sizeAndFit                                                | longDescription                                           | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

  Scenario:25-(WCSCMS-256)Create and Process Product Description PIM file with header row wrong order
    Given I create Product Description PIM file with header row wrong order
      | gtin | catalogIdentifier | type    | language | extendedName                                              | editorialDescription                                      | detailsAndCare                                            | sizeAndFit                                                | longDescription                                           | delete | modelId  | variantId |
      |      | catalogIdentifier | Product | en_GB    | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | Plain weave Rubber sole Plain weave Woven not made of fur | 0      | 11001390 |           |
    When I process the products PIM files from Talend to WCS
    Then Verify ProductPIM files into failure folder
    And Verify ProductDescriptionPIM files into failure folder

