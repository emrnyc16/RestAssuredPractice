package com.cydeo.utils;
import com.cydeo.pojo.Spartan;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class SpartanRestUtils {

    private static String baseUrl = ConfigurationReader.getProperty("spartan.api.url");

    public static void deleteSpartanById(int spartanId) {

        //send DELETE request {{baseUrl}}/api/spartans/{id}
        given().pathParam("id", spartanId)
                .when().delete(baseUrl + "/spartans/{id}")
                .then().log().all();
    }

    public static Spartan getNewSpartan() {
        //create Faker class object to help us genarate random values
        Faker random = new Faker();
        Spartan spartan = new Spartan();
        spartan.setName(random.name().firstName());

        int num = random.number().numberBetween(1, 3);
        if (num == 1) {
            spartan.setGender("Female");
        } else {
            spartan.setGender("Male");
        }
        spartan.setPhone(random.number().numberBetween(1000000000L, 9999999999L));

        return spartan;
    }

    /**
     * Method accepts spartanID and sends a GET request
     * @param spartanId
     * @return is Map object containing response json data
     */

    public static Map<String, Object> getSpartan(int spartanId) {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", spartanId)
                .when().get(baseUrl + "/spartans/{id}");

        Map<String,Object>spartanMap = response.as(Map.class);
        return spartanMap;
        // return response.as(Map.class);

    }
}
