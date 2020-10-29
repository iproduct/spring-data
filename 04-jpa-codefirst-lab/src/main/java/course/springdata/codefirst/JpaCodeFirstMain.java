package course.springdata.codefirst;

import course.springdata.codefirst.entity.Car;
import course.springdata.codefirst.entity.Plane;
import course.springdata.codefirst.entity.PlateNumber;
import course.springdata.codefirst.entity.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class JpaCodeFirstMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("vehicles");
        EntityManager em = emf.createEntityManager();
        Car car1 = new Car("Audi A8", new BigDecimal(56000), "hybrid", 5);
        Truck truck1 = new Truck("Fuso Canter", new BigDecimal(120000), "gasoline", 5.5);
        Plane plain1 = new Plane("Boing", new BigDecimal(1200000), "gasoline", 120);
        em.getTransaction().begin();
        em.persist(car1);
        PlateNumber car1PlateNumber = new PlateNumber("CB1234", car1);
        car1.setPlate(car1PlateNumber);
        em.persist(car1PlateNumber);
        em.persist(truck1);
        em.persist(plain1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Car found = em.find(Car.class, 1L);
        System.out.printf("Found car1: %s%n", found);
        Truck truck = em.find(Truck.class, 2L);
        System.out.printf("Found truck1: %s%n", truck);
        Plane plane = em.find(Plane.class, 3L);
        System.out.printf("Found plane1: %s%n", plane);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.createQuery("SELECT v FROM Vehicle v")   // WHERE c.name LIKE :name ORDER BY s.name")
//                .setParameter("name", "%")
                .getResultList().forEach(System.out::println);
        em.getTransaction().commit();
    }

}
