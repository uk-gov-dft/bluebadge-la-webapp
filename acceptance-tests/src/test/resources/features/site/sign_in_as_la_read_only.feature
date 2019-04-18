@SignInLaReadOnly @UsersRolesAndPermissionsScripts
Feature: Dft BlueBadge LA Sign In Page - User with LA read only role

  As an LA read only user
  I want to sign in to LA webapp
  So that I can view LA read only dashboard

  Scenario: Verify valid Sign in
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "aberdlareadonly@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    And I should see LA name as "Aberdeenshire council"
    And I should see username as "aberd la read only user"
    And I should see signout link

  Scenario: Verify Editor only sees Find a badge in the nav bar
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    And I type username "aberdlareadonly@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    And I should not see the left navigation menu item "Applications"
    And I should not see the left navigation menu item "Order a badge"
    And I should see the left navigation menu item "Find a badge"
    And I should not see the left navigation menu item "Manage users"
    And I should not see the left navigation menu item "Manage local authorities"


