@actuators
Feature: Dft BlueBadge Actuators

  As an system admin user
  I want to check service status in LA webapp
  So that I can manage LA webapp services efficiently

  Scenario: Verify actuators info page
    Given I navigate to the actuator's "info" page
    Then I should not see actuator's error page
    And I should see status code as 200

  Scenario: Verify actuators health page
    Given I navigate to the actuator's "health" page
    Then I should not see actuator's error page
    And I should see status code as 200
    And I should see status as "UP"

  Scenario: Verify actuators loggers page
    Given I navigate to the actuator's "loggers" page
    Then I should not see actuator's error page
    And I should see status code as 200