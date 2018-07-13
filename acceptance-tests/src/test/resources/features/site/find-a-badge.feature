@FindABadge
Feature: Dft BlueBadge LA Find a Badge

  As a Blue Badge team member
  I want to find a badge using the search functionality
  So that I can find the badge I am looking for

  Scenario: Verify Find a badge by badge number for an existing badge and present the results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "badgeNumber.radio"
    And I type "AAAAA1" for "searchTerm.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should not see "No results found for badge number" text on the page
    When I can click "back-link" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"

  Scenario: Verify Find a badge by badge number for a non existing badge and present the results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "badgeNumber.radio"
    And I type "BADGENOTTOBEFOUND" for "searchTerm.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should see "No results found for badge number" text on the page
    And I should see "BADGENOTTOBEFOUND" text on the page


  Scenario: Verify Find a badge by badge number with radio button not selected
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I type "AAAAA1" for "searchTerm.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
