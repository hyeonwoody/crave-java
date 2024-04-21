package com.toyproject.crave.service.namuStep.method;

import com.toyproject.crave.DTO.Page.NamuPageDTO;
import com.toyproject.crave.controller.ConfigController;

import java.util.ArrayDeque;
import java.util.Deque;

public class Front implements Method{


    @Override
    public boolean resultIsDestionation(String result) {
        return result.equals(ConfigController.getDestination());
    }

    @Override
    public Deque<String> createRoute(NamuPageDTO currentTarget) {
        Deque<String> route = new ArrayDeque<>();
        while (currentTarget != null){
            route.addFirst(currentTarget.getName());
            currentTarget = currentTarget.getTmp();
        }
        return route;
    }


}
