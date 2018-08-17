@CancelBadge
Feature: Dft BlueBadge LA Cancel a Badge

  As a Blue Badge team member
  I want to be able to cancel blue badges

  Scenario: Cancelling a badge
    Given I navigate to the "home" page
    When  I can click on the "Sign in" link
    When  I type username "abc@dft.gov.uk" and  ***REMOVED***
    And   I can click Sign in button
    And   I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then  I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When  I enter all the mandatory valid personal details to order a badge
    And   I can click "continue" button
    Then  I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When  I enter all the mandatory valid processing details to order a badge for person
    And   I can click "continue" button
    Then  I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When  I can click "orderBadge.button" button
    Then  I should see the page titled "Badge ordered - GOV.UK Manage Blue Badges"
    And   I should see a badge number on badge ordered page
    When  I can click on the "Manage badges" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number of the applicant who previously ordered a badge
    And   I can click "button" button
    When  I click on the badge number of the first result
    Then  I click on "Cancel badge" link
    Then  I should see the page title for Cancel badge for that particular badge number
    When  I select the "reason.option.REVOKE" option
    And   I can click "cancel.badge.button" button
    Then  I should see the badge cancelled page
    Then  I can click on the "Manage badges" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number of the applicant who previously ordered a badge
    And   I can click "button" button
    When  I click on the badge number of the first result
    Then  I should see the badge details page without the "Cancel badge" link button

  Scenario: Cancelling a badge without selecting a reason
    Given I navigate to the "home" page
    When  I can click on the "Sign in" link
    When  I type username "abc@dft.gov.uk" and  ***REMOVED***
    And   I can click Sign in button
    And   I can click on the "Order a badge" link on left navigation
    Then I should see the page titled "Order a badge - GOV.UK Manage Blue Badges"
    When I select option "applicantType.option.person"
    And I click on element "continue" button
    Then  I should see the page titled "Personal Details - GOV.UK Manage Blue Badges"
    When  I enter all the mandatory valid personal details to order a badge
    And   I can click "continue" button
    Then  I should see the page titled "Processing - GOV.UK Manage Blue Badges"
    When  I enter all the mandatory valid processing details to order a badge for person
    And   I can click "continue" button
    Then  I should see the page titled "Check order - GOV.UK Manage Blue Badges"
    When  I can click "orderBadge.button" button
    Then  I should see the page titled "Badge ordered - GOV.UK Manage Blue Badges"
    And   I should see a badge number on badge ordered page
    When  I can click on the "Manage badges" link on left navigation
    Then  I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    When  I select option "badgeNumber.radio"
    And   I type the badge number of the applicant who previously ordered a badge
    And   I can click "button" button
    When  I click on the badge number of the first result
    Then  I click on "Cancel badge" link
    Then  I should see the page title for Cancel badge for that particular badge number
    And   I can click "cancel.badge.button" button
    Then  I should see the same page with validation errors
