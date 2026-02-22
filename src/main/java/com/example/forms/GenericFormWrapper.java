package com.example.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@SuppressWarnings("unchecked")
public abstract class GenericFormWrapper<F extends GenericFormWrapper<F>> {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected GenericFormWrapper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement clickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected F type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(value == null ? "" : value);
        return (F) this;
    }

    protected F click(By locator) {
        clickable(locator).click();
        return (F) this;
    }

    public abstract F submit();
}