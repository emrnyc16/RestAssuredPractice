package com.cydeo.tests.day12_jsonschema_authorization;

import com.cydeo.utils.SpartanTestBase;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.cydeo.utils.SpartanTestBase;
public class SingleSpartanJsonSchemaValidationTest extends SpartanTestBase {

    /**
     given accept type is json
     and path param id is 15
     when I send GET request to /spartans/{id}
     Then status code is 200
     And json payload/body matches SingleSpartanSchema.json
     */

    @Test
    public void singleSpartanSchemaValidationTest(){
     given().accept(ContentType.JSON)
             .and().pathParam("id",15)
             .when().get("/spartans/{id}")
             .then().statusCode(200)
             .and().body(JsonSchemaValidator.matchesJsonSchema(
                     new File("src/test/resources/jsonschemas/SingleSpartanSchema.json")))
             .and().log().all();

    }

    @DisplayName("GET /spartans/search and json schema validation")
    @Test
    public void searchSpartanSchemaValidationTest(){
        given().accept(ContentType.JSON)
                .and().queryParams("nameContains","e")
                .and().queryParams("gender","Female")
                .when().get("/spartans/search")
                .then().assertThat().statusCode(200)
                .and().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/jsonschemas/SearchSpartansSchema.json"))) .log().all();
    }
}
