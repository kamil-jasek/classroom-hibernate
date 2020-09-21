package pl.classroom.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import pl.classroom.entity.Lesson;
import pl.classroom.entity.OnlineLesson;
import pl.classroom.entity.StationaryLesson;
import pl.classroom.entity.Student;

public final class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        final var configuration = new Configuration();
        try {
            configuration.setProperties(loadAppProperties());
            addAnnotatedClasses(configuration);

            final var registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

            sessionFactory = configuration.buildSessionFactory(registry);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot load application properties", e);
        }
    }

    private static void addAnnotatedClasses(Configuration configuration) {
        configuration.addAnnotatedClass(Student.class);
        configuration.addAnnotatedClass(Lesson.class);
        configuration.addAnnotatedClass(StationaryLesson.class);
        configuration.addAnnotatedClass(OnlineLesson.class);
    }

    private static Properties loadAppProperties() throws IOException {
        var properties = new Properties();
        properties.load(requireNonNull(HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties")));
        return properties;
    }

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
