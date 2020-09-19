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
        Student student = new Student("Jan", "Kowalski", DateUtil.from(LocalDate.of(1990, 1, 1)), Gender.MALE);

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


        // when - create hql to find students with given "name" (query should check first and lastname)
        List<Student> results = new ArrayList<>();


        // then - check if results contains students with "name"
        assertFalse(results.isEmpty());
        assertTrue(results.stream().allMatch(student -> student
            .getFullName().toLowerCase().contains(name.toLowerCase())));
    }

    @Test
    public void testHqlCountStudentsByGender() {
        // given - example gender
        var gender = Gender.FEMALE;
        // create some students


        // when - create hql which counts students by gender
        BigInteger count = BigInteger.ZERO;

        // then - check count
        assertTrue(count.compareTo(BigInteger.ZERO) > 0);
    }
}
