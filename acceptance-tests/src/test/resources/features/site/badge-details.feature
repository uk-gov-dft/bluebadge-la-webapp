@BadgeDetails
Feature: Dft BlueBadge LA Find a Badge

  As a Blue Badge team member
  I want to be able to view badges details
  So that I can see a citizens badge details

  Scenario: View Badge Details: After ordering a badge and searching for it by post code I can click to access its details and then go back and click to access again.
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid personal details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid processing details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "orderBadge.button" button
    Then I should see the page titled "Badge ordered - GOV.UK Manage Blue Badges"
    And I should see a badge number on badge ordered page
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "postcode.radio"
    And I type the post code of the applicant who previously ordered a badge
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should not see "No results found for " text on the page
    When I click on the badge number of the first result
    Then I should see the page title for Badge Details for that particular badge number
    When I can click "back-link" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should not see "No results found for " text on the page
    When I click on the badge number of the first result
    Then I should see the page title for Badge Details for that particular badge number
