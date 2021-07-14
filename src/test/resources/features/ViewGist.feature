Feature: View gist
  Background:
    Given User login into github
    And User navigate to "https://gist.github.com/"
    And User fill the Gist Description text field
    And User fill the Gist filename text field
    And User fill the Gist content
    And User click Create secret gist button
    And User will see the created gist
    And User navigate to "https://gist.github.com/"

  Scenario: View list all of User gist
    When User click view your gists
    Then User will see all the gist they have

  Scenario: View detail of a gist
    Given User click view your gists
    And User click the newest gist
    Then User will see the created gist