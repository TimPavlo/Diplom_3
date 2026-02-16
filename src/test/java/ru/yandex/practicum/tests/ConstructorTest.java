package ru.yandex.practicum.tests;

import org.junit.Assert;
import org.junit.Test;
import ru.yandex.practicum.pages.HomePage;

public class ConstructorTest extends BaseTest {

    @Test
    public void switchToSauces() {
        HomePage mainPage = new HomePage(driver);
        mainPage.clickSaucesSectionAndWait();
        Assert.assertTrue("Вкладка Соусы не активировалась", mainPage.isSaucesSelected());
    }

    @Test
    public void switchToFillings() {
        HomePage mainPage = new HomePage(driver);
        mainPage.clickFillingsSectionAndWait();
        Assert.assertTrue("Вкладка Начинки не активировалась", mainPage.isFillingsSelected());
    }

    @Test
    public void switchToBuns() {
        HomePage mainPage = new HomePage(driver);
        mainPage.clickSaucesSection();
        mainPage.clickBunsSectionAndWait();
        Assert.assertTrue("Вкладка Булки не активировалась", mainPage.isBunsSelected());
    }
}