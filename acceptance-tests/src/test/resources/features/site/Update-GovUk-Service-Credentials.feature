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
    And I click on "API credentials" link
    When I click the update button
    Then I should get a message that says "Select a service"


  Scenario: Display Error if PAY API Key is not entered or more than 200 characters
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I should see a link named "sidebar-nav.credentials"
    And I click on "API credentials" link
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
    And I click on "API credentials" link
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
    And I click on "API credentials" link
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
    And I click on "API credentials" link
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
    And I click on "API credentials" link
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
    And I click on "API credentials" link
    And I select "serviceApplicationSubmittedTemplateId" service
    When I enter a valid value in "applicationSubmittedTemplateId.field"
    And I click the update button
    Then I should get a message that says "Credentials stored"


  Scenario: DftAdminGovApiKeyMoreThan200Characters
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
    And I select option "nation.option.ENG"
    And I type on field "contactNumber" value "01224 522599"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "disabledbadges@aberdeencity.gov.uk"
    And I select option "badgePackType.option.Standard"
    And I select option "paymentsEnabled.option.false"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
    And I type on field "govUkPayApiKey" value "MoreThan200Characters:Lorem Ipsum is simply dummy text of the printing and typesetting industry.The following string contains more than two hundred characters and will therefore throw an exception for api key"
    And I can click on element "updateDetails" button
    Then I should get a message that says "GOV.UK Pay API key must be 200 characters or less"


  Scenario: DftAdminNotifyApiKeyMoreThan200Characters
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
    And I select option "nation.option.ENG"
    And I type on field "contactNumber" value "01224 522599"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "disabledbadges@aberdeencity.gov.uk"
    And I select option "badgePackType.option.Standard"
    And I select option "paymentsEnabled.option.false"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
    And I type on field "govUkNotifyApiKey" value "MoreThan200Characters:Lorem Ipsum is simply dummy text of the printing and typesetting industry.The following string contains more than two hundred characters and will therefore throw an exception for api key"
    And I can click on element "updateDetails" button
    Then I should get a message that says "GOV.UK Notify API key must be 200 characters or less"


  Scenario: DftAdminNotifyAppSubmittedTemplateMoreThan200Characters
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
    And I select option "nation.option.ENG"
    And I type on field "contactNumber" value "01224 522599"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "disabledbadges@aberdeencity.gov.uk"
    And I select option "badgePackType.option.Standard"
    And I select option "paymentsEnabled.option.false"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
    And I type on field "govUkNotifyApplicationSubmittedTemplateId" value "MoreThan200Characters:Lorem Ipsum is simply dummy text of the printing and typesetting industry.The following string contains more than two hundred characters and will therefore throw an exception for api key"
    And I can click on element "updateDetails" button
    Then I should get a message that says "GOV.UK Notify template ID must be 200 characters or less"

  Scenario: DftAdminGovApiKeyLessThan200Characters
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
    And I select option "nation.option.ENG"
    And I type on field "contactNumber" value "01224 522599"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "disabledbadges@aberdeencity.gov.uk"
    And I select option "badgePackType.option.Standard"
    And I select option "paymentsEnabled.option.false"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
    And I type on field "govUkPayApiKey" value "LessThan200CharactersValidForApiKey"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"

  Scenario: DftAdminNotifyApiKeyLessThan200Characters
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
    And I select option "nation.option.ENG"
    And I type on field "contactNumber" value "01224 522599"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "disabledbadges@aberdeencity.gov.uk"
    And I select option "badgePackType.option.Standard"
    And I select option "paymentsEnabled.option.false"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
    And I type on field "govUkNotifyApiKey" value "LessThan200CharactersValidForApiKey"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"


  Scenario: DftAdminNotifyAppSubmittedTemplateLessThan200Characters
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
    And I select option "nation.option.ENG"
    And I type on field "contactNumber" value "01224 522599"
    And I type on field "websiteUrl" value "http://localhost"
    And I type on field "emailAddress" value "disabledbadges@aberdeencity.gov.uk"
    And I select option "badgePackType.option.Standard"
    And I select option "paymentsEnabled.option.false"
    And I type on field "differentServiceSignpostUrl" value "https://bluebadge.direct.gov.uk/bluebadge/why-are-you-here"
    And I type on field "govUkNotifyApplicationSubmittedTemplateId" value "LessThan200CharactersValidForApiKey"
    And I can click on element "updateDetails" button
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"


