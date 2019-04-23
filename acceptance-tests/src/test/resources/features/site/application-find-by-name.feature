@ApplicationsFindByName @ApplicationScripts
Feature: Dft BlueBadge LA applications find by name

  As a Blue Badge team member
  I want to filter the latest applications by name
  So that I can process applications

  Scenario: Verify Applications can be searched by name
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I can see all records

    When I select option "name" from dropdown "searchFilter.dropdown"
    And I type the search term "john" in the field "search.field"
    And I can click "search.button" button
    Then I should see only results containing search term "john"
    And Search filter "searchFilter.dropdown" has value "Name" and search field "search.field" has value "john"

  Scenario: Verify Applications searched by non existing name will return no results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I can see all records

    When I select option "name" from dropdown "searchFilter.dropdown"
    And I type the search term "notinthelist" in the field "search.field"
    And I can click "search.button" button
    Then I see no records returned for the search term "notinthelist"
    And Search filter "searchFilter.dropdown" has value "Name" and search field "search.field" has value "notinthelist"
