package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public final class _03_ExamTest extends BaseEntityTest {

    @Test
    public void testCreateExam() {
        // given - create Exam
        Exam exam = null;

        // when - save exam to db

        // then - read exam from db & verify
        Exam readExam = null;
        assertNotNull(readExam);
        assertEquals(readExam, exam);
    }

    @Test
    public void testAddRateToExam() {
        // given - create an exam
        Exam exam = null;

        // when - add one Rate with A and add one Rate with D to exam. Save to db.

        // then - get exam from db & verify
        Exam readExam = null;
        assertNotNull(readExam);
        assertEquals(readExam, exam);
        assertEquals(2, readExam.getRates().size());
        assertEquals(Rate.Value.A, readExam.getRates().get(0).getValue());
        assertEquals(Rate.Value.D, readExam.getRates().get(1).getValue());
    }

    @Test
    public void testCalculateAvgRateValueForExam() {
        // given - create exam and add rates with values: A,B,B,D. Save to db.

        // when - calculate avg rate for exam
        Rate.Value value = null;

        // then - expect rate value equal to B
        assertEquals(Rate.Value.B, value);
    }

    @Test
    public void testHqlFindAllExamsForGivenStudentLastname() {
        // given - example student lastname
        String lastname = "Kowalski";
        // create exam, students and add rates

        // when - create hql to get all exams for given student lastname
        List<Exam> exams = new ArrayList<>();

        // then - verify all exams have student with given lastname
        assertFalse(exams.isEmpty());
        assertTrue(exams.stream()
            .allMatch(exam -> exam.getRates()
                .stream()
                .allMatch(rate -> rate.getStudent().getLastname().equals(lastname))));
    }
}
