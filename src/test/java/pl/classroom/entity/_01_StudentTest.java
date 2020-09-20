package pl.classroom.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.time.LocalDate;
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
        saveAndFlush(
            new Student("Jan", "Kowalski", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.MALE),
            new Student("Adam", "Kowalski", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.MALE),
            new Student("Wojtek", "Jan", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.MALE),
            new Student("jan", "Kwiatek", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.MALE));

        // when - create hql to find students with given "name" (query should check first and lastname)
        List<Student> results = getSession()
            .createQuery("from Student s where upper(s.firstName) like :name or upper(s.lastname) like :name",
                Student.class)
            .setParameter("name", name)
            .getResultList();

        // then - check if results contains students with "name"
        assertFalse(results.isEmpty());
        assertTrue(results.stream().allMatch(student -> student
            .getFullName().toLowerCase().contains(name.toLowerCase())));
        assertEquals(3, results.size());
    }

    @Test
    public void testHqlCountStudentsByGender() {
        // given - example gender
        var gender = Gender.FEMALE;
        // create some students
        saveAndFlush(
            new Student("Jan", "Kowalski", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.FEMALE),
            new Student("Adam", "Kowalski", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.MALE),
            new Student("Wojtek", "Jan", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.FEMALE),
            new Student("jan", "Kwiatek", DateUtil.from(LocalDate.of(2000, 1, 1)), Gender.MALE));

        // when - create hql which counts students by gender
        var count = getSession()
            .createQuery("select count(s) from Student s where s.gender = :gender", Long.class)
            .setParameter("gender", gender)
            .getSingleResult();

        // then - check count
        assertEquals(2, count.intValue());
    }
}
