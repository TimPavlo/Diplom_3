package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameField = By.xpath(".//li[1]//input");
    private final By emailField = By.xpath(".//li[2]//input");
    private final By profileText = By.xpath(".//*[contains(text(), 'персональные данные')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(profileText));
    }

    @Step("Получить значение поля «Имя»")
    public String getNameValue() {
        return driver.findElement(nameField).getAttribute("value");
    }

    @Step("Получить значение поля «Email»")
    public String getEmailValue() {
        return driver.findElement(emailField).getAttribute("value");
    }
}