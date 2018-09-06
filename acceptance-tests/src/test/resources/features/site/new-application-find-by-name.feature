@NewApplicationsFindByName
Feature: Dft BlueBadge LA New applications find by name

  As a Blue Badge team member
  I want to filter the latest applications by name
  So that I can process applications

  Scenario: Verify New Applications can be searched by name
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    
   
    When I select option "name" from dropdown "searchFilter.dropdown"
    And I type the search term "john" in the field "search.field"
    And I can click "search.button" button
    Then I should see only results containing search term "john"

    