package pl.classroom.entity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "classrooms")
@SQLDelete(sql = "UPDATE classrooms SET deleted = true, deleteTime = NOW() WHERE id = ?")
@Where(clause = "deleted <> true")
public final class Classroom extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Student> students;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exam> exams;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @OnlyForHibernate
    protected Classroom() {
    }

    public Classroom(String name, ZonedDateTime startDate, ZonedDateTime endDate) {
        super(ZonedDateTime.now(), "HIBERNATE");
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public void addStudents(Student... studentList) {
        for (Student s : studentList) {
            this.students.add(s);
        }
    }

    public void addExams(Exam... examList) {
        for (Exam exam : examList) {
            this.exams.add(exam);
        }
    }

    public void addLessons(Lesson... lessonList) {
        for (Lesson lesson : lessonList) {
            this.lessons.add(lesson);
        }
    }

    public void removeStudents(Student... studentList) {
        for (Student student : studentList) {
            this.students.remove(student);
        }
    }


    public List<Student> getStudents() {
        // TODO -fix
        return new ArrayList<>(students);
    }

    public List<Lesson> getLessons() {
        // TODO - fix
        return new ArrayList<>(lessons);
    }

    public List<Exam> getExams() {
        // TODO - fix
        return new ArrayList<>(exams);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classroom classroom = (Classroom) o;
        return id == classroom.id &&
                name.equals(classroom.name) &&
                startDate.equals(classroom.startDate) &&
                endDate.equals(classroom.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate);
    }
}
