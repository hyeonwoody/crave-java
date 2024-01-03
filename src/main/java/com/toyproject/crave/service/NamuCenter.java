package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.controller.ConfigController;
import com.toyproject.crave.service.how.Exact;
import com.toyproject.crave.service.how.Maximum;
import com.toyproject.crave.service.how.Minimum;
import com.toyproject.crave.service.how.None;
import com.toyproject.crave.service.namuStep.FrontStep;
import com.toyproject.crave.service.NamuStep;
import jdk.jfr.Event;
import libs.EventManager;
import libs.ThreadClass;
import libs.sse.CustomSseEmitterList;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    private How how;

    public NamuCenter(ConfigDTO config) {
        super("NamuCenter");
        this.config = config;
        this.port = ++initPort;
        if (this.config.getHow() == ConfigController.EHowType.EXACT.ordinal()){
            how = new Exact();
        }

        if (this.config.getHow() == ConfigController.EHowType.MINIMUM.ordinal()){
            how = new Minimum();
        }
        if (this.config.getHow() == ConfigController.EHowType.MAXIMUM.ordinal()){
            how = new Maximum();
        }
        if (this.config.getHow() == ConfigController.EHowType.NONE.ordinal()){
            how = new None();
        }
    }

    public int getPort() {return this.port;}


    @Override
    public void threadMain() {
        System.out.println("THread");
        NamuStep universeStep = null;
        CustomSseEmitterList SseEmitters = new CustomSseEmitterList();
        switch (config.getMethod()) {
            case 0: //FRONT
                frontStep = new FrontStep(this.config);
                if (frontStep.startThread())
                    frontStep.setMThreadStatus(EThreadStatus.THREAD_ACTIVE);
                universeStep = frontStep;
                break;
            case 1: //FRONT & BACK
                break;
            case 2: //BACK
                break;
        }
        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE)
        {
            if (!universeStep.foundRoute.isEmpty()) {
                Deque<String> tmp = new LinkedList<>(universeStep.foundRoute.remove(0));
                if (how.checkStage(config.getNumStage(), tmp.size())){
                    EventManager.LogOutput(EventManager.LOG_LEVEL.INFO.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 0, "Elements in foundRoute : ");
                    for (String element : tmp)
                        EventManager.LogOutput(EventManager.LOG_LEVEL.INFO.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 0, "Elements in foundRoute : %s", element);
                    finalResult.add(new LinkedList<>(tmp));
                    SseEmitters.send (this.port, String.join("->", tmp));
                }
            }
        }
    }
}
