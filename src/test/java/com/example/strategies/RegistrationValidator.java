package com.example.strategies;

import com.example.forms.RegistrationForm;
import org.openqa.selenium.By;

public interface RegistrationValidator {
    void apply(RegistrationForm form);
    By fieldLocator();
    String expectedMessage();
}