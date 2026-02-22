package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public class EmptyEmailValidator implements RegistrationValidator {
    @Override
    public void apply(RegistrationForm form) {
        form.withFirstName("Valid")
            .withLastName("User")
            .withEmail("")
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
        return "E-Mail Address does not appear to be valid!";
    }
}