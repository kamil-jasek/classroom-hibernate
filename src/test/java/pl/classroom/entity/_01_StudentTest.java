package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import pl.classroom.entity.Student.Gender;

public final class _01_StudentTest extends BaseEntityTest {

    @Test
    public void testCreateStudent() {
        // given - create student entity
        Student student = null;

        // when - save student entity


        // then - read student entity & verify
        Student readStudent = null;

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
