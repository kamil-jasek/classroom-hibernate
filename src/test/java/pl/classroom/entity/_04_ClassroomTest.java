package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pl.classroom.util.DateTimeUtil;
import pl.classroom.util.DateUtil;

public final class _04_ClassroomTest {

    Student student1 = new Student("Alex", "Kowal",
            DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
    Student student2 = new Student("Alex", "Kowal",
            DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
    Student student3 = new Student("Alex", "Kowal",
            DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);

    StationaryLesson mathLesson = new StationaryLesson(Subject.MATH,
            ZonedDateTime.of(2020, 10, 2, 11, 40, 40, 0, ZoneId.systemDefault()),
            "234", "66 Street");

    @Test
    public void testCreateClassroom() {
        // given - create classroom entity
        Classroom classroom = new Classroom("First Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
                DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0));
        // add 3 students, add 2 lessons and 1 exam
        classroom.addStudents(student1, student2, student3);




        // when - save classroom into db


        // then - read from db & verify
        Classroom readClassroom = null;
        assertNotNull(readClassroom);
        assertEquals(readClassroom, classroom);
        assertEquals(3, readClassroom.getStudents().size());
        assertEquals(2, readClassroom.getLessons().size());
        assertEquals(1, readClassroom.getExams().size());
    }

    @Test
    public void testRemoveStudentFromClassroom() {
        // given - create classroom and add 2 students. Save to db.
        Classroom classroom = null;
        Student student1 = null;
        Student student2 = null;


        // when - remove student1 from classroom. Save changes to db.


        // then - read classroom from db & verify
        Classroom readClassroom = null;
        assertNotNull(readClassroom);
        assertEquals(readClassroom, classroom);
        assertEquals(1, readClassroom.getStudents().size());
        assertTrue(readClassroom.getStudents().contains(student2));
    }

    @Test
    public void testHqlFindAllClassroomsWithoutExams() {
        // given - create 2 classrooms, one with exam, second without exams. Save to db.


        // when - create query to retrieve classrooms without exams
        List<Classroom> classrooms = new ArrayList<>();


        // then - expect all classrooms without exams
        assertFalse(classrooms.isEmpty());
        assertTrue(classrooms.stream().allMatch(classroom -> classroom.getExams().isEmpty()));
    }

    @Test
    public void testHqlFindTheBestStudentInClassroom() {
        // given - create classroom, add students, add exams and rates. Save to db.
        Student theBestStudent = null;


        // when - find the best student
        Student student = null;


        // then - verify
        assertNotNull(student);
        assertEquals(theBestStudent, student);
    }
}
