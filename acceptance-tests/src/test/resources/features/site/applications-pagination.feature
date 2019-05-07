@ApplicationsPaging @ApplicationPaginationScripts
Feature: Dft BlueBadge LA applications paging

  As a Blue Badge team member
  I want to navigate through pages when viewing applications
  So that I can see all applications

  Scenario: Verify Applications can be navigate by mouse/touchpad
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I can see first page of paged records
    When I can click on the "Next" link
    And I can see second page of paged records
    When I can click on the "Next" link
    And I can see third page of paged records
    When I can click on the "Previous" link
    And I can see second page of paged records
    When I can click on the "Previous" link
    And I can see first page of paged records

