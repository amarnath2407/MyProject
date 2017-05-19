Feature: Vehicle Registration details page and validating details

  Scenario Outline: verify that correct vehicle details displayed
    Given user on gov-vehicle-information main page
    And  clicks on "Start now" button
    Then system should navigates to enter vehicle reg number page
    When user input the "<registration number>" and clicks "Continue" button
    Then verify that system displayed correct registration number
    And select "Yes" and click on "Continue" button
    Examples:
      | registration number |
      | GJ05OKC             |
      | EN627AU             |


