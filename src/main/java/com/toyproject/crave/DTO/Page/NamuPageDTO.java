package com.toyproject.crave.DTO.Page;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class NamuPageDTO {

    private static int id = 0;

    @Getter
    @Setter
    private String name;
    @Setter
    private String displayName;
    @Getter
    @Setter
    private int stage;
    @Setter
    public List<NamuPageDTO> next;
    @Getter
    private int nextIndex;

    @Setter
    public List<NamuPageDTO> prev;
    @Getter
    private int prevIndex;
    @Getter
    @Setter
    private NamuPageDTO tmp;

    public NamuPageDTO(String name){
        id += 1;
        this.name = name;
        this.next = new ArrayList<>();
        this.prev = new ArrayList<>();
        this.nextIndex = 0;
        this.prevIndex = 0;
    }


    public void setNextIndex(int i) {
        this.nextIndex = i;
    }

    public Boolean incrementNextIndex(){
        this.nextIndex++;
        return this.nextIndex != this.next.size();
    }
    public void incrementPrevIndex(){this.prevIndex++;}

    public NamuPageDTO getFirstPrev() {
        return this.prev.isEmpty() ? null : this.prev.get(0);
    }

}
