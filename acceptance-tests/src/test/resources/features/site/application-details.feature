@ApplicationDetails @NewApplicationDetailsScripts
Feature: Dft BlueBadge LA New applications - view application details

  As a Blue Badge team member
  I want to view application details
  So that I can determine how to progress the application

  Scenario: View application details
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    When I click on application with name "John The First"
    Then I should see the page titled "New application John The First - GOV.UK Manage Blue Badges"
    Then I should see the page title for Application Details for that particular "1087ac26-491a-46f0-9006-36187dc40764"
    When  I can click "back-link" button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"