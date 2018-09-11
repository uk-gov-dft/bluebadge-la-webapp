@NewApplicationsFindByPostcode @NewApplicationScripts
Feature: Dft BlueBadge LA New applications find by postcode

  As a Blue Badge team member
  I want to filter the latest applications by postcode
  So that I can process applications

  Scenario: Verify New Applications can be searched by postcode
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can see all records  
   
    When I select option "postcode" from dropdown "searchFilter.dropdown"
    And I type the search term "sw11aa" in the field "search.field"
    And I can click "search.button" button
    Then I should see only results where postcode "sw11aa"
    And Search filter "searchFilter.dropdown" has value "Postcode" and search field "search.field" has value "sw11aa"

  Scenario: Verify New Applications searched by non existing postcode will return no results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    And I can see all records  
   
    When I select option "postcode" from dropdown "searchFilter.dropdown"
    And I type the search term "notinthelist" in the field "search.field"
    And I can click "search.button" button
    Then I see no records returned
    And Search filter "searchFilter.dropdown" has value "Postcode" and search field "search.field" has value "notinthelist"
    