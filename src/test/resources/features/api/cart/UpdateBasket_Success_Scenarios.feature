#Feature: Update basket to increase or decrease the oredr items - WCSCMS-764
#
#  Background:
#    Given A user is on the site as a guest user
#    And verify order entry is NOT created for this user in ORDERS table
#    When below SKU's are added to cart
#      | partNumber   | quantity   |
#      | <partNumber.1> | 2 |
#      | <partNumber.2> | 3 |
#    Then the order is  created successfully
##    And a new order entry is  created for this user in the ORDERS table
#
#  Scenario: should be able to decrease order items in the cart
#
#    When cart details are requested for this user
#    Then the GET cart API response should have 5 total number of items in the cart
#
#    when 1 item "<partNumber.2>" is removed from the cart
#    When cart details are requested for this user
#    Then the GET cart API response should have 4 total number of items in the cart
#    And the GET cart API response should have order item count for "<partNumber.2>" is 2
#
#    when 1 item "<partNumber.2>" is removed from the cart
#    When cart details are requested for this user
#    Then the GET cart API response should have 3 total number of items in the cart
#    And the GET cart API response should have order item count for "<partNumber.2>" is 1
#
#
#  Scenario: should be able to increase oredr items in the cart
#    When cart details are requested for this user
#    Then the GET cart API response should have 5 total number of items in the cart
#
#    when 1 item "<partNumber.2>" is added to the cart
#    And cart details are requested for this user
#    Then the GET cart API response should have 6 total number of items in the cart
#    And the GET cart API response should have order item count for "<partNumber.2>" is 4
#
#
#  Scenario: should be able to empty cart
#    When cart details are requested for this user
#    Then the GET cart API response should have 5 total number of items in the cart
#
#    when 2 items "<partNumber.2>" are removed from the cart
#    When cart details are requested for this user
#    Then the GET cart API response should have 3 total number of items in the cart
#    And the GET cart API response should NOT have order item "<partNumber.2>"
#
#
#
#
#
