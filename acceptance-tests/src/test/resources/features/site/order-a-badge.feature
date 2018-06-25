@OrderABadge
Feature: Dft BlueBadge LA Order a Badge

  As a Blue Badge team member
  I want to add a badge record without placing an order
  So that I can add missing records without issuing a badge

  Scenario: Verify Submit person details
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
