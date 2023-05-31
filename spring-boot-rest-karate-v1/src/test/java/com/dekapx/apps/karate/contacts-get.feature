Feature: Contact GET Feature

  Background:
    * url baseUrl

  Scenario: Find contact by ID
    * def id = 1
    Given path '/api/v1/contacts/', id
    When method get
    Then status 200
    And match response.firstName == 'Test'
    And match response.lastName == 'Person'
    And match response == {id: '#(id)', firstName: 'Test', lastName: 'Person', email: 'test.person@google.com', phone: '+353 089 123 4567'}

  Scenario: Find all contacts
    Given path '/api/v1/contacts'
    When method GET
    Then status 200
    And assert response.length == 1
    And match response[*].id == [1]
    And match response[0].firstName == 'Test'
    And match response[0].lastName == 'Person'
