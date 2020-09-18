package pl.classroom.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Lesson {

    public boolean isPlannedOn(LocalDate date) {
        // TODO - fix
        return false;
    }

    public boolean isPlannedAtTime(LocalDateTime dateTime) {
        // TODO - fix
        return false;
    }

    public List<Student> getParticipants() {
        return null;
    }
}
