package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public class UncheckedPrivacyValidator implements RegistrationValidator {
    @Override
    public void apply(RegistrationForm form) {
        String email = "valid" + System.currentTimeMillis() + "@example.com";
        form.withFirstName("Valid")
            .withLastName("User")
            .withEmail(email)
            .withTelephone("1234567890")
            .withPassword("Password1")
            .withPasswordConfirm("Password1")
            .subscribeToNewsletter(false)
            .submit();
    }

    @Override
    public By fieldLocator() {
        return null;
    }

    @Override
    public String expectedMessage() {
        return "Warning: You must agree to the Privacy Policy";
    }
}