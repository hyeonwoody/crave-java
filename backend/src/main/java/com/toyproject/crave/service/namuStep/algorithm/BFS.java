package com.toyproject.crave.service.namuStep.algorithm;

import com.toyproject.crave.DTO.Page.NamuPageDTO;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BFS implements Algorithm {

    private static int step = 1;
    private static ConcurrentLinkedQueue<NamuPageDTO> nextTarget = new ConcurrentLinkedQueue<>();

    @Override
    public NamuPageDTO moveTarget(NamuPageDTO currentTarget) {
        if (currentTarget.getTmp() != null){
            currentTarget.getTmp().incrementNextIndex();
        }
        nextTarget.addAll(currentTarget.next);
        currentTarget.next.clear();
        NamuPageDTO tmp = nextTarget.poll();
        return tmp;
    }

    @Override
    public NamuPageDTO justMoveTarget() {
        return nextTarget.poll();
    }
}
