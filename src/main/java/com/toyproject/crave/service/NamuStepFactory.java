package com.toyproject.crave.service;

import java.util.HashMap;
import java.util.Map;

public class NamuStepFactory {
    public interface CreateCallback{
        NamuStep Create();
    }

    public static Map<String, CreateCallback> mSteps = new HashMap<>();

    public static void RegisterStep (String type, CreateCallback step){
        mSteps.put(type, step);
    }
    public static void UnRegisterStep (String type){
        mSteps.remove(type);
    }
    public static NamuStep CreateStep (String type){
        return mSteps.get(type).Create();
    }

}
