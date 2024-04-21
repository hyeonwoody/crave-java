package com.toyproject.crave.service.namuCenter.constraint;

public interface Constraint {
    abstract boolean checkStage(int stage, int currentStage);
}
