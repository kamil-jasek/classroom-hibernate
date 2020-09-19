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
import pl.classroom.util.DateUtil;

public final class _02_LessonTest extends BaseEntityTest {

    @Test
    public void testCreateOnlineLesson() {
        // given - create online lesson entity
        OnlineLesson lesson = null;

        // when - save lesson


        // then - read from db & verify
        OnlineLesson readLesson = null;
        assertNotNull(readLesson);
        assertEquals(readLesson, lesson);
    }

    @Test
    public void testCreateStationaryLesson() {
        // given - create online lesson entity
        StationaryLesson lesson = null;

        // when - save lesson


        // then - read from db & verify
        StationaryLesson readLesson = null;
        assertNotNull(readLesson);
        assertEquals(readLesson, lesson);
    }

    @Test
    public void testAddParticipantToLesson() {
        // given - create some lesson

        // when - add 2 students to lesson and save to db

        // then - read lesson from db & verify
        Lesson lesson = null;
        assertNotNull(lesson);
        assertEquals(2, lesson.getParticipants().size());
    }

    @Test
    public void testHqlFindAllLessonsPlannedOnDate() {
        // given - example day
        ZonedDateTime date = DateUtil.from(LocalDate.of(2020, 9, 1));
        // create some lessons & save to db

        // when - create Hql to find all lessons which are planned on given day
        List<Lesson> lessons = new ArrayList<>();

        // then - check if all lessons are planned on given day
        assertFalse(lessons.isEmpty());
        assertTrue(lessons.stream().allMatch(lesson -> lesson.isPlannedOn(date)));
    }

    @Test
    public void testHqlFindStationaryLessonsBookedAtTime() {
        // given - example date time
        ZonedDateTime dateTime = ZonedDateTime.of(2020, 9, 1, 12, 45, 0, 0, ZoneId.systemDefault());
        String roomNumber = "2A";
        // create some lessons


        // when - create hql to fina all stationary lessons at given time and with room number
        List<StationaryLesson> lessons = new ArrayList<>();


        // then - verify
        assertFalse(lessons.isEmpty());
        assertTrue(lessons.stream()
            .allMatch(lesson -> lesson.isPlannedAtTime(dateTime) && lesson.getRoomNumber().equals(roomNumber)));
    }

    @Test
    public void testHqlCountOnlineLessonsBySubjectInYear() {
        // given - example year
        var year = 2020;
        // add 2 lessons with Math subject and 1 lesson with Biology in given year. Add 1 lesson in a previous year.

        // when - create hql which counts online lessons by subject in given year
        List<Object[]> lessons = new ArrayList<>();


        // then - verify
        assertEquals(2, lessons.size());

        Object[] firstRow = lessons.get(0);
        assertEquals(2, firstRow[0]);
        assertEquals(Subject.MATH, firstRow[1]);

        Object[] secondRow = lessons.get(0);
        assertEquals(1, secondRow[0]);
        assertEquals(Subject.BIOLOGY, secondRow[1]);
    }

    @Test
    public void testHqlFindAllStudentLessons() {
        // given - create student and lessons. Save to db.
        Student student = null;

        // when - find all student lessons
        List<Lesson> lessons = new ArrayList<>();

        // then - verify
        assertFalse(lessons.isEmpty());
        assertTrue(lessons.stream().allMatch(lesson -> lesson.getParticipants().contains(student)));
    }
}
