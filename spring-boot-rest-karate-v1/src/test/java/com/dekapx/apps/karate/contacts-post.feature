Feature: Contact API Test

  Background:
    * url baseUrl

  Scenario: Create new contact
    Given path '/api/v1/contacts'
    And request {'firstName': 'Dummy','lastName': 'Person','email': 'dummy.person@google.com','phone': '+1 123 456 7890'}
    When method post
    Then status 201
    And match response.firstName == 'Dummy'
    And match response.lastName == 'Person'
