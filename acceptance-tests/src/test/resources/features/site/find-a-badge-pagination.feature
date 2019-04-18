@FindBadgePaging @FindBadgePaginationScripts
Feature: Dft BlueBadge LA applications paging

  As a Blue Badge team member,
  I want to view all badges that match my search criteria
  So that I can find the badge that I am looking for

  Scenario: Verify Find a badge results are paginated
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Find a badge" link on left navigation
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When I select option "name.radio"
    And I type "E" for "searchTerm" field
    And I can click "button" button
    Then I should see the page titled "Badge search results - GOV.UK Manage Blue Badges"
    And I should not see "No results found for " text on the page
    And I can see first page of paged records
    When I can click on the "Next" link
    And I can see second page of paged records
    When I can click on the "Next" link
    And I can see third page of paged records
    When I can click on the "Previous" link
    And I can see second page of paged records
    When I can click on the "Previous" link
    And I can see first page of paged records

