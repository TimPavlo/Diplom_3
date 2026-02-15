package ru.yandex.practicum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import io.qameta.allure.Step;

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

    @Step("Закрытие модального окна (если есть)")
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

    @Step("Нажать кнопку «Войти в аккаунт»")
    public void navigateToLogin() {
        closeModalIfPresent();
        driver.findElement(loginButton).click();
    }

    @Step("Открыть личный кабинет")
    public void openProfile() {
        closeModalIfPresent();
        driver.findElement(personalAccountButton).click();
    }

    @Step("Ожидание появления кнопки «Оформить заказ»")
    public void waitForOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(orderButton));
    }

    @Step("Клик по вкладке «Булки»")
    public void clickBunsSection() {
        closeModalIfPresent();
        driver.findElement(bunsTab).click();
    }

    @Step("Клик по вкладке «Соусы»")
    public void clickSaucesSection() {
        closeModalIfPresent();
        driver.findElement(saucesTab).click();
    }

    @Step("Клик по вкладке «Начинки»")
    public void clickFillingsSection() {
        closeModalIfPresent();
        driver.findElement(fillingsTab).click();
    }

    @Step("Проверка, активна ли вкладка «Булки»")
    public boolean isBunsSelected() {
        String classValue = driver.findElement(bunsTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }

    @Step("Проверка, активна ли вкладка «Соусы»")
    public boolean isSaucesSelected() {
        String classValue = driver.findElement(saucesTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }

    @Step("Проверка, активна ли вкладка «Начинки»")
    public boolean isFillingsSelected() {
        String classValue = driver.findElement(fillingsTab).getAttribute("class");
        return classValue.contains("tab_tab_type_current__2BEPc");
    }
}