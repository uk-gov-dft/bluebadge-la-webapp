@SignIn
Feature: Dft BlueBadge LA Sign In Page

  As an admin user
  I want to sign in to LA webapp
  So that I can view admin dashbaord

  Scenario: Verify Sign in Page
    Given I navigate to the "sign-in" page
    Then I should see the page titled "Manage Blue Badges"

  Scenario: Verify Sign in with an invalid login
    Given I navigate to the "sign-in" page
    Then I wait 2s
    And I type username "test123" and  ***REMOVED***
    And I can click Sign in button
    Then I wait 1s
    Then I should see the validation message "This is not an password address."

#  Scenario: Verify Sign in with a valid login
#    Given I navigate to the "news" page
#    When I click on the "sign in" link
#    And I type username "smahavithana@gmail.com" and  ***REMOVED***
#    And I can click Sign in button
#    Then I should see the page titled "Home - BBC News"


