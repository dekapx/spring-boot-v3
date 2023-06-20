@SmokeTest
Feature: Application Health Check

  Background:
    * url baseUrl

  Scenario: Actuator Health UP
    Given path 'actuator/health'
    When method GET
    Then status 200
    And match response.status == 'UP'
