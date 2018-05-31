@SignIn
Feature: Dft BlueBadge LA Sign In Page

  As an admin user
  I want to sign in to LA webapp
  So that I can view admin dashbaord

  Scenario: Verify valid Sign in
    Given I navigate to the "sign-in" page
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "GOV.UK Manage Blue Badges"
    And I should see LA name as "Manchester City Council"
    And I should see username as "Sampath"
    And I should see signout link

  Scenario: Verify Sign in with an invalid email address
    Given I navigate to the "sign-in" page
    And I type username "example" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the validation message for "invalid email" as "Enter a valid email address"

  Scenario: Verify Sign in with an invalid password
    Given I navigate to the "sign-in" page
    And I type username "example@gmail.com" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the validation message for "invalid  ***REMOVED***



