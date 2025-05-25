package org.example.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeUtilsTest {

    @Test
    void testChangeTimeFormatToString() {
        LocalTime time = LocalTime.of(6, 18, 0);
        String formatted = TimeUtils.changeTimeFormat(time);
        assertEquals("06:18:00", formatted);
    }

    @Test
    void testChangeTimeFormatFromStringValid() {
        String input = "06:18:00";
        LocalTime expected = LocalTime.of(6, 18, 0);
        LocalTime actual = TimeUtils.changeTimeFormat(input);
        assertEquals(expected, actual);
    }

    @Test
    void testChangeTimeFormatFromStringInvalidFormat() {
        String input = "6:18"; // неповний формат
        LocalTime expected = LocalTime.of(0, 6, 18);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            TimeUtils.changeTimeFormat(input);
        });

        assertFalse(exception.getMessage().contains("Wrong time format in DB " + input));
    }

}
