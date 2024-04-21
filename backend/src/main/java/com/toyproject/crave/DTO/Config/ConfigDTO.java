package com.toyproject.crave.DTO.Config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;



public class ConfigDTO {

    @Getter
    @JsonProperty("inputScope")
    private Integer scope;

    @Getter
    @JsonProperty("inputAlgorithm")
    private Integer algorithm;

    @Getter
    @JsonProperty("inputMethod")
    private Integer method;

    @Getter
    @JsonProperty("inputOrigin")
    private String origin;

    @Getter
    @JsonProperty("inputDestination")
    private String destination;

    @Getter
    @JsonProperty("inputHow")
    private Integer how;

    @Getter
    @JsonProperty("inputNumStage")
    private Integer numStage;

    @JsonIgnore
    private String inputDropDown;

    public boolean areMembersNotNull() {
        return scope != -1 && algorithm != -1 && method != -1 &&
                !origin.isEmpty() &&
                !destination.isEmpty() &&
                how != -1 && numStage != -1;
    }


}

