@UpdateApplication
Feature: Dft BlueBadge LA New applications - update application

  As an LA user,
  I want to be able to view the status of an application,
  So I can manage applications effectively

  As an LA user,
  I want to be able to update the status of an application,
  So I can manage applications effectively

  Scenario: Update application
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    When I click on application with name "John The Second"
    Then I should see the page titled "New application - GOV.UK Manage Blue Badges"
    And I can see labelled element "currentApplicationStatus" with content "TO DO"
    When I can click "updateStatusSummary" button
    When I select option "applicationStatus.option.INPROGRESS"
    When I can click "updateStatusSummary" button
    When I can click "updateStatusSummary" button
    And I can click "updateApplicationStatusButton" button
    Then I should see the page titled "New application - GOV.UK Manage Blue Badges"
    And I can see labelled element "currentApplicationStatus" with content "IN PROGRESS"
    When I can click "updateStatusSummary" button
    When I select option "applicationStatus.option.COMPLETED"
    And I can click "updateApplicationStatusButton" button
    Then I should see the page titled "New application - GOV.UK Manage Blue Badges"
    And I can see labelled element "currentApplicationStatus" with content "COMPLETED"
    When I can click "updateStatusSummary" button
    When I select option "applicationStatus.option.TODO"
    And I can click "updateApplicationStatusButton" button
    Then I should see the page titled "New application - GOV.UK Manage Blue Badges"
    And I can see labelled element "currentApplicationStatus" with content "TO DO"
