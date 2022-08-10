package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Practice extends HRApiTestBase {


    /**
     * Q1:
     * - Given accept type is Json
     * - Path param value- US
     * - When users sends request to /countries
     * - Then status code is 200
     * - And Content - Type is Json
     * - And country_id is US
     * - And Country_name is United States of America
     * - And Region_id is 2
     */

    @DisplayName("hr/countries/{value}")
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("value", "US")
                .when().get("/countries/{value}");

        assertEquals(HttpStatus.SC_OK,response.statusCode());


        assertEquals("application/json",response.getHeader("Content-Type"));

        JsonPath jsonPath = response.jsonPath();

        assertEquals("US",jsonPath.getString("country_id"));
        assertEquals("United States of America",jsonPath.getString("country_name"));
        assertEquals(2,jsonPath.getInt("region_id"));


    }

    /**
     * Q2:
     * - Given accept type is Json
     * - Query param value - q={"department_id":80}
     * - When users sends request to /employees
     * - Then status code is 200
     * - And Content - Type is Json
     * - And all job_ids start with 'SA'
     * - And all department_ids are 80
     * - Count is 25
     */

    @DisplayName("countries/q={\"department_id\":80}")
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"department_id\":80}")
                .get("/employees");

        assertEquals(HttpStatus.SC_OK,response.statusCode());

        assertEquals("application/json",response.getContentType());
        JsonPath jsonPath = response.jsonPath();

        List<String> jobID= jsonPath.getList(("items.job_id"));
        System.out.println(jobID);
        List<String > jobIDSA = new ArrayList<>();

        for (String each : jobID){
            if(each.startsWith("SA")){
                jobIDSA.add(each);
            }
        }
        System.out.println(jobIDSA);

        List<Integer> ids=jsonPath.getList("items.department_id");
        ids.forEach(p->assertEquals(80,p));
        assertEquals(25,ids.size());

    }

    /**
     * - Given accept type is Json
     * -Query param value q= region_id 3
     * - When users sends request to /countries
     * - Then status code is 200
     * - And all regions_id is 3
     * - And count is 6
     * - And hasMore is false
     * - And Country_name are;
     * Australia,China,India,Japan,Malaysia,Singapore
     */
    @DisplayName("countries/{q= region_id 3}")
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":3}")
                .get("/countries");

        assertEquals(HttpStatus.SC_OK,response.statusCode());

        JsonPath jsonPath = response.jsonPath();
        List<Integer> regionID= jsonPath.getList("items.region_id");

        regionID.forEach(p->assertEquals(3,p));
        assertEquals(6,regionID.size());
        assertEquals("false",jsonPath.getString("hasMore"));

        List<String>countries = jsonPath.getList("items.country_name");
        List<String> expectedCountries= new ArrayList<>(Arrays.asList("Australia","China","India","Japan","Malaysia","Singapore"));

        assertEquals(expectedCountries,countries);

        /**
         * Iterator approach
         */
        Iterator it = countries.iterator();
        Iterator it2 = expectedCountries.iterator();
        while(it.hasNext()){
            if(it.next().equals(it2.next())){
                it.remove();
                it2.remove();
            }
        }
        assertEquals(expectedCountries.size(),countries.size());
        System.out.println(countries);
        System.out.println(expectedCountries);
    }
}
