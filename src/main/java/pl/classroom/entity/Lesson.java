package pl.classroom.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "lesson")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "lesson_type")
public abstract class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Student> participants;

    protected Lesson() {}

    public Lesson(ZonedDateTime date, Subject subject) {
        this.date = date;
        this.subject = subject;
        this.participants = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Subject getSubject() {
        return subject;
    }

    public boolean isPlannedOn(LocalDate date) {
        return this.date.toLocalDate().equals(date);
    }

    public boolean isPlannedAtTime(ZonedDateTime dateTime) {
        return this.date.equals(dateTime);
    }

    public List<Student> getParticipants() {
        return participants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lesson lesson = (Lesson) o;
        return id == lesson.id &&
            date.equals(lesson.date) &&
            subject == lesson.subject;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, subject);
    }

    public void addParticipant(Student student) {
        if (!participants.contains(student)) {
            participants.add(student);
        }
    }
}
