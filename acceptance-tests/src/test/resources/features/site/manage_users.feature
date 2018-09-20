@ManageUsers
Feature: Dft BlueBadge LA Manage Users

  As an admin user
  I want to create,remove,modify a new user in LA webapp
  So that the new users can login to LA webapp

  Scenario: Verify Create a new user
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Create a new user" button
    Then I should see the page titled "Create a new user - GOV.UK Manage Blue Badges"
    When I enter full name and email address and clicks on create a new user button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see the newly created user is on the users list

  Scenario: Verify Create a new user with an invalid full name
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    When I type " " for "name" field
    And I type "test@dft.gov.uk" for "emailAddress" field
    And I can click "createUserButton" button
    Then I should see the validation message for "invalid name" as "Enter a valid name"

  Scenario: Verify Find user by email address
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    Then I should see the search results with newly created user

  Scenario: Verify update user details - email address
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    And I click on the first name link from users table
    Then I should see the title "User details"
    When I change email address and clicks on update button
    And I search for newly create user using full name
    Then I should see the relevant email address has updated

  Scenario: Verify update user details with invalid email address
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I click on the first name link from users table
    Then I should see the title "User details"
    When I enter invalid email address and clicks on update button
    Then I should see the validation message for "invalid email" as "Enter a valid email address"

  Scenario: Verify remove user
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    And I click on the first name link from users table
    Then I should see the title "User details"
    And I can click on element "removeUserSummaryText" link
    And I can click on element "removeUserButton" button
    Then I search for newly create user using email address
    And I should see the content "There are no results for"

  Scenario: Verify reset other users password
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    And I click on the first name link from users table
    Then I should see the title "User details"
    And I can click on element "resetPasswordSummaryText" link
    And I can click on element "resetPasswordButton" button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"

  Scenario: Verify reset other users password - cancel
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    And I click on the first name link from users table
    Then I should see the title "User details"
    And I can click on element "resetPasswordSummaryText" link
    And I can click on element "doNotResetPasswordButton" button
    Then I should see the title "User details"
