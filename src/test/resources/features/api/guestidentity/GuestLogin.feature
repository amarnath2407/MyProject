Feature: Creates identity tokens for a guest user
  @api
  Scenario: Guest user login
    When I login as a guest User
    And  I perform query "query.getUsers" for this user
    Then I exist with a USERS.REGISTERTYPE = G





