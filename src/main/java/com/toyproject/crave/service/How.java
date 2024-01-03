package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Page.NamuPageDTO;

import java.util.Deque;

public interface How {
    abstract boolean checkStage(int stage, int currentStage);
}
