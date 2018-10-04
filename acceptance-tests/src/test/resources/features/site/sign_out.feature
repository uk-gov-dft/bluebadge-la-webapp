@SignOut
Feature: user can log out without errors

  Scenario: Verify valid sign out
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
     Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see LA name as "Aberdeenshire council"
    And I should see username as "Bruce Wayne"
    And I should see signout link
    When I can click Sign out button
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I should see "You've been signed out" text on the page
    And I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    