@ApplicationDetails
Feature: Dft BlueBadge LA New applications - view application details

  As a Blue Badge team member
  I want to view application details
  So that I can determine how to progress the application

  Scenario: View application details
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    #When I click on the first application of the table
    #Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    #Then I should see the page title for Application Details for that particular application id
# TODO
#I need to insert one application, I have it in my notes, there is no standard way to insert data for acceptance-tests
# in la-webapp.

