@ApplicationsFindByPostcode @ApplicationScripts
Feature: Dft BlueBadge LA applications find by postcode

  As a Blue Badge team member
  I want to filter the latest applications by postcode
  So that I can process applications

  Scenario: Verify Applications can be searched by postcode
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I can see all records

    When I select option "postcode" from dropdown "searchFilter.dropdown"
    And I type the search term "sw11aa" in the field "search.field"
    And I can click "search.button" button
    Then I should see only results where postcode "sw11aa"
    And Search filter "searchFilter.dropdown" has value "Postcode" and search field "search.field" has value "sw11aa"

  Scenario: Verify Applications can be searched by postcode and filtered by NEW applications only
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I can see all records

    When I select option "postcode" from dropdown "searchFilter.dropdown"
    And I type the search term "sw11aa" in the field "search.field"
    And I select option "NEW" from dropdown "searchFilter.applicationType.dropdown"
    And I can click "search.button" button
    Then I should see only results where postcode "sw11aa"
    And Search filter "searchFilter.dropdown" has value "Postcode" and search field "search.field" has value "sw11aa"
    And Search filter "searchFilter.applicationType.dropdown" has text "New"

  Scenario: Verify Applications can be searched by postcode and filtered by RENEW applications only
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I can see all records

    When I select option "postcode" from dropdown "searchFilter.dropdown"
    And I type the search term "sw11aa" in the field "search.field"
    And I select option "RENEW" from dropdown "searchFilter.applicationType.dropdown"
    And I can click "search.button" button
    Then I see no records returned for the search term "sw11aa"
    And Search filter "searchFilter.dropdown" has value "Postcode" and search field "search.field" has value "sw11aa"
    And Search filter "searchFilter.applicationType.dropdown" has text "Reapplication"

  Scenario: Verify Applications searched by non existing postcode will return no results
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"
    And I can see all records

    When I select option "postcode" from dropdown "searchFilter.dropdown"
    And I type the search term "notinthelist" in the field "search.field"
    And I can click "search.button" button
    Then I see no records returned for the search term "notinthelist"
    And Search filter "searchFilter.dropdown" has value "Postcode" and search field "search.field" has value "notinthelist"
