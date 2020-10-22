package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/";

    private static Connection connection;

    public static void createConnection(String dbName, String user, String password) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        connection = DriverManager.getConnection(DB_URL + dbName, props);
    }

    public static Connection getConnection() {
        return connection;
    }
}
