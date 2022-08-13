package com.cydeo.tests.day06_xmlpath_deserilization;

import com.cydeo.utils.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExtraPractice extends SpartanTestBase {

    /**
     *  /**
     *      Given accept type is application/xml
     *      When i send GET request to /api/spartans
     *      Then status code is 200
     *      And content type is XML
     *      And spartan at index 5 is matching:
     *      id > 6
     *      name>Tedmund
     *      gender >Male
     *      phone >2227140732
     *      */


    @DisplayName("spartan/xml")
    @Test
    public void spartanXmlPathTest2(){

        Response response = given().accept(ContentType.XML)
                .when().get("/spartans");

        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals("application/xml",response.contentType());

        XmlPath xmlPath = response.xmlPath();

        assertEquals(6, xmlPath.getInt("List.item[5].id"));

        assertEquals("Tedmund",xmlPath.getString("List.item[5].name"));

        assertEquals("Male",xmlPath.getString("List.item[5].gender"));

        assertEquals("2227140732" ,xmlPath.getString("List.item[5].phone"));


    }

}
