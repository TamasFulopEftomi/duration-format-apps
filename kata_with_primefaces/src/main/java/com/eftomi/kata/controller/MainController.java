package com.eftomi.kata.controller;

import com.eftomi.kata.dto.TimeUnitDTO;
import com.eftomi.kata.service.BusinessService;
import com.eftomi.kata.service.PrintService;
import com.eftomi.kata.service.exception.NegativeSecondsException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;


@Named("mainController")
@ViewScoped
public class MainController {

    private final BusinessService businessService;
    private final PrintService printService;

    @Getter
    @Setter
    private int inputSecondsInTotal;

    @Getter
    private String timeString;

    @Setter
    @Getter
    private String errorMessage;

    MainController(BusinessService businessService, PrintService printService) {
        this.businessService = businessService;
        this.printService = printService;
    }

    public void processInput() {
        try {
            businessService.validateInputAmount(inputSecondsInTotal);
        } catch (NegativeSecondsException e) {
            errorMessage = e.getMessage();
            return;
        }
        errorMessage = "";
        TimeUnitDTO timeUnitDTO = businessService.secondsConverter(inputSecondsInTotal);
        timeString = printService.processTimeUnit(timeUnitDTO);
    }
}