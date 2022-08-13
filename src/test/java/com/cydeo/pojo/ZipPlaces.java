package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZipPlaces {


    @JsonProperty("place name")
    private String placeName;

    private String longitude;

    @JsonProperty("state abbreviation")
    private String stateAbbreviation;
    private String state;
    private String latitude;

}
