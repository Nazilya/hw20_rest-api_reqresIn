package guru.qa.spec;

import guru.qa.config.Config;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static guru.qa.helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;

public class Specs {

    public static RequestSpecification request = with()
            .baseUri(Config.baseURL())
            .basePath(Config.basePath())
            .log().uri()
            .log().method()
            .filter(withCustomTemplates())
            //.log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(BODY)
            .expectStatusCode(200)
//            .expectBody(containsString("success"))
            .build();

    public static ResponseSpecification responseSpec201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .build();
}
