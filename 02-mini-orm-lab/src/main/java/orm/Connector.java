package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connector {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static Connection connection;
    public static void createConnection(String database, String user, String password) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);
        Connection connection = DriverManager.getConnection(DB_URL + database, props);
    }
    private Connection getConnection() {
        return connection;
    }
}
