@ErrorPages
Feature: Dft BlueBadge Display Error Pages

  As an admin user
  If I navigate to the wrong url or if the application has a problem
  I should see an appropriate error page displayed

  Scenario: Verify I see 404 page not found when navigate to url that doesn't exist
    Given   I navigate to the "home" page
    When    I can click on the "Sign in" link
    Then    I should see the page titled "Sign in - GOV.UK Manage Blue Badges"
    And     I type username "abc@dft.gov.uk" and  ***REMOVED***
    And     I can click Sign in button
    Then    I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    Given   I navigate to the "some-page-that-does exist" page
    Then    I should see the page titled "Page not found - GOV.UK Manage Blue Badges"
