package guru.qa.tests;

import guru.qa.helpers.TestData;
import guru.qa.models.RequestAuthorizationModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static guru.qa.helpers.Endpoints.GET_USERS_LIST_ENDPOINT;
import static guru.qa.helpers.Endpoints.LOGIN_ENDPOINT;
import static guru.qa.spec.Specs.request;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@Tag("APITests")
public class NegativeTests {
    TestData testData = new TestData();

    @DisplayName("проверка неудачной авторизации (без логина и пароля)")
    @Test
    void authorizationWithOutLoginPasswordNegativeTest() {
        step("попытка авторизации без логина и пароля", () -> {
            given(request)
                    .when()
                    .post(LOGIN_ENDPOINT)
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .body("error", is(testData.getErrorMessage()));
        });
    }

    @DisplayName("проверка неудачной авторизации (отсутствует пароль)")
    @Test
    void authorizationWithOutPasswordNegativeTest() {
        step("попытка авторизации без пароля", () -> {
            RequestAuthorizationModel data = new RequestAuthorizationModel();
            data.setEmail("eve.holt@reqres.in");
            data.setPassword("");

            given()
                    .spec(request)
                    .body(data)
                    .when()
                    .post(LOGIN_ENDPOINT)
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(400)
                    .body("error", is(testData.getMissPassError()));
        });
    }

    @DisplayName("проверка получения польз-ля по несуществующему id")
    @Test
    void getUserByInvalidIdNegativeTest() {
        step("получить польз-ля по несуществующему id", () -> {
            given(request)
                    .when()
                    .get("/api/users/0")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(404);
        });
    }

    @DisplayName("проверка получения списка пользователей несуществующей страницы")
    @Test
    void getListUsersByWrongPageNumberNegativeTest() {
        step("получить список пользователей несуществующей страницы", () -> {
            given(request)
                    .when()
                    .param("page", 0)
                    .post(GET_USERS_LIST_ENDPOINT)
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(400);
        });
    }

}
