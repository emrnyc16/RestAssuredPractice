package com.cydeo.tests.day02_headers;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class SpartanApiHelloTest {

    /**
     * When I send GET request to http://3.93.242.50:8000/api/hello
     * Then status code should be 200
     * And response body should be equal to "Hello from Sparta"
     * And Content type is "text/plain;charset=UTF-8"
     */
    String url= "http://3.93.242.50:8000/api/hello";
    @DisplayName("Get api/Hello")
    @Test
    public void spartanTest(){


        Response response = when().get(url);

        System.out.println("Status code= "+ response.statusCode());
        assertEquals(200,response.statusCode());

        response.prettyPrint();
        assertEquals("Hello from Sparta",response.asString());

        String expectedContent="text/plain;charset=UTF-8";

        assertEquals(expectedContent,response.contentType());
    }


    /**
     *
     When I send GET request to http://3.93.242.50:8000/api/hello
     Then status code should be 200
     And content type is "text/plain;charset=UTF-8"
     */

    @DisplayName("GET api/hello - bdd")
    @Test
    public void helloApiBddTest() {
        when().get(url)
                .then().assertThat().statusCode(200)
                .and().contentType("text/plain;charset=UTF-8");
    }
}
