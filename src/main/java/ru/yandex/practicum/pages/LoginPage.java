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

public class LoginPage {
    private final WebDriver driver;

    private final By emailField = By.xpath(".//label[text()='Email']/../input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/../input");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.className("Auth_link__1fOlj");
    private final By loadingAnimation = By.className("Modal_modal__loading__3534A");
    private final By pageHeading = By.xpath("//h2[text()='Вход']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Ожидание загрузки страницы логина")
    public void waitForPageLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(pageHeading));
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .until(ExpectedConditions.visibilityOfElementLocated(loginButton));
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
                .withTimeout(Duration.ofSeconds(60))
                .pollingEvery(Duration.ofSeconds(1))
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

    private void clickWithJS(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void clickWithRetries(By locator, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                // Перед каждой попыткой убедимся, что анимации нет
                waitForLoadingToDisappear();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return;
            } catch (Exception e) {
                attempts++;
                if (attempts == maxAttempts) {
                    // Если обычный клик не сработал, пробуем JS
                    try {
                        clickWithJS(locator);
                        return;
                    } catch (Exception jsException) {
                        throw jsException;
                    }
                }
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
        }
    }

    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        closeModalIfPresent();
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввести пароль: {password}")
    public void enterPassword(String password) {
        closeModalIfPresent();
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLoginButton() {
        closeModalIfPresent();
        clickWithRetries(loginButton, 5);
    }

    @Step("Перейти по ссылке «Зарегистрироваться»")
    public void goToRegistration() {
        closeModalIfPresent();
        driver.findElement(registerLink).click();
    }

    @Step("Выполнить вход (email, пароль)")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}