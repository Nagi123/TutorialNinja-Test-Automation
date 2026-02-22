package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public class InvalidEmailValidator implements RegistrationValidator {
    @Override
    public void apply(RegistrationForm form) {
        form.withFirstName("Valid")
            .withLastName("User")
            .withEmail("invalid-email-format")
            .withTelephone("1234567890")
            .withPassword("Password1")
            .withPasswordConfirm("Password1")
            .subscribeToNewsletter(false)
            .acceptPrivacyPolicy()
            .submit();
    }

    @Override
    public By fieldLocator() {
        return By.id("input-email");
    }

    @Override
    public String expectedMessage() {
        // The Tutorial Ninja site validates email format via built-in HTML5 patterns, which doesn’t produce a server-side text message.
        // Return an empty expected message to indicate no text error is expected for invalid format submissions.
        return "";
    }
}