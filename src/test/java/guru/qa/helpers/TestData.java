package guru.qa.helpers;

import lombok.Getter;

@Getter
public class TestData {
    private String usersName = "morpheus",
            usersJob = "leader",
            usersNewJob = "manager",
            email = "eve.holt@reqres.in",
            password = "cityslicka",
            token = "QpwL5tke4Pnpja7X4",
            errorMessage = "Missing email or username",
            missPassError = "Missing password";
}
