Feature: Account registration

  As a new user
  I want to create an account
  So that I can access member-only features

  Scenario: Successful registration
    Given the user opens the registration page
    When the user enters valid registration details
    Then the account is created successfully

  Scenario: Registration field validations
    Given the user opens the registration page
    When the user submits the registration form with various invalid values
    Then each field displays the correct error message