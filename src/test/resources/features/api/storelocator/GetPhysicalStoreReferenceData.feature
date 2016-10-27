@regression
Feature:( WCSCMS-386/INT_ ) This is to test the attribute reference data related to physical store

  @smoke
  Scenario: ( WCSCMS-386/INT_ ) As a Customer I should be able to get all attribute service for any specific Store
    Given I have the store identifiers for store"store.identifiers"
    Then I call the attribute service and compare the attributes returned for the specific store against the database

  Scenario: ( WCSCMS-386/INT_ ) As a Customer I should be able to get all attribute service for any specific Store with language ID
    Given I have the store identifiers for store"store.identifiers" and language"stloc.languages.id"
    Then I call the attribute service by language ID and compare the attributes returned for the specific store against the database

  Scenario: ( WCSCMS-386/INT_ ) As a Customer I should be able to get all attribute service for any specific Store with locale
    Given I have the store identifiers for store"store.identifiers" and locale"stloc.locales"
    Then I call the attribute service by locale and compare the attributes returned for the specific store against the database
  @smoke
  Scenario Outline: ( WCSCMS-382/INT_ ) As a site user I should be unable to access the GetContactTypes service
    Given I have the store identifiers for store"<storeIdentifiers>"
    Then When I am logged in as a "<user type>" and call the GetContactTypes service without parameters for the current store I receive an error
    Examples:
      |user type   |  storeIdentifiers |
      | guest      | store.identifiers |
      | registered | store.identifiers |

  Scenario: ( WCSCMS-382/INT_ ) I should be unable to access the GetContactTypes service under http
    Given I have the store identifiers for store"store.identifiers"
    Then when I attempt to execute the GetContactTypes service under http as admin user I receive an error
  @smoke
  Scenario: ( WCSCMS-382/INT_ ) As an admin user when I call GetContactTypes with no parameters I should receive all contact types in the default language
    Given I have the store identifiers for store"store.identifiers"
    Then when I call GetContactTypes as admin user with no parameters I receive all contact types in the default language

  Scenario: ( WCSCMS-382/INT_ ) As an admin user when I call GetContactTypes with a locale parameter I receive the contact types in the associated language for that locale
    Given I have the store identifiers for store"store.identifiers" and locale"stloc.locales"
    Then when I call GetContactTypes as admin user with a locale parameter I receive the contact types in the associated language for that locale

  Scenario: ( WCSCMS-382/INT_ ) As an admin user when I call GetContactTypes with a language parameter I receive the contact types in the associated language for that language
    Given I have the store identifiers for store"store.identifiers" and language"stloc.languages.id"
    Then when I call GetContactTypes as admin user with a language parameter I receive the contact types in the associated language for that language

  Scenario: ( WCSCMS-382/INT_ ) As an admin user when I call GetContactTypes with both language parameter and locale parameter then language takes priority
    Given I have the store identifiers for store"store.identifiers" and language"stloc.languages.id" and locale "stloc.locales"
    Then when I call GetContactTypes as admin user with locale and language parameters then the language parameter takes priority

  Scenario Outline: ( WCSCMS-383/INT_ ) As a site user I should be unable to access the GetContactNames service
    Given I have the store identifiers for store"<storeIdentifiers>"
    Then When I am logged in as a "<user type>" and call the GetContactNames service without parameters for the current store I receive an error
  Examples:
  |user type   |  storeIdentifiers |
  | guest      | store.identifiers |
  | registered | store.identifiers |

  Scenario: ( WCSCMS-383/INT_ ) I should be unable to access the GetContactNames service under http
    Given I have the store identifiers for store"store.identifiers"
    Then when I attempt to execute the GetContactNames service under http as admin user I receive an error

  Scenario: ( WCSCMS-383/INT_ ) As an admin user when I call GetContactNames with no parameters I should receive all contact names in the default language
    Given I have the store identifiers for store"store.identifiers"
    Then when I call GetContactNames as admin user with no parameters I receive all contact names in the default language

  Scenario: ( WCSCMS-383/INT_ ) As an admin user when I call GetContactNames with a locale parameter I receive the contact names in the associated language for that locale
    Given I have the store identifiers for store"store.identifiers" and locale"stloc.locales"
    Then when I call GetContactNames as admin user with a locale parameter I receive the contact names in the associated language for that locale

  Scenario: ( WCSCMS-383/INT_ ) As an admin user when I call GetContactNames with a language parameter I receive the contact names in the associated language for that language
    Given I have the store identifiers for store"store.identifiers" and language"stloc.languages.id"
    Then when I call GetContactNames as admin user with a language parameter I receive the contact names in the associated language for that language

  Scenario: ( WCSCMS-383/INT_ ) As an admin user when I call GetContactNames with both language parameter and locale parameter then language takes priority
    Given I have the store identifiers for store"store.identifiers" and language"stloc.languages.id" and locale "stloc.locales"
    Then when I call GetContactames as admin user with locale and language parameters then the language parameter takes priority


