@TransferApplication
Feature: Dft BlueBadge LA applications - transfer application

  As an LA user,
  I want to be able to transfer an application to another local authority,
  So they can deal with it

  Scenario: Transfer application
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I should see "John Spartan" text on the page
    When I click on application with name "John Spartan"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    When I can click "transferApplicationSummaryText" button
    And I select option "ANGL" from dropdown "transferToLaShortCode.field"
    And I can click "transferApplicationButton" button
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I should not see "John Spartan" text on the page
    When I can click Sign out button
#    Anglesee user logs back in...
    When I navigate to the "home" page
    And I can click on the "Sign in" link
    And I type username "angl_editor@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    When I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I should see "John Spartan" text on the page
    When I click on application with name "John Spartan"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    And I can see labelled element "application.transferdetails.la" with content "Aberdeenshire council"
