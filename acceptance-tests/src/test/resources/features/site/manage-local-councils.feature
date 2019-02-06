@ManageLocalCouncils
Feature: Dft BlueBadge LA Manage local councils

  As a DfT admin
  I want to view LCs
  So that I can ensure that LC details are correct

  Scenario: Verify Manage local councils (all values valid)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Manage local councils" link on left navigation
    Then I should see the page titled "Manage local councils - GOV.UK Manage Blue Badges"
    When I click on the first name link from local councils table
    Then I should see the page titled "Manage local council - GOV.UK Manage Blue Badges"
    When I type "Aberdeen city council in Welsh" for "welshDescription" field
    And I type "Aberdeen city council" for "description" field
    And I can click on element "updateCouncilDetails" button
    Then I should see the page titled "Manage local councils - GOV.UK Manage Blue Badges"

  Scenario: Verify Manage local councils (mandatory values valid)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Manage local councils" link on left navigation
    Then I should see the page titled "Manage local councils - GOV.UK Manage Blue Badges"
    When I click on the first name link from local councils table
    Then I should see the page titled "Manage local council - GOV.UK Manage Blue Badges"
    When I clear the field "welshDescription"
    And I type "Aberdeen city council" for "description" field
    And I can click on element "updateCouncilDetails" button
    Then I should see the page titled "Manage local councils - GOV.UK Manage Blue Badges"

  Scenario: Verify Manage local councils (empty values)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Manage local councils" link on left navigation
    Then I should see the page titled "Manage local councils - GOV.UK Manage Blue Badges"
    When I click on the first name link from local councils table
    Then I should see the page titled "Manage local council - GOV.UK Manage Blue Badges"
    When I clear the field "welshDescription"
    And I clear the field "description"
    And I can click on element "updateCouncilDetails" button
    Then I should see the page titled "Manage local council - GOV.UK Manage Blue Badges"
    And I should see the validation message for "description.summary-error" as "Enter a description"

  Scenario: Verify Manage local councils (invalid values first, then correct them and submit)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    And I can click on the "Manage local councils" link on left navigation
    Then I should see the page titled "Manage local councils - GOV.UK Manage Blue Badges"
    When I click on the first name link from local councils table
    Then I should see the page titled "Manage local council - GOV.UK Manage Blue Badges"
    And I clear the field "description"
    And I can click on element "updateCouncilDetails" button
    Then I should see the page titled "Manage local council - GOV.UK Manage Blue Badges"
    And I should see the validation message for "description.summary-error" as "Enter a description"
    When I type "Aberdeen city council" for "description" field
    And I can click on element "updateCouncilDetails" button
    Then I should see the page titled "Manage local councils - GOV.UK Manage Blue Badges"

  Scenario: Verify Manage local councils (admin users only)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should not see the left navigation menu item "Manage local councils"
    And I should see the left navigation menu item "Manage users"