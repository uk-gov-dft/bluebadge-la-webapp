@ManageLocalAuthorities
Feature: Dft BlueBadge LA Manage local authorities

  As a DfT admin
  I want to view LAs
  So that I can ensure that LA details are correct

  Scenario: Verify Manage local authorities
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "dft_admin@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "Manage local authorities" link on left navigation
    Then I should see the page titled "Manage local authorities - GOV.UK Manage Blue Badges"
