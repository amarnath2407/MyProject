#Feature: Get Cart API should have PDP GET API details + Total Quantity of cart - WCSCMS-763
#
#  Scenario: GET cart details response should be empty for a user with no cart attached [user with NO order object ]
#    Given A user is on the site as a guest user
#    And verify order entry is NOT created for this user in ORDERS table
#    When cart details are requested for this user
#    And the response should have HTTP status code 404
#
#  Scenario: cart details response should include all product details - All success
#    Given A user is on the site as a guest user
#    And verify order entry is NOT created for this user in ORDERS table
#    When below SKU's are added to cart
#      | partNumber   | quantity   |
#      | <partNumber.1> | 5 |
#      | <partNumber.2> | 3 |
#
#    Then the order is  created successfully
#    And a new order entry is  created for this user in the ORDERS table
#    When cart details are requested for this user
#    Then the GET cart API response should have 8 total number of items in the cart
#    Then the GET cart API response should include all product details
#
#  Scenario: GET cart details response should have empty list of order items inside an order - when cart is emptied by removing items from cart
#      Given A user is on the site as a guest user
#      And verify order entry is NOT created for this user in ORDERS table
#      When below SKU's are added to cart
#        | partNumber   | quantity   |
#        | <partNumber.1> | 1 |
#        | <partNumber.2> | 1 |
#
#      Then the order is  created successfully
#      And a new order entry is  created for this user in the ORDERS table
#      When cart details are requested for this user
#      Then the GET cart API response should have 2 total number of items in the cart
#      Then the GET cart API response should include all product details
#      ###NOW CALLING UPDATE CART API
#      When the cart is emptied for this user
#      And When cart details are requested for this user
#      Then the GET cart API response should have an order with empty list of items
#
#
#  Scenario: cart details response should include all product details but NOT the items failed to add to cart
#    Given A user is on the site as a guest user
#    And verify order entry is NOT created for this user in ORDERS table
#    When below SKU's are added to cart
#      | partNumber   | quantity   |
#      | <partNumber.1> | 1 |
##      this item is out of stock. just to generate error
#      | <partNumber.4> | 1 |
#
#    Then the order is  created successfully
#    And a new order entry is  created for this user in the ORDERS table
#    When cart details are requested for this user
#    Then the GET cart API response should have 1 total number of items in the cart
#    Then the GET cart API response should include all product details
#    And the GET cart API response should NOT include the item "<partNumber.1>" details
#
##    NOT SURE HOW TO GENERATE PDP ERRORS FOR AUTOMATION
#  Scenario: cart details response should include product details AND errors for items which have an issue while getting product details
#     Given A user is on the site as a guest user
#     And verify order entry is NOT created for this user in ORDERS table
#     When below SKU's are added to cart
#       | partNumber   | quantity   |
#       | <partNumber.1> | 1 |
#       | <partNumber.2> | 1 |
#
#     Then the order is  created successfully
#     And a new order entry is  created for this user in the ORDERS table
#     When cart details are requested for this user
#     Then the GET cart API response should have 2 total number of items in the cart
#     Then the GET cart API response should include all product details for item "<partNumber.1>"
#     Then the GET cart API response should have error details displayed for item "<partNumber.2>"
#
#
#
#
#
#
