@PrivacyPage
Feature: Dft BlueBadge Display Privacy page

  As a citizen I should be able to navigate to the privacy page

  Scenario: Verify I can navigate to privacy page
    Given   I navigate to the "privacy-notice" page
    Then    I should see the page titled "Privacy notice - GOV.UK Manage Blue Badges"

  Scenario: Verify I can navigate to cookies page from the footer
    Given   I navigate to the "home" page
    And     I can click on "Privacy"
    Then    I should see the page titled "Privacy notice - GOV.UK Manage Blue Badges"

