package pl.classroom.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public final class Classroom {

    private String name;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    @OneToMany
    private List<Student> students;
    @OneToMany
    private List<Exam> exams;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    public Classroom() {
        this.students = new ArrayList<>();
        this.exams = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public Classroom(String name, ZonedDateTime startDate, ZonedDateTime endDate) {
        this();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
