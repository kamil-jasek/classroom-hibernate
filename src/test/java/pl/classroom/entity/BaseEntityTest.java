package pl.classroom.entity;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import pl.classroom.util.HibernateUtil;

abstract class BaseEntityTest {

    private SessionFactory factory = HibernateUtil.getSessionFactory();
    private Session session;
    private Transaction tx;

    @Before
    public void before() {
        // prepare
        session = factory.openSession();
        tx = session.beginTransaction();
    }

    @After
    public void after() {
        tx.rollback();
        session.close();
    }

    protected Session getSession() {
        return session;
    }

    protected Serializable saveAndFlush(Object object) {
        final Serializable id = session.save(object);
        session.flush();
        session.clear();
        return id;
    }

    protected void saveAndFlush(Object... objects) {
        for (Object object : objects) {
            session.save(object);
        }
        session.flush();
        session.clear();
    }
}
