package com.toyproject.crave.service.namuStep;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.controller.ConfigController;
import com.toyproject.crave.service.How;
import com.toyproject.crave.service.how.Exact;
import com.toyproject.crave.service.how.Maximum;
import com.toyproject.crave.service.how.Minimum;
import com.toyproject.crave.service.how.None;
import libs.EventManager;
import libs.ThreadClass;
import libs.sse.CustomSseEmitterList;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

import static com.toyproject.crave.service.namuStep.NamuStep.foundRoute;

public class NamuStepFacadeService extends ThreadClass {
    private String origin;
    private String destination;

    @Getter
    private int port;

    private static int initPort = 17000;
        private int nStage;
        private int nRoute;

    @Setter
    private ConfigDTO config;

    private List<List<String>> finalResult = new ArrayList<>();

    private Map<String, NamuStep> namuSteps;


    public NamuStepFacadeService(ConfigDTO config){
        super("NamuCenter");
        this.setConfig(config);
        this.setHow();
        this.createStep();
        this.port = ++initPort;
    }

    public NamuStepFacadeService(){
        super ("NamuCenter");
        this.port = ++initPort;
    }
    private How how;

    public void setHow (){
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

    public void createStep (){
        switch (this.config.getMethod()) {
            case 0: //FRONT
                NamuStepFactory.RegisterStep("FrontStep", FrontStepService::Create);
                break;
            case 1: //FRONT & BACK
                NamuStepFactory.RegisterStep("FrontStep", FrontStepService::Create);
                NamuStepFactory.RegisterStep("BackStep", BackStepService::Create);
                break;
            case 2: //BACK
                NamuStepFactory.RegisterStep("BackStep", BackStepService::Create);
                break;
        }
    }


    @Override
    public void threadMain() {
        System.out.println("THread");
        CustomSseEmitterList SseEmitters = new CustomSseEmitterList();

        int i = 0;
        for (Map.Entry<String, NamuStepFactory.CreateCallback> entry : NamuStepFactory.mSteps.entrySet()) {
            NamuStep namuStep = entry.getValue().Create();
            namuStep.init(this.config);
            if (namuStep.startThread()){
                namuStep.setMThreadStatus(EThreadStatus.THREAD_ACTIVE);
            }
            namuSteps.put(entry.getKey(), namuStep);
        }

        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE)
        {
            if (!foundRoute.isEmpty()) {
                Deque<String> tmp = new LinkedList<>(foundRoute.remove(0));
                if (how.checkStage(config.getNumStage(), tmp.size())){
                    EventManager.LogOutput(EventManager.LOG_LEVEL.INFO.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 0, "Elements in foundRoute : ");
                    for (String element : tmp)
                        EventManager.LogOutput(EventManager.LOG_LEVEL.INFO.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 1, "Elements in foundRoute : %s", element);
                    finalResult.add(new LinkedList<>(tmp));
                    SseEmitters.send (this.port, String.join("->", tmp));
                }
            }
        }
    }
}
