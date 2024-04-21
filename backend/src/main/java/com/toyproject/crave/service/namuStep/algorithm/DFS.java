package com.toyproject.crave.service.namuStep.algorithm;

import com.toyproject.crave.DTO.Page.NamuPageDTO;

public class DFS implements Algorithm {
    @Override
    public NamuPageDTO moveTarget(NamuPageDTO currentTarget){
        NamuPageDTO tmp = currentTarget;
        return tmp;
    }

    @Override
    public NamuPageDTO justMoveTarget() {
        return null;
    }
}
