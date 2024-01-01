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
        nextTarget.addAll(currentTarget.next);
        System.out.println("Size before poll: " + nextTarget.size());

        NamuPageDTO tmp = nextTarget.poll();

        System.out.println("Size after poll: " + nextTarget.size());

        return tmp;
    }
}
