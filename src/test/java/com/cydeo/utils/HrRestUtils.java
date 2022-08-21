package com.cydeo.utils;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
public class HrRestUtils {

    private static final String baseUrl = ConfigurationReader.getProperty("hr.db.url");

    public static void addNewRegion(int regionID,String regionName){
        Map<String,Object> newEntry = new HashMap<>();
        newEntry.put("region_id",regionID);
        newEntry.put("region_name",regionName);

        given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(newEntry)
                .post(baseUrl+"/regions/")
                .then().log().all();
    }

    public static void delete(int regionId){
        given().accept(ContentType.JSON)
                .pathParam("region_Id",regionId)
                .when().delete(baseUrl+"/regions/{region_Id}")
                .then().log().all();
    }
}
