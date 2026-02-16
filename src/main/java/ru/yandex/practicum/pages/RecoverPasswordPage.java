package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import java.time.Duration;

public class RecoverPasswordPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By restoreHeading = By.xpath(".//*[text()='Восстановление пароля']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");

    public RecoverPasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(restoreHeading));
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

    @Step("Нажать ссылку «Войти» на странице восстановления пароля")
    public void clickLoginLink() {
        clickWithJS(loginLink);
    }
}