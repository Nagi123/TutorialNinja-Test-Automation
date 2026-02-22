package com.example.steps;

import com.example.forms.RegistrationForm;
import com.example.hooks.DriverFactory;
import com.example.strategies.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class RegistrationSteps {
    private RegistrationForm registrationForm;
    private String successHeading;
    private final List<String> validationFailures = new ArrayList<>();

    @Given("the user opens the registration page")
    public void theUserOpensTheRegistrationPage() {
        WebDriver driver = DriverFactory.getDriver();
        registrationForm = new RegistrationForm(driver).open();
    }

    @When("the user enters valid registration details")
    public void theUserEntersValidRegistrationDetails() {
        String uniqueEmail = "user" + System.currentTimeMillis() + "@example.com";
        String password = "Password1";
        registrationForm
            .withFirstName("John")
            .withLastName("Doe")
            .withEmail(uniqueEmail)
            .withTelephone("1234567890")
            .withPassword(password)
            .withPasswordConfirm(password)
            .subscribeToNewsletter(false)
            .acceptPrivacyPolicy()
            .submit();
        String warning = registrationForm.getWarningMessage();
        if (warning.isEmpty()) {
            successHeading = DriverFactory.getDriver().findElement(By.cssSelector("#content h1")).getText().trim();
        } else {
            successHeading = warning;
        }
        SharedData.registeredEmail = uniqueEmail;
        SharedData.registeredPassword = password;
    }

    @Then("the account is created successfully")
    public void theAccountIsCreatedSuccessfully() {
        Assertions.assertEquals("Your Account Has Been Created!", successHeading);
    }

    @When("the user submits the registration form with various invalid values")
    public void theUserSubmitsTheRegistrationFormWithVariousInvalidValues() {
        List<com.example.strategies.RegistrationValidator> validators = List.of(
                new EmptyFirstNameValidator(),
                new EmptyLastNameValidator(),
                new EmptyEmailValidator(),
                new InvalidEmailValidator(),
                new EmptyTelephoneValidator(),
                new InvalidTelephoneLengthValidator(),
                new ShortPasswordValidator(),
                new EmptyPasswordValidator(),
                new EmptyConfirmPasswordValidator(),
                new MismatchedConfirmPasswordValidator(),
                new UncheckedPrivacyValidator()
        );

        for (var validator : validators) {
            registrationForm.open();
            validator.apply(registrationForm);
            String expected = validator.expectedMessage();
            By locator = validator.fieldLocator();
            WebDriver driver = DriverFactory.getDriver();
            try {
                new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .until(d -> {
                        if (locator != null) {
                            String err = registrationForm.fieldErrorForInput(locator);
                            if (!err.isEmpty()) return true;
                        }
                        String warnMsg = registrationForm.getWarningMessage();
                        return !warnMsg.isEmpty();
                    });
            } catch (Exception ignored) {
            }
            String actualMessage;
            if (locator != null) {
                actualMessage = registrationForm.fieldErrorForInput(locator);
            } else {
                actualMessage = registrationForm.getWarningMessage();
            }
            String normalizedExpected = expected.replaceAll("!", "").trim();
            String normalizedActual = actualMessage.replaceAll("!", "").trim();
            if (!normalizedActual.contains(normalizedExpected)) {
                validationFailures.add("Expected message '" + expected + "' but found '" + actualMessage + "'");
            }
        }
    }

    @Then("each field displays the correct error message")
    public void eachFieldDisplaysTheCorrectErrorMessage() {
        if (!validationFailures.isEmpty()) {
            Assertions.fail(String.join("; ", validationFailures));
        }
    }
}