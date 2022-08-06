package com.cydeo.tests.day3_parameters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;



public class Practice {

    String url = "https://jsonplaceholder.typicode.com/posts";

    /**
     * - Given accept type is Json
     * - When user sends request to https://jsonplaceholder.typicode.com/posts
     *
     * -Then print response body
     *
     * - And status code is 200
     * - And Content - Type is Json
     */

    @DisplayName("Question 1")
    @Test
    public void test() {
        Response response = given().accept(ContentType.JSON)
                .when().get(url);

        String body = response.asString();
        System.out.println("body = " + body);

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        assertTrue(response.contentType().contains("application/json"));


    }

    /**
     * - Given accept type is Json
     * - Path param "id" value is 1
     * - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
     * - Then status code is 200
     * <p>
     * -And json body contains "repellat provident"
     * - And header Content - Type is Json
     * - And header "X-Powered-By" value is "Express"
     * - And header "X-Ratelimit-Limit" value is 1000
     * - And header "Age" value is more than 100
     * <p>
     * - And header "NEL" value contains "success_fraction"
     */

    @DisplayName("GET/api/spartans/{id}")
    @Test
    public void getTypiCode() {

//    given().accept(ContentType.JSON).and().pathParam("id",1)
//            .when().get(url+"/{id}").then().assertThat().statusCode(HttpStatus.SC_OK).and().assertThat().body(containsString("repellat provident"))
//            .and().assertThat().contentType(ContentType.JSON)
//            .and().assertThat().header("X-Powered-By","Express")
//            .and().assertThat().header("X-Ratelimit-Limit","1000")
//            .and().assertThat().header("age",greaterThan("100"))
//            .and().assertThat().header("NEL",containsString("success_fraction"));

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 1)
                .when().get(url + "/{id}");

        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertTrue(response.asString().contains("repellat provident"));
        assertTrue(response.getHeader("Content-Type").contains("json"));
        assertEquals("Express", response.getHeader("X-Powered-By"));
        assertEquals("1000", response.getHeader("X-Ratelimit-Limit"));
        assertTrue(Integer.parseInt(response.getHeader("age")) > 100);
        assertTrue(response.getHeader("NEL").contains("success_fraction"));

    }

    /**
     * - Given accept type is Json
     * - Path param "id" value is 12345
     * - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
     * - Then status code is 404
     *
     * - And json body contains "{}"
     */

    @DisplayName("GET/api/spartans/{id} - body contains {}")
    @Test
    public void test3() {

    given().accept(ContentType.JSON)
            .and().pathParam("id",12345)
            .when().get(url+"/{id}")
            .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
            .and().assertThat().body(containsString("{}"));

    }

    /**
     * Q4:
     * - Given accept type is Json
     * - Path param "id" value is 2
     * - When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}/comments
     * - Then status code is 200
     *
     * - And header Content - Type is Json
     * - And json body contains "Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"
     */

    @DisplayName("GET/api/spartans/{id} - body contains emails")
    @Test
    public void test4() {
        given().accept(ContentType.JSON)
                .and().pathParam("id",2)
                .and().get(url+"/{id}/comments")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body(stringContainsInOrder("Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"));

}

    /**
     * Q5:
     *
     * - Given accept type is Json
     * - Query Param "postId" value is 1
     * - When user sends request to  https://jsonplaceholder.typicode.com/comments
     * - Then status code is 200
     *
     * - And header Content - Type is Json
     *
     * - And header "Connection" value is "keep-alive"
     * - And json body contains "Lew@alysha.tv"
     */


    @DisplayName("GET/api/spartans/{id} - body contains Lew@alysha.tv")
    @Test
    public void test5() {
        url="https://jsonplaceholder.typicode.com/comments";
        given().accept(ContentType.JSON)
                .and().queryParam("postId",1)
                .when().get(url)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().header("Connection","keep-alive")
                .and().assertThat().body(containsString("Lew@alysha.tv"));

    }

    /**
     * - Given accept type is Json
     * - Query Param "postId" value is 333
     * - When user sends request to  https://jsonplaceholder.typicode.com/comments
     *
     * - And header Content - Type is Json
     *
     * - And header "Content-Length" value is 2
     * - And json body contains "[]"
     */
    @DisplayName("GET/api/spartans/{id} - body contains []")
    @Test
    public void test6() {
        url="https://jsonplaceholder.typicode.com/comments";

        given().accept(ContentType.JSON)
                .and().queryParam("postId",333)
                .when().get(url)
                .then().assertThat().contentType(ContentType.JSON)
                .and().assertThat().header("Content-Length","2")
                .and().assertThat().body(containsString("[]"));


    }
}