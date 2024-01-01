package com.toyproject.crave.service.scope;

import com.toyproject.crave.service.Scope;

public class PersonOnly implements Scope {
    @Override
    public Boolean validation(String html){
        System.out.println("Scope :PersonOnly");
        return true;
    }
}
