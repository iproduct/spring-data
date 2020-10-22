

import entity.User;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/fsd";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Connection connection = getConnection();
        EntityManager<User> entityManager = new EntityManager<>(connection);

        System.out.println("Connected to database.");
        User user = new User();
//        user.setId(6);
        user.setUsername("999999");
        user.setPassword("!!!!!!!!!!");
        user.setAge(42);
        user.setRegistrationDate(LocalDate.of(2000,11,1));

        entityManager.persist(user);
        System.out.println(entityManager.findFirst(User.class, "username like ?", "%8"));
        System.out.println(entityManager.findById(User.class, 2));

        System.out.printf("Number of deleted records: %d%n", entityManager.delete(User.class, 2));

        System.out.println("--------------------------------\nUsers above 40:");
        System.out.println(listToString(entityManager.find(User.class, "age > ? ", 40)));

        System.out.println("--------------------------------\nAll Users:");
        System.out.println(listToString(entityManager.find(User.class, "")));
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_STRING, "root", "root");
    }

    private static <T> String listToString(List<T> list) {
        return list.stream()
            .map(T::toString).collect(Collectors.joining("\n"));
    }
}
