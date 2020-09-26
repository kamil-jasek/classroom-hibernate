package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.classroom.util.DateTimeUtil;
import pl.classroom.util.DateUtil;
import pl.classroom.util.HibernateUtil;

public class _05_ClassroomDaoTest {

    private final ClassroomDao dao = new ClassroomDao(HibernateUtil.getSessionFactory());

    @Before
    public void beforeTest() {
        dao.begin();
    }

    @After
    public void afterTest() {
        dao.rollback();
    }

    @Test
    public void testCreateClassroom() {
        // given
        Classroom classroom = new Classroom(
            "class A", DateUtil.from(LocalDate.of(2020, 9, 1)), DateUtil.from(LocalDate.of(2020, 6, 30)));

        // when
        final var id = dao.save(classroom);
        dao.flushAndClear();

        // then
        final var readClassroom = dao.findById(id);
        assertEquals(readClassroom, classroom);
    }

    @Test
    public void testFindAllClassroomsWithoutExams() {
        // given
        Classroom classroom1 = new Classroom("First Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
            DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0)));
        Classroom classroom2 = new Classroom("Second Class", DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 9, 0)),
            DateTimeUtil.from(LocalDateTime.of(2020, 10, 1, 15, 0)));
        Exam biolExam = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime
            .of(2020,10,10, 9, 40, 0)));
        classroom1.addExams(biolExam);

        dao.save(classroom1);
        dao.save(classroom2);
        dao.flushAndClear();

        // when
        List<Classroom> classrooms = dao.findAllClassroomsWithoutExams();

        // then
        assertFalse(classrooms.isEmpty());
        assertTrue(classrooms.stream().allMatch(classroom -> classroom.getExams().isEmpty()));
    }
}
