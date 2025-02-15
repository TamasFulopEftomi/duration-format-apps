package com.eftomi.kata.service;

import com.eftomi.kata.dto.TimeUnitsDTO;
import com.eftomi.kata.enums.TimeUnitDetails;
import com.eftomi.kata.service.exception.NegativeSecondsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;

@Slf4j
@Service
public class BusinessServiceImpl implements BusinessService {

    /**
     * An error is thrown if the amount of input seconds is negative.
     *
     * @param secondsInTotal
     * @throws NegativeSecondsException
     */
    @Override
    public void validateInputAmount(int secondsInTotal) throws NegativeSecondsException {
        if (secondsInTotal < 0) {
            throw new NegativeSecondsException("Please enter a non negative integer number.");
        }
    }

    /**
     * Converts total seconds into years, days, hours, minutes, and seconds.
     *
     * @param secondsInTotal total seconds to convert
     * @return TimeUnitsDTO with the converted time units
     */
    @Override
    public TimeUnitsDTO secondsConverter(int secondsInTotal) {
        int[] timeUnits = calculateTimeUnitAmounts();
        int[] times = calculateTime(timeUnits, secondsInTotal);

        //           Constructor -> new TimeUnitsDTO(totalSeconds, years, days, hours, minutes, seconds)
        TimeUnitsDTO timeUnitsDTO = new TimeUnitsDTO(secondsInTotal, times[4], times[3], times[2], times[1], times[0]);
        log.info("MSG: " + timeUnitsDTO + " has been created.");
        return timeUnitsDTO;
    }

    /**
     * Returns an array of time unit values (in seconds) in descending order.
     *
     * @return array of seconds per time unit
     */
    private int[] calculateTimeUnitAmounts() {
        return Arrays.stream(TimeUnitDetails.values())
                .sorted(Comparator.comparingInt(TimeUnitDetails::getSec).reversed())
                .mapToInt(TimeUnitDetails::getSec)
                .toArray();
    }

    /**
     * Calculates how many of each time unit fit into the total seconds.
     *
     * @param timeUnitAmounts array of time unit values (seconds) in descending order
     * @param secondsInTotal  total seconds to convert
     * @return array of counts for each time unit
     */
    private int[] calculateTime(int[] timeUnitAmounts, int secondsInTotal) {
        int[] times = new int[timeUnitAmounts.length];
        int remainingSeconds = secondsInTotal;

        for (int i = 0; i < timeUnitAmounts.length; i++) {
            times[i] = remainingSeconds / timeUnitAmounts[i];
            remainingSeconds %= timeUnitAmounts[i];
        }

        return times;
    }
}
