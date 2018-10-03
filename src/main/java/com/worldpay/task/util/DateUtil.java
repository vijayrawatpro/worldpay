package com.worldpay.task.util;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.TimeZone;

public class DateUtil {
    private DateUtil() {
    }

    public static final String DEFAULT_FORMAT = "dd-MM-yyyy";

    static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    public static Long parse(String date, String format) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
            .appendPattern(format)
            .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
            .toFormatter()
            .withZone(UTC.toZoneId());
        Instant instant = fmt.parse(date, Instant::from);
        return instant.toEpochMilli();
    }

    public static String format(Long date) {
        DateTimeFormatter fmt = new DateTimeFormatterBuilder()
            .appendPattern(DEFAULT_FORMAT)
            .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
            .toFormatter()
            .withZone(UTC.toZoneId());
        return fmt.format(ZonedDateTime.ofInstant(Instant.ofEpochMilli(date), UTC.toZoneId()));
    }

    public static Long getStartOfDay(Long date) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(date), UTC.toZoneId())
            .toLocalDate().atStartOfDay(UTC.toZoneId()).toInstant().toEpochMilli();
    }
}
