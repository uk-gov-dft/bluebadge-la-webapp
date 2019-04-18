@SignInLaAdmin @UsersRolesAndPermissionsScripts
Feature: Dft BlueBadge LA Sign In Page - User with LA admin role

  As an LA admin user
  I want to sign in to LA webapp
  So that I can view LA admin dashboard

  Scenario: Verify valid Sign in
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "aberdlaadmin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see LA name as "Aberdeenshire council"
    And I should see username as "aberd la admin user"
    And I should see signout link

  Scenario: Verify Editor sees all links in the nav bar
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    And I type username "aberdlaadmin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see the left navigation menu item "Applications"
    And I should see the left navigation menu item "Order a badge"
    And I should see the left navigation menu item "Find a badge"
    And I should see the left navigation menu item "Manage users"
    And I should not see the left navigation menu item "Manage local authorities"


