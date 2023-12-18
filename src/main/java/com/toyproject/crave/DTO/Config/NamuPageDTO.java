package com.toyproject.crave.DTO.Config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class NamuPageDTO {
    @Setter
    int id;

    @Getter
    @Setter
    String name;
    @Setter
    String displayName;
    @Setter
    List<NamuPageDTO> next;
    @Setter
    List<NamuPageDTO> prev;
}
