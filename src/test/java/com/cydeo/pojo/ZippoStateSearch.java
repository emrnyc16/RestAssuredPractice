package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ZippoStateSearch {

    @JsonProperty("place name")
    private String placeName;

    private String country;
    private String state;
    @JsonProperty("state abbreviation")
    private String stateAbbreviation;
    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    private List<ZipPlacesCity> places;
}
