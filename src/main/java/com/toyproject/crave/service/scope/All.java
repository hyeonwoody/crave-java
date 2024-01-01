package com.toyproject.crave.service.scope;

import com.toyproject.crave.service.Scope;

public class All implements Scope {
    @Override
    public Boolean validation(String html){
        return true;
    }
}
