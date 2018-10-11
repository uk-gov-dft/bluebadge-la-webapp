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
    When I select an option "role.LA_ADMIN"
    And I enter full name and email address and clicks on create a new user button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see the newly created user is on the users list

  Scenario: Verify Find user by email address
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
    Then I should see the search results with newly created user

  Scenario: Verify update user details - email address
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
    When I change email address and clicks on update button
    And I search for newly create user using full name
    Then I should see the relevant email address has updated

  Scenario: Verify remove user
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
    When I select an option "role.LA_ADMIN"
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
    When I select an option "role.LA_ADMIN"
    And I enter full name and email address and clicks on create a new user button
    And I should see the newly created user is on the users list
    When I search for newly create user using email address
    And I click on the first name link from users table
    Then I should see the title "User details"
    And I can click on element "resetPasswordSummaryText" link
    And I can click on element "doNotResetPasswordButton" button
    Then I should see the title "User details"

  Scenario: Verify creating Administrator user
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_ADMIN"
    And I enter full name and email address and clicks on create a new user button
    When I search for newly create user using email address
    Then I should see the newly created user's permission as "Administrator"

  Scenario: Verify creating Editor user
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_EDITOR"
    And I enter full name and email address and clicks on create a new user button
    When I search for newly create user using email address
    Then I should see the newly created user's permission as "Editor"

  Scenario: Verify creating View only user
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_READ"
    And I enter full name and email address and clicks on create a new user button
    When I search for newly create user using email address
    Then I should see the newly created user's permission as "View only"

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
    And I select an option "role.LA_ADMIN"
    And I can click "createUserButton" button
    Then I should see the validation message for "invalid name" as "Enter a valid name"

  Scenario: Verify Create a new user without selecting a permission
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    When I type "No permission" for "name" field
    And I type "nopermission@dft.gov.uk" for "emailAddress" field
    And I can click "createUserButton" button
    Then I should see the validation message for "blank permissions" as "Select permissions"

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

  Scenario: Verify creating View only user by Dft Administrator
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_READ"
    When I select option "ABERD" from dropdown "localAuthorityShortCode.field"
    And I enter full name and email address and clicks on create a new user button
    When I search for newly create user using email address
    Then I should see the newly created user's permission as "View only"

  Scenario: Verify creating Editor user by Dft Administrator
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_EDITOR"
    When I select option "ABERD" from dropdown "localAuthorityShortCode.field"
    And I enter full name and email address and clicks on create a new user button
    When I search for newly create user using email address
    Then I should see the newly created user's permission as "Editor"

  Scenario: Verify creating Administrator user by Dft Administrator
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_ADMIN"
    When I select option "ANGL" from dropdown "localAuthorityShortCode.field"
    And I enter full name and email address and clicks on create a new user button
    When I search for newly create user using email address
    Then I should see the newly created user's permission as "Administrator"

  Scenario: Verify creating DfT Administrator user by Dft Administrator
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.DFT_ADMIN"
    And I enter full name and email address and clicks on create a new user button
    When I search for newly create user using email address
    Then I should see the newly created user's permission as "DfT Administrator"

  Scenario: Verify Create a new view only user without selecting a Local authority
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_READ"
    And I enter full name and email address and clicks on create a new user button
    Then I should see the validation message for "blank Local authority" as "Select a local authority"

  Scenario: Verify Create a new Editor user without selecting a Local authority
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_EDITOR"
    And I enter full name and email address and clicks on create a new user button
    Then I should see the validation message for "blank Local authority" as "Select a local authority"

  Scenario: Verify Create a new Administrator user without selecting a Local authority
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage users" link on left navigation
    When I can click on the "Create a new user" button
    And I select an option "role.LA_ADMIN"
    And I enter full name and email address and clicks on create a new user button
    Then I should see the validation message for "blank Local authority" as "Select a local authority"