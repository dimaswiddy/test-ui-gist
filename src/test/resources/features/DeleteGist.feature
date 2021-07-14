Feature: Delete gist
  Background:
    Given User login into github
    And User navigate to "https://gist.github.com/"
    And User fill the Gist Description text field
    And User fill the Gist filename text field
    And User fill the Gist content
    And User click Create secret gist button
    And User will see the created gist
    And User navigate to "https://gist.github.com/"

  Scenario: User delete gist
    Given User click view your gists
    And User click the newest gist
    And User click delete button
    Then User will see deleted gist notification