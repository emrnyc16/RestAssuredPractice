package com.cydeo.tests.day07_deserialization;

import com.cydeo.pojo.ZipInformation;
import com.cydeo.pojo.ZipPlaces;
import com.cydeo.pojo.ZipPlacesCity;
import com.cydeo.pojo.ZippoStateSearch;
import com.cydeo.utils.ZippotamusTestBase;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class ZippotamusPOJOTestPractice extends ZippotamusTestBase {


    /**
     * Given Accept application/json
     * And path zipcode is 22031
     * When I send a GET request to /us endpoint
     * Then status code must be 200
     * And content type must be application/json
     * And Server header is cloudflare
     * And Report-To header exists
     * And body should contains following information
     * post code is 22031
     * country  is United States
     * country abbreviation is US
     * place name is Fairfax
     * state is Virginia
     * latitude is 38.8604
     */

    @DisplayName("GET/ZipCode")
    @Test
    public void zippotamusGetPOJOZipcode() {

        Response response = given().accept(ContentType.JSON)
                .pathParam("zipcode", "22031")
                .when().get("/us/{zipcode}");

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals("application/json", response.getContentType());
        assertEquals("cloudflare", response.getHeader("Server"));
        assertTrue(response.getHeader("Report-To").length() > 0);

        response.prettyPrint();

        ZipInformation zippo = response.as(ZipInformation.class);

        assertEquals(22031, Integer.parseInt(zippo.getPostCode()));
        assertEquals("United States", zippo.getCountry());
        assertEquals("US", zippo.getCountryAbbreviation());

        ZipPlaces zippoFirst = zippo.getPlaces().get(0);

        assertEquals("Fairfax", zippoFirst.getPlaceName());
        assertEquals("Virginia", zippoFirst.getState());
        assertEquals("38.8604", zippoFirst.getLatitude());
    }


    /**
     * Given Accept application/json
     * And path zipcode is 50000
     * When I send a GET request to /us endpoint
     * Then status code must be 404
     * And content type must be application/json
     */

    @DisplayName("GET/ZipCode/pathParam")
    @Test
    public void zippotamusGetPOJOZipcode2() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("zipcode", "50000")
                .when().get("/us/{zipcode}");
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode());
        assertEquals(ContentType.JSON.toString(), response.getContentType());
    }

    /**
     * Given Accept application/json
     * And path state is va
     * And path city is farifax
     * When I send a GET request to /us endpoint
     * Then status code must be 200
     * And content type must be application/json
     * And payload should contains following information
     * country abbreviation is US
     * country  United States
     * place name  Fairfax
     * each places must contains fairfax as a value
     * each post code must start with 22
     */

    @DisplayName("GET/US{state}/{city}")
    @Test
    public void zipppo3() {
        Map<String, String> pathMap = new HashMap<>();
        pathMap.put("state", "va");
        pathMap.put("city", "fairfax");
        Response response = given().accept(ContentType.JSON)
                .and().pathParams(pathMap)
                .when().get("/us/{state}/{city}");

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        assertEquals(ContentType.JSON.toString(), response.contentType());

        ZippoStateSearch zipInf = response.body().as(ZippoStateSearch.class);

        assertEquals("US", zipInf.getCountryAbbreviation());
        assertEquals("United States", zipInf.getCountry());


        List<ZipPlacesCity> zipAll = zipInf.getPlaces();

        // postal code

        for (int i = 0; i < zipAll.size(); i++) {
            if (!(zipAll.get(i).getPlaceName().contains("Fairfax"))) {
                throw new AssertionError(zipAll.get(i) + " does not contain fairfax");
            }
        }

        for (int i = 0; i < zipAll.size(); i++) {
            if (!(zipAll.get(i).getPostalCode().startsWith("22"))) {
                throw new AssertionError(zipAll.get(i) + " does not start with 22");
            }
        }
    }

    @DisplayName("GET/ZipCode")
    @Test
    public void zippotamusGetPOJOZipcode1Extra() {

        given().accept(ContentType.JSON)
                .pathParam("zipcode", "22031")
                .when().get("/us/{zipcode}")
                .then().statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON).and()
                .header("Server",is("cloudflare"))
                        .header("Report-To",notNullValue())
                .body("'post code'",equalTo(22031+""),"country",is("United States"),
                        "'country abbreviation'",is("US"),"places[0].'place name'",is("Fairfax"),
                "places[0].state",is("Virginia"),"places[0].latitude",equalTo("38.8604"));
    }


    /**
     * Given Accept application/json
     * And path zipcode is 22031
     * When I send a GET request to /us endpoint
     * Then status code must be 200
     * And content type must be application/json
     * And Server header is cloudflare
     * And Report-To header exists
     * And body should contains following information
     * post code is 22031
     * country  is United States
     * country abbreviation is US
     * place name is Fairfax
     * state is Virginia
     * latitude is 38.8604
     */
}