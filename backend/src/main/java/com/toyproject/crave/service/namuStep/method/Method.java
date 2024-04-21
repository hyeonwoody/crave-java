package com.toyproject.crave.service.namuStep.method;

import com.toyproject.crave.DTO.Page.NamuPageDTO;

import java.util.Deque;

public interface Method {
    abstract boolean resultIsDestionation(String result);

    abstract Deque<String> createRoute(NamuPageDTO currentTarget);
}
