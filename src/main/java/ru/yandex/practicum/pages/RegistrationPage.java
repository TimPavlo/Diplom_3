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
    private final By passwordError = By.xpath("//*[@id=\"root\"]/div/main/div/form/fieldset[3]/div/p");
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

    private void clickWithWait(By locator) {
        removeAllOverlays();
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
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
        clickWithWait(registerButton);
    }

    @Step("Получить текст ошибки под полем пароля")
    public String getPasswordError() {
        removeAllOverlays();
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement errorElement = longWait.until(ExpectedConditions.visibilityOfElementLocated(passwordError));
        return errorElement.getText();
    }

    @Step("Перейти на страницу логина по ссылке «Войти»")
    public void clickLoginLink() {
        clickWithWait(loginLink);
    }

    @Step("Заполнить форму регистрации (имя: {name}, email: {email})")
    public void fillForm(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }
}