package com.cydeo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ZipPlacesCity {

    @JsonProperty("place name")
    private String placeName;

    private String longitude;

    @JsonProperty("post code")
    private String postalCode;
    private String latitude;

}
