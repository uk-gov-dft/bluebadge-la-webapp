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
    When I type on field "welshDescription" value "Aberdeen city council"
    And I type on field "description" value "Aberdeen city council"
    And I type on field "nameLine2" value "Traffic Management Team"
    And I type on field "addressLine1" value "Business Hub 11"
    And I type on field "addressLine2" value "Level 2 West"
    And I type on field "addressLine3" value "Aberdeen City Council"
    And I type on field "addressLine4" value "Marischal College"
    And I type on field "town" value "Broad Street"
    And I type on field "county" value "Aberdeen"
    And I type on field "postcode" value "AB10 1AB"
    And I type on field "country" value "United Kingdom"
    And I type on field "nation" value "ENG"
    And I type on field "contactNumber" value "01224 522599"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "disabledbadges@aberdeencity.gov.uk"
    And I type on field "badgePackType" value "STANDARD"
    And I select option "paymentsEnabled.option.false"
    And I type on field "badgeCost" value "100"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
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
    And I type on field "description" value "Aberdeen city council"
    And I type on field "postcode" value "AB10 1AB"
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
    And I type on field "description" value "Aberdeen city council"
    And I type on field "postcode" value "AB10 1ABBB"
    And I type on field "country" value "This is definitely longer than forty characters"
    And I type on field "nation" value "invalid"
    And I type on field "websiteUrl" value "invalid"
    And I type on field "differentServiceSignpostUrl" value "invalid"
    And I type on field "badgeCost" value "1000"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    And I should see the validation message for "postcode.summary-error" as "Enter a valid postcode"
    And I should see the validation message for "country.summary-error" as "Country must be 40 characters or less"
    And I should see the validation message for "nation.summary-error" as "Enter a valid nation i.e. ENG, WLS, SCO, NIR"
    And I should see the validation message for "websiteUrl.summary-error" as "Enter a valid URL"
    And I should see the validation message for "differentServiceSignpostUrl.summary-error" as "Enter a valid URL"
    And I should see the validation message for "badgeCost.summary-error" as "Enter a badge cost between 0.0 and 999.99"
    When I type on field "postcode" value "AB10 1AB"
    When I type on field "country" value "United Kingdom"
    And I type on field "nation" value "ENG"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
    And I type on field "badgeCost" value "100"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"

  Scenario: Verify Manage local authorities (validate invalid optional fields)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage local authorities" link on left navigation
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"
    When I click on the first name link from local authorities table
    Then I should see the page titled "Manage local authority - GOV.UK Manage Blue Badges"
    And I should see non-mandatory fields marked as optional
    And I type on field "nameLine2" value "This is definitely longer than forty characters"
    And I type on field "addressLine1" value "This is definitely longer than forty characters"
    And I type on field "addressLine2" value "This is definitely longer than forty characters"
    And I type on field "addressLine3" value "This is definitely longer than forty characters"
    And I type on field "addressLine4" value "This is definitely longer than forty characters"
    And I type on field "town" value "This is definitely longer than forty characters"
    And I type on field "county" value "This is definitely longer than forty characters"
    And I type on field "contactNumber" value "078991"
    And I type on field "emailAddress" value "hello at hello dot com"

    And I can click on element "updateDetails" button

    Then I should see the validation message for "nameLine2.summary-error" as "Name line 2 must be 40 characters or less"
    Then I should see the validation message for "addressLine1.summary-error" as "Address line 1 must be 40 characters or less"
    And I should see the validation message for "addressLine2.summary-error" as "Address line 2 must be 40 characters or less"
    And I should see the validation message for "addressLine3.summary-error" as "Address line 3 must be 40 characters or less"
    And I should see the validation message for "addressLine4.summary-error" as "Address line 4 must be 40 characters or less"
    And I should see the validation message for "town.summary-error" as "Town must be 40 characters or less"
    And I should see the validation message for "county.summary-error" as "County must be 40 characters or less"
    And I should see the validation message for "contactNumber.summary-error" as "Enter a valid contact number"
    And I should see the validation message for "emailAddress.summary-error" as "Enter a valid email address (max length 100)"
