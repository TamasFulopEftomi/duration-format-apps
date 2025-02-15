package com.eftomi.kata.service;

import com.eftomi.kata.dto.TimeUnitsDTO;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

public class BusinessServiceImplTest {

    private final BusinessService businessService = new BusinessServiceImpl();

    @Test
    public void testCalculateTimeUnits() throws Exception {
        Method method = BusinessServiceImpl.class.getDeclaredMethod("calculateTimeUnitAmounts");
        method.setAccessible(true);
        int[] timeUnits = (int[]) method.invoke(businessService);

        assertNotNull(timeUnits);
        assertTrue(timeUnits.length > 0);
    }

    @Test
    public void testCalculateTime() throws Exception {
        Method method = BusinessServiceImpl.class.getDeclaredMethod("calculateTime", int[].class, int.class);
        method.setAccessible(true);

        int[] timeUnits = {31536000, 86400, 3600, 60, 1};
        int secondsInTotal = 86461;

        int[] times = (int[]) method.invoke(businessService, new Object[]{timeUnits, secondsInTotal});

        assertNotNull(times);
        assertEquals(0, times[0]);
        assertEquals(1, times[1]);
        assertEquals(0, times[2]);
        assertEquals(1, times[3]);
        assertEquals(1, times[4]);
    }

    @Test
    public void testSecondsConverter() {
        TimeUnitsDTO timeUnitsDTO = businessService.secondsConverter(86461);

        assertNotNull(timeUnitsDTO);
        assertEquals(86461, timeUnitsDTO.secondsInTotal());
        assertEquals(1, timeUnitsDTO.seconds());
        assertEquals(1, timeUnitsDTO.minutes());
        assertEquals(1, timeUnitsDTO.days());

    }
}
