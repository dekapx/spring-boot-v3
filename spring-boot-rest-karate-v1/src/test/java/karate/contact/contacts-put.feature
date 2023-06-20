@SmokeTest
Feature: Contact API Test

  Background:
    * url baseUrl

  Scenario: Update existing contact
    * def id = 2
    Given path '/api/v1/contacts/', id
    And header Content-Type = 'application/json'
    And request {id: '#(id)', 'firstName': 'Dummy1','lastName': 'Person1','email': 'dummy1.person1@google.com','phone': '+1 123 456 7890'}
    When method PUT
    Then status 200
    And match response.firstName == 'Dummy1'
    And match response.lastName == 'Person1'
