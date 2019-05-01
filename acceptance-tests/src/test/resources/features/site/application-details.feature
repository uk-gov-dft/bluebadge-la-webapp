@ApplicationDetails @ApplicationDetailsScripts
Feature: Dft BlueBadge LA applications - view application details

  As a Blue Badge team member
  I want to view application details
  So that I can determine how to progress the application

  Scenario: View application details - when NEW application and badge number is not provided
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"

    When I click on application with name "John The First"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    And I should not see element with ui path "previous-badge.title"
    And I should not see element with ui path "previous-badge.number"
    And I should not see element with ui path "previous-badge.date"
    And I should not see element with ui path "previous-badge.status"

    When  I can click "back-link" button
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"

  Scenario: View application details - when badge number provided and badge is found
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"

    When I click on application with name "New application with existing badge number"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    And I should see "previous-badge.title" element with content "Previous badge"
    And I should see "previous-badge.number" element with content "DEL1"
    And I should see "previous-badge.date" element with content "01 May 2028"
    And I should see "previous-badge.status" element with content "Issued"

    When  I can click "back-link" button
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"

  Scenario: View application details - when badge number provided and badge is not found
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"

    When I click on application with name "New application with made up badge number"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    And I should see "previous-badge.title" element with content "Previous badge"
    And I should see "previous-badge.number" element with content "AAA000"
    And I should see "previous-badge.date" element with content "Badge not found for this number"
    And I should see "previous-badge.status" element with content "Badge not found for this number"

  Scenario: View application details - when reapplication but badge number not provided
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"

    When I click on application with name "Renewed application without provided badge number"
    Then I should see the page titled "View application - GOV.UK Manage Blue Badges"
    And I should see "previous-badge.title" element with content "Previous badge"
    And I should see "previous-badge.number" element with content "Not provided"
    And I should not see element with ui path "previous-badge.date"
    And I should not see element with ui path "previous-badge.status"

    When  I can click "back-link" button
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
