package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pl.classroom.util.DateTimeUtil;
import pl.classroom.util.DateUtil;

public final class _03_ExamTest extends BaseEntityTest {

    @Test
    public void testCreateExam() {
        // given - create Exam
        Exam exam = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime
                .of(2020,10,10, 12, 45, 0)));

        // when - save exam to db
        Serializable id = saveAndFlush(exam);

        // then - read exam from db & verify
        Exam readExam = getSession().get(Exam.class, id);
        assertNotNull(readExam);
        assertEquals(readExam, exam);
    }

    @Test
    public void testAddRateToExam() {
        // given - create an exam
        Exam exam = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime
                .of(2020,10,10, 12, 45, 0)));

        Student student1 = new Student("Alex", "Baaaba",
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
        Student student2 = new Student("Jan", "Makaba",
                DateUtil.from(LocalDate.of(1980, 4, 1)), Student.Gender.FEMALE);

        Rate rate1 = new Rate(Rate.Value.A, student1);
        Rate rate2 = new Rate(Rate.Value.D, student2);

        // when - add one Rate with A and add one Rate with D to exam. Save to db.
        exam.addRate(rate1);
        exam.addRate(rate2);


        Serializable id = saveAndFlush(exam);

        // then - get exam from db & verify
        Exam readExam = getSession().get(Exam.class, id);
        assertNotNull(readExam);
        assertEquals(readExam, exam);
        assertEquals(2, readExam.getRates().size());
        assertEquals(Rate.Value.A, readExam.getRates().get(0).getValue());
        assertEquals(Rate.Value.D, readExam.getRates().get(1).getValue());
    }

    @Test
    public void testCalculateAvgRateValueForExam() {
        // given - create exam and add rates with values: A,B,B,D. Save to db.
        Exam exam = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime
                .of(2020,10,10, 12, 45, 0)));

        Student student1 = new Student("Alex", "Baaaba",
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
        Student student2 = new Student("Jan", "Makaba",
                DateUtil.from(LocalDate.of(1980, 4, 1)), Student.Gender.FEMALE);
        Student student3 = new Student("aaaa", "Makavvvvba",
                DateUtil.from(LocalDate.of(1980, 4, 1)), Student.Gender.FEMALE);
        Student student4 = new Student("Jbbban", "Makaba",
                DateUtil.from(LocalDate.of(1980, 4, 1)), Student.Gender.FEMALE);

        Rate rate1 = new Rate(Rate.Value.A, student1);
        Rate rate2 = new Rate(Rate.Value.B, student2);
        Rate rate3 = new Rate(Rate.Value.B, student3);
        Rate rate4 = new Rate(Rate.Value.D, student4);

        exam.addRate(rate1);
        exam.addRate(rate2);
        exam.addRate(rate3);
        exam.addRate(rate4);

        Serializable id = saveAndFlush(exam);

        // when - calculate avg rate for exam
        Exam readExam = getSession().get(Exam.class, id);
        

        Rate.Value value = readExam.getAverageRate();

        // then - expect rate value equal to B
        assertEquals(Rate.Value.B, value);
    }

    @Test
    public void testHqlFindAllExamsForGivenStudentLastname() {
        // given - example student lastname
        String lastName = "Kowalski";
        // create exam, students and add rates
        Exam exam1 = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime
                .of(2020,10,10, 12, 45, 0)));
        Exam exam2 = new Exam(Subject.MATH, DateTimeUtil.from(LocalDateTime
                .of(2020,10,11, 12, 45, 0)));
        Exam exam3 = new Exam(Subject.BIOLOGY, DateTimeUtil.from(LocalDateTime
                .of(2020,10,12, 12, 45, 0)));

        Student student1 = new Student("Alex", lastName,
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);
        Student student2 = new Student("Alex", "Baaaba",
                DateUtil.from(LocalDate.of(1990, 10, 1)), Student.Gender.MALE);

        Rate rate11 = new Rate(Rate.Value.A, student1);
        Rate rate12 = new Rate(Rate.Value.B, student1);
        Rate rate21 = new Rate(Rate.Value.C, student2);
        Rate rate22 = new Rate(Rate.Value.D, student2);
        Rate rate23 = new Rate(Rate.Value.C, student2);

        exam1.addRate(rate11);
//        exam1.addRate(rate21);

        exam2.addRate(rate12);
//        exam2.addRate(rate22);

        exam3.addRate(rate23);

        saveAndFlush(exam1);
        saveAndFlush(exam2);
        saveAndFlush(exam3);


        // when - create hql to get all exams for given student lastname
        List<Exam> exams = getSession().createQuery("SELECT e FROM Exam e INNER JOIN e.rates r " +
                "INNER JOIN r.student s WHERE s.lastName = :lastname ", Exam.class)
                .setParameter("lastname", lastName)
                .getResultList();

        // then - verify all exams have student with given lastname
        assertFalse(exams.isEmpty());
        assertEquals(2, exams.size());
        assertTrue(exams.stream()
            .allMatch(exam -> exam.getRates()
                .stream()
                .allMatch(rate -> rate.getStudent().getLastname().equals(lastName))));
    }
}
