Feature: This is to do the physical store CRUD operations -- GET, POST, UPDATE, DELETE

@smoke @regression
Scenario:(WCSCMS-/INT ) As a Customer I should be able to get the physical store details with store identifier and Boutique Identifier
  Given I have the store identifier "store.identifiers" and boutique identifier to get the physical store data
  Then I call the service Get Physical Store Data and compare the response returned from the API with the database
@regression
Scenario:(WCSCMS-/INT )  As a Customer I should be able to get the physical store details with store identifier, Boutique Identifier and query parameter locale
  Given I have the store identifier "store.identifiers" , boutique identifier  and locale "stloc.locales" to get the physical store data
  Then I call the service Get Physical Store Data with query parameter locale and compare the response returned from the API with the database
@regression
Scenario:(WCSCMS-/INT ) As a Customer I should be able to get the physical store details with store identifier, Boutique Identifier and query parameter locale and langId
  Given I have the store identifier "store.identifiers" , boutique identifier  and locale "stloc.locales" and langId "stloc.languages.id" to get the physical store data
  Then I call the service Get Physical Store Data with query parameter locale and langId and compare the response returned from the API with the database
@regression
Scenario:(WCSCMS-/INT )  As a Customer I should be able to get the physical store details with store identifier and wrong Boutique Identifier
  Given I have the store identifier "store.identifiers" and wrong "stloc.boutique.neg.identifier" to get the physical store data
  Then I call the service Get Physical Store Data with wrong boutique identifier and compare the error response returned from the API

Scenario:(WCSCMS-/INT ) As a Customer I should be able to create new physical store for store identifier
  Given I have the store identifier "store.identifiers.create" to create new physical store
  Then I have the Payload generated and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand

Scenario:(WCSCMS-/INT ) As a Customer I should be able to create new physical store for store identifier with attributes
  Given I have the store identifier "store.identifiers.create" to create new physical store
  Then I have the Payload generated with attributes and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand

Scenario:(WCSCMS-/INT ) As a Customer I should be able to create new physical store for store identifier with attributes and contacts
  Given I have the store identifier "store.identifiers.create" to create new physical store
  Then I have the Payload generated with attributes and contacts and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand

Scenario:(WCSCMS-/INT ) As a Customer I should be able to create new physical store for store identifier with attributes,contacts and opening times
  Given I have the store identifier "store.identifiers.create" to create new physical store
  Then I have the Payload generated with attributes,contacts and opening times and call the service Post Physical Store Data create and check whether the new physical store is created in database for the brand

Scenario:(WCSCMS-/INT ) As a Customer I should be able to update the physical store with store identifier and Boutique Identifier
  Given I have the store identifier "store.identifiers.update" with boutique identifier "store.boutique.update.identifier" to update physical store
  Then I call the service Put Physical Store and check whether the physical store is updated successfully from the database

Scenario:(WCSCMS-/INT ) As a Customer I should be able to update the physical store with store identifier and Boutique Identifier with atributes
  Given I have the store identifier "store.identifiers.update" with boutique identifier "store.boutique.update.identifier" to update physical store
  Then I call the service Put Physical Store with attributes and check whether the physical store is updated successfully in the database

Scenario:(WCSCMS-/INT ) As a Customer I should be able to update the physical store with store identifier and wrong Boutique Identifier with atributes
  Given I have the store identifier "store.identifiers.update" with boutique identifier "store.boutique.update.wrong.identifier" to update physical store
  Then I call the service Put Physical Store with attributes and check whether the physical store is giving the proper error message

Scenario:(WCSCMS-/INT ) As a Customer I should be able to update the physical store with store identifier and Boutique Identifier with atributes and contacts
  Given I have the store identifier "store.identifiers.update" with boutique identifier "store.boutique.update.identifier" to update physical store
  Then I call the service Put Physical Store with attributes and contacts and check whether the physical store is updated successfully in the database

Scenario:(WCSCMS-/INT ) As a Customer I should be able to update the physical store with store identifier and Boutique Identifier with atributes,contacts and opening times
  Given I have the store identifier "store.identifiers.update" with boutique identifier "store.boutique.update.identifier" to update physical store
  Then I call the service Put Physical Store with attributes,contacts and opening times and check whether the physical store is updated successfully in the database

Scenario:(WCSCMS-/INT ) As a Customer I should be able to delete the physical store with store identifier and Boutique Identifier
  Given I have the store identifier "store.identifiers.create" with boutique identifier "store.boutique.identifier" to delete physical store
  Then I call the service Delete Physical Store and check whether the physical store is deleted successfully from the database