#language: en
#Author: Marcelo Ribelato
#Version: 1.0


@postTransaction
Feature: Post Transaction

  @done
  Scenario: Post Transaction withdrawal
    Given that I have a valid MP token
    When I send a post request with all valid data
    Then the transaction data should be displayed
    And the Status code should be "202"

  @done
  Scenario Outline: Post Transaction withdrawal with missing data
    Given that I have a valid MP token
    When I send a post request without "<field>" for "<scenario>"
    Then the transaction data should not be displayed
    And the Status code should be "<status_code>"
    Examples:
      | scenario                   | field              | status_code |
      | Without externalIdentifier | externalIdentifier | 400         |

  @done
  Scenario Outline: Post Transaction withdrawal with invalid data 400
    Given that I have a valid MP token
    When I send a post request with invalid "<value>" for "<field>" for "<scenario>"
    Then the transaction data should be displayed
    And the Status code should be "<status_code>"
    Examples:
      | scenario                    | value                                | field           | status_code |
      | Nonexistent accountHolderId | 495b778d-dca5-40a7-8c1c-72e28b55c311 | accountHolderId | 202         |


  @done
  Scenario: Post Transaction status with invalid authentication token
    Given that I have a invalid MP token
    When I send a post request with all valid data
    Then the transaction data should not be displayed
    And the Status code should be "401"

  @done
  Scenario: Post Transaction status without authentication token
    Given that I have no MP token
    When I send a post request with all valid data
    Then the transaction data should not be displayed
    And the Status code should be "401"
