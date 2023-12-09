package com.toyproject.crave.service;

import libs.ThreadClass;

import java.util.List;

public class NamuCenter extends ThreadClass {
    private String origin;
    private String destination;

    private int nStage;
    private int nRoute;

    private List<List<String>> finalResult;

    public NamuCenter(String front, String back, int route, int stage) {
        super("NamuCenter");
        this.origin = front;
        this.destination = back;
        this.nRoute = route;
    }

    @Override
    public void threadMain() {
        System.out.println("THread");
        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE)
        {
            int a = 0;
        }
    }
}
