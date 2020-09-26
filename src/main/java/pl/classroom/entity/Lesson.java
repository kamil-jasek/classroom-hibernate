package pl.classroom.entity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "lessons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "lesson_type")
@Where(clause = "deleted <> true")
public abstract class Lesson extends Auditable {

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
        super(ZonedDateTime.now(), "HIBERNATE");
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
