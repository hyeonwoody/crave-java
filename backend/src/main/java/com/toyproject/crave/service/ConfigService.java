package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import com.toyproject.crave.service.namuStep.NamuStepFacadeService;
import libs.ThreadClass;

public class ConfigService {
    public int startNamuCenter(ConfigDTO config) {
        NamuStepFacadeService namu = new NamuStepFacadeService(config);
        int ret = namu.getPort();
        if (namu.startThread())
            namu.setMThreadStatus(ThreadClass.EThreadStatus.THREAD_ACTIVE);
        return ret;
    }

    public int startNamuFacade (ConfigDTO config){

    }
}
