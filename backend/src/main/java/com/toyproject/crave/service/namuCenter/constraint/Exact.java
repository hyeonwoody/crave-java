package com.toyproject.crave.service.namuCenter.constraint;

public class Exact implements Constraint {

    @Override
    public boolean checkStage(int stage, int currentStage) {
        return stage == currentStage;
    }
}
