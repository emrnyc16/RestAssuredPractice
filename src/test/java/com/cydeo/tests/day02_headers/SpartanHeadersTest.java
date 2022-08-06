package com.cydeo.tests.day02_headers;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.Key;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


public class SpartanHeadersTest {

    /**
     • When I send a GET request to
     • spartan_base_url/api/spartans
     • Then Response STATUS CODE must be 200
     • And I Should see all Spartans data in JSON format
     */

    String url="http://3.92.197.172:8000/api/spartans";

    @DisplayName("GET /api/spartans and expect Json response")
    @Test
    public void getAllSpartansHeaderTest(){
        when().get(url)
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON);
               // .and().contentType("application/json");
    }

    /**
     * Given Accept type is application/xml
     • When I send a GET request to
     • spartan_base_url/api/spartans
     • Then Response STATUS CODE must be 200
     • And I Should see all Spartans data in xml format
     */

    @DisplayName("GET /api/spartans and expect XML response")
    @Test
    public void acceptTypeHeaderTest(){
        given().accept(ContentType.XML). // or you can type application/XML
        when().get(url)
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.XML);
        // .and().contentType("application/json");
    }

    /**
     * Given Accept type is application/json
     • When I send a GET request to
     -----------------------------
     • spartan_base_url/api/spartans
     • Then Response STATUS CODE must be 200
     • And read headers
     */
    @DisplayName("GET /api/spartans - read headers")
    @Test
    public void readResponseHeaders() {
       Response response=given().accept(ContentType.JSON).when().get(url);

        System.out.println("Date header= " + response.getHeader("Date"));
        System.out.println("Contenttype= " + response.getHeader("Content-type"));
        System.out.println("Connection= " + response.getHeader("Connection"));
        System.out.println(response.getHeaders());

    }
}
