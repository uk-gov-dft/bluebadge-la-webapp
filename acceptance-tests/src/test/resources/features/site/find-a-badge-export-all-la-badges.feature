@ExportAllLABadges
Feature: Dft BlueBadge LA Export all LA badges

  As a Blue Badge team member
  I want to get a snapshot of all of my badges
  So that I can measure the performance of my team
  And so that I can send expiry reminders

  Scenario: Export all la badges
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    And I can click on element "exportAllLABadgesDetails" button
    And I can click on element "exportAllLABadgesButton" button
