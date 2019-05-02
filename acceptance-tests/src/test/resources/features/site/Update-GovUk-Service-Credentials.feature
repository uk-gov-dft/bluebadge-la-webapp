@UpdateServiceCredentials
Feature: Dft BlueBadge LA Manage Users

  As an admin user
  I update service credentials

  Scenario: Verify New Api Credential Visible for Admin users
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.api-credentials"


  Scenario: Verify New Api Credential Not Visible for Non Admin users
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "editor@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should not see the link named "sidebar-nav.api-credentials"

    @kc
  Scenario: Display Error if no service is selected
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.api-credentials"
    And I click on "Api credentials" link
    And I unselect all the services
    When I click the update button
    Then I should get an error message that says "select service"


  Scenario: Display Error if PAY API Key is not entered or more than 200 characters
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.api-credentials"
    And I click on "Api credentials" link
    And I select GovUK PAY Api Key service
    And I clear the Pay API Key field
    When I click the update button
    Then I should get an error message that says "Enter your GOV.UK Pay API key"
    When I enter a value more than 200 characters for the pay api key
    Then I should get an error message that says "GOV.UK Pay API key must be 200 characters or less"


  Scenario: Display Error if NOTIFY API Key is not entered or more than 200 characters
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Settings"
    And I click settings button
    And I select GovUK Notify API Key service
    And I clear the Notify API Key field
    When I click the update button
    Then I should get an error message that says "Enter your GOV.UK Notify API key"
    When I enter a value more than 200 characters for the notify api key
    Then I should get an error message that says "GOV.UK Notify API key must be 200 characters or less"

  Scenario: Display Error if NOTIFY Application template id is not entered or more than 200 characters
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Settings"
    And I click settings button
    And I select GovUK Notify API Key service
    And I clear the Notify Application template Id field
    When I click the update button
    Then I should get an error message that says "Enter your 'Application submitted' template ID"
    When I enter a value more than 200 characters for the notify application template id
    Then I should get an error message that says "GOV.UK Notify template ID must be 200 characters or less"
