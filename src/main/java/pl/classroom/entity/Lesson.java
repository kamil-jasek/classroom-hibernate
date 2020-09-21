package pl.classroom.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Lessons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "lesson_type")
public abstract class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Enumerated(EnumType.STRING)
    Subject subject;
    ZonedDateTime date;

    @ManyToMany(cascade = CascadeType.ALL)
    List<Student> participants;

    public Lesson() {
    }

    public Lesson(Subject subject, ZonedDateTime date) {
        this.subject = subject;
        this.date = date;
        participants = new ArrayList<>();
    }

    public boolean isPlannedOn(ZonedDateTime date) {
        // TODO - fix
        return this.date.toLocalDate().equals(date.toLocalDate());
    }

    public boolean isPlannedAtTime(ZonedDateTime dateTime) {
        // TODO - fix
        return this.date.equals(dateTime);
    }
    public void addParticipant(Student student) {
        if (!participants.contains(student)) {
            this.participants.add(student);
        }
    }

    public List<Student> getParticipants() {
        return new ArrayList<>(participants);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return id == lesson.id &&
                subject == lesson.subject &&
                date.equals(lesson.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, date);
    }
}
