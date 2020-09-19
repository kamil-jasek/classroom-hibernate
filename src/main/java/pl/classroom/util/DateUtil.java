package pl.classroom.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtil {

    public static ZonedDateTime from(LocalDate date) {
        return ZonedDateTime.of(date, LocalTime.MIDNIGHT, ZoneId.systemDefault());
    }
}
