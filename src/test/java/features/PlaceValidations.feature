Feature: Validating Place APIs

  Scenario Outline: Verify if place is successfully added using Add Place API
    Given Add Place API with "<name>" "<language>" "<address>"
    When User calls "Add Place API" with POST request
    Then API call is successful with status code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"

    Examples:
      | name            | language   | address                       |
      | Frontline house | French-IN  | 29, side layout, cohen 09     |
      | test name 1     | Vietnamese | nguyen dinh chieu, Dictrict 1 |



