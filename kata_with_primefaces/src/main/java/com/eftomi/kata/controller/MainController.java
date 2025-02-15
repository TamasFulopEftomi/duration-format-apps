package com.eftomi.kata.controller;

import com.eftomi.kata.dto.TimeUnitsDTO;
import com.eftomi.kata.service.BusinessService;
import com.eftomi.kata.service.BusinessServiceImpl;
import com.eftomi.kata.service.PrintService;
import com.eftomi.kata.service.PrintServiceImpl;
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

    MainController(BusinessServiceImpl businessService, PrintServiceImpl printService) {
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
        TimeUnitsDTO timeUnitsDTO = businessService.secondsConverter(inputSecondsInTotal);
        timeString = printService.processTimeUnits(timeUnitsDTO);
    }
}