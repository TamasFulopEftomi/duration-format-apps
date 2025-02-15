package com.eftomi.kata.service;

import com.eftomi.kata.dto.TimeUnitsDTO;
import com.eftomi.kata.service.exception.NegativeSecondsException;

public interface BusinessService {

    void validateInputAmount(int secondsInTotal) throws NegativeSecondsException;
    TimeUnitsDTO secondsConverter(int secondsInTotal);
}
