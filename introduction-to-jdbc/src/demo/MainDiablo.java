package demo;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class MainDiablo {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner sc = new Scanner(System.in);
//
        Properties props = new Properties();
//        String appConfigPath = Main2.class.getClassLoader()
//                .getResource("db.properties").getPath();
//        props.load(new FileInputStream(appConfigPath));
        System.out.println("Enter  username (default root): ");
        String user = sc.nextLine().trim();
        user = user.length() > 0 ? user : "root";
        props.setProperty("user", user);

        System.out.println("Enter password (default root): ");
        String password = sc.nextLine().trim();
        password = password.length() > 0 ? password : "root";
        props.setProperty("password", password);

        // 1. Load jdbc driver (optional)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Driver loaded successfully.");

        // 2. Connect to DB
//        try (
        Connection connection =
                DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/diablo", props);

        System.out.println("Connected successfully.");

        // 3. Execute query
        PreparedStatement stmt =
                connection.prepareStatement(
            "SELECT u.id, first_name, last_name, COUNT(*) count FROM users u JOIN users_games ug ON u.id = ug.user_id WHERE u.user_name = ?");
        System.out.println("Enter username (Alex): ");
        String usename = sc.nextLine().trim();
        usename = usename.length() > 0 ? usename : "Alex";
//            String salaryStr = props.getProperty("salary", "20000");

        stmt.setString(1, usename);
        ResultSet rs = stmt.executeQuery();

        // 4. Process results
        while (rs.next()) {
            System.out.printf("| %10d | %-15.15s | %-15.15s | %10d |%n",
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getLong("count")
            );
        }

        // 4. Process results
        if(rs.)
        while (rs.next()) {
            System.out.printf("| %10d | %-15.15s | %-15.15s | %10d |%n",
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getLong("count")
            );
        }

//        }

        // 5. Close connection and statement
        connection.close();
    }
}
