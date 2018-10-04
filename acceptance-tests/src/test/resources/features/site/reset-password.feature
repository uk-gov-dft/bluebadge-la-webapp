@ManageUsersResetPassword
Feature: Reset my password

  As an user
  I can reset my password from a password reset link

  Scenario: Verify password must be entered
    Given I navigate to the "set***REMOVED***/4db47b8d-00b7-4825-9f94-b61e2406fc27" page
    When I click on element "button" button
    Then I should see the validation message for " ***REMOVED***

  Scenario: Verify validation when using blacklisted password
    Given I navigate to the "set***REMOVED***/4db47b8d-00b7-4825-9f94-b61e2406fc27" page
    And   I type " ***REMOVED*** field by uipath
    And   I type " ***REMOVED*** field by uipath
    When I click on element "button" button
    Then I should see the validation message for "common  ***REMOVED***
    
  Scenario: Verify reset other users password
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    When I select an option "role.LA_ADMIN"
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    And I click on the first name link from users table
    Then I should see the title "User details"
    And I can click on element "resetPasswordSummaryText" link
    And I can click on element "resetPasswordButton" button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Create a new user" button
    