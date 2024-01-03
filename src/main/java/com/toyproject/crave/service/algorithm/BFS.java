package com.toyproject.crave.service.algorithm;

import com.toyproject.crave.DTO.Page.NamuPageDTO;

import com.toyproject.crave.service.Algorithm;

import java.util.LinkedList;

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
        NamuPageDTO tmp = nextTarget.poll();
        return tmp;
    }

    @Override
    public NamuPageDTO justMoveTarget() {
        return nextTarget.poll();
    }
}
