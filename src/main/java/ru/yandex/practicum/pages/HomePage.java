package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[contains(text(), 'Личный Кабинет')]");
    private final By orderButton = By.xpath(".//button[text()='Оформить заказ']");

    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    @Step("Нажать кнопку «Войти в аккаунт»")
    public void navigateToLogin() {
        clickWithJS(loginButton);
    }

    @Step("Открыть личный кабинет")
    public void openProfile() {
        clickWithJS(personalAccountButton);
    }

    @Step("Ожидать появления кнопки «Оформить заказ»")
    public void waitForOrderButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    @Step("Кликнуть на вкладку «Булки» и дождаться активации")
    public void clickBunsSectionAndWait() {
        clickWithJS(bunsTab);
        wait.until(ExpectedConditions.attributeContains(bunsTab, "class", "tab_tab_type_current__2BEPc"));
    }

    @Step("Кликнуть на вкладку «Соусы» и дождаться активации")
    public void clickSaucesSectionAndWait() {
        clickWithJS(saucesTab);
        wait.until(ExpectedConditions.attributeContains(saucesTab, "class", "tab_tab_type_current__2BEPc"));
    }

    @Step("Кликнуть на вкладку «Начинки» и дождаться активации")
    public void clickFillingsSectionAndWait() {
        clickWithJS(fillingsTab);
        wait.until(ExpectedConditions.attributeContains(fillingsTab, "class", "tab_tab_type_current__2BEPc"));
    }

    @Step("Кликнуть на вкладку «Булки»")
    public void clickBunsSection() {
        clickWithJS(bunsTab);
    }

    @Step("Кликнуть на вкладку «Соусы»")
    public void clickSaucesSection() {
        clickWithJS(saucesTab);
    }

    @Step("Кликнуть на вкладку «Начинки»")
    public void clickFillingsSection() {
        clickWithJS(fillingsTab);
    }

    @Step("Проверить, активна ли вкладка «Булки»")
    public boolean isBunsSelected() {
        String classValue = driver.findElement(bunsTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }

    @Step("Проверить, активна ли вкладка «Соусы»")
    public boolean isSaucesSelected() {
        String classValue = driver.findElement(saucesTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }

    @Step("Проверить, активна ли вкладка «Начинки»")
    public boolean isFillingsSelected() {
        String classValue = driver.findElement(fillingsTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }
}