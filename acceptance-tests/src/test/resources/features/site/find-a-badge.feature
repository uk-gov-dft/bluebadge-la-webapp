@FindABadge @CreateBadgesScripts
Feature: Dft BlueBadge LA Find a Badge

  As a Blue Badge team member
  I want to find a badge using the search functionality
  So that I can find the badge I am looking for

  Scenario: Verify Find a badge by post code for an empty search term first and then non existing badge and present no results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "postcode.radio"
    And I type "  " for "searchTermPostcode.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should see "No results found for " text on the page
    When I can click "back-link" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "postcode.radio"
    And I type "1AB C23" for "searchTermPostcode.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should see "No results found for " text on the page

  Scenario: Verify Find a badge by name first with a blank search term and then for a non existing badge and present zero results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "name.radio"
    And I type "  " for "searchTermName.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should see "No results found for " text on the page
    When I can click "back-link" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "name.radio"
    And I type "json" for "searchTermName.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should see "No results found for " text on the page

  Scenario: Verify Find a badge by badge number for a non existing badge and present the results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "badgeNumber.radio"
    And I type "BADGENOTTOBEFOUND" for "searchTermBadgeNumber.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should see "No results found for " text on the page
    And I should see "BADGENOTTOBEFOUND" text on the page

  Scenario: Verify Find a badge by badge number with radio button not selected - badge number is default
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I type "AAAAA1" for "searchTermBadgeNumber.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"

  Scenario: Verify Find a badge by badge number and I can see badge details automatically when there is a single result
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "badgeNumber.radio"
    And I type "DEL4" for "searchTermBadgeNumber.field" field by uipath
    And I can click "button" button
    And I should see the page titled "Badge details DEL4 - GOV.UK Manage Blue Badges"
    And It is a badge for "PERSON"
    And I should see correct details for "PERSON" badge
    When I can click "back-link" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"

  Scenario: Verify Find a badge by postcode and I can see search results when there are multiple results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "postcode.radio"
    And I type "S637FU" for "searchTermPostcode.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    When I can click on the "DEL3" link
    And I should see the page titled "Badge details DEL3 - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"

  Scenario: Verify Find a badge by postcode and I can see badge details automatically when there is a single result
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "postcode.radio"
    And I type "AB333CC" for "searchTermPostcode.field" field by uipath
    And I can click "button" button
    And I should see the page titled "Badge details DEL4 - GOV.UK Manage Blue Badges"
    And It is a badge for "PERSON"
    And I should see correct details for "PERSON" badge
    When I can click "back-link" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"

  Scenario: Verify Find a badge by name and I can see badge details automatically when there is a single result
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "name.radio"
    And I type "TEST DATA ROB" for "searchTermName.field" field by uipath
    And I can click "button" button
    And I should see the page titled "Badge details DEL4 - GOV.UK Manage Blue Badges"
    And It is a badge for "PERSON"
    And I should see correct details for "PERSON" badge
    When I can click "back-link" button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"

  Scenario: Verify Find a badge by name and I can see search results when there are multiple results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "name.radio"
    And I type "TEST DATA JOHN" for "searchTermName.field" field by uipath
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    When I can click on the "DEL3" link
    Then I should see the page titled "Badge details DEL3 - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
