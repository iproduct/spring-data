package course.springdata.hibernateintro;

import course.springdata.hibernateintro.entity.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Optional;

public class HibernateIntroMain {
    public static void main(String[] args) {
        //Create Hibernate config
        Configuration cfg = new Configuration();
        cfg.configure();

        // Create SessionFactory
        SessionFactory sf = cfg.buildSessionFactory();

        // Create Session
        Session session = sf.openSession();

        // Persist an entity
        Student student = new Student("Hristo Georgiev");
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();

        // Read entity by Id
        session.beginTransaction();
        session.setHibernateFlushMode(FlushMode.MANUAL);
//        Student result = session.get(Student.class, 1L, LockMode.READ);
        long queryId = 10L;
//        Student result = session.byId(Student.class).load(queryId);
        Optional<Student> result = session.byId(Student.class).loadOptional(queryId);
        session.getTransaction().commit();
        if(result.isPresent()) {
            System.out.printf("Student: %s", result.get());
        } else {
            System.out.printf("Student with ID:%d does not exist.%n", queryId);
        }

        // Close Session
        session.close();
    }
}
