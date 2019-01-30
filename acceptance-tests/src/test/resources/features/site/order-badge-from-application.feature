@OrderBadgeFromApplication @OrderBadgeFromApplicationScripts
Feature: Dft BlueBadge Order a Badge - order badge from application

  As a Blue Badge team member
  I want to order a badge based on application
  So that I don't have to rekey all of the supporting data

  Scenario: Order badge from PERSON application
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    When I click on application with name "John The First"
    Then I should see the page titled "New application - GOV.UK Manage Blue Badges"
    Then I should see element with ui path "order"
    And I can see the "Order badge" button
    When I click the "Order badge" button
    Then I should see the "Personal Details" page where the form is pre-filled with the application's relevant data
