package com.toyproject.crave.service.namuCenter;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.controller.ConfigController;
import com.toyproject.crave.service.namuCenter.constraint.Constraint;
import com.toyproject.crave.service.namuCenter.constraint.Exact;
import com.toyproject.crave.service.namuCenter.constraint.Maximum;
import com.toyproject.crave.service.namuCenter.constraint.Minimum;
import com.toyproject.crave.service.namuCenter.constraint.None;
import libs.EventManager;
import libs.ThreadClass;
import libs.sse.CustomSseEmitterList;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static com.toyproject.crave.service.namuStep.NamuStep.foundRoute;

public class NamuCenterService extends ThreadClass {

    private static int objectId = 0;

    @Setter
    private ConfigDTO config;

    @Setter
    private Constraint constraint;

    private List<List<String>> finalResult = new ArrayList<>();

    @Setter
    @Getter
    private int port;

    public NamuCenterService(ConfigDTO config, int port){
        super("NamuCenter");
        this.setConfig(config);
        this.setConstraint();
        this.setPort(port);
    }
    public NamuCenterService(){
        super("NamuCenter");
    }

    private void setConstraint(){
        if (this.config.getHow() == ConfigController.EHowType.EXACT.ordinal()){
            constraint = new Exact();
        }
        if (this.config.getHow() == ConfigController.EHowType.MINIMUM.ordinal()){
            constraint = new Minimum();
        }
        if (this.config.getHow() == ConfigController.EHowType.MAXIMUM.ordinal()){
            constraint = new Maximum();
        }
        if (this.config.getHow() == ConfigController.EHowType.NONE.ordinal()){
            constraint = new None();
        }
    }

    @Override
    protected void threadMain() throws InterruptedException, IOException {
        CustomSseEmitterList SseEmitters = new CustomSseEmitterList();
        while (getMThreadStatus() == EThreadStatus.THREAD_ACTIVE)
        {
            if (!foundRoute.isEmpty()) {
                Deque<String> tmp = new LinkedList<>(foundRoute.remove(0));
                if (constraint.checkStage(config.getNumStage(), tmp.size())){
                    EventManager.LogOutput(EventManager.LOG_LEVEL.INFO.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 0, "Elements in foundRoute : ");
                    for (String element : tmp)
                        EventManager.LogOutput(EventManager.LOG_LEVEL.INFO.ordinal(), mName, new Object(){}.getClass().getEnclosingMethod().getName(), 1, "Elements in foundRoute : %s", element);
                    finalResult.add(new LinkedList<>(tmp));
                    SseEmitters.send (getPort(), String.join("->", tmp));
                }
            }
        }
    }
}
