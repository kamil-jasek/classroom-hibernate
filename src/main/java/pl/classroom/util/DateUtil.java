package pl.classroom.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtil {

    public static ZonedDateTime from(LocalDate dateOfBirth) {
        return ZonedDateTime.of(dateOfBirth, LocalTime.MIDNIGHT, ZoneId.systemDefault());
    }
}
