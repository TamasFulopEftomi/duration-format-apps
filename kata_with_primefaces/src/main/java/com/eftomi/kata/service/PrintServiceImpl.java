package com.eftomi.kata.service;

import com.eftomi.kata.dto.TimeUnitsDTO;
import com.eftomi.kata.enums.TimeUnitDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;

@Slf4j
@Service
public class PrintServiceImpl implements PrintService {

    /**
     * Formats the time units from the DTO into a readable string.
     * If the total seconds is 0, returns "now".
     * Negative amount is not acceptable. (checked on input)
     *
     * @param timeUnitsDTO the DTO containing time units and values
     * @return a formatted time string
     */
    @Override
    public String processTimeUnits(TimeUnitsDTO timeUnitsDTO) {
        int secondsInTotal = timeUnitsDTO.secondsInTotal();
        int[] timeUnitss = {
                timeUnitsDTO.years(), timeUnitsDTO.days(), timeUnitsDTO.hours(), timeUnitsDTO.minutes(), timeUnitsDTO.seconds()
        };

        TimeUnitDetails[] timeUnitDetailss = getTimeUnitDetailss();

        StringBuilder timeString = new StringBuilder();

        boolean zeroSecondsInTotal = secondsInTotal == 0;

        if (zeroSecondsInTotal) {
            timeString.append("now");
        } else {
            prepareTimeString(timeString, timeUnitss, timeUnitDetailss);
        }
        log.info("MSG: timeString[" + timeString + "] has been created.");
        return timeString.toString();
    }

    /**
     * Appends formatted time units to the given StringBuilder.
     *
     * @param timeString       StringBuilder to append the result to
     * @param timeUnitAmounts  array of time unit values
     * @param timeUnitDetailss array of time units details
     */
    private void prepareTimeString(StringBuilder timeString, int[] timeUnitAmounts, TimeUnitDetails[] timeUnitDetailss) {
        boolean needSeparator = false;
        int indexOfSmallestValidTimeUnit = 0;
        int lengthOfTimeUnitAmounts = timeUnitAmounts.length;
        for (int i = 0; i < lengthOfTimeUnitAmounts; i++) {
            boolean validTimeUnitAmount = 0 < timeUnitAmounts[i];
            if (validTimeUnitAmount && indexOfSmallestValidTimeUnit < i) {
                indexOfSmallestValidTimeUnit = i;
            }
        }

        for (int i = 0; i < lengthOfTimeUnitAmounts; i++) {
            boolean validTimeUnitAmount = 0 < timeUnitAmounts[i];
            if (validTimeUnitAmount) {
                createTimeString(i, indexOfSmallestValidTimeUnit, needSeparator, timeString, timeUnitDetailss[i], timeUnitAmounts[i]);
                needSeparator = true;
            }
        }
    }

    /**
     * Appends a single time unit portion to the StringBuilder.
     * Adds a separator if needed, then appends the time amount and unit name,
     * adding a plural sign if the amount is greater than one.
     *
     * @param indexOfTimeUnit               index of time unit
     * @param indexOfSmallestValidTimeUnit  index of smallest valid time unit
     * @param needSeparator                 whether to prepend a separator
     * @param timeString                    StringBuilder to append to
     * @param timeUnitDetails               the details of the time unit
     * @param timeUnitAmount                the amount of the time unit
     */
    private void createTimeString(int indexOfTimeUnit, int indexOfSmallestValidTimeUnit, boolean needSeparator,
                                  StringBuilder timeString, TimeUnitDetails timeUnitDetails, int timeUnitAmount) {
        boolean lastSeparator = indexOfTimeUnit == indexOfSmallestValidTimeUnit;
        if (needSeparator) {
            if (lastSeparator) {
                timeString.append(LAST_SEPARATOR);
            } else {
                timeString.append(MID_SEPARATOR);
            }
       }
        timeString.append(timeUnitAmount).append(SPACE_SIGN).append(timeUnitDetails.getName());
        boolean moreThanOneTimeUnitAmount = 1 < timeUnitAmount;
        if (moreThanOneTimeUnitAmount) {
            timeString.append(PLURAL_SIGN);
        }
    }

    /**
     * Retrieves time unit details sorted in descending order by their second values.
     *
     * @return sorted array of TimeUnitDetails
     */
    private TimeUnitDetails[] getTimeUnitDetailss() {
        return Arrays.stream(TimeUnitDetails.values())
                .sorted(Comparator.comparingInt(TimeUnitDetails::getSec)
                        .reversed())
                .toArray(TimeUnitDetails[]::new);
    }
}
