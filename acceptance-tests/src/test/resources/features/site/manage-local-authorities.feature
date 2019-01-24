@ManageLocalAuthorities
Feature: Dft BlueBadge LA Manage local authorities

  As a DfT admin
  I want to view LAs
  So that I can ensure that LA details are correct

  Scenario: Verify Manage local authorities (all values valid)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage local authorities" link on left navigation
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"
    When I click on the first name link from local authorities table
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    When I type on field "welshDescription" value "local authority in Cymraeg"
    And I type on field "description" value "local authority in english"
    And I type on field "nameLine2" value "name line 2"
    And I type on field "addressLine1" value "address line 1"
    And I type on field "addressLine2" value "address line 2"
    And I type on field "addressLine3" value "address line 3"
    And I type on field "addressLine4" value "address line 4"
    And I type on field "town" value "town"
    And I type on field "county" value "county"
    And I type on field "postcode" value "ABC123"
    And I type on field "country" value "United Kingdom"
    And I type on field "nation" value "ENG"
    And I type on field "contactNumber" value "00000000000"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "abc@dft.com"
    And I type on field "badgePackType" value "STANDARD"
    And I select option "paymentsEnabled.option.false"
    And I type on field "badgeCost" value "100"
    And I type on field "differentServiceSignpostUrl" value "http://localhost:1111/test"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"

  Scenario: Verify Manage local authorities (mandatory values valid)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage local authorities" link on left navigation
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"
    When I click on the first name link from local authorities table
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    And I type on field "description" value "A local authority description"
    And I type on field "postcode" value "ABC123"
    And I type on field "country" value "United Kingdom"
    And I type on field "nation" value "ENG"
    And I type on field "websiteUrl" value "http://localhost"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"

  Scenario: Verify Manage local authorities (empty values)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage local authorities" link on left navigation
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"
    When I click on the first name link from local authorities table
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    When I type on field "welshDescription" value ""
    And I type on field "description" value ""
    And I type on field "nameLine2" value ""
    And I type on field "addressLine1" value ""
    And I type on field "addressLine2" value ""
    And I type on field "addressLine3" value ""
    And I type on field "addressLine4" value ""
    And I type on field "town" value ""
    And I type on field "county" value ""
    And I type on field "postcode" value ""
    And I type on field "country" value ""
    And I type on field "nation" value ""
    And I type on field "contactNumber" value ""
    And I type on field "websiteUrl" value ""
    And I type on field "emailAddress" value ""
    And I type on field "badgePackType" value ""
    And I type on field "badgeCost" value ""
    And I type on field "differentServiceSignpostUrl" value ""
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    And I should see the validation message for "description.summary-error" as "Enter a description"
    And I should see the validation message for "websiteUrl.summary-error" as "Enter a website URL"
    And I should see the validation message for "country.summary-error" as "Enter a country"
    And I should see the validation message for "nation.summary-error" as "Enter a nation"
    And I should see the validation message for "postcode.summary-error" as "Enter a postcode"

  Scenario: Verify Manage local authorities (invalid values first, then correct them and submit)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage local authorities" link on left navigation
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"
    When I click on the first name link from local authorities table
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    And I type on field "description" value "a local authority description"
    And I type on field "postcode" value "ABC123"
    And I type on field "country" value "United Kingdom"
    And I type on field "nation" value "ENG"
    And I type on field "websiteUrl" value "invalid"
    And I type on field "differentServiceSignpostUrl" value "invalid"
    And I type on field "badgeCost" value "1000"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    And I should see the validation message for "websiteUrl.summary-error" as "Enter a valid URL"
    And I should see the validation message for "differentServiceSignpostUrl.summary-error" as "Enter a valid URL"
    And I should see the validation message for "badgeCost.summary-error" as "Enter a badge cost between 0.0 and 999.99"
    When I type on field "websiteUrl" value "http://localhost"
    And I type on field "differentServiceSignpostUrl" value "http://localhost"
    And I type on field "badgeCost" value "100"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"
