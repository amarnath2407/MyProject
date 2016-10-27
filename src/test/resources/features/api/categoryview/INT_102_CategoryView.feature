@api @integration  @INT_102_category
Feature: Get category data for search-based catalog navigation

  Scenario Outline: As a head if I call get findTopCategories and omit a mandatory parameter I will receive an error response
    When I call the findTopCategories method under protocol "<protocol>" omitting the store identifier
    Then All response fields are empty or null
    Examples:
      | protocol  |
      | http      |
      | https     |

  Scenario Outline: As a Head I should be able to get all top level categories for a given store
    When  I call the findTopCategories method with "<langId>" "<pageType>" "<designerCat>" and "<attrs>" parameters under protocol "<protocol>"
    Then  I get all top categories for this store for the supplied langId, pageType, designerCat
    And   Attributes are only returned when requested
    Examples:
      | langId  | pageType      | designerCat | attrs | protocol |
      | empty   | empty         | empty       | empty | http     |
      | -2      | empty         | empty       | empty | https    |
      | 44      | OtherPageType | empty       | empty | http     |
      | -2      | Designer      | empty       | empty | https    |
      | 44      | Designer      | Y           | empty | http     |
      | 44      | OtherPageType | Y           | true  | https    |
      | 44      | OtherPageType | empty       | true  | http     |

  Scenario Outline: As a head if I call findCategoryByIdentifier and omit a mandatory parameter I will receive an error response
    When I call the findCategoryByIdentifier method for a specific category identifier under protocol "<protocol>" omitting the store identifier
    Then All response fields are empty or null
    Examples:
      | protocol  |
      | http      |
      | https     |

  Scenario Outline: As a head I should be able to retrieve category information about a specific category identifier
    When I call the findCategoryByIdentifier method for a specific category identifier with parameters "<langId>" and "<attrs>" for category identifier "<categoryIdentifier>" under protocol "<protocol>"
    Then I get the correct category information
    And Category attributes are returned if requested
    Examples:
      | langId | attrs |categoryIdentifier  | protocol |
      | empty  | empty | with attributes    | http     |
      | 44     | false | with attributes    | https    |
      | empty  | true  | with attributes    | http     |
      | -2     | true  | without attributes | https    |
      | 44     | true  | with attributes    | http     |
      | -2     | false | with attributes    | https    |

  Scenario Outline: As a head if I call findCategoryByUniqueId and omit a mandatory parameter I will receive an error response
    When I call the findCategoryByUniqueId method for a specific category identifier under protocol "<protocol>" omitting "<omitted parameter>"
    Then All response fields are empty or null
    Examples:
      | protocol  |omitted parameter 	|
      | http      |storeIdentifier  	|
      | https     |categoryId			|

  Scenario Outline: As a head I should be able to retrieve category information about a specific category id
    When I call the findCategoryByUniqueId method for a specific category identifier with parameters "<langId>" and "<attrs>" for category identifier "<categoryId>" under protocol "<protocol>"
    Then I get the correct category information
    And Category attributes are returned if requested
    Examples:
      | langId | attrs |categoryId  		| protocol |
      | empty  | empty | with attributes    | http     |
      | 44     | false | with attributes    | https    |
      | empty  | true  | with attributes    | http     |
      | -2     | true  | without attributes | https    |
      | 44     | true  | with attributes    | http     |
      | -2     | false | with attributes    | https    |

  Scenario Outline: As a head if I call findCategoryByUniqueIds and omit a mandatory parameter I will receive an error response
    When I call the findCategoryByUniqueIds method for a specific category identifier under protocol "<protocol>" omitting "<omitted parameter>"
    Then All response fields are empty or null
    Examples:
      | protocol  |omitted parameter 	|
      | http      |storeIdentifier  	|
      | https     |categoryId			|

  Scenario Outline: As a head I should be able to retrieve category information about specific category ids
    When I call the findCategoryByUniqueIds method with query parameters "<catIds>"  "<langId>" and "<attrs>" under protocol "<protocol>"
    Then I get the correct category information
    And Category attributes are returned if requested
    Examples:
      | langId | attrs |catIds  		                                                          | protocol |
      | empty  | empty | with attributes                                                          | http     |
      | 44     | false | with attributes,with attributes,without attributes                       | https    |
      | empty  | true  | with attributes,without attributes,with attributes                       | http     |
      | -2     | true  | without attributes,without attributes,with attributes                    | https    |
      | 44     | true  | without attributes,without attributes,without attributes                 | http     |
      | -2     | false | with attributes,with attributes,with attributes                          | https    |