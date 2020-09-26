package pl.classroom.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractDao {

    private final SessionFactory sessionFactory;
    protected Session session;
    private Transaction tx;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void begin() {
        session = sessionFactory.openSession();
        tx = session.getTransaction();
        tx.begin();
    }

    public void commit() {
        tx.commit();
        session.close();
        session = null;
        tx = null;
    }

    public void rollback() {
        tx.rollback();
        session.close();
        session = null;
        tx = null;
    }

    public void flushAndClear() {
        session.flush();
        session.clear();
    }
}
