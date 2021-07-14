Feature: Create a Gist
  Background:
    Given User login into github
    And User navigate to "https://gist.github.com/"

  Scenario: Create a secret gist
    Given User fill the Gist Description text field
    And User fill the Gist filename text field
    And User fill the Gist content
    When User click Create secret gist button
    Then User will see the created gist

  Scenario: Create a public gist
    Given User fill the Gist Description text field
    And User fill the Gist filename text field
    And User fill the Gist content
    When User click Create public gist button
    Then User will see the created gist

  Scenario: Create a multiple file gist
    Given User fill the Gist Description text field
    And User fill the Gist filename text field
    And User fill the Gist content
    And User click add file button
    And User fill the second Gist filename text field
    And User fill the second Gist content
    When User click Create secret gist button
    Then User will see the created gist

  Scenario: Create a secret gist with empty content
    Given User fill the Gist Description text field
    And User fill the Gist filename text field
    And User left the Gist content empty
    When User click Create secret gist button
    Then User will see the content cannot be empty error