package pl.classroom.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public abstract class Lesson {

    public boolean isPlannedOn(ZonedDateTime date) {
        // TODO - fix
        return false;
    }

    public boolean isPlannedAtTime(ZonedDateTime dateTime) {
        // TODO - fix
        return false;
    }

    public List<Student> getParticipants() {
        return null;
    }
}
