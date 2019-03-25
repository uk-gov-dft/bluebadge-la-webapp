@Breathlessness
Feature: Dft BlueBadge LA New applications

  Scenario:Breathlessness Answers for UPHILL
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    When I click on application with applicant name as "BreathlessnessUPHillTest"
    Then I should see that the application contains answer for "Walking up a slight hill"

  Scenario:Breathlessness Answers for KeepUp
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    When I click on application with applicant name as "BreathlessnessKeepUpTest"
    Then I should see that the application contains answer for "Trying to keep up with others on level ground"


  Scenario:Breathlessness Answers for OwnPace
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    When I click on application with applicant name as "BreathlessnessOwnPace"
    Then I should see that the application contains answer for "Walking on level ground at my own pace"

  Scenario:Breathlessness Answers for Dressed
    Given I navigate to the "home" page
    When I can click on the "Sign in" link
    When I type username "abc@dft.gov.uk" and  ***REMOVED***
    And I can click Sign in button
    Then I should see the page titled "Manage users - GOV.UK Manage Blue Badges"
    And I can click on the "New applications" link on left navigation
    Then I should see the page titled "New applications - GOV.UK Manage Blue Badges"
    When I click on application with applicant name as "BreathlessnessDressed"
    Then I should see that the application contains answer for "Getting dressed or trying to leave my home"



