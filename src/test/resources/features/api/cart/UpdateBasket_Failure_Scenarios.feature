#Feature: Update basket to increase or decrease the oredr items - WCSCMS-764
#
#  Background:
#    Given A user is on the site as a guest user
#    And verify order entry is NOT created for this user in ORDERS table
#    When below SKU's are added to cart
#      | partNumber     | quantity |
#      | <partNumber.1> | 2        |
#      | <partNumber.2> | 3        |
#
#  Scenario: should include proper error messages along with rest of the details when UPDATE order fails for one item
#    When cart details are requested for this user
#    Then the GET cart API response should have 5 total number of items in the cart
#
#    when 5 items "<partNumber.2>" are updated to the cart
#    Then the the response should include
#
#    And cart details are requested for this user
#    Then the GET cart API response should have 6 total number of items in the cart
#    And the GET cart API response should have order item count for "<partNumber.2>" is 4
