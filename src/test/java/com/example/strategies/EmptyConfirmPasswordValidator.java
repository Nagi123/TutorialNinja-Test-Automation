package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public class EmptyConfirmPasswordValidator implements RegistrationValidator {
    @Override
    public void apply(RegistrationForm form) {
        String email = "valid" + System.currentTimeMillis() + "@example.com";
        form.withFirstName("Valid")
            .withLastName("User")
            .withEmail(email)
            .withTelephone("1234567890")
            .withPassword("Password1")
            .withPasswordConfirm("")
            .subscribeToNewsletter(false)
            .acceptPrivacyPolicy()
            .submit();
    }

    @Override
    public By fieldLocator() {
        return By.id("input-confirm");
    }

    @Override
    public String expectedMessage() {
        return "Password confirmation does not match password";
    }
}