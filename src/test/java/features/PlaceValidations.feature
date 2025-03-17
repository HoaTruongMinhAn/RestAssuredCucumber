Feature: Validating Place APIs
  Scenario: Verify if place is successfully added using Add Place API
    Given Add Place API
    When User calls "Add Place API" with POST request
    Then API call is successful with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"