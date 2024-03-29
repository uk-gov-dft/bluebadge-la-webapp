@ManageUsersResetPassword @UsersRolesAndPermissionsScripts
Feature: Reset my password

  As an user
  I can reset my password from a password reset link

  Scenario: Verify password must be entered
    Given I navigate to the "set***REMOVED***/06c8f2fd-7064-4abb-bba9-b3839acd19b7" page
    When I click on element "button" button
    Then I should see the validation message for " ***REMOVED***

  Scenario: Verify validation when using blacklisted password
    Given I navigate to the "set***REMOVED***/06c8f2fd-7064-4abb-bba9-b3839acd19b7" page
    And   I type " ***REMOVED*** field by uipath
    And   I type " ***REMOVED*** field by uipath
    When I click on element "button" button
    Then I should see the validation message for " ***REMOVED***

  Scenario: Verify validation error for invalid email link uuid
    Given I navigate to the "set***REMOVED***/62017dcb-4ac6-45fa-ad30-5582e058a58c" page
    And   I type "SomeNewUniquePass" for "password.field" field by uipath
    And   I type "SomeNewUniquePass" for "passwordConfirm.field" field by uipath
    When I click on element "button" button
    Then I should see the validation message for " ***REMOVED***

  Scenario: Verify validation error when email link is used twice
    Given I navigate to the "set***REMOVED***/06c8f2fd-7064-4abb-bba9-b3839acd19b7" page
    And   I type "SomeNewUniquePass" for "password.field" field by uipath
    And   I type "SomeNewUniquePass" for "passwordConfirm.field" field by uipath
    When I click on element "button" button
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    Given I navigate to the "set***REMOVED***/06c8f2fd-7064-4abb-bba9-b3839acd19b7" page
    And   I type "SomeNewUniquePass22" for "password.field" field by uipath
    And   I type "SomeNewUniquePass22" for "passwordConfirm.field" field by uipath
    When I click on element "button" button
    Then I should see the validation message for " ***REMOVED***

  Scenario: Verify validation error when email link is no longer active
    Given I navigate to the "set***REMOVED***/032da369-8877-4722-bde2-7ae01408b06d" page
    And   I type "SomeNewUniquePass22" for "password.field" field by uipath
    And   I type "SomeNewUniquePass22" for "passwordConfirm.field" field by uipath
    When I click on element "button" button
    Then I should see the validation message for " ***REMOVED***

  Scenario: Verify validation error when email link has expired
    Given I navigate to the "set***REMOVED***/8fac089e-b925-4b6e-8470-7ab0a1c6a301" page
    And   I type "SomeNewUniquePass22" for "password.field" field by uipath
    And   I type "SomeNewUniquePass22" for "passwordConfirm.field" field by uipath
    When I click on element "button" button
    Then I should see the validation message for " ***REMOVED***