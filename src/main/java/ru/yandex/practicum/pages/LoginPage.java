package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emailInput = By.xpath(".//label[text()='Email']/../input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/../input");
    private final By loginButton = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.className("Auth_link__1fOlj");
    private final By recoverPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
    }

    private void removeAllOverlays() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('[class*=\"overlay\"]').forEach(el => el.remove());"
            );
        } catch (Exception e) {
            // игнорируем
        }
    }

    private void clickWithJS(By locator) {
        removeAllOverlays();
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку «Войти»")
    public void clickLoginButton() {
        clickWithJS(loginButton);
    }

    @Step("Перейти по ссылке «Зарегистрироваться»")
    public void goToRegistration() {
        clickWithJS(registerLink);
    }

    @Step("Перейти на страницу восстановления пароля")
    public void goToPasswordRecovery() {
        clickWithJS(recoverPasswordLink);
    }

    @Step("Выполнить вход (email: {email})")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}