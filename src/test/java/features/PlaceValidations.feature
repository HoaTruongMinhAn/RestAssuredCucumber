Feature: Validating Place APIs

  @AddPlace @Regression
  Scenario Outline: Verify if place is successfully added using Add Place API
    Given Add Place API with "<name>" "<language>" "<address>"
    When User calls "AddPlaceAPI" with "POST" request
    Then API call is successful with status code 200 and content type "application/json"
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    When GetPlaceAPI with the created place_id
    And User calls "GetPlaceAPI" with "GET" request
    Then Verify place_Id created maps to "<name>" "<language>" "<address>" using "GetPlaceAPI"

    Examples:
      | name            | language   | address                       |
      | Frontline house | French-IN  | 29, side layout, cohen 09     |
      | test name 1     | Vietnamese | nguyen dinh chieu, Dictrict 1 |

  @DeletePlace @Regression
  Scenario: Verify if Delete Place API is working
    Given Delete Place API
    When User calls "DeletePlaceAPI" with "DELETE" request
    Then API call is successful with status code 200 and content type "application/json"
    And "status" in response body is "OK"
      