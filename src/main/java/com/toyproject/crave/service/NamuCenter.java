package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.service.namuStep.FrontStep;
import com.toyproject.crave.service.NamuStep;
import libs.ThreadClass;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class NamuCenter extends ThreadClass {
    private String origin;
    private String destination;

    private int port;

    private static int initPort = 17000;
    private int nStage;
    private int nRoute;

    private ConfigDTO config;


    private FrontStep frontStep;

    private List<List<String>> finalResult = new ArrayList<>();

    public NamuCenter(ConfigDTO config) {
        super("NamuCenter");
        this.config = config;
        this.port = ++initPort;
    }

    public int getPort() {return this.port;}


    @Override
    public void threadMain() {
        System.out.println("THread");
        switch (config.getMethod()) {
            case 0: //FRONT
                frontStep = new FrontStep(this.config);
                if (frontStep.startThread())
                    frontStep.setMThreadStatus(ThreadClass.EThreadStatus.THREAD_ACTIVE);
                break;
            case 1: //FRONT & BACK
                break;
            case 2: //BACK
                break;
        }
        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE)
        {
            if (!NamuStep.foundRoute.isEmpty()) {
                Deque<String> tmp = new LinkedList<>(NamuStep.foundRoute.remove(0));

                System.out.println("Elements in foundRoute:");
                for (String element : tmp)
                    System.out.println(element);
                finalResult.add(new LinkedList<>(tmp));
                System.out.println("FOUND");
            }
        }
    }
}
