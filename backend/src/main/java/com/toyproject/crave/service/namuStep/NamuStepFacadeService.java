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



    private Map<String, NamuStep> namuSteps;


    public NamuStepFacadeService(ConfigDTO config){
        super("NamuCenter");
        this.setConfig(config);
        this.createStep();
        this.port = ++initPort;
    }

    public NamuStepFacadeService(){
        super ("NamuCenter");
        this.port = ++initPort;
    }
    private How how;



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


        int i = 0;
        for (Map.Entry<String, NamuStepFactory.CreateCallback> entry : NamuStepFactory.mSteps.entrySet()) {
            NamuStep namuStep = entry.getValue().Create();
            namuStep.init(this.config);
            if (namuStep.startThread()){
                namuStep.setMThreadStatus(EThreadStatus.THREAD_ACTIVE);
            }
            namuSteps.put(entry.getKey(), namuStep);
        }
    }
}
