package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.NamuPageDTO;

public class FrontStep extends NamuStep{
    public FrontStep(String name) {
        super("FrontStep", name);
    }

    @Override
    protected void threadMain() {
        System.out.println("Front start");
        System.out.println(currentTarget.getName());
    }
}
