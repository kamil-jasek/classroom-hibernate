package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pl.classroom.entity.Student.Gender;
import pl.classroom.util.DateUtil;

public final class _01_StudentTest extends BaseEntityTest {

    @Test
    public void testCreateStudent() {
        // given - create student entity
        Student student = new Student("Alex", "Baaaba",
                DateUtil.from(LocalDate.of(1990, 10, 1)),Gender.MALE);

        // when - save student entity
        Serializable id = saveAndFlush(student);

        // then - read student entity & verify
        Student readStudent = getSession().get(Student.class, id);

        assertNotNull(readStudent);
        assertEquals(readStudent, student);
    }

    @Test
    public void testHqlFindByName() {
        // given - example name:
        var name = "Jan";
        // create some students
        Student student1 = new Student("Alex", "Baaaba",
                DateUtil.from(LocalDate.of(1990, 10, 1)),Gender.MALE);
        Student student2 = new Student("Jan", "Makaba",
                DateUtil.from(LocalDate.of(1980, 4, 1)),Gender.FEMALE);

        saveAndFlush(student1);
        saveAndFlush(student2);

        // when - create hql to find students with given "name" (query should check first and lastname)
        List<Student> results = getSession().createQuery("FROM Student WHERE upper (firstName) " +
                "LIKE :name OR upper (lastName) " +
                "LIKE :name")
                .setParameter("name", name.toUpperCase())
                .getResultList();


        // then - check if results contains students with "name"
        assertFalse(results.isEmpty());
        assertTrue(results.stream().allMatch(student -> student
            .getFullName().toLowerCase().contains(name.toLowerCase())));
        assertEquals(1, results.size());
    }


    @Test
    public void testHqlCountStudentsByGender() {
        // given - example gender
        var gender = Gender.FEMALE;
        // create some students
        Student student1 = new Student("Alex", "Baaaba",
                DateUtil.from(LocalDate.of(1990, 10, 1)),Gender.MALE);
        Student student2 = new Student("Jan", "Makaba",
                DateUtil.from(LocalDate.of(1980, 4, 1)),Gender.FEMALE);

        saveAndFlush(student1);
        saveAndFlush(student2);

        // when - create hql which counts students by gender
        Long count = getSession().createQuery("select count (s) FROM Student s WHERE s.gender = :gender", Long.class)
                .setParameter("gender", gender)
                .getSingleResult();

        // then - check count
        assertTrue(count.compareTo(0l) > 0);
        assertEquals(Long.valueOf(1), count);
    }
}
