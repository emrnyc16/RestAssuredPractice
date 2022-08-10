package com.cydeo.tests.day3_parameters;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//        given().accept(ContentType.JSON)
//                .and().pathParam("id",2)
//                .and().get(url+"/{id}/comments")
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .and().assertThat().contentType(ContentType.JSON)
//                .and().assertThat().body(stringContainsInOrder("Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"));
        given().accept(ContentType.JSON)
                .and().pathParam("id",2)
                .and().get(url+"/{id}/comments")
                .then().statusCode(HttpStatus.SC_OK)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("email[0]", equalTo("Presley.Mueller@myrl.com"),"email[1]",equalTo("Dallas@ole.me"),"email[2]",equalTo("Mallory_Kunze@marie.org"));
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

    @Test
    public void videotest(){
        url="http://3.92.197.172:8000/api/spartans";

        Response response = given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains","J")
                .when().get(url+"/search");

        // verify status code
        assertEquals(HttpStatus.SC_OK,response.statusCode());

        // verify content type
        assertTrue(response.getContentType().contains("application/json"));

        //verify Female
        assertTrue(response.body().asString().contains("Female"));

        // verify Male not exists
        assertFalse(response.body().asString().contains("Male"));

        // verify Janette
        assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();
    }

    @Test
    public void queryParam2(){
        // creating map for query Params
        url="http://3.92.197.172:8000/api/spartans";

        Map<String,Object> paramsMap = new HashMap<>();
        paramsMap.put("gender","Female");
        paramsMap.put("nameContains","J");

        // send request
        Response response = given().accept(ContentType.JSON)
                .and().queryParams(paramsMap)
                .when().get(url+"/search");

        // verify status code
        assertEquals(HttpStatus.SC_OK,response.statusCode());

        // verify content type
        assertTrue(response.getContentType().contains("application/json"));

        //verify Female
        assertTrue(response.body().asString().contains("Female"));

        // verify Male not exists
        assertFalse(response.body().asString().contains("Male"));

        // verify Janette
        assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();
    }

    @Test
    public void pathMethod(){
        url="http://3.92.197.172:8000/api/spartans";

        Response response = given().accept(ContentType.JSON)
                .pathParam("id",10)
                .when().get(url+"/{id}");

        // Status code
        assertEquals(HttpStatus.SC_OK,response.statusCode());

        //verifying content type
        assertTrue(response.getContentType().contains("application/json"));

        System.out.println("id= " + response.body().path("id").toString());
        System.out.println("name= " + response.body().path("name").toString());
        System.out.println("gender= " + response.body().path("gender").toString());
        System.out.println("phone= " + response.body().path("phone").toString());

        int id= response.path("id");
        String name = response.body().path("name");
        String gender = response.body().path("gender");
        long phone = response.path("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        assertEquals(10,id);
        assertEquals("Lorenza",name);
        assertEquals("Female",gender);
        assertEquals(3312820936L,phone);

    }

    @Test
    public void patMethod2(){
        Response response = get("http://3.92.197.172:8000/api/spartans");
        //extract first id

        int firstId= response.path("id[0]");
        System.out.println("firstId = " + firstId);

        //extract name
        String first1stName= response.path("name[0]");
        System.out.println("first1stName = " + first1stName);

        // get  the last firstName
        String last1stName = response.path("name[-1]");
        System.out.println("last1stName = " + last1stName);

        // extract all firstNames and print them
        List<String> names = response.path("name");
        System.out.println("names = " + names);

        System.out.println("names.size() = " + names.size());

        List<Object> phoneNumbers = response.path("phone");

        for(Object phoneNumber : phoneNumbers){
            System.out.println(phoneNumber);
        }
    }

    @Test
    public void jsonPath(){
        url="http://3.92.197.172:8000/api/spartans";

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 11)
                .when().get(url + "/{id}");

        assertEquals(HttpStatus.SC_OK,response.statusCode());
        int id = response.path("id");
        System.out.println("id = " + id);

        // how read value with JsonPath?

        JsonPath jsonData =response.jsonPath();

        int id1 = jsonData.getInt("id");
        String name = jsonData.getString("name");
        String gender = jsonData.getString("gender");
        long phone = jsonData.getLong("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        //verifying json payload with JsonPAth
        assertEquals(11,id);
        assertEquals("Nona",name);
        assertEquals("Female",gender);
        assertEquals(7959094216L,phone);

    }

    @Test
    public void hamsCrest(){
        url="http://3.92.197.172:8000/api/spartans";

        given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get(url + "/{id}")
                    // response
                .then().statusCode(HttpStatus.SC_OK)
                .and().assertThat().contentType(ContentType.JSON);
    }
    @Test
    public void hamCrest2(){
        url="http://3.92.197.172:8000/api/spartans";
        given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get(url + "/{id}")
                .then().statusCode(HttpStatus.SC_OK)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("id", equalTo(15),"name",equalTo("Meta"),"gender",equalTo("Female"),"phone",equalTo(1938695106));
    }

    @Test
    public void gdsonTest(){
        url="http://3.92.197.172:8000/api/spartans";

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 11)
                .and().when().get(url + "/{id}");

        // convert Json response to Java Collections(Maps)

        Map<String,Object> spartanMap = response.body().as(Map.class);

        System.out.println(spartanMap.get("name"));
        System.out.println(spartanMap.get("id"));

        assertEquals("Nona",spartanMap.get("name"));

    }

    @Test
    public void gdsonTest2(){
        url="http://3.92.197.172:8000/api/spartans";

        Response response = given().accept(ContentType.JSON)
                .when().get(url);

        response.prettyPrint();

        // convert full json bod to list of maps

        List<Map<String,Object>> listOfSpartans = response.body().as(List.class);

        // print all data of first spartan

        System.out.println(listOfSpartans.get(0));
        System.out.println(listOfSpartans.get(listOfSpartans.size() - 1));

        Map<String,Object> firstSpartan = listOfSpartans.get(0);
        System.out.println(firstSpartan.get("name"));

        int counter = 1;

        for(Map<String,Object> map : listOfSpartans){
            System.out.println(counter+" - spartan " + map);
            counter++;
        }

    }
}