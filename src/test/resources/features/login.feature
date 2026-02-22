Feature: Account login

  As an existing user
  I want to authenticate with my credentials
  So that I can access my account

  Background:
    Given a user account exists

  Scenario: Successful login
    When the user logs in with valid credentials
    Then the user is logged in successfully

  Scenario: Invalid login
    When the user attempts to log in with invalid credentials
    Then a login error message is displayed