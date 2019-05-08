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
    And I should see a link named "sidebar-nav.credentials"


  Scenario: Verify New Api Credential Not Visible for Non Admin users
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "editor@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    #Then I should not see the link named "sidebar-nav.credentials"


  Scenario: Display Error if no service is selected
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API Credentials" link
    When I click the update button
    Then I should get a message that says "Select a service"


  Scenario: Display Error if PAY API Key is not entered or more than 200 characters
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API Credentials" link
    And I select "servicePayApiKey" service
    When I click the update button
    Then I should get a message that says "Enter your GOV.UK Pay API key"
    When I enter a value more than two hundred characters for the "payApiKey.field"
    And I click the update button
    Then I should get a message that says "GOV.UK Pay API key must be 200 characters or less"


  Scenario: Display Error if NOTIFY API Key is not entered or more than 200 characters
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API Credentials" link
    And I select "serviceNotifyApiKey" service
    When I click the update button
    Then I should get a message that says "Enter your GOV.UK Notify API key"
    When I enter a value more than two hundred characters for the "notifyApiKey.field"
    And I click the update button
    Then I should get a message that says "GOV.UK Notify API key must be 200 characters or less"


  Scenario: Display Error if NOTIFY Application template id is not entered or more than 200 characters
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API Credentials" link
    And I select "serviceApplicationSubmittedTemplateId" service
    When I click the update button
    Then I should get a message "Enter your 'Application submitted' template ID"
    When I enter a value more than two hundred characters for the "applicationSubmittedTemplateId.field"
    And I click the update button
    Then I should get a message that says "GOV.UK Notify template ID must be 200 characters or less"


  Scenario: CompletePayApiKeyHappyPath
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API Credentials" link
    And I select "servicePayApiKey" service
    When I enter a valid value in "payApiKey.field"
    And I click the update button
    Then I should get a message that says "Credentials stored"


  Scenario: CompleteNotifyApiKeyHappyPath
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API Credentials" link
    And I select "serviceNotifyApiKey" service
    When I enter a valid value in "notifyApiKey.field"
    And I click the update button
    Then I should get a message that says "Credentials stored"


  Scenario: CompleteNotifyAppSubmittedTemplateHappyPath
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API Credentials" link
    And I select "serviceApplicationSubmittedTemplateId" service
    When I enter a valid value in "applicationSubmittedTemplateId.field"
    And I click the update button
    Then I should get a message that says "Credentials stored"
