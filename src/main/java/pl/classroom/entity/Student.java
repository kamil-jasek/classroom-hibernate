package pl.classroom.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "students")
@SQLDelete(sql = "UPDATE students SET deleted = true, deleteTime = NOW() WHERE id = ?")
@Where(clause = "deleted <> true")
public final class Student extends Auditable {

    public enum Gender {
        MALE, FEMALE;
    }

    @Column(length = 40)
    private String firstName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 40)
    private String lastName;
    private ZonedDateTime dateOfBirth;
    private Gender gender;

    @OnlyForHibernate
    protected Student() {
    }

    public Student(String firstName, String lastName, ZonedDateTime dateOfBirth, Gender gender) {
        super(ZonedDateTime.now(), "HIBERNATE");
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public String getLastname() {
        // TODO - fix
        return lastName;
    }

    public String getFullName() {
        // TODO - fix
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) &&
                Objects.equals(dateOfBirth, student.dateOfBirth) &&
                gender == student.gender;
    }



    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, dateOfBirth, gender);
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


}
