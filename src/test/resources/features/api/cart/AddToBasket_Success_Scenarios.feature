@apiTest @api @smoke
Feature: Add item to basket success scenarios
  @failing
  Scenario Outline: Successfully add an item (SKU) to basket with GUEST identity token
    Given A user is on the site as a guest user
    And verify order entry is NOT created for this user in ORDERS table
    When below json message is POST'ed onto the API end point /{storeId}/cart
    """
     {
      "orderItem": [
        {
          "partNumber": "<partNumber>",
          "quantity": "<quantity>"
        }
      ]
     }
    """
    Then the order is  created successfully
    Then a new order entry is  created for this user in the ORDERS table
    And the response should have HTTP status code 201
    And the response message body should be as below
    """
    {
      "orderItem": [
        {
          "orderItemId": "${orderItemId}"
        }
      ],
      "orderId": "${orderId}"
    }
    """

    Examples:
      | partNumber      | quantity |
      | <partNumber.1> | 2.0      |

  Scenario Outline: Adding a same product (SKU) to the basket should be merged to the existed SKU
    Given A user is on the site as a guest user
    And verify order entry is NOT created for this user in ORDERS table
    #-1
    When below SKU's are added to cart
      | partNumber   | quantity   |
      | <partNumber> | <quantity> |

    Then the order is  created successfully
    And a new order entry is  created for this user in the ORDERS table
    #-2
    When below SKU's are added to the existed cart
      | partNumber    | quantity |
      | <partNumber> | <quantity> |
    Then the order item is successfully added to the already existed cart
    And this SKU should be added to the already existed Order
    And the order items should have merged to the already existed Order item

    Examples:
      | partNumber    | quantity |
      | 0400240168209 | 2.0      |
#      | 0400346793275 | 1        |









