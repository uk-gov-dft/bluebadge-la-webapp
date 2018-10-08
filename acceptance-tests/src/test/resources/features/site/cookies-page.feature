@CookiesPage
Feature: Dft BlueBadge Display Cookies page

  As a citizen I should be able to navigate to the cookies page

  Scenario: Verify I can navigate to cookies page
    Given   I navigate to the "cookies" page
    Then    I should see the page titled "Cookies - GOV.UK Manage Blue Badges"

  Scenario: Verify I can navigate to cookies page from the footer
    Given   I navigate to the "home" page
    And     I can click on "Cookies"
    Then    I should see the page titled "Cookies - GOV.UK Manage Blue Badges"
