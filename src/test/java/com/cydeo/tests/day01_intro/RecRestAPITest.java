package com.cydeo.tests.day01_intro;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import javax.annotation.meta.When;

public class RecRestAPITest {

    String url="https://reqres.in/api/users";

    /**
     * When User sends GET Request to
     * https://reqres.in/api/users23
     *
     * Then Response status code should be 404
     * And Response body should contain "{}"
     *
     */

    @DisplayName("Get request to non existing user")
    @Test
    public void test(){

        //When user sends a get requrest
        Response response = when().get(url+"/23");

        // Then response code should be 404
        System.out.println("status code= "+ response.statusCode());

        assertEquals(404,response.statusCode());

        // and response body should contain {}

        System.out.println("Response json body = " + response.asString());
        assertEquals("{}",response.asString());

    }
}
