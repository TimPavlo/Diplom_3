package ru.yandex.practicum.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.practicum.pages.HomePage;
import ru.yandex.practicum.pages.LoginPage;
import ru.yandex.practicum.pages.ProfilePage;
import ru.yandex.practicum.pages.RecoverPasswordPage;
import ru.yandex.practicum.pages.RegistrationPage;
import ru.yandex.practicum.models.User;
import ru.yandex.practicum.models.UserClient;
import io.restassured.response.ValidatableResponse;

public class LoginTest extends BaseTest {

    private User user;
    private String accessToken;
    private final UserClient userClient = new UserClient();

    private void createTestUser() {
        String email = RandomStringUtils.randomAlphanumeric(7) + "@test.org";
        String password = RandomStringUtils.randomAlphanumeric(8);
        String name = RandomStringUtils.randomAlphabetic(8);
        user = new User(email, password, name);
        ValidatableResponse response = userClient.create(user);
        accessToken = userClient.getAccessToken(response);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    public void loginViaMainPageButton() {
        createTestUser();
        HomePage mainPage = new HomePage(driver);
        mainPage.navigateToLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForOrderButton();
        mainPage.openProfile();

        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertEquals("Имя не совпадает", user.getName(), profilePage.getNameValue());
        Assert.assertEquals("Email не совпадает", user.getEmail().toLowerCase(), profilePage.getEmailValue().toLowerCase());
    }

    @Test
    public void loginViaProfileButton() {
        createTestUser();
        HomePage mainPage = new HomePage(driver);
        mainPage.openProfile(); // Здесь клик по "Личный кабинет"

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user.getEmail(), user.getPassword());

        mainPage.waitForOrderButton();
        mainPage.openProfile();

        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertEquals("Имя не совпадает", user.getName(), profilePage.getNameValue());
    }

    @Test
    public void loginViaRegistrationForm() {
        createTestUser();
        HomePage mainPage = new HomePage(driver);
        mainPage.navigateToLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToRegistration();

        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickLoginLink(); // возвращаемся на страницу логина

        LoginPage loginPage2 = new LoginPage(driver);
        loginPage2.login(user.getEmail(), user.getPassword());

        mainPage.waitForOrderButton();
        mainPage.openProfile();

        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertEquals("Имя не совпадает", user.getName(), profilePage.getNameValue());
    }

    @Test
    public void loginViaPasswordRecovery() {
        createTestUser();
        HomePage mainPage = new HomePage(driver);
        mainPage.navigateToLogin();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToPasswordRecovery();

        RecoverPasswordPage recoverPage = new RecoverPasswordPage(driver);
        recoverPage.clickLoginLink(); // возвращаемся на страницу логина

        LoginPage loginPage2 = new LoginPage(driver);
        loginPage2.login(user.getEmail(), user.getPassword());

        mainPage.waitForOrderButton();
        mainPage.openProfile();

        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertEquals("Имя не совпадает", user.getName(), profilePage.getNameValue());
    }
}