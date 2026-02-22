package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public class EmptyTelephoneValidator implements RegistrationValidator {
    @Override
    public void apply(RegistrationForm form) {
        form.withFirstName("Valid")
            .withLastName("User")
            .withEmail("valid" + System.currentTimeMillis() + "@example.com")
            .withTelephone("")
            .withPassword("Password1")
            .withPasswordConfirm("Password1")
            .subscribeToNewsletter(false)
            .acceptPrivacyPolicy()
            .submit();
    }

    @Override
    public By fieldLocator() {
        return By.id("input-telephone");
    }

    @Override
    public String expectedMessage() {
        return "Telephone must be between 3 and 32 characters";
    }
}