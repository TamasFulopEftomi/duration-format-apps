package com.eftomi.kata.service;

import com.eftomi.kata.dto.TimeUnitDTO;
import com.eftomi.kata.enums.TimeUnitDetails;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

public class PrintServiceImplTest {

    private final PrintService printService = new PrintServiceImpl();

    @Test
    public void testProcessTimeUnitsWithPositiveSeconds() {
        TimeUnitDTO timeUnitDTO
                = new TimeUnitDTO(31_626_061, 1, 1, 1, 1, 1);
        String result = printService.processTimeUnit(timeUnitDTO);

        assertEquals("1 year, 1 day, 1 hour, 1 minute and 1 second", result);
    }

    @Test
    public void testProcessTimeUnitsWithNegativeSeconds() {
        TimeUnitDTO timeUnitDTO
                = new TimeUnitDTO(-1000,-40, -16, 0, 0, 0);
        String result = printService.processTimeUnit(timeUnitDTO);

        assertEquals("now", result);
    }

    @Test
    public void testProcessTimeUnitsWithSingleTimeUnit() {
        TimeUnitDTO timeUnitDTO
                = new TimeUnitDTO(86_460, 60, 0, 0, 1, 0);
        String result = printService.processTimeUnit(timeUnitDTO);

        assertEquals("1 day and 60 seconds", result);
    }

    @Test
    public void testProcessTimeUnitsWithMultipleTimeUnit() {
        TimeUnitDTO timeUnitDTO
                = new TimeUnitDTO(63_072_002,2, 0, 0, 0, 2);
        String result = printService.processTimeUnit(timeUnitDTO);

        assertEquals("2 years and 2 seconds", result);
    }

    @Test
    public void testPrepareTimeString() throws Exception {
        StringBuilder timeString = new StringBuilder();
        int[] timeUnitAmounts = {1, 2, 3, 4, 5};
        TimeUnitDetails[] timeUnitDetails = TimeUnitDetails.values();

        Method method = PrintServiceImpl.class.getDeclaredMethod(
                "prepareTimeString", StringBuilder.class, int[].class, TimeUnitDetails[].class);
        method.setAccessible(true);
        method.invoke(printService, timeString, timeUnitAmounts, timeUnitDetails);

        assertTrue(timeString.length() > 0);
    }
}
