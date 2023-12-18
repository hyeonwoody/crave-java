package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.NamuPageDTO;
import libs.ThreadClass;

public class NamuStep extends ThreadClass {

    public NamuPageDTO currentTarget;


    public NamuStep(String stepMethod, String targetName) {
        super(stepMethod);
        currentTarget = new NamuPageDTO();
        currentTarget.setName(targetName);
    }

    protected String GetHtml(String url) {
        return null;
    }

    @Override
    protected void threadMain() {

    }
}
