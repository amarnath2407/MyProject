Feature: This is to check the partNumber, mfpartNumber,field4 and extendedName under catalogEntryView in the Get products by category API response

  Scenario: This is to check the response returned by Get products by category API should contain the partNumber, mfpartNumber,field4 and extendedName
    Given I have the store identifiers "store.identifiers" with categories to check the catalogEntryView details
    Then I call the Get products by category API with categoryId and verify the response with the database

  Scenario: This is to check the response returned by Get products by category API should contain the partNumber, mfpartNumber,field4 and extendedName in different languages
    Given I have the store identifiers "store.identifiers" with categories and langId "plp.languages.id" to check the catalogEntryView details
    Then I call the Get products by category API with categoryId and langId then verify the response with the database