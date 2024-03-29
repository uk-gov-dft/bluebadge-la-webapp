@ApplicationScripts
Feature: Dft BlueBadge LA applications

  As a Blue Badge team member
  I want to view the latest applications
  So that I can process applications

  Scenario: Verify Applications (no applications)
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    When I can click on the "Order a badge" link on left navigation
    And I can click on the "Applications" link on left navigation
    Then I should see the page titled "Applications - GOV.UK Manage Blue Badges"


