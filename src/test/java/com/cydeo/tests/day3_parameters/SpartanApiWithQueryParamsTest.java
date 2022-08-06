package com.cydeo.tests.day3_parameters;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class SpartanApiWithQueryParamsTest {

    /**
        Given Accept type is Json
        And query parameter values are:
        gender|Female
        nameContains|e
        When user sends GET request to /api/spartans/search
        Then response status code should be 200
        And response content-type: application/json
        And "Female" should be in response payload
        And "Janette" should be in response payload
     **/

    String url="http://3.92.197.172:8000/api/spartans";

    @Test
    public void searchForSpartanTest(){

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains","e")
                .when().get(url+"/search");

        System.out.println("response.statusCode() = " + response.statusCode());
        assertEquals(HttpStatus.SC_OK,response.statusCode());

        System.out.println("response.getContentType() = " + response.getContentType());

        assertTrue(response.asString().contains("Female"));

        assertTrue(response.asString().contains("Janette"));

        assertEquals(ContentType.JSON.toString(),response.contentType());
    }
}
