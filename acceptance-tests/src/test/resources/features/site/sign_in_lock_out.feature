@SignInLockOut @UsersRolesAndPermissionsScripts
Feature: LA Sign In Page - User locked out after too many failed attempts

  Scenario: Verify user locked out
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "lawa_locked@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    Then I should see the validation message for "sign in account locked" as "Your account is locked"

  Scenario: Verify user is locked out on too many failed attempts
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "lawa_nearlylocked@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    Then I should see the validation message for "invalid email address or  ***REMOVED***
    And I type username "lawa_locked@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    Then I should see the validation message for "sign in account locked" as "Your account is locked"

  Scenario: Verify successful sign in resets the failed sign in count
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "lawa_nearlylocked@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
    And I can click Sign out button

    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "lawa_nearlylocked@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    Then I should see the validation message for "invalid email address or  ***REMOVED***

    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "lawa_nearlylocked@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    Then I should see the validation message for "invalid email address or  ***REMOVED***

  Scenario: Verify resetting the password unlocks the account
    Given I navigate to the "set***REMOVED***/06c8f2fd-7064-4abb-bba9-b3839acd19b7" page
    And   I type "my_new_ ***REMOVED*** field by uipath
    And   I type "my_new_ ***REMOVED*** field by uipath
    When I click on element "button" button
    Then I should see the validation message for "common  ***REMOVED***
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And I type username "lawa_locked@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"

  Scenario: Verify newly created user account is locked until password is set
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I create a new user
    And I can click Sign out button

    When I sign in as new user with  ***REMOVED***
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    Then I should see the validation message for "sign in account locked" as "Your account is locked"

    Given I navigate to new users reset password page
    And   I type "my_new_ ***REMOVED*** field by uipath
    And   I type "my_new_ ***REMOVED*** field by uipath
    When I click on element "button" button
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    Then I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    When I sign in as new user with  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Find a badge - GOV.UK Manage Blue Badges"
