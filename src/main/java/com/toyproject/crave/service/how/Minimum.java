package com.toyproject.crave.service.how;

import com.toyproject.crave.service.How;
public class Minimum implements How {

    @Override
    public boolean checkStage(int stage, int currentStage) {
        return stage <= currentStage;
    }
}

