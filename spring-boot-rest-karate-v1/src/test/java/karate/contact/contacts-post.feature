Feature: Contact API Test

  Background:
    * url baseUrl

  Scenario: Read JSON Model and Print
    * def jsonModel = read('data/createNewRequest.json')
    * print jsonModel

  Scenario: Create new contact
    Given path '/api/v1/contacts'
    And header Content-Type = 'application/json'
    And def jsonModel = read('data/createNewRequest.json')
    And request jsonModel
    When method POST
    Then status 201
    And match response.firstName == 'Dummy'
    And match response.lastName == 'Person'
