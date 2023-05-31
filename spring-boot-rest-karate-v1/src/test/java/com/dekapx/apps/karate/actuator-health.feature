Feature: Application Health Check

  Background:
    * url baseUrl

  Scenario: Actuator Health UP
    Given path 'actuator/health'
    When method get
    Then status 200
    And match response.status == 'UP'
