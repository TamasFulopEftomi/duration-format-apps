package com.eftomi.kata.service;

import com.eftomi.kata.dto.TimeUnitDTO;

public interface PrintService {

    String PLURAL_SIGN = "s";
    String SPACE_SIGN = " ";
    String MID_SEPARATOR = ", ";
    String LAST_SEPARATOR = " and ";
    String processTimeUnit(TimeUnitDTO timeUnitDTO) throws NumberFormatException;
}
