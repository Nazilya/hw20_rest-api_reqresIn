package guru.qa.tests;

import guru.qa.helpers.TestData;
import guru.qa.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static guru.qa.helpers.Endpoints.*;
import static guru.qa.spec.Specs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("APITests")
public class PositiveTests {
    TestData testData = new TestData();

    @Tag("APITests")
    @DisplayName("проверка успешной авторизации по логину и паролю")
    @Test
    void loginTest() {
        step("проверка успешной авторизации по логину и паролю", () -> {
            RequestAuthorizationModel data = new RequestAuthorizationModel();
            data.setEmail(testData.getEmail());
            data.setPassword(testData.getPassword());

            given(request)
                    .body(data)
                    .when()
                    .post(LOGIN_ENDPOINT)
                    .then()
                    .log().status()
                    .log().body()
                    .spec(responseSpec)
                    .body("token", is(testData.getToken()));
        });
    }

    @DisplayName("проверка создания нового польз-ля")
    @Test
    void createUserTest() {
        step("создать нового пользователя", () -> {
            RequestUserModel user = new RequestUserModel();
            user.setName(testData.getUsersName());
            user.setJob(testData.getUsersJob());

            given()
                    .spec(request)
                    .body(user)
                    .when()
                    .post(CREATE_ENDPOINT)
                    .then()
                    .log().status()
                    .log().body()
                    .spec(responseSpec201)
                    .body("name", is(testData.getUsersName()))
                    .body("job", is(testData.getUsersJob()));
        });
    }

    @DisplayName("проверка обновления данных созданного польз-ля")
    @Test
    void updateUserTest() {
        step("обновить данные созданного польз-ля", () -> {
            CreateUserByBuilder user = CreateUserByBuilder.builder()
                    .name(testData.getUsersName())
                    .job(testData.getUsersNewJob())
                    .build();

            given()
                    .spec(request)
                    .body(user)
                    .when()
                    .put(UPDATE_ENDPOINT)
                    .then()
                    .log().status()
                    .log().body()
                    .spec(responseSpec)
                    .body("name", is(testData.getUsersName()))
                    .body("job", is(testData.getUsersNewJob()));
        });
    }

    @DisplayName("проверка удаления пользователя")
    @Test
    void deleteUserTest() {
        step("удалить пользователя", () -> {
            given(request)
                    .when()
                    .delete(DELETE_ENDPOINT)
                    .then()
                    .statusCode(204);
        });
    }

    @DisplayName("проверка получения польз-ля по id")
    @Test
    void getUserByIdTest() {
        step("получить польз-ля по id", () -> {
            ResponseUserModel user = given(request)
                    .when()
                    .get(GET_USER_ENDPOINT)
                    .then()
                    .spec(responseSpec)
                    .log().body()
                    .extract().as(ResponseUserModel.class);
            assertEquals("Weaver", user.getData().getLastName());
        });
    }

    @DisplayName("проверка получения списка всех пользователей")
    @Test
    void getListUsersByPageNumberTest() {
        step("получить список всех пользователей", () -> {
            ResponseListUsersModel listUsers = given()
                    .spec(request)
                    .when()
                    .param("page", 2)
                    .get(GET_USERS_LIST_ENDPOINT)
                    .then()
                    .spec(responseSpec)
                    .log().body()
                    .body("data.findAll{it.id == 7}.last_name.flatten()",
                            hasItem("Lawson"))
                    .extract().as(ResponseListUsersModel.class);

            assertEquals(2, listUsers.getPage());
        });
    }

    @Test
    public void checkEmailUsingGroovy() {
        step("получить список всех пользователей (пример с урока)", () -> {
            given()
                    .spec(request)
                    .when()
                    .get(GET_USERS_LIST_ENDPOINT)
                    .then()
                    .log().body()
                    .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                            hasItem("eve.holt@reqres.in"));
        });
    }

}
