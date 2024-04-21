package com.toyproject.crave.service.namuStep;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.service.namuCenter.constraint.Constraint;
import com.toyproject.crave.service.namuStep.method.BackStepService;
import com.toyproject.crave.service.namuStep.method.FrontStepService;
import libs.ThreadClass;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

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
    private Constraint constraint;



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
