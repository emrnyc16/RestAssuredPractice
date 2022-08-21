package com.cydeo.tests.day11_put_patch_request;

import com.cydeo.tests.day09_post_put_delete.SpartanPostTest;
import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.meta.When;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class POSTVSPATCHPractices extends HRApiTestBase {

   @DisplayName("POST/GET/Data Validation")
   @Test
   public void test1(){
       /**
        * 1) POST a region then do validations. Please use Map or POJO class, or JsonPath
        * /**
        *  * given accept is json
        *  * and content type is json
        *  * When I send post request to "/regions/"
        *  * With json:
        *  * {
        *  *     "region_id":100,
        *  *     "region_name":"Test Region"
        *  * }
        *  * Then status code is 201
        *  * content type is json
        *  * region_id is 100
        *  * region_name is Test Region
        *  */

       /**
        *  * given accept is json
        *  * When I send post request to "/regions/100"
        *  * Then status code is 200
        *  * content type is json
        *  * region_id is 100
        *  * region_name is Test Region
        *  */
        int id=100;
       Map<String,Object> postMap = new HashMap<>();
       postMap.put("region_id",id);
       postMap.put("region_name","Test Region");
       Response postRequest = given().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .and().body(postMap)
               .when().post("/regions/");


       postRequest.prettyPrint();

       postRequest.then().assertThat().statusCode(HttpStatus.SC_CREATED)
               .and().assertThat().contentType(ContentType.JSON)
               .body("region_id",is(id),"region_name",is("Test Region"));

       int postId= postRequest.jsonPath().getInt("region_id");

       Response getRequest = given().accept(ContentType.JSON)
               .pathParam("id", postId)
               .when().get("/regions/{id}");

       JsonPath jsonPath = getRequest.jsonPath();

       assertThat(jsonPath.getInt("region_id"),equalTo(postMap.get("region_id")));
       assertThat(jsonPath.getString("region_name"),equalTo(postMap.get("region_name")));


   }

   @DisplayName("PUT/DELETE")
    @Test
    public void test2(){
       /**
        * 2) PUT request then DELETE
        * /**
        *  * Given accept type is Json
        *  * And content type is json
        *  * When i send PUT request to /regions/100
        *  * With json body:
        *  *    {
        *  *      "region_id": 100,
        *  *      "region_name": "Wooden Region"
        *  *    }
        *  * Then status code is 200
        *  * And content type is json
        *  * region_id is 100
        *  * region_name is Wooden Region
        * */
        /**
        *  * Given accept type is Json
        *  * When i send DELETE request to /regions/100
        *  * Then status code is 200
        *
   */
        int id=100;
       Map<String,Object> postMap = new HashMap<>();
       postMap.put("region_id",id);
       postMap.put("region_name","Wooden Region");
       Response putRequest = given().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .pathParam("id", id)
               .body(postMap)
               .when().put("/regions/{id}");

       putRequest.then().assertThat().statusCode(HttpStatus.SC_OK)
               .and().assertThat().contentType(ContentType.JSON);

       // Validating by Get RQ
       int postId= putRequest.jsonPath().getInt("region_id");

       Response getRequest = given().accept(ContentType.JSON)
               .pathParam("id", postId)
               .when().get("/regions/{id}");

       JsonPath jsonPath = getRequest.jsonPath();

       assertThat(jsonPath.getInt("region_id"),equalTo(postMap.get("region_id")));
       assertThat(jsonPath.getString("region_name"),equalTo(postMap.get("region_name")));

       // Delete RQ

       Response deleteRQ = given().accept(ContentType.JSON)
               .pathParam("id", postId)
               .delete("/regions/{id}");

       deleteRQ.then().assertThat().statusCode(HttpStatus.SC_OK);
   }

   @DisplayName("POST/Database/DELETE")
    @Test
    public void test3(){

/**
 * given accept is json
 * and content type is json
 * When I send post request to "/regions/"
 * With json:
 * {
 * "region_id":200,
 * "region_name":"Test Region"
 * }
 * Then status code is 201
 * content type is json
 * When I connect to HR database and execute query "SELECT region_id, region_name FROM regions WHERE region_id = 200"
 * Theen region_name from database should match region_name from POST request

 */


/**
 * Given accept type is Json
 * When i send DELETE request to /regions/200
 * Then status code is 200
 */

       int id=200;
       Map<String,Object> postMap = new HashMap<>();
       postMap.put("region_id",id);
       postMap.put("region_name","Test Region");
       Response postRequest = given().accept(ContentType.JSON)
               .contentType(ContentType.JSON)
               .and().body(postMap)
               .when().post("/regions/");

       postRequest.prettyPrint();

       postRequest.then().assertThat().statusCode(HttpStatus.SC_CREATED)
               .and().assertThat().contentType(ContentType.JSON)
               .body("region_id",is(id),"region_name",is("Test Region"));

       int postId= postRequest.jsonPath().getInt("region_id");

       String dbUrl= ConfigurationReader.getProperty("hr.db.url");
       String dbPwd=ConfigurationReader.getProperty("hr.db.password");
       String dbUsr=ConfigurationReader.getProperty("hr.db.userName");

       String query="SELECT region_id, region_name FROM regions WHERE region_id = 200";

       DBUtils.createConnection(dbUrl,dbUsr,dbPwd);
       Map<String, Object> rowMap = DBUtils.getRowMap(query);

       System.out.println(rowMap);
       assertThat(rowMap.get("REGION_NAME"),equalTo(postMap.get("region_name")));

       //Delete

       Response deleteRQ = given().accept(ContentType.JSON)
               .and().pathParam("id", id)
               .when().delete("/regions/{id}");

       deleteRQ.then().assertThat().statusCode(HttpStatus.SC_OK);
   }

}
