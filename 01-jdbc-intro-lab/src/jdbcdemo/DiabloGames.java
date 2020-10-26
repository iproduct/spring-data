package jdbcdemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DiabloGames {
    public static void main(String[] args) {
        // 1. Read props from external property file
        Properties props = new Properties();
        String path = DiabloGames.class.getClassLoader()
                .getResource("jdbc.properties").getPath();
        System.out.printf("Reasource path: %s%n", path);

        try {
            props.load(new FileInputStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: add meaningful defaults
        System.out.println(props);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter username (<Enter> for 'Alex'): ");
        String username = sc.nextLine().trim();
        username = username.length() > 0 ? username : "Alex";

        // 2. try with resources - Connection, PreparedStatement
        try (Connection con = DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password"));
             PreparedStatement ps = con.prepareStatement(props.getProperty("sql.games"))) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            // 3. Print results
            while (rs.next()) {
                if (rs.getString("id") == null) {
                    System.out.printf("DB user with username '%s' not found.", username);
                } else {
                    System.out.printf("| %10d | %-15.15s | %-15.15s | %10d |%n",
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("count")
                    );
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
