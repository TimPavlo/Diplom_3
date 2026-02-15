package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    private final WebDriver driver;

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath(".//p[contains(text(), 'Личный Кабинет')]");
    private final By orderButton = By.xpath(".//button[text()='Оформить заказ']");

    private final By bunsTab = By.xpath(".//span[text()='Булки']/parent::div");
    private final By saucesTab = By.xpath(".//span[text()='Соусы']/parent::div");
    private final By fillingsTab = By.xpath(".//span[text()='Начинки']/parent::div");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToLogin() {
        driver.findElement(loginButton).click();
    }

    public void openProfile() {
        driver.findElement(personalAccountButton).click();
    }

    public void waitForOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    private void closeModalIfPresent() {
        System.out.println("Пытаемся удалить оверлей через JS...");
        try {
            List<WebElement> overlays = driver.findElements(By.className("Modal_modal_overlay__x2ZCr"));

            if (!overlays.isEmpty()) {
                System.out.println("🔍 Найдено оверлеев: " + overlays.size());
                // Удаляем оверлей через JavaScript
                for (WebElement overlay : overlays) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].remove();", overlay);
                }
                System.out.println("Оверлей удалён из DOM");
                Thread.sleep(500);
            } else {
                System.out.println("Оверлея нет");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public void clickBunsSection() {
        closeModalIfPresent();
        driver.findElement(bunsTab).click();
    }

    public void clickSaucesSection() {
        closeModalIfPresent();
        driver.findElement(saucesTab).click();
    }

    public void clickFillingsSection() {
        closeModalIfPresent();
        driver.findElement(fillingsTab).click();
    }

    public boolean isBunsSelected() {
        String classValue = driver.findElement(bunsTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }

    public boolean isSaucesSelected() {
        String classValue = driver.findElement(saucesTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }

    public boolean isFillingsSelected() {
        String classValue = driver.findElement(fillingsTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }
}