package com.cydeo.tests.day05_path_jsonpath;

import com.cydeo.utils.HRApiTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class HREmployeesJsonPathTest extends HRApiTestBase {


    /**
     Given accept type is Json
     And query param limit is 200
     when I send GET request to /employees
     Then I can use jsonPath to query and read values from json body
     */

    @DisplayName("GET /employees?limit=200 => jsonPath filters")
    @Test
    public void jsonPathEmployeesTest() {
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("limit", 200)
                .when().get("/employees");
        assertEquals(200,response.statusCode());

        //convert/parse json response body to jsonpath object

        JsonPath jsonPath = response.jsonPath();

        System.out.println("first emp firstname = " + jsonPath.getString("item[0].first_name"));
        System.out.println("first emp job title = " + jsonPath.getString("item[0].job_title"));

        // Working with JsonPath lists:
          // get all the emails into a list and print out
        List<String> emails = jsonPath.getList("items.email");
        System.out.println("emails = " + emails);

        //assert TGATES is among emails:
        assertTrue(emails.contains("TGATES"));

        // get all employee first names who work for department_id 90

        List<String> namesAtDep90 = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name");
        System.out.println("namesAtDep90 = " + namesAtDep90);
        System.out.println(namesAtDep90.size());

        //get all employee first names who work as IT_PROG

        List<String> itProgs = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.first_name");
        System.out.println("itProgs = " + itProgs);

        //get all employee first names whose salary is more than or equal 5000

        List<String> salaryMore5000 = jsonPath.getList("items.findAll{it.salary >= 5000}.first_name");
        System.out.println("salaryMore5000 = " + salaryMore5000);

        // get emp first name who has max salary

        String firstName = jsonPath.getString("items.max{it.salary}.first_name");

        System.out.println("firstName = " + firstName);

        //get emp first name who has min salary

        String minSalaryEmp = jsonPath.getString("items.min{it.salary}.first_name");

        System.out.println("minSalaryEmp = " + minSalaryEmp);

        String minSalaryEmpInfo = jsonPath.getString("items.min{it.salary}");

        System.out.println("minSalaryEmpInfo = " + minSalaryEmpInfo);


    }

}