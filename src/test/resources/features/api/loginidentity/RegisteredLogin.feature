@api
Feature: Sample CRUD operations to test a RESTful Service

  Scenario: Registered user login
    When I login as a registered User
    And I perform query "query.getUsers" on the users table
    Then the user exists with the correct values
    When I perform query "query.getAddress" on the address table
    Then the users self address contains the supplied registration data





