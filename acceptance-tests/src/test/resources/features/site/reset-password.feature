@ManageUsersResetPassword
Feature: Reset my password

  As an user
  I can reset my password from a password reset link

  Scenario: Verify password must be entered
    Given I navigate to the "set***REMOVED***/4db47b8d-00b7-4825-9f94-b61e2406fc27" url
    When I click "button"
    Then I should see the validation message for " ***REMOVED***
