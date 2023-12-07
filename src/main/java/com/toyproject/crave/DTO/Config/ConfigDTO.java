package com.toyproject.crave.DTO.Config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigDTO {

    @JsonProperty("inputScope")
    private int scope;

    @JsonProperty("inputAlgorithm")
    private int algorithm;

    @JsonProperty("inputMethod")
    private int method;

    @JsonProperty("inputOrigin")
    private String origin;

    @JsonProperty("inputDestination")
    private String destination;

    @JsonProperty("inputHow")
    private int how;

    @JsonProperty("inputNumStage")
    private int numStage;

    @JsonIgnore
    private String inputDropDown;

    public int getHow() {
        return how;
    }
}
