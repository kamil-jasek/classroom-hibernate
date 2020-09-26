package pl.classroom.entity;

import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "rates")
@SQLDelete(sql = "UPDATE rates SET deleted = true, deleteTime = NOW() WHERE id = ?")
@Where(clause = "deleted <> true")
public final class Rate extends Auditable {

    public enum Value {
        A, B, C, D, E;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.ORDINAL)
    private Value value;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    public Rate() {
    }

    public Rate(Value value, Student student) {
        super(ZonedDateTime.now(), "HIBERNATE");
        this.value = value;
        this.student = student;
    }

    public Value getValue() {
        // TODO - fix
        return value;
    }

    public Student getStudent() {
        // TODO - fix
        return student;
    }
}
