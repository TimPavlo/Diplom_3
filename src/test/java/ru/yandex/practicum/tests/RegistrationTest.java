package ru.yandex.practicum.tests;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.practicum.pages.HomePage;
import ru.yandex.practicum.pages.LoginPage;
import ru.yandex.practicum.pages.RegistrationPage;
import ru.yandex.practicum.pages.ProfilePage;
import ru.yandex.practicum.utils.Constants;

public class RegistrationTest extends BaseTest {

    @Test
    public void newUserCanRegister() {
        String userName = RandomStringUtils.randomAlphabetic(8);
        String userEmail = RandomStringUtils.randomAlphanumeric(7) + "@test.org";
        String userPassword = RandomStringUtils.randomAlphanumeric(8);

        HomePage mainPage = new HomePage(driver);
        mainPage.navigateToLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToRegistration();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterName(userName);
        registrationPage.enterEmail(userEmail);
        registrationPage.enterPassword(userPassword);
        registrationPage.clickRegisterButton();

        // Ждём полной загрузки страницы логина (заголовок и кнопка)
        loginPage.waitForPageLoaded();

        Assert.assertEquals("Регистрация не привела на страницу логина",
                Constants.LOGIN_URL, driver.getCurrentUrl());

        loginPage.enterEmail(userEmail);
        loginPage.enterPassword(userPassword);
        loginPage.clickLoginButton();

        mainPage.waitForOrderButton();
        mainPage.openProfile();

        ProfilePage userProfile = new ProfilePage(driver);
        Assert.assertEquals("Имя пользователя не совпадает",
                userName, userProfile.getDisplayName());
        Assert.assertEquals("Email пользователя не совпадает",
                userEmail.toLowerCase(), userProfile.getDisplayEmail().toLowerCase());
    }

    @Test
    public void cannotRegisterWithShortPassword() {
        String userName = RandomStringUtils.randomAlphabetic(8);
        String userEmail = RandomStringUtils.randomAlphanumeric(7) + "@test.org";
        String shortPassword = RandomStringUtils.randomAlphanumeric(5);

        HomePage mainPage = new HomePage(driver);
        mainPage.navigateToLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToRegistration();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.enterName(userName);
        registrationPage.enterEmail(userEmail);
        registrationPage.enterPassword(shortPassword);
        registrationPage.clickRegisterButton();

        String errorMessage = registrationPage.getPasswordError();
        Assert.assertEquals("Некорректный пароль", errorMessage);
        Assert.assertEquals("Страница регистрации не должна смениться",
                Constants.REGISTER_URL, driver.getCurrentUrl());
    }
}