
Feature: This is to test the Physical store journey which includes Creation, updation and deletion

Scenario: (WCSCMS-/INT )As a customer I should be able to manage the physical store

  Given I have the store identifier "store.identifiers.create" to create  physical store
  Then Prepare the Payload and call the servicees Post,Put and delete Physical Store Data and check whether the new physical store operations are happening properly in database for the brand
