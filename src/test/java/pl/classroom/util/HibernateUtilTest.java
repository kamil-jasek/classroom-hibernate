package pl.classroom.util;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.List;
import org.hibernate.query.NativeQuery;
import org.junit.Test;

public final class HibernateUtilTest {

    @Test
    public void testConnection() {
        // given:
        final var sessionFactory = HibernateUtil.getSessionFactory();
        final var session = sessionFactory.openSession();

        // when:
        final NativeQuery<Object> query = session.createSQLQuery("SELECT 1");
        List<Object> result = query.getResultList();

        // then:
        assertEquals(BigInteger.ONE, result.get(0));
    }
}
