@CreateUser
Feature: Dft BlueBadge LA Create a new user

  As an admin user
  I want to create a new user in LA webapp
  So that the new users can login to LA webapp

  Scenario: Verify Create a new user page
    Given I navigate to the "sign-in" page
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I wait 1s
    And I navigate to the "manage-users" page
    Then I should see the page titled "Manage Users - GOV.UK Manage Blue Badges"
    When I can click on the "Create a new user" button
    Then I should see the page titled "Create a new user - GOV.UK Manage Blue Badges"
    #   Need to use different names/email address for creating users, otherwise this will fail to re-run
    When I type "Blue Badge User" for "name" field
    And I type "blue_badge_user@dft.gov.uk" for "emailAddress" field
    #   Missing element for Create user button. So can't continue
    #   And I can click on the "Create user" button
    #   Then I wait 4s
    #   Then I should see the page titled "Manage Users - GOV.UK Manage Blue Badges"

  Scenario: Verify Create a new user with an invalid full name
    Given I navigate to the "sign-in" page
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I navigate to the "manage-users" page
    Then I should see the page titled "Manage Users - GOV.UK Manage Blue Badges"
    When I can click on the "Create a new user" button
    Then I should see the page titled "Create a new user - GOV.UK Manage Blue Badges"
    #   Need to use different names/email address for creating users, otherwise this will fail to re-run
    When I type " " for "name" field
    And I type "blue_badge_user@dft.gov.uk" for "emailAddress" field
    #   Missing element for Create user button. So can't continue
    #   And I can click on the "Create user" button
    #   Then I should see the validation message "Enter a valid name"


  Scenario: Verify Create a new user with an invalid email address
    Given I navigate to the "sign-in" page
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I navigate to the "manage-users" page
    Then I should see the page titled "Manage Users - GOV.UK Manage Blue Badges"
    When I can click on the "Create a new user" button
    Then I should see the page titled "Create a new user - GOV.UK Manage Blue Badges"
    #   Need to use different names/email address for creating users, otherwise this will fail to re-run
    When I type "Blue Badge User" for "name" field
    And I type "blue_badge_user" for "emailAddress" field
    #   Missing element for Create user button. So can't continue
    #   And I can click on the "Create user" button
    #   Then I should see the validation message "Enter a valid email address"

