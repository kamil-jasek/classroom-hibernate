package pl.classroom.entity;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import org.hibernate.SessionFactory;
import pl.classroom.util.AbstractDao;

public final class ClassroomDao extends AbstractDao {

    public ClassroomDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public long save(Classroom classroom) {
        final var id = session.save(classroom);
        return (long) id;
    }

    public Classroom findById(long id) {
        return session.get(Classroom.class, id);
    }

    public List<Classroom> findAllClassroomsWithoutExams() {
        return session.createQuery("SELECT c FROM Classroom c WHERE c.exams IS EMPTY", Classroom.class)
            .getResultList();
    }

    public List<Classroom> findAllClassroomsWithLessonsPlannedOnDate(LocalDate date) {
        return null;
    }
}
