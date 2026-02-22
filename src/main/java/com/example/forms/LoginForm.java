package com.example.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class LoginForm extends GenericFormWrapper<LoginForm> {
    private static final String LOGIN_URL = "https://tutorialsninja.com/demo/index.php?route=account/login";

    private final By emailInput = By.id("input-email");
    private final By passwordInput = By.id("input-password");
    private final By loginButton = By.cssSelector("input[type='submit'][value='Login']");
    private final By alertDanger = By.cssSelector("div.alert.alert-danger");

    public LoginForm(WebDriver driver) {
        super(driver);
    }

    public LoginForm open() {
        driver.get(LOGIN_URL);
        visible(emailInput);
        return this;
    }

    public LoginForm withEmail(String email) {
        return type(emailInput, email);
    }

    public LoginForm withPassword(String password) {
        return type(passwordInput, password);
    }

    @Override
    public LoginForm submit() {
        return click(loginButton);
    }

    public String getWarningMessage() {
        try {
            return driver.findElement(alertDanger).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}