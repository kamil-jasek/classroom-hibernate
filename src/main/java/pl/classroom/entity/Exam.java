package pl.classroom.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "exams")
@SQLDelete(sql = "UPDATE exams SET deleted = true, deleteTime = NOW() WHERE id = ?")
@Where(clause = "deleted <> true")
public final class Exam extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private Subject subject;
    private ZonedDateTime time;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rate> rates;

    private Exam() {
    }

    public Exam(Subject subject, ZonedDateTime time) {
        super(ZonedDateTime.now(), "HIBERNATE");
        this.subject = subject;
        this.time = time;
        this.rates = new ArrayList<>();
    }

    public void addRate(Rate rate) {
        rates.add(rate);
    }

    public List<Rate> getRates() {
        // TODO - fix
        return new ArrayList<>(rates);
    }

    public Rate.Value getAverageRate() {
        int sum = 0;
        Rate.Value[] valueArray = Rate.Value.values();
        for (Rate rate : rates) {
            sum += rate.getValue().ordinal();
        }
        sum = sum / rates.size();

        return valueArray[sum];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return id == exam.id &&
                subject == exam.subject &&
                time.equals(exam.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subject, time);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", subject=" + subject +
                ", time=" + time +
                ", rates=" + rates +
                '}';
    }
}
