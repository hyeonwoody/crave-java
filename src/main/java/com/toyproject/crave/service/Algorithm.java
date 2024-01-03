package com.toyproject.crave.service;

import com.toyproject.crave.DTO.Page.NamuPageDTO;

import java.io.UnsupportedEncodingException;

public interface Algorithm {
    abstract NamuPageDTO moveTarget(NamuPageDTO currentTarget) throws UnsupportedEncodingException;

    abstract NamuPageDTO justMoveTarget();
}
