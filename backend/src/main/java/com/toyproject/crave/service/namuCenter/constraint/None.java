package com.toyproject.crave.service.namuCenter.constraint;

public class None implements Constraint {

    @Override
    public boolean checkStage(int stage, int currentStage) {
        return true;
    }
}
