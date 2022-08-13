package com.cydeo.tests.day08_hamcrest;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class HamcrestMatchersIntro {

    @Test
    public void numbersTest(){
        assertThat(3+2,is(5));
        assertThat(3+2,equalTo(5));
        assertThat(3+2,is(equalTo(5)));
        assertThat(3+2,is(greaterThan(4)));

        // assertTrue(100+4 >99); JUnit
        int num = 10+2;
        assertThat(num, is(not(10)));
        assertThat(num,is(not(equalTo(9))));
    }

    @Test
    public void stringsTest(){
        String word = "wooden spoon";
        assertThat(word,is("wooden spoon"));

        assertThat(word,equalTo("wooden spoon"));

        // startswith / endswith
        assertThat(word,startsWith("wood"));
        assertThat(word,startsWithIgnoringCase("WOOD"));
        assertThat("endsWith 'n'",word,endsWith("n"));

        //contains
        assertThat(word,containsString("den"));
        assertThat(word,containsStringIgnoringCase("SPOON"));

        // empty string
        String str = " ";
        assertThat(str,is(blankString()));
        assertThat(str.trim(),is(emptyOrNullString()));

    }

    @Test
    public void listTest(){
        //List<Integer> nums = List.of(5,20,1,54); ==> another option for list // from java 9
        List<Integer> nums = Arrays.asList(5,20,1,54,0);
        List<String> words = Arrays.asList("java","selenium","git","maven","api");

        //size
        assertThat(nums,hasSize(5));
        assertThat(words,hasSize(5));

        // contains
        assertThat(nums,hasItem(5));
        assertThat(words,hasItems("git","java"));
        assertThat(nums,containsInAnyOrder(54,5,0,20,1));

        //everyitem
        assertThat(nums,everyItem(greaterThanOrEqualTo(0)));
        assertThat(words,everyItem(not(blankString())));
        //assertThat(Arrays.asList(words.split("")),everyItem(not(blankString())));
    }
}