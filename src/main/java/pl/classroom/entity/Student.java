package pl.classroom.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public final class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastname;

    private ZonedDateTime dateOfBirth;

    private Gender gender;

    private Student() {}

    public enum Gender {
        MALE, FEMALE;
    }

    public Student(String firstName, String lastname, ZonedDateTime dateOfBirth, Gender gender) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public ZonedDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFullName() {
        return firstName + " " + lastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return id.equals(student.id) &&
            firstName.equals(student.firstName) &&
            lastname.equals(student.lastname) &&
            dateOfBirth.equals(student.dateOfBirth) &&
            gender == student.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastname, dateOfBirth, gender);
    }
}
