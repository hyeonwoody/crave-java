package com.toyproject.crave.DTO.Config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;



public class ConfigDTO {

    @Getter
    @JsonProperty("inputScope")
    private int scope;

    @Getter
    @JsonProperty("inputAlgorithm")
    private int algorithm;

    @Getter
    @JsonProperty("inputMethod")
    private int method;

    @Getter
    @JsonProperty("inputOrigin")
    private String origin;

    @Getter
    @JsonProperty("inputDestination")
    private String destination;

    @Getter
    @JsonProperty("inputHow")
    private int how;

    @Getter
    @JsonProperty("inputNumStage")
    private int numStage;

    @JsonIgnore
    private String inputDropDown;

    public boolean areMembersNotNull() {
        return scope != -1 && algorithm != -1 && method != -1 &&
                !origin.isEmpty() &&
                !destination.isEmpty() &&
                how != -1 && numStage != -1;
    }


}

