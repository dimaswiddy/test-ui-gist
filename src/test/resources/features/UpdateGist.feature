Feature: Update gist
  Background:
    Given User login into github
    And User navigate to "https://gist.github.com/"
    And User fill the Gist Description text field
    And User fill the Gist filename text field
    And User fill the Gist content
    And User click Create secret gist button
    And User will see the created gist
    And User navigate to "https://gist.github.com/"

  Scenario: Update secret gist
    Given User click view your gists
    And User click the newest gist
    And User click edit button
    And User edit Description
    And User edit FileName
    And User edit content
    When User click Update secret gist button
    Then User see updated gist detail