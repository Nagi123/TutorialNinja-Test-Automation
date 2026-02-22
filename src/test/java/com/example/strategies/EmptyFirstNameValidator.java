package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public class EmptyFirstNameValidator implements RegistrationValidator {
    @Override
    public void apply(RegistrationForm form) {
        form.withFirstName("")
            .withLastName("User")
            .withEmail("valid" + System.currentTimeMillis() + "@example.com")
            .withTelephone("1234567890")
            .withPassword("Password1")
            .withPasswordConfirm("Password1")
            .subscribeToNewsletter(false)
            .acceptPrivacyPolicy()
            .submit();
    }

    @Override
    public By fieldLocator() {
        return By.id("input-firstname");
    }

    @Override
    public String expectedMessage() {
        return "First Name must be between 1 and 32 characters";
    }
}