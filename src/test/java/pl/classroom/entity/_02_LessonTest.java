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

public final class _02_LessonTest extends BaseEntityTest {

    @Test
    public void testCreateOnlineLesson() {
        // given
        OnlineLesson lesson = new OnlineLesson(Subject.BIOLOGY,
                DateUtil.from(LocalDate.of(2020, 10, 10)), "first_lesson_url", "firstCode");

        // when - save lesson
        Serializable id = saveAndFlush(lesson);

        // then - read from db & verify
        OnlineLesson readLesson = getSession().get(OnlineLesson.class, id);
        assertNotNull(readLesson);
        assertEquals(readLesson, lesson);
    }

    @Test
    public void testCreateStationaryLesson() {
        // given - create stationary lesson entity
        StationaryLesson lesson = new StationaryLesson(Subject.MATH,
                DateUtil.from(LocalDate.of(2020, 11, 12)), "101", "street 66");

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
        OnlineLesson lesson = new OnlineLesson(Subject.BIOLOGY,
                DateUtil.from(LocalDate.of(2020, 10, 10)), "first_lesson_url", "firstCode");

        Student student1 = new Student("Alex", "Baaaba",
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
        Student student2 = new Student("Jan", "Makaba",
                DateUtil.from(LocalDate.of(1980, 4, 1)), Student.Gender.FEMALE);

        // when - add 2 students to lesson and save to db
        lesson.addParticipant(student1);
        lesson.addParticipant(student2);

        Serializable id = saveAndFlush(lesson);

        // then - read lesson from db & verify
        Lesson readLesson = getSession().get(OnlineLesson.class, id);
        assertNotNull(lesson);
        assertEquals(2, readLesson.getParticipants().size());
    }

    @Test
    public void testHqlFindAllLessonsPlannedOnDate() {
        // given - example day
        ZonedDateTime date = DateUtil.from(LocalDate.of(2020, 9, 1));

        // create some lessons & save to db
        OnlineLesson firstLesson = new OnlineLesson(Subject.MATH,
                ZonedDateTime.of(2020, 9, 1, 14, 40, 40, 0, ZoneId.systemDefault()),
                "some_url", "code233");

        OnlineLesson secondLesson = new OnlineLesson(Subject.MATH,
                ZonedDateTime.of(2020, 9, 2, 14, 40, 40, 0, ZoneId.systemDefault()),
                "other_url", "code999");

        saveAndFlush(firstLesson);
        saveAndFlush(secondLesson);

        // when - create Hql to find all lessons which are planned on given day
        List<Lesson> lessons = getSession().createQuery("SELECT l FROM Lesson l WHERE l.date < :date AND l.date >:date2 ", Lesson.class)
                .setParameter("date", date.plusDays(1l))
                .setParameter("date2", date).getResultList();

        // then - check if all lessons are planned on given day
        assertFalse(lessons.isEmpty());
        assertEquals(1, lessons.size());
        assertTrue(lessons.stream().allMatch(lesson -> lesson.isPlannedOn(date)));

    }

    @Test
    public void testHqlFindStationaryLessonsBookedAtTime() {
        // given - example date time
        ZonedDateTime dateTime = ZonedDateTime.of(2020, 9, 1, 12, 45,0,0, ZoneId.systemDefault());
        String roomNumber = "2A";
        // create some lessons

        StationaryLesson lesson1 = new StationaryLesson(Subject.MATH,
                DateTimeUtil.from(LocalDateTime.of(2020, 10, 15, 12, 0,0)), roomNumber, "street 88");

        StationaryLesson lesson2 = new StationaryLesson(Subject.MATH,
                dateTime, roomNumber, "street 88");

        saveAndFlush(lesson1);
        saveAndFlush(lesson2);


        // when - create hql to fina all stationary lessons at given time and with room number
        List<StationaryLesson> lessons = getSession().createQuery("SELECT l from Lesson l WHERE l.date = :date AND l.roomNumber = :room")
                .setParameter("date", dateTime)
                .setParameter("room", roomNumber)
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
        ZonedDateTime yearStart = ZonedDateTime.of(year, 1, 1, 0, 0,0,0, ZoneId.systemDefault());


        // add 2 lessons with Math subject and 1 lesson with Biology in given year. Add 1 lesson in a previous year.
        OnlineLesson firstMathLesson = new OnlineLesson(Subject.MATH,
                ZonedDateTime.of(2020, 9, 1, 14, 40, 40, 0, ZoneId.systemDefault()),
                "some_url", "code233");

        OnlineLesson secondMathLesson = new OnlineLesson(Subject.MATH,
                ZonedDateTime.of(2020, 9, 1, 14, 40, 40, 0, ZoneId.systemDefault()),
                "some_url", "code233");

        OnlineLesson biologyLesson = new OnlineLesson(Subject.BIOLOGY,
                ZonedDateTime.of(2020, 9, 1, 14, 40, 40, 0, ZoneId.systemDefault()),
                "some_url", "code233");

        saveAndFlush(firstMathLesson);
        saveAndFlush(secondMathLesson);
        saveAndFlush(biologyLesson);

        // when - create hql which counts online lessons by subject in given year
        List<Object[]> lessons = getSession().createQuery("SELECT l.subject, count(l) FROM Lesson l " +
                        "WHERE year(l.date) = :year GROUP BY l.subject")
                .setParameter("year", year)
                .getResultList();


        // then - verify
        assertEquals(2, lessons.size());

        Object[] firstRow = lessons.get(0);
        assertEquals(2l, firstRow[1]);
        assertEquals(Subject.MATH, firstRow[0]);

        Object[] secondRow = lessons.get(1);
        assertEquals(1l, secondRow[1]);
        assertEquals(Subject.BIOLOGY, secondRow[0]);
    }

    @Test
    public void testHqlFindAllStudentLessons() {
        // given - create student and lessons. Save to db.
        Student student = new Student("John", "Dog",
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);

        OnlineLesson lesson1 = new OnlineLesson(Subject.MATH,
                ZonedDateTime.of(2020, 9, 1, 14, 40, 40, 0, ZoneId.systemDefault()),
                "some_url", "code233");

        StationaryLesson lesson2 = new StationaryLesson(Subject.MATH,
                ZonedDateTime.of(2020, 9, 1, 14, 40, 40, 0, ZoneId.systemDefault()),
                "room234", "defbwhjc wkejfbw");

        OnlineLesson lesson3 = new OnlineLesson(Subject.BIOLOGY,
                ZonedDateTime.of(2020, 9, 1, 14, 40, 40, 0, ZoneId.systemDefault()),
                "some_url", "code233");

        lesson1.addParticipant(student);
        lesson2.addParticipant(student);

        saveAndFlush(lesson1);
        saveAndFlush(lesson2);
        saveAndFlush(lesson3);


        // when - find all student lessons
//        List<Lesson> lessons = getSession().createQuery("SELECT l FROM Lesson l WHERE :student MEMBER OF participants", Lesson.class)
//                .setParameter("student", student)
//                .getResultList();

        List<Lesson> lessons = getSession().createQuery("SELECT l FROM Lesson l INNER JOIN l.participants p where p.firstName =:student", Lesson.class)
                .setParameter("student", student.getFirstName())
                .getResultList();

        // then - verify
        assertFalse(lessons.isEmpty());
        assertTrue(lessons.stream().allMatch(lesson -> lesson.getParticipants().contains(student)));
        assertEquals(2l, lessons.size());
    }
}
