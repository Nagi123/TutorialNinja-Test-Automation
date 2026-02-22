package com.example.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class RegistrationForm extends GenericFormWrapper<RegistrationForm> {
    private static final String REGISTRATION_URL = "https://tutorialsninja.com/demo/index.php?route=account/register";

    public final By firstNameInput = By.id("input-firstname");
    public final By lastNameInput = By.id("input-lastname");
    public final By emailInput = By.id("input-email");
    public final By telephoneInput = By.id("input-telephone");
    public final By passwordInput = By.id("input-password");
    public final By confirmInput = By.id("input-confirm");

    private final By newsletterYes = By.cssSelector("input[name='newsletter'][value='1']");
    private final By newsletterNo = By.cssSelector("input[name='newsletter'][value='0']");
    private final By privacyCheckbox = By.name("agree");
    private final By continueButton = By.cssSelector("input[type='submit'][value='Continue']");
    private final By alertDanger = By.cssSelector("div.alert.alert-danger");

    public RegistrationForm(WebDriver driver) {
        super(driver);
    }

    public RegistrationForm open() {
        driver.get(REGISTRATION_URL);
        visible(firstNameInput);
        return this;
    }

    public RegistrationForm withFirstName(String firstName) {
        return type(firstNameInput, firstName);
    }

    public RegistrationForm withLastName(String lastName) {
        return type(lastNameInput, lastName);
    }

    public RegistrationForm withEmail(String email) {
        return type(emailInput, email);
    }

    public RegistrationForm withTelephone(String telephone) {
        return type(telephoneInput, telephone);
    }

    public RegistrationForm withPassword(String password) {
        return type(passwordInput, password);
    }

    public RegistrationForm withPasswordConfirm(String confirm) {
        return type(confirmInput, confirm);
    }

    public RegistrationForm subscribeToNewsletter(boolean subscribe) {
        if (subscribe) {
            click(newsletterYes);
        } else {
            click(newsletterNo);
        }
        return this;
    }

    public RegistrationForm acceptPrivacyPolicy() {
        WebElement checkbox = visible(privacyCheckbox);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    @Override
    public RegistrationForm submit() {
        return click(continueButton);
    }

    public String getWarningMessage() {
        try {
            return driver.findElement(alertDanger).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public String fieldErrorForInput(By inputLocator) {
        try {
            WebElement input = driver.findElement(inputLocator);
            WebElement err = input.findElement(By.xpath("following-sibling::div[contains(@class,'text-danger')][1]"));
            return err.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public List<String> getFieldErrorMessages() {
        List<WebElement> errors = driver.findElements(By.cssSelector("div.text-danger"));
        return errors.stream()
                .map(el -> el.getText().trim())
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public String registerValidAccount(String firstName, String lastName, String baseEmail, String telephone, String password) {
        String uniqueEmail = baseEmail + System.currentTimeMillis() + "@example.com";
        open()
            .withFirstName(firstName)
            .withLastName(lastName)
            .withEmail(uniqueEmail)
            .withTelephone(telephone)
            .withPassword(password)
            .withPasswordConfirm(password)
            .subscribeToNewsletter(false)
            .acceptPrivacyPolicy()
            .submit();
        try {
            return driver.findElement(By.cssSelector("#content h1")).getText().trim();
        } catch (NoSuchElementException e) {
            return "";
        }
    }
}