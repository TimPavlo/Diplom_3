package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private final By emailField = By.xpath(".//label[text()='Email']/../input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/../input");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.className("Auth_link__1fOlj");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void goToRegistration() {
        driver.findElement(registerLink).click();
    }

    // обратная совместимость (используется где то старый метод)
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}