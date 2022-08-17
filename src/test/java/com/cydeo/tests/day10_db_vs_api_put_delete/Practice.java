package com.cydeo.tests.day10_db_vs_api_put_delete;

import com.cydeo.pojo.Spartan;
import com.cydeo.utils.SpartanTestBase;
import org.junit.jupiter.api.Test;
import com.cydeo.utils.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.cydeo.utils.SpartanRestUtils;
import com.cydeo.utils.SpartanTestBase;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class Practice extends SpartanTestBase {

    /**
     given accept is json and content type is json
     and body is:
     {
     "gender": "Male",
     "name": "PostVSDatabase"
     "phone": 1234567425
     }
     When I send POST request to /spartans
     Then status code is 201
     And content type is json
     And "success" is "A Spartan is Born!"
     When I send database query
     SELECT name, gender, phone
     FROM spartans
     WHERE spartan_id = newIdFrom Post request;
     Then name, gender , phone values must match with POST request details
     */

    @Test
    public void test() throws SQLException {
        Spartan spartan = new Spartan();
        spartan.setPhone(1234567425L);
        spartan.setGender("Male");
        spartan.setName("PostVSDatabase");

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(spartan)
                .when().post("/spartans");
        response.prettyPrint();
        assertThat(response.statusCode(),equalTo(201));
        assertThat(response.getContentType(),equalTo("application/json"));
        assertThat(response.jsonPath().getString("success"),is("A Spartan is Born!"));



        int spartanId= response.jsonPath().getInt("data.id");

        String dbUrl = ConfigurationReader.getProperty("spartan.db.url");
        String dbUser = ConfigurationReader.getProperty("spartan.db.userName");
        String dbPwd = ConfigurationReader.getProperty("spartan.db.password");

        Connection connection = DriverManager.getConnection(dbUrl,dbUser,dbPwd);

        String query="SELECT name,gender phone FROM spartans where spartan_id = " + spartanId;

        Map<String, Object> dbMap = DBUtils.getRowMap(query);
        System.out.println("dbMap = " + dbMap);

        //assert/validate data from database Matches data from post request
        assertThat(dbMap.get("NAME") , equalTo(spartan.getName()));
        assertThat(dbMap.get("GENDER") , equalTo(spartan.getGender()));
        assertThat(dbMap.get("PHONE") , equalTo(spartan.getPhone()+""));

        //disconnect from database
        DBUtils.destroy();



    }
}
