@ReplaceBadge @UsersRolesAndPermissionsScripts @ReplaceBadgeScripts
Feature: Dft BlueBadge LA delete a Badge

  As a Blue Badge team member
  I want to be able to issue badge replacement

  Scenario: Admin user is allowed to replace badge
    Given I navigate to the "home" page
    When  I can click on the "Sign in" link
    When  I type username "aberdlaadmin@dft.gov.uk" and  ***REMOVED***
    And   I can click Sign in button
    Then  I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When  I can click on the "Find a badge" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number "DEL1"
    And   I can click "button" button
    When  I click on the first badge of the table
    When  I click on "Order a replacement" link
    Then  I should see the page title for Repalce badge for that particular badge number
    When  I select the "reason.option.STOLE" option
    When  I select the "deliverTo_home" option
    When  I select the "deliveryOptions.label.STAND" option
    And   I can click "replace.badge.button" button
    Then  I should see the badge replaced page
    Then  I can click on the "Find a badge" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number "DEL1"
    And   I can click "button" button
    When  I click on the first badge of the table
    Then  I should see the badge details page without the "Order a replacement" link button

  Scenario: Admin user from different LA  is not allowed to replace badge
    Given I navigate to the "home" page
    When  I can click on the "Sign in" link
    When  I type username "kentclaadminuser@dft.gov.uk" and  ***REMOVED***
    And   I can click Sign in button
    Then  I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When  I can click on the "Find a badge" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number "DEL2"
    And   I can click "button" button
    When  I click on the first badge of the table
    Then  I should see the badge details page without the "Order a replacement" link button

  Scenario: Read only user is not allowed to replace badge
    Given I navigate to the "home" page
    When  I can click on the "Sign in" link
    When  I type username "aberdlareadonly@dft.gov.uk" and  ***REMOVED***
    And   I can click Sign in button
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number "DEL2"
    And   I can click "button" button
    When  I click on the first badge of the table
    Then  I should see the badge details page without the "Order a replacement" link button

  Scenario: Editor user is allowed to replace badge
    Given I navigate to the "home" page
    When  I can click on the "Sign in" link
    When  I type username "aberdlaeditor@dft.gov.uk" and  ***REMOVED***
    And   I can click Sign in button
    Then  I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    When  I can click on the "Find a badge" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number "DEL2"
    And   I can click "button" button
    When  I click on the first badge of the table
    When  I click on "Order a replacement" link
    Then  I should see the page title for Repalce badge for that particular badge number
    When  I select the "reason.option.STOLE" option
    When  I select the "deliverTo_home" option
    When  I select the "deliveryOptions.label.STAND" option
    And   I can click "replace.badge.button" button
    Then  I should see the badge replaced page
    Then  I can click on the "Find a badge" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number "DEL2"
    And   I can click "button" button
    When  I click on the first badge of the table
    Then  I should see the badge details page without the "Order a replacement" link button

  Scenario: Expired badge can not be replaced
    Given I navigate to the "home" page
    When  I can click on the "Sign in" link
    When  I type username "aberdlaeditor@dft.gov.uk" and  ***REMOVED***
    And   I can click Sign in button
    Then  I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    When  I can click on the "Find a badge" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number "DEL3"
    And   I can click "button" button
    When  I click on the first badge of the table
    Then  I should see the badge details page without the "Order a replacement" link button
