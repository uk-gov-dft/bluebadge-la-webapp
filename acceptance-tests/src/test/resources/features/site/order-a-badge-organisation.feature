@OrderABadgeOrganisation
Feature: Dft BlueBadge LA Order a Badge (Organisation)

  As a Blue Badge team member
  I want to add a badge record without placing an order
  So that I can add missing records without issuing a badge

  Scenario: Verify Submit organisation details with empty fields
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.organisation"
    And I click on element "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I click on element "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"

  Scenario: Verify Submit organisation details with all mandatory fields set and Processing with empty fields set
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.organisation"
    And I click on element "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid organisation details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"

  Scenario: Verify Submit organisation details with mandatory fields set and Processing with mandatory fields set and continue to check order a page and ordered a badge
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.organisation"
    And I click on element "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid organisation details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid processing details to order a badge for organisation
    And I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "orderBadge.button" button
    Then I should see the page titled "Badge ordered - GOV.UK Manage Blue Badges"
    And I should see a badge number on badge ordered page

  Scenario: Verify Submit organisation details with all fields set and Processing with all fields set and continue to check order a page and ordered a badge
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.organisation"
    And I click on element "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I enter all valid organisation details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all valid processing details to order a badge for organisation
    And I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "orderBadge.button" button
    Then I should see the page titled "Badge ordered - GOV.UK Manage Blue Badges"
    And I should see badge numbers on badge ordered page

  Scenario: Clicking back buttons should go back to the previous step with the form prepopulated with the previous data (Organisation)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.organisation"
    And I click on element "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid organisation details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid processing details to order a badge for organisation
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"

  Scenario: Clicking change details from check-order page should redirect you to details page with data prepopulated (Organisation)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.organisation"
    And I click on element "continue" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid organisation details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid processing details to order a badge for organisation
    And I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "details.change" button
    Then I should see the page titled "Organisation details - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "processing.change" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
