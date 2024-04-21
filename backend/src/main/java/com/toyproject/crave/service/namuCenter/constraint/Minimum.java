package com.toyproject.crave.service.namuCenter.constraint;

public class Minimum implements Constraint {

    @Override
    public boolean checkStage(int stage, int currentStage) {
        return stage <= currentStage;
    }
}

