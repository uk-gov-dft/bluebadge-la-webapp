@ApplicationDetails @ApplicationDetailsScripts
Feature: Dft BlueBadge LA applications - view application details

  As a Blue Badge team member
  I want to view application details
  So that I can determine how to progress the application

  Scenario: View application details
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    When I click on application with name "John The First"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    When  I can click "back-link" button
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
