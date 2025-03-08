package com.eftomi.kata.service;

import com.eftomi.kata.dto.TimeUnitDTO;
import com.eftomi.kata.service.exception.NegativeSecondsException;

public interface BusinessService {

    void validateInputAmount(int secondsInTotal) throws NegativeSecondsException;
    TimeUnitDTO secondsConverter(int secondsInTotal);
}
