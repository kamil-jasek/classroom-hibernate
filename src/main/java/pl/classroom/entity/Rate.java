package pl.classroom.entity;

import javax.persistence.*;

@Entity
public final class Rate {

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
