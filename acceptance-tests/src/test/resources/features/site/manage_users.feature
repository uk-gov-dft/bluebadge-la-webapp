@ManageUsers
Feature: Dft BlueBadge LA Manage User

  As an admin user
  I want to create,remove,modify a new user in LA webapp
  So that the new users can login to LA webapp

  Scenario: Verify Create a new user page
    Given I navigate to the "sign-in" page
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I navigate to the "manage-users" page
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Create a new user" button
    Then I should see the page titled "Create a new user - GOV.UK Manage Blue Badges"
    When I enter full name and email address and clicks on create a new user button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see the newly created user is on the users list

  Scenario: Verify Create a new user with an invalid full name
    Given I navigate to the "sign-in" page
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I navigate to the "manage-users" page
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Create a new user" button
    Then I should see the page titled "Create a new user - GOV.UK Manage Blue Badges"
    When I type " " for "name" field
    And I type "test@dft.gov.uk" for "emailAddress" field
    And I can click on the "Create user" button on manage user page
    Then I should see the validation message for "invalid name" as "Enter a valid name"

  Scenario: Verify Find user by email address
    Given I navigate to the "sign-in" page
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I navigate to the "manage-users" page
    When I can click on the "Create a new user" button
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    Then I should see the search results with newly created user




