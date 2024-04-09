package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Config.ConfigDTO;
import libs.ThreadClass;

public class ConfigService {
    public int startNamuCenter(ConfigDTO config) {
        NamuCenter namu = new NamuCenter();
        namu.init(config);
        int ret = namu.getPort();
        if (namu.startThread())
            namu.setMThreadStatus(ThreadClass.EThreadStatus.THREAD_ACTIVE);
        return ret;
    }
}
