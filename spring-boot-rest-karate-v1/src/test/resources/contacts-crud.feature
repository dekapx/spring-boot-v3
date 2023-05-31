#Feature: Contact API Test
#
#  Background:
#    * url baseUrl
#
#  Scenario: Create new contact
#    Given path '/api/v1/contacts'
#    And header Content-Type = 'application/json'
#    And request {'firstName': 'Dummy','lastName': 'Person','email': 'dummy.person@google.com','phone': '+1 123 456 7890'}
#    When method POST
#    Then status 201
#    And match response.firstName == 'Dummy'
#    And match response.lastName == 'Person'
#
#  Scenario: Update existing contact
#    * def id = 2
#    Given path '/api/v1/contacts/', id
#    And header Content-Type = 'application/json'
#    And request {id: '#(id)', 'firstName': 'Dummy1','lastName': 'Person1','email': 'dummy1.person1@google.com','phone': '+1 123 456 7890'}
#    When method PUT
#    Then status 200
#    And match response.firstName == 'Dummy1'
#    And match response.lastName == 'Person1'
#
#  Scenario: Find contact by ID
#    * def id = 1
#    Given path '/api/v1/contacts/', id
#    And header Content-Type = 'application/json'
#    When method GET
#    Then status 200
#    And match response.firstName == 'Test'
#    And match response.lastName == 'Person'
#    And match response == {id: '#(id)', firstName: 'Test', lastName: 'Person', email: 'test.person@google.com', phone: '+353 089 123 4567'}
#
#  Scenario: Find all contacts
#    Given path '/api/v1/contacts'
#    And header Content-Type = 'application/json'
#    When method GET
#    Then status 200
#    And assert response.length == 2
#    And match response[*].id == [1, 2]
#    And match response[0].firstName == 'Test'
#    And match response[0].lastName == 'Person'
