package ru.yandex.practicum.models;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import ru.yandex.practicum.utils.Constants;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(Constants.BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Step("Создание пользователя {user.email}")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(Constants.CREATE_USER_ENDPOINT)
                .then();
    }

    @Step("Удаление пользователя по токену")
    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(Constants.REMOVE_USER_ENDPOINT)
                .then();
    }

    @Step("Извлечение accessToken из ответа")
    public String getAccessToken(ValidatableResponse response) {
        return response.extract().path("accessToken");
    }
}