package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {
    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);
    private static final DateTimeFormatter DB_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String changeTimeFormat(LocalTime duration) {
        return duration.format(DB_FORMATTER);
    }

    public static LocalTime changeTimeFormat(String duration) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("m:s"),
                DateTimeFormatter.ofPattern("m:ss"),
                DateTimeFormatter.ofPattern("mm:ss"),
                DateTimeFormatter.ofPattern("H:mm:ss"),
                DateTimeFormatter.ofPattern("HH:mm:ss")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalTime.parse(duration, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }
        logger.error("Wrong time format");
        throw new IllegalArgumentException("Wrong time format in DB: " + duration);
    }

}
