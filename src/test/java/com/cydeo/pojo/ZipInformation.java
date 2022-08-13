package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZipInformation {

    @JsonProperty("post code")
    private String postCode;

    private String country;

    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    private List<ZipPlaces> places;


}
