package ru.yandex.practicum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class RegistrationPage {
    private final WebDriver driver;

    private final By nameInput = By.xpath(".//fieldset[1]//input");
    private final By emailInput = By.xpath(".//fieldset[2]//input");
    private final By passwordInput = By.xpath(".//fieldset[3]//input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By passwordError = By.xpath(".//fieldset[3]//p");
    private final By loadingAnimation = By.className("Modal_modal__loading__3534A");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(registerButton));
    }

    private void closeModalIfPresent() {
        try {
            List<WebElement> overlays = driver.findElements(By.className("Modal_modal_overlay__x2ZCr"));
            if (!overlays.isEmpty()) {
                for (WebElement overlay : overlays) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", overlay);
                }
                Thread.sleep(500);
            }
        } catch (Exception e) {
            // ignore
        }
    }

    private void waitForLoadingToDisappear() {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        try {
            wait.until(d -> {
                List<WebElement> animations = d.findElements(loadingAnimation);
                return animations.isEmpty();
            });
        } catch (Exception e) {
            // если анимации нет, продолжаем
        }
    }

    private void clickWithRetries(By locator, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            } catch (Exception e) {
                attempts++;
                if (attempts == maxAttempts) throw e;
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
        }
    }

    @Step("Ввести имя: {name}")
    public void enterName(String name) {
        closeModalIfPresent();
        driver.findElement(nameInput).sendKeys(name);
    }

    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        closeModalIfPresent();
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввести пароль: {password}")
    public void enterPassword(String password) {
        closeModalIfPresent();
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку «Зарегистрироваться»")
    public void clickRegisterButton() {
        closeModalIfPresent();
        waitForLoadingToDisappear();
        clickWithRetries(registerButton, 3);
    }

    @Step("Получить текст ошибки пароля")
    public String getPasswordError() {
        return driver.findElement(passwordError).getText();
    }
}