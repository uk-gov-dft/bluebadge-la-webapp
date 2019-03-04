@OrderABadgePerson
Feature: Dft BlueBadge LA Order a Badge (Person)

  As a Blue Badge team member
  I want to add a badge record without placing an order
  So that I can add missing records without issuing a badge

  Scenario: Submit no selection of applicant type on order a badge index page
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I click on element "continue" button
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"

  Scenario: Verify Submit person details with empty fields
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"

  Scenario: Verify Submit person details with all mandatory fields set and Processing with empty fields set
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid personal details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"

  Scenario: Verify Submit person details with mandatory fields set and Processing with mandatory fields set and continue to check order a page and ordered a badge
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid personal details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid processing details to order a badge for person
    And I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "orderBadge.button" button
    Then I should see the page titled "Badge ordered - GOV.UK Manage Blue Badges"
    And I should see a badge number on badge ordered page

  Scenario: Verify Submit person details with all fields set and Processing with all fields set and continue to check order a page and ordered a badge
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I enter all valid personal details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all valid processing details to order a badge for person
    And I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "orderBadge.button" button
    Then I should see the page titled "Badge ordered - GOV.UK Manage Blue Badges"
    And I should see a badge number on badge ordered page

  Scenario: Clicking back buttons should go back to the previous step with the form prepopulated with the previous data (person)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid personal details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid processing details to order a badge for person
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I can click "back-link" button
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"

  Scenario: Clicking change details from check-order page should redirect you to details page with data prepopulated (person)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid personal details to order a badge
    And I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I enter all the mandatory valid processing details to order a badge for person
    And I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "details.change" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When I can click "processing.change" button
    Then I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When I can click "continue" button
    Then I should see the page titled "Check order - GOV.UK Manage Blue Badges"

  Scenario: Verify validation errors when character limits of address fields are exceeded
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    And I type "I am pretty sure this string is way over fifty characters" for "buildingAndStreet.field" field by uipath
    And I type "This string is just over forty characters" for "optionalAddressField.field" field by uipath
    And I type "This string is just over forty characters" for "townOrCity.field" field by uipath
    When I click on Continue button
    Then I should see the validation message for "buildingAndStreet.error" as "Building and street must be 50 characters or less"
    And I should see the validation message for "optionalAddressField.error" as "Optional address line must be 40 characters or less"
    And I should see the validation message for "townOrCity.error" as "Town or City must be 40 characters or less"
