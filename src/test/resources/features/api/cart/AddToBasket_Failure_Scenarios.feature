@apiTest @api @smoke
Feature: Add item to basket failure scenarios

  Background:
    Given A user is on the site as a guest user
    And verify order entry is NOT created for this user in ORDERS table

#ITEM TYPE
  Scenario: A PRODUCT item can't be added to basket
    When below json message is POST'ed onto the API end point /{storeId}/cart
  """
     {
      "orderItem": [
        {
          "productId": "<partNumber.5>",
          "quantity": "1.0"
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 400

  Scenario: A COLOR item can't be added to basket
    When below json message is POST'ed onto the API end point /{storeId}/cart
"""
     {
      "orderItem": [
        {
          "productId": "<partNumber.6>",
          "quantity": "1.0"
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 400

# GENERAL REST
  Scenario: item can't be added to basket with invalid request - non string values
    When below json message is POST'ed onto the API end point /{storeId}/cart
"""
     {
      "orderItem": [
        {
          "partNumber": <partNumber.1>,
          "quantity": 1.0
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 500

  Scenario: item can't be added to basket with empty payload
    When below json message is POST'ed onto the API end point /{storeId}/cart
    """
     {
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 500

  Scenario: item can't be added to basket with invalid store identifier
    When below json message is POST'ed onto the API end point /SUDA/cart
"""
     {
      "orderItem": [
        {
          "partNumber": "<partNumber.1>",
          "quantity": "1.0"
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 400

  Scenario: item can't be added to basket for a SKU with no stock
    When below json message is POST'ed onto the API end point /{storeId}/cart
"""
     {
      "orderItem": [
        {
          "partNumber": "<partNumber.4>",
          "quantity": "1.0"
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 400

  Scenario: item can't be added to basket with PART NUMBER which belongs to other stores
    And A guest user token is generated by guest identity service for the store "Valentino_FR"
    When below json message is POST'ed onto the API end point /Valentino_FR/cart
"""
     {
      "orderItem": [
        {
          "partNumber": "<partNumber.1>",
          "quantity": "1.0"
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 400

  Scenario: SKU can't be added to basket with ZERO quantity
    When below json message is POST'ed onto the API end point /{storeId}/cart
"""
     {
      "orderItem": [
        {
          "partNumber": "<partNumber.1>",
          "quantity": "0.0"
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 400

  Scenario: SKU can't be added to basket with NEGATIVE quantity value
    When below json message is POST'ed onto the API end point /{storeId}/cart
"""
     {
      "orderItem": [
        {
          "partNumber": "<partNumber.1>",
          "quantity": "-1.0"
        }
      ]
     }
    """
    Then a new order entry is NOT created for this user in the ORDERS table
    And the response should have HTTP status code 400