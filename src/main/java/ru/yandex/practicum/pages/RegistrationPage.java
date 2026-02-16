package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import java.time.Duration;

public class RegistrationPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameInput = By.xpath(".//fieldset[1]//input");
    private final By emailInput = By.xpath(".//fieldset[2]//input");
    private final By passwordInput = By.xpath(".//fieldset[3]//input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By passwordError = By.xpath("//fieldset[.//input[@type='password']]//p[contains(@class, 'input_error')]");
    private final By loginLink = By.xpath(".//a[text()='Войти']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(registerButton));
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

    @Step("Ввести имя: {name}")
    public void enterName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step("Нажать кнопку «Зарегистрироваться»")
    public void clickRegisterButton() {
        clickWithJS(registerButton);
    }

    @Step("Получить текст ошибки под полем пароля")
    public String getPasswordError() {
        removeAllOverlays();
        // Мгновенная проверка (без ожидания)
        if (driver.findElements(passwordError).size() > 0) {
            return driver.findElement(passwordError).getText();
        }
        System.out.println("!!! Элемент с ошибкой не найден сразу после клика");
        // Ожидание до 20 секунд
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement errorElement = longWait.until(ExpectedConditions.presenceOfElementLocated(passwordError));
        return errorElement.getText();
    }

    @Step("Перейти на страницу логина по ссылке «Войти»")
    public void clickLoginLink() {
        clickWithJS(loginLink);
    }

    @Step("Заполнить форму регистрации (имя: {name}, email: {email})")
    public void fillForm(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }
}