package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public class ShortPasswordValidator implements RegistrationValidator {
    @Override
    public void apply(RegistrationForm form) {
        String email = "valid" + System.currentTimeMillis() + "@example.com";
        form.withFirstName("Valid")
            .withLastName("User")
            .withEmail(email)
            .withTelephone("1234567890")
            .withPassword("123")
            .withPasswordConfirm("123")
            .subscribeToNewsletter(false)
            .acceptPrivacyPolicy()
            .submit();
    }

    @Override
    public By fieldLocator() {
        return By.id("input-password");
    }

    @Override
    public String expectedMessage() {
        return "Password must be between 4 and 20 characters";
    }
}