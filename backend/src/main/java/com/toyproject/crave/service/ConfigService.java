package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.service.namuCenter.NamuCenterService;
import com.toyproject.crave.service.namuStep.NamuStepFactoryConsumer;
import libs.ThreadClass;
import lombok.Getter;
import lombok.Setter;

public class ConfigService {

    @Getter
    @Setter
    private int port;

    public int startNamuMediator(ConfigDTO config){
        NamuStepFactoryConsumer namu = new NamuStepFactoryConsumer(config);
        this.setPort(namu.getPort());
        if (namu.startThread())
            namu.setMThreadStatus(ThreadClass.EThreadStatus.THREAD_ACTIVE);
        return this.getPort();
    }
    public int startNamuCenter(ConfigDTO config) {
        NamuCenterService namu = new NamuCenterService(config, this.getPort());
        if (namu.startThread())
            namu.setMThreadStatus(ThreadClass.EThreadStatus.THREAD_ACTIVE);
        else {
            return -2;
        }
        return this.getPort();
    }


}
