package ru.yandex.practicum.tests;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.practicum.pages.HomePage;
import java.time.Duration;

public class ConstructorTest extends BaseTest {

    @Test
    public void switchToSauces() {
        HomePage mainPage = new HomePage(driver);
        mainPage.clickSaucesSection();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> mainPage.isSaucesSelected());

        Assert.assertTrue("Вкладка Соусы не активировалась",
                mainPage.isSaucesSelected());
    }

    @Test
    public void switchToFillings() {
        HomePage mainPage = new HomePage(driver);
        mainPage.clickFillingsSection();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> mainPage.isFillingsSelected());

        Assert.assertTrue("Вкладка Начинки не активировалась",
                mainPage.isFillingsSelected());
    }

    @Test
    public void switchToBuns() {
        HomePage mainPage = new HomePage(driver);
        mainPage.clickSaucesSection();
        mainPage.clickBunsSection();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> mainPage.isBunsSelected());

        Assert.assertTrue("Вкладка Булки не активировалась",
                mainPage.isBunsSelected());
    }
}