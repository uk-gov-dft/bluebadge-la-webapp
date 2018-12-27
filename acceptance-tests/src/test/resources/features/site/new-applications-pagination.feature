@NewApplicationsPaging @NewApplicationPaginationScripts
Feature: Dft BlueBadge LA New applications paging

  As a Blue Badge team member
  I want to navigate through pages when viewing applications
  So that I can see all applications

  Scenario: Verify New Applications can be navigate by mouse/touchpade
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can see first page of paged records
    When I can click on the "Next" link
    And I can see second page of paged records
    When I can click on the "Next" link
    And I can see third page of paged records
    When I can click on the "Previous" link
    And I can see second page of paged records
    When I can click on the "Previous" link
    And I can see first page of paged records

