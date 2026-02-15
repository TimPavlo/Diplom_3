package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;

    private final By nameField = By.xpath(".//li[1]//input");
    private final By emailField = By.xpath(".//li[2]//input");
    private final By profileText = By.xpath(".//*[contains(text(), 'персональные данные')]");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(profileText));
    }

    public String getDisplayName() {
        return driver.findElement(nameField).getAttribute("value");
    }

    public String getDisplayEmail() {
        return driver.findElement(emailField).getAttribute("value");
    }
}