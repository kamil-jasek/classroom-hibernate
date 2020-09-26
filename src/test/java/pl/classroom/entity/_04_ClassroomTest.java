package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pl.classroom.util.DateTimeUtil;
import pl.classroom.util.DateUtil;

public final class _04_ClassroomTest extends BaseEntityTest {

    Student student1 = new Student("Alex", "Kowal",
            DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
    Student student2 = new Student("bbbb", "bbbbb",
            DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
    Student student3 = new Student("Alex", "Kowal",
            DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);

    StationaryLesson mathLesson = new StationaryLesson(Subject.MATH,
            ZonedDateTime.of(2020, 10, 2, 11, 40, 40, 0, ZoneId.systemDefault()),
            "234", "66 Street");
    StationaryLesson biolLesson = new StationaryLesson(Subject.MATH,
            ZonedDateTime.of(2020, 10, 2, 9, 40, 40, 0, ZoneId.systemDefault()),
            "222", "66 Street");

    Exam biolExam = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime
            .of(2020,10,10, 9, 40, 0)));

    @Test
    public void testCreateClassroom() {
        // given - create classroom entity
        Classroom classroom = new Classroom("First Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
                DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0)));
        // add 3 students, add 2 lessons and 1 exam
        classroom.addStudents(student1, student2, student3);
        classroom.addLessons(mathLesson, biolLesson);
        classroom.addExams(biolExam);


        // when - save classroom into db
        Serializable id = saveAndFlush(classroom);

        // then - read from db & verify
        Classroom readClassroom = getSession().get(Classroom.class, id);
        assertNotNull(readClassroom);
        assertEquals(readClassroom, classroom);
        assertEquals(3, readClassroom.getStudents().size());
        assertEquals(2, readClassroom.getLessons().size());
        assertEquals(1, readClassroom.getExams().size());
    }

    @Test
    public void testRemoveStudentFromClassroom() {
        // given - create classroom and add 2 students. Save to db.
        Classroom classroom = new Classroom("First Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
                DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0)));

        classroom.addStudents(student1, student2);
        Serializable id = saveAndFlush(classroom);
        // when - remove student1 from classroom. Save changes to db.
        Classroom classroom1 = getSession().get(Classroom.class, id);
        classroom1.removeStudents(student1);

        id = saveAndFlush(classroom1);


        // then - read classroom from db & verify
        Classroom readClassroom = getSession().get(Classroom.class, id);
        assertNotNull(readClassroom);
        assertEquals(readClassroom, classroom);
        assertEquals(1, readClassroom.getStudents().size());
        assertTrue(readClassroom.getStudents().contains(student2));
    }

    @Test
    public void testHqlFindAllClassroomsWithoutExams() {
        // given - create 2 classrooms, one with exam, second without exams. Save to db.
        Classroom classroom1 = new Classroom("First Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
                DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0)));
        Classroom classroom2 = new Classroom("Second Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
                DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0)));
        classroom1.addExams(biolExam);

        saveAndFlush(classroom1);
        saveAndFlush(classroom2);

        // when - create query to retrieve classrooms without exams
        List<Classroom> classrooms = getSession().createQuery("SELECT c FROM Classroom c WHERE c.exams IS EMPTY")
                .getResultList();

        // then - expect all classrooms without exams
        assertFalse(classrooms.isEmpty());
        assertTrue(classrooms.stream().allMatch(classroom -> classroom.getExams().isEmpty()));
    }

    @Test
    public void testHqlFindTheBestStudentInClassroom() {
        // given - create classroom, add students, add exams and rates. Save to db.
        Classroom classroom = new Classroom("First Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
                DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0)));

        Student student1 = new Student("Alex", "Kowal",
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
        Student student2 = new Student("bbbb", "bbbbb",
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);

        Student theBestStudent = student2;

        Rate rate11 = new Rate(Rate.Value.B, student1);
        Rate rate12 = new Rate(Rate.Value.C, student1);
        Rate rate21 = new Rate(Rate.Value.A, student2);
        Rate rate22 = new Rate(Rate.Value.B, student2);

        Exam mathExam = new Exam(Subject.MATH, DateTimeUtil.from(LocalDateTime.of(2020,10,10, 9, 40, 0)));
        Exam biolExam = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime.of(2020,10,10, 9, 40, 0)));

        mathExam.addRate(rate11);
        mathExam.addRate(rate21);
        biolExam.addRate(rate12);
        biolExam.addRate(rate22);

        classroom.addExams(mathExam, biolExam);

        saveAndFlush(classroom);
                                        //  SELECT s FROM Students s WHERE s.firstName = (
        // when - find the best student
        Student objects = getSession().createQuery("SELECT s FROM Classroom c INNER JOIN c.exams e" +
                " INNER JOIN e.rates r INNER JOIN r.student s GROUP BY s ORDER BY AVG(r.value) ASC", Student.class)
                .setMaxResults(1).getSingleResult();

        // then - verify
        assertNotNull(objects);
        assertEquals(theBestStudent, objects);

    }
}