package pl.classroom.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtil {

    public static ZonedDateTime from(LocalDateTime date) {
        return ZonedDateTime.of(date, ZoneId.systemDefault());
    }
}
