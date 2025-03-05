package com.eftomi.kata.controller;

import com.eftomi.kata.service.exception.NegativeSecondsException;
import org.springframework.ui.Model;
import com.eftomi.kata.dto.TimeUnitsDTO;
import com.eftomi.kata.service.BusinessService;
import com.eftomi.kata.service.PrintService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {

    private final BusinessService businessService;
    private final PrintService printService;

    MainController(BusinessService businessService, PrintService printService) {
        this.businessService = businessService;
        this.printService = printService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        if (!model.containsAttribute("inputSecondsInTotal")) {
            model.addAttribute("inputSecondsInTotal", "0");
        }
        return "index";
    }


    @PostMapping("/calculate")
    public String calculate(@RequestParam("inputSecondsInTotal") String inputSecondsInTotal,
                            Model model) {
        try {
            int inputSeconds = Integer.parseInt(inputSecondsInTotal);
            businessService.validateInputAmount(inputSeconds);
            TimeUnitsDTO timeUnitsDTO = businessService.secondsConverter(inputSeconds);
            String timeString = printService.processTimeUnits(timeUnitsDTO);
            model.addAttribute("inputSecondsInTotal", inputSecondsInTotal);
            model.addAttribute("timeString", timeString);
        } catch (NumberFormatException e) {
            model.addAttribute("errorMessage", "Please enter a valid integer number.");
            model.addAttribute("inputSecondsInTotal", inputSecondsInTotal);
        } catch (NegativeSecondsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("inputSecondsInTotal", inputSecondsInTotal);
        }
        return "index";
    }
}