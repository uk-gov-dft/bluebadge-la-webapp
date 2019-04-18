@DeleteApplication
Feature: Dft BlueBadge LA applications - delete application

  As a Blue Badge team member
  I want to delete an application
  So that I don't hold personal data for longer than necessary

  Scenario: Delete application
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    When I click on application with name "John The Second"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    When I can click "removeApplicationSummaryText" button
    And I can click on element "doNotRemoveApplicationButton" link
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    When I can click "removeApplicationSummaryText" button
    And I can click "removeApplicationButton" button
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
