package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pl.classroom.entity.Student.Gender;
import pl.classroom.util.DateUtil;

public final class _02_LessonTest extends BaseEntityTest {

    @Test
    public void testCreateOnlineLesson() {
        // given - create online lesson entity
        OnlineLesson lesson = new OnlineLesson(ZonedDateTime.now(), Subject.MATH, "test", "test");

        // when - save lesson
        Serializable id = saveAndFlush(lesson);

        // then - read from db & verify
        OnlineLesson readLesson = getSession().get(OnlineLesson.class, id);
        assertNotNull(readLesson);
        assertEquals(readLesson, lesson);
    }

    @Test
    public void testCreateStationaryLesson() {
        // given - create online lesson entity
        StationaryLesson lesson = new StationaryLesson(ZonedDateTime.now(), Subject.BIOLOGY, "2", "test");

        // when - save lesson
        Serializable id = saveAndFlush(lesson);

        // then - read from db & verify
        StationaryLesson readLesson = getSession().get(StationaryLesson.class, id);
        assertNotNull(readLesson);
        assertEquals(readLesson, lesson);
    }

    @Test
    public void testAddParticipantToLesson() {
        // given - create some lesson
        StationaryLesson lesson = new StationaryLesson(ZonedDateTime.now(), Subject.BIOLOGY, "2", "test");

        // when - add 2 students to lesson and save to db
        lesson.addParticipant(new Student("Jan", "Kowalski", DateUtil.from(LocalDate.of(1990, 1, 1)), Gender.MALE));
        lesson.addParticipant(new Student("Adam", "Kowalski", DateUtil.from(LocalDate.of(1990, 1, 1)), Gender.MALE));
        Serializable id = saveAndFlush(lesson);

        // then - read lesson from db & verify
        Lesson readLesson = getSession().get(Lesson.class, id);
        assertNotNull(readLesson);
        assertEquals(2, readLesson.getParticipants().size());
    }

    @Test
    public void testHqlFindAllLessonsPlannedOnDate() {
        // given - example day
        ZonedDateTime date = DateUtil.from(LocalDate.of(2020, 9, 1));
        // create some lessons & save to db
        saveAndFlush(
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 12, 45)), Subject.BIOLOGY, "2", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 13, 45)), Subject.BIOLOGY, "2", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 2, 10, 45)), Subject.BIOLOGY, "2", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 8, 31, 15, 45)), Subject.BIOLOGY, "2", "test")
        );

        // when - create Hql to find all lessons which are planned on given day
        List<Lesson> lessons = getSession()
            .createQuery("from Lesson l where l.date between :start and :end", Lesson.class)
            .setParameter("start", date)
            .setParameter("end", date.plusDays(1))
            .getResultList();

        // then - check if all lessons are planned on given day
        assertFalse(lessons.isEmpty());
        assertEquals(2, lessons.size());
        assertTrue(lessons.stream().allMatch(lesson -> lesson.isPlannedOn(date.toLocalDate())));
    }

    @Test
    public void testHqlFindStationaryLessonsBookedAtTime() {
        // given - example date time
        ZonedDateTime dateTime = DateUtil.from(LocalDateTime.of(2020, 9, 1, 12, 45));
        String roomNumber = "2A";
        // create some lessons
        saveAndFlush(
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 12, 45)), Subject.BIOLOGY, "2A", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 13, 45)), Subject.BIOLOGY, "2", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 2, 10, 45)), Subject.BIOLOGY, "2", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 8, 31, 15, 45)), Subject.BIOLOGY, "2", "test")
        );

        // when - create hql to find all stationary lessons at given time and with room number
        List<StationaryLesson> lessons = getSession()
            .createQuery("from StationaryLesson l where l.date = :date and l.roomNumber = :roomNumber", StationaryLesson.class)
            .setParameter("date", dateTime)
            .setParameter("roomNumber", roomNumber)
            .getResultList();

        // then - verify
        assertFalse(lessons.isEmpty());
        assertEquals(1, lessons.size());
        assertTrue(lessons.stream()
            .allMatch(lesson -> lesson.isPlannedAtTime(dateTime) && lesson.getRoomNumber().equals(roomNumber)));
    }

    @Test
    public void testHqlCountOnlineLessonsBySubjectInYear() {
        // given - example year
        var year = 2020;
        // add 2 lessons with Math subject and 1 lesson with Biology in given year. Add 1 lesson in a previous year.
        saveAndFlush(
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 12, 45)), Subject.MATH, "2A", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 13, 45)), Subject.MATH, "2", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 2, 10, 45)), Subject.BIOLOGY, "2", "test"),
            new StationaryLesson(DateUtil.from(LocalDateTime.of(2019, 8, 31, 15, 45)), Subject.BIOLOGY, "2", "test")
        );

        // when - create hql which counts online lessons by subject in given year
        List<Object[]> lessons = getSession()
            .createQuery("select count(l), l.subject from Lesson l where year(l.date) = :year group by l.subject")
            .setParameter("year", year)
            .getResultList();


        // then - verify
        assertEquals(2, lessons.size());

        Object[] firstRow = lessons.get(0);
        assertEquals(2L, firstRow[0]);
        assertEquals(Subject.MATH, firstRow[1]);

        Object[] secondRow = lessons.get(1);
        assertEquals(1L, secondRow[0]);
        assertEquals(Subject.BIOLOGY, secondRow[1]);
    }

    @Test
    public void testHqlFindAllStudentLessons() {
        // given - create student and lessons. Save to db.
        Student student = new Student("Jan", "Nowak", DateUtil.from(LocalDate.of(1990, 1, 1)), Gender.MALE);
        final var firstLesson = new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 12, 45)), Subject.MATH, "2A",
            "test");
        final var secondLesson = new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 1, 13, 45)), Subject.MATH, "2",
            "test");
        final var thirdLesson = new StationaryLesson(DateUtil.from(LocalDateTime.of(2020, 9, 2, 10, 45)), Subject.BIOLOGY, "2",
            "test");

        firstLesson.addParticipant(student);
        secondLesson.addParticipant(student);

        saveAndFlush(firstLesson, secondLesson, thirdLesson);

        // when - find all student lessons
        List<Lesson> lessons = getSession()
            .createQuery("from Lesson l where :student member of l.participants", Lesson.class)
            .setParameter("student", student)
            .getResultList();

        // then - verify
        assertFalse(lessons.isEmpty());
        assertEquals(2, lessons.size());
        assertTrue(lessons.stream().allMatch(lesson -> lesson.getParticipants().contains(student)));
    }
}
