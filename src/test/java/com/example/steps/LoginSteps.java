package com.example.steps;

import com.example.forms.LoginForm;
import com.example.forms.RegistrationForm;
import com.example.hooks.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    private LoginForm loginForm;
    private String loginError;

    @Given("a user account exists")
    public void aUserAccountExists() {
        if (SharedData.registeredEmail == null || SharedData.registeredPassword == null) {
            WebDriver driver = DriverFactory.getDriver();
            RegistrationForm form = new RegistrationForm(driver);
            String email = "user" + System.currentTimeMillis() + "@example.com";
            String password = "Password1";
            form.open()
                .withFirstName("Auto")
                .withLastName("User")
                .withEmail(email)
                .withTelephone("1234567890")
                .withPassword(password)
                .withPasswordConfirm(password)
                .subscribeToNewsletter(false)
                .acceptPrivacyPolicy()
                .submit();
            SharedData.registeredEmail = email;
            SharedData.registeredPassword = password;
            // clear cookies so subsequent login scenarios start from a logged-out state
            driver.manage().deleteAllCookies();
        }
    }

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        WebDriver driver = DriverFactory.getDriver();
        loginForm = new LoginForm(driver);
        loginForm.open()
            .withEmail(SharedData.registeredEmail)
            .withPassword(SharedData.registeredPassword)
            .submit();
    }

    @Then("the user is logged in successfully")
    public void theUserIsLoggedInSuccessfully() {
        WebDriver driver = DriverFactory.getDriver();
        // wait until the account page heading is present and non-stale
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
            .until(d -> {
                try {
                    var el = d.findElement(By.cssSelector("#content h2, #content h1"));
                    String text = el.getText().trim();
                    return !text.isEmpty();
                } catch (org.openqa.selenium.StaleElementReferenceException | org.openqa.selenium.NoSuchElementException e) {
                    return false;
                }
            });
        String heading = driver.findElement(By.cssSelector("#content h2, #content h1")).getText().trim();
        Assertions.assertTrue(heading.contains("My Account"));
    }

    @When("the user attempts to log in with invalid credentials")
    public void theUserAttemptsToLogInWithInvalidCredentials() {
        WebDriver driver = DriverFactory.getDriver();
        loginForm = new LoginForm(driver);
        loginForm.open()
            .withEmail("invalid@example.com")
            .withPassword("invalidpassword")
            .submit();
        loginError = loginForm.getWarningMessage();
    }

    @Then("a login error message is displayed")
    public void aLoginErrorMessageIsDisplayed() {
        Assertions.assertTrue(loginError.contains("No match for E-Mail Address and/or Password"));
    }
}